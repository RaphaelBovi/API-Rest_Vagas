package VagasSkills.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import VagasSkills.dto.CandidaturaRequestDTO;
import VagasSkills.dto.CandidaturaResponseDTO;
import VagasSkills.dto.VagaRequestDTO;
import VagasSkills.dto.VagaResponseDTO;
import VagasSkills.entity.Candidatura;
import VagasSkills.entity.Curriculo;
import VagasSkills.entity.Empresa;
import VagasSkills.entity.Vaga;
import VagasSkills.exception.CurriculoNotFoundException;
import VagasSkills.repository.CandidaturaRepository;
import VagasSkills.repository.CurriculoRepository;
import VagasSkills.repository.EmpresaRepository;
import VagasSkills.repository.VagaRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VagaService {
    
    private final VagaRepository vagaRepository;
    private final EmpresaRepository empresaRepository;
    private final CurriculoRepository curriculoRepository;
    private final CandidaturaRepository candidaturaRepository;
    
    @Transactional
    public VagaResponseDTO criarVaga(VagaRequestDTO request) {
        Empresa empresa = empresaRepository.findById(request.getEmpresaId())
            .orElseThrow(() -> new CurriculoNotFoundException("Empresa não encontrada com id: " + request.getEmpresaId()));
        
        Vaga vaga = new Vaga();
        vaga.setTitulo(request.getTitulo());
        vaga.setEmpresa(empresa);
        vaga.setLocalizacao(request.getLocalizacao());
        vaga.setSalario(request.getSalario());
        vaga.setModalidade(request.getModalidade());
        vaga.setTipoContrato(request.getTipoContrato());
        vaga.setNivelExperiencia(request.getNivelExperiencia());
        vaga.setDescricao(request.getDescricao());
        vaga.setRequisitos(request.getRequisitos() != null ? request.getRequisitos() : new ArrayList<>());
        vaga.setBeneficios(request.getBeneficios() != null ? request.getBeneficios() : new ArrayList<>());
        vaga.setDestaque(request.getDestaque() != null ? request.getDestaque() : false);
        vaga.setUrl(request.getUrl());
        vaga.setDataPublicacao(LocalDateTime.now());
        
        Vaga saved = vagaRepository.save(vaga);
        return toResponseDTO(saved);
    }
    
    public VagaResponseDTO buscarPorId(Long id) {
        Vaga vaga = vagaRepository.findById(id)
            .orElseThrow(() -> new CurriculoNotFoundException("Vaga não encontrada com id: " + id));
        return toResponseDTO(vaga);
    }
    
    public List<VagaResponseDTO> listarTodas(
        String palavraChave,
        String cidade,
        String estado,
        String tipoContrato,
        String modalidade,
        String nivelExperiencia
    ) {
        List<Vaga> vagas = vagaRepository.buscarComFiltros(
            palavraChave, cidade, estado, tipoContrato, modalidade, nivelExperiencia
        );
        return vagas.stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    public List<VagaResponseDTO> buscarPorPalavraChave(String palavraChave) {
        List<Vaga> vagas = vagaRepository.buscarPorPalavraChave(palavraChave);
        return vagas.stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    public List<VagaResponseDTO> buscarPorRegiao(String cidade, String estado) {
        List<Vaga> vagas = vagaRepository.buscarPorRegiao(cidade, estado);
        return vagas.stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    public List<VagaResponseDTO> buscarDestaques() {
        List<Vaga> vagas = vagaRepository.findByDestaqueTrueOrderByDataPublicacaoDesc();
        return vagas.stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    public List<VagaResponseDTO> buscarRecentes(Integer limite) {
        int limit = limite != null && limite > 0 ? limite : 10;
        List<Vaga> vagas = vagaRepository.findAll().stream()
            .sorted(Comparator.comparing(Vaga::getDataPublicacao).reversed())
            .limit(limit)
            .collect(Collectors.toList());
        return vagas.stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    public List<VagaResponseDTO> buscarVagasRecomendadas(Long curriculoId) {
        Curriculo curriculo = curriculoRepository.findById(curriculoId)
            .orElseThrow(() -> new CurriculoNotFoundException(curriculoId));
        
        // Buscar todas as vagas
        List<Vaga> todasVagas = vagaRepository.findAll();
        
        // Extrair skills do currículo
        Set<String> skillsCurriculo = curriculo.getSkills().stream()
            .map(skill -> skill.getNome().toLowerCase())
            .collect(Collectors.toSet());
        
        // Extrair localização do currículo
        String[] residenciaParts = curriculo.getResidencia().split(",");
        String cidadeCurriculo = residenciaParts.length > 0 ? residenciaParts[0].trim().toLowerCase() : "";
        String estadoCurriculo = residenciaParts.length > 1 ? residenciaParts[1].trim().toLowerCase() : "";
        
        // Calcular compatibilidade para cada vaga
        List<VagaComCompatibilidade> vagasComCompatibilidade = todasVagas.stream()
            .map(vaga -> {
                int score = calcularCompatibilidade(vaga, skillsCurriculo, cidadeCurriculo, estadoCurriculo);
                return new VagaComCompatibilidade(vaga, score);
            })
            .filter(vcc -> vcc.score > 0) // Apenas vagas com alguma compatibilidade
            .sorted(Comparator.comparing((VagaComCompatibilidade vcc) -> vcc.score).reversed())
            .collect(Collectors.toList());
        
        return vagasComCompatibilidade.stream()
            .map(vcc -> toResponseDTO(vcc.vaga))
            .collect(Collectors.toList());
    }
    
    private int calcularCompatibilidade(Vaga vaga, Set<String> skillsCurriculo, String cidadeCurriculo, String estadoCurriculo) {
        int score = 0;
        
        // Comparar skills com requisitos
        if (vaga.getRequisitos() != null && !vaga.getRequisitos().isEmpty()) {
            long skillsMatch = vaga.getRequisitos().stream()
                .map(req -> req.toLowerCase())
                .filter(skillsCurriculo::contains)
                .count();
            score += (int) (skillsMatch * 10); // 10 pontos por skill compatível
        }
        
        // Comparar localização
        String localizacaoVaga = vaga.getLocalizacao().toLowerCase();
        if (vaga.getModalidade().equalsIgnoreCase("Remoto")) {
            score += 5; // Bônus para vagas remotas
        } else if (cidadeCurriculo != null && !cidadeCurriculo.isEmpty() && localizacaoVaga.contains(cidadeCurriculo)) {
            score += 15; // Bônus alto para mesma cidade
        } else if (estadoCurriculo != null && !estadoCurriculo.isEmpty() && localizacaoVaga.contains(estadoCurriculo)) {
            score += 10; // Bônus médio para mesmo estado
        }
        
        return score;
    }
    
    @Transactional
    public CandidaturaResponseDTO candidatar(Long vagaId, CandidaturaRequestDTO request) {
        Vaga vaga = vagaRepository.findById(vagaId)
            .orElseThrow(() -> new CurriculoNotFoundException("Vaga não encontrada com id: " + vagaId));
        
        Curriculo curriculo = curriculoRepository.findById(request.getCurriculoId())
            .orElseThrow(() -> new CurriculoNotFoundException("Currículo não encontrado com id: " + request.getCurriculoId()));
        
        // Verificar se já existe candidatura
        if (candidaturaRepository.existsByCurriculoIdAndVagaId(request.getCurriculoId(), vagaId)) {
            throw new IllegalArgumentException("Você já se candidatou a esta vaga");
        }
        
        Candidatura candidatura = new Candidatura();
        candidatura.setCurriculo(curriculo);
        candidatura.setVaga(vaga);
        
        Candidatura saved = candidaturaRepository.save(candidatura);
        
        CandidaturaResponseDTO response = new CandidaturaResponseDTO();
        response.setId(saved.getId());
        response.setCurriculoId(saved.getCurriculo().getId());
        response.setVagaId(saved.getVaga().getId());
        response.setDataCandidatura(saved.getDataCandidatura());
        response.setMensagem("Candidatura realizada com sucesso!");
        
        return response;
    }
    
    @Transactional
    public VagaResponseDTO atualizarVaga(Long id, VagaRequestDTO request) {
        Vaga vaga = vagaRepository.findById(id)
            .orElseThrow(() -> new CurriculoNotFoundException("Vaga não encontrada com id: " + id));
        
        Empresa empresa = empresaRepository.findById(request.getEmpresaId())
            .orElseThrow(() -> new CurriculoNotFoundException("Empresa não encontrada com id: " + request.getEmpresaId()));
        
        vaga.setTitulo(request.getTitulo());
        vaga.setEmpresa(empresa);
        vaga.setLocalizacao(request.getLocalizacao());
        vaga.setSalario(request.getSalario());
        vaga.setModalidade(request.getModalidade());
        vaga.setTipoContrato(request.getTipoContrato());
        vaga.setNivelExperiencia(request.getNivelExperiencia());
        vaga.setDescricao(request.getDescricao());
        vaga.setRequisitos(request.getRequisitos() != null ? request.getRequisitos() : new ArrayList<>());
        vaga.setBeneficios(request.getBeneficios() != null ? request.getBeneficios() : new ArrayList<>());
        vaga.setDestaque(request.getDestaque() != null ? request.getDestaque() : false);
        vaga.setUrl(request.getUrl());
        
        Vaga saved = vagaRepository.save(vaga);
        return toResponseDTO(saved);
    }
    
    @Transactional
    public void deletarVaga(Long id) {
        if (!vagaRepository.existsById(id)) {
            throw new CurriculoNotFoundException("Vaga não encontrada com id: " + id);
        }
        vagaRepository.deleteById(id);
    }
    
    private VagaResponseDTO toResponseDTO(Vaga vaga) {
        VagaResponseDTO dto = new VagaResponseDTO();
        dto.setId(vaga.getId());
        dto.setTitulo(vaga.getTitulo());
        
        VagaResponseDTO.EmpresaDTO empresaDTO = new VagaResponseDTO.EmpresaDTO();
        empresaDTO.setId(vaga.getEmpresa().getId());
        empresaDTO.setNome(vaga.getEmpresa().getNome());
        dto.setEmpresa(empresaDTO);
        
        dto.setLocalizacao(vaga.getLocalizacao());
        dto.setSalario(vaga.getSalario());
        dto.setModalidade(vaga.getModalidade());
        dto.setTipoContrato(vaga.getTipoContrato());
        dto.setNivelExperiencia(vaga.getNivelExperiencia());
        dto.setDescricao(vaga.getDescricao());
        dto.setRequisitos(vaga.getRequisitos() != null ? vaga.getRequisitos() : new ArrayList<>());
        dto.setBeneficios(vaga.getBeneficios() != null ? vaga.getBeneficios() : new ArrayList<>());
        dto.setDataPublicacao(vaga.getDataPublicacao());
        dto.setDestaque(vaga.getDestaque());
        dto.setUrl(vaga.getUrl());
        
        return dto;
    }
    
    private static class VagaComCompatibilidade {
        Vaga vaga;
        int score;
        
        VagaComCompatibilidade(Vaga vaga, int score) {
            this.vaga = vaga;
            this.score = score;
        }
    }
}

