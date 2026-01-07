package VagasSkills.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import VagasSkills.dto.CurriculoRequestDTO;
import VagasSkills.dto.CurriculoResponseDTO;
import VagasSkills.dto.VagaResponseDTO;
import VagasSkills.entity.Curriculo;
import VagasSkills.entity.CursoComplementar;
import VagasSkills.entity.Experiencia;
import VagasSkills.entity.Idioma;
import VagasSkills.entity.Skill;
import VagasSkills.exception.CurriculoNotFoundException;
import VagasSkills.exception.MaxCursosExcedidoException;
import VagasSkills.repository.CurriculoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurriculoService {
    
    private final CurriculoRepository curriculoRepository;
    private final AdzunaApiService adzunaApiService;
    private final VagaService vagaService;
    
    @Transactional
    public CurriculoResponseDTO criarCurriculo(CurriculoRequestDTO request) {
        // Validar máximo de 15 cursos complementares
        if (request.getCursosComplementares() != null && request.getCursosComplementares().size() > 15) {
            throw new MaxCursosExcedidoException(request.getCursosComplementares().size(), 15);
        }
        
        // Validar nome da universidade se nível superior
        if (request.getNivelEscolaridade() != null && 
            (request.getNivelEscolaridade().contains("Superior") || request.getNivelEscolaridade().contains("superior")) &&
            (request.getNomeUniversidade() == null || request.getNomeUniversidade().trim().isEmpty())) {
            throw new IllegalArgumentException("Nome da universidade é obrigatório para nível superior");
        }
        
        Curriculo curriculo = new Curriculo();
        curriculo.setNome(request.getNome());
        curriculo.setResidencia(request.getResidencia());
        curriculo.setDataNascimento(request.getDataNascimento());
        curriculo.setNivelEscolaridade(request.getNivelEscolaridade());
        curriculo.setNomeUniversidade(request.getNomeUniversidade());
        curriculo.setCargoDesejado(request.getCargoDesejado());
        curriculo.setPretensaoSalarial(request.getPretensaoSalarial());
        curriculo.setDisponibilidadeMudanca(request.getDisponibilidadeMudanca() != null ? request.getDisponibilidadeMudanca() : false);
        curriculo.setDisponibilidadeViagem(request.getDisponibilidadeViagem() != null ? request.getDisponibilidadeViagem() : false);
        
        // Mapear cursos complementares
        if (request.getCursosComplementares() != null) {
            List<CursoComplementar> cursos = request.getCursosComplementares().stream()
                .map(dto -> {
                    CursoComplementar curso = new CursoComplementar();
                    curso.setNome(dto.getNome());
                    curso.setInstituicao(dto.getInstituicao());
                    curso.setCargaHoraria(dto.getCargaHoraria());
                    curso.setCurriculo(curriculo);
                    return curso;
                })
                .collect(Collectors.toList());
            curriculo.setCursosComplementares(cursos);
        }
        
        // Mapear idiomas
        if (request.getIdiomas() != null) {
            List<Idioma> idiomas = request.getIdiomas().stream()
                .map(dto -> {
                    Idioma idioma = new Idioma();
                    idioma.setNome(dto.getNome());
                    idioma.setNivel(dto.getNivel());
                    idioma.setCurriculo(curriculo);
                    return idioma;
                })
                .collect(Collectors.toList());
            curriculo.setIdiomas(idiomas);
        }
        
        // Mapear skills
        if (request.getSkills() != null) {
            List<Skill> skills = request.getSkills().stream()
                .map(dto -> {
                    Skill skill = new Skill();
                    skill.setNome(dto.getNome());
                    skill.setNivel(dto.getNivel());
                    skill.setCurriculo(curriculo);
                    return skill;
                })
                .collect(Collectors.toList());
            curriculo.setSkills(skills);
        }
        
        // Mapear experiências
        if (request.getExperiencias() != null) {
            List<Experiencia> experiencias = request.getExperiencias().stream()
                .map(dto -> {
                    Experiencia experiencia = new Experiencia();
                    experiencia.setCargo(dto.getCargo());
                    experiencia.setEmpresa(dto.getEmpresa());
                    experiencia.setDataInicio(dto.getDataInicio());
                    experiencia.setDataFim(dto.getDataFim());
                    experiencia.setAtualmente(dto.getAtualmente() != null ? dto.getAtualmente() : false);
                    experiencia.setDescricao(dto.getDescricao());
                    experiencia.setCurriculo(curriculo);
                    return experiencia;
                })
                .collect(Collectors.toList());
            curriculo.setExperiencias(experiencias);
        }
        
        Curriculo saved = curriculoRepository.save(curriculo);
        return toResponseDTO(saved);
    }
    
    public List<VagaResponseDTO> buscarVagasPorCurriculo(Long id) {
        // Usar sistema de recomendação interno baseado em skills e localização
        return vagaService.buscarVagasRecomendadas(id);
    }
    
    public CurriculoResponseDTO buscarPorId(Long id) {
        Curriculo curriculo = curriculoRepository.findById(id)
            .orElseThrow(() -> new CurriculoNotFoundException(id));
        return toResponseDTO(curriculo);
    }
    
    @Transactional
    public CurriculoResponseDTO atualizarCurriculo(Long id, CurriculoRequestDTO request) {
        // Validar máximo de 15 cursos complementares
        if (request.getCursosComplementares() != null && request.getCursosComplementares().size() > 15) {
            throw new MaxCursosExcedidoException(request.getCursosComplementares().size(), 15);
        }
        
        Curriculo curriculo = curriculoRepository.findById(id)
            .orElseThrow(() -> new CurriculoNotFoundException(id));
        
        curriculo.setNome(request.getNome());
        curriculo.setResidencia(request.getResidencia());
        curriculo.setDataNascimento(request.getDataNascimento());
        curriculo.setNivelEscolaridade(request.getNivelEscolaridade());
        curriculo.setNomeUniversidade(request.getNomeUniversidade());
        curriculo.setCargoDesejado(request.getCargoDesejado());
        curriculo.setPretensaoSalarial(request.getPretensaoSalarial());
        curriculo.setDisponibilidadeMudanca(request.getDisponibilidadeMudanca() != null ? request.getDisponibilidadeMudanca() : false);
        curriculo.setDisponibilidadeViagem(request.getDisponibilidadeViagem() != null ? request.getDisponibilidadeViagem() : false);
        
        // Limpar e atualizar experiências
        curriculo.getExperiencias().clear();
        if (request.getExperiencias() != null) {
            request.getExperiencias().forEach(dto -> {
                Experiencia experiencia = new Experiencia();
                experiencia.setCargo(dto.getCargo());
                experiencia.setEmpresa(dto.getEmpresa());
                experiencia.setDataInicio(dto.getDataInicio());
                experiencia.setDataFim(dto.getDataFim());
                experiencia.setAtualmente(dto.getAtualmente() != null ? dto.getAtualmente() : false);
                experiencia.setDescricao(dto.getDescricao());
                experiencia.setCurriculo(curriculo);
                curriculo.getExperiencias().add(experiencia);
            });
        }
        
        // Limpar e atualizar cursos complementares
        curriculo.getCursosComplementares().clear();
        if (request.getCursosComplementares() != null) {
            request.getCursosComplementares().forEach(dto -> {
                CursoComplementar curso = new CursoComplementar();
                curso.setNome(dto.getNome());
                curso.setInstituicao(dto.getInstituicao());
                curso.setCargaHoraria(dto.getCargaHoraria());
                curso.setCurriculo(curriculo);
                curriculo.getCursosComplementares().add(curso);
            });
        }
        
        // Limpar e atualizar idiomas
        curriculo.getIdiomas().clear();
        if (request.getIdiomas() != null) {
            request.getIdiomas().forEach(dto -> {
                Idioma idioma = new Idioma();
                idioma.setNome(dto.getNome());
                idioma.setNivel(dto.getNivel());
                idioma.setCurriculo(curriculo);
                curriculo.getIdiomas().add(idioma);
            });
        }
        
        // Limpar e atualizar skills
        curriculo.getSkills().clear();
        if (request.getSkills() != null) {
            request.getSkills().forEach(dto -> {
                Skill skill = new Skill();
                skill.setNome(dto.getNome());
                skill.setNivel(dto.getNivel());
                skill.setCurriculo(curriculo);
                curriculo.getSkills().add(skill);
            });
        }
        
        Curriculo saved = curriculoRepository.save(curriculo);
        return toResponseDTO(saved);
    }
    
    @Transactional
    public void deletarCurriculo(Long id) {
        if (!curriculoRepository.existsById(id)) {
            throw new CurriculoNotFoundException(id);
        }
        curriculoRepository.deleteById(id);
    }
    
    private CurriculoResponseDTO toResponseDTO(Curriculo curriculo) {
        CurriculoResponseDTO dto = new CurriculoResponseDTO();
        dto.setId(curriculo.getId());
        dto.setNome(curriculo.getNome());
        dto.setResidencia(curriculo.getResidencia());
        dto.setDataNascimento(curriculo.getDataNascimento());
        dto.setNivelEscolaridade(curriculo.getNivelEscolaridade());
        dto.setNomeUniversidade(curriculo.getNomeUniversidade());
        dto.setCargoDesejado(curriculo.getCargoDesejado());
        dto.setPretensaoSalarial(curriculo.getPretensaoSalarial());
        dto.setDisponibilidadeMudanca(curriculo.getDisponibilidadeMudanca());
        dto.setDisponibilidadeViagem(curriculo.getDisponibilidadeViagem());
        
        // Mapear experiências
        if (curriculo.getExperiencias() != null) {
            dto.setExperiencias(curriculo.getExperiencias().stream()
                .map(exp -> {
                    CurriculoResponseDTO.ExperienciaDTO expDTO = new CurriculoResponseDTO.ExperienciaDTO();
                    expDTO.setId(exp.getId());
                    expDTO.setCargo(exp.getCargo());
                    expDTO.setEmpresa(exp.getEmpresa());
                    expDTO.setDataInicio(exp.getDataInicio());
                    expDTO.setDataFim(exp.getDataFim());
                    expDTO.setAtualmente(exp.getAtualmente());
                    expDTO.setDescricao(exp.getDescricao());
                    return expDTO;
                })
                .collect(Collectors.toList()));
        }
        
        // Mapear cursos complementares
        if (curriculo.getCursosComplementares() != null) {
            dto.setCursosComplementares(curriculo.getCursosComplementares().stream()
                .map(curso -> {
                    CurriculoResponseDTO.CursoComplementarDTO cursoDTO = new CurriculoResponseDTO.CursoComplementarDTO();
                    cursoDTO.setId(curso.getId());
                    cursoDTO.setNome(curso.getNome());
                    cursoDTO.setInstituicao(curso.getInstituicao());
                    cursoDTO.setCargaHoraria(curso.getCargaHoraria());
                    return cursoDTO;
                })
                .collect(Collectors.toList()));
        }
        
        // Mapear idiomas
        if (curriculo.getIdiomas() != null) {
            dto.setIdiomas(curriculo.getIdiomas().stream()
                .map(idioma -> {
                    CurriculoResponseDTO.IdiomaDTO idiomaDTO = new CurriculoResponseDTO.IdiomaDTO();
                    idiomaDTO.setId(idioma.getId());
                    idiomaDTO.setNome(idioma.getNome());
                    idiomaDTO.setNivel(idioma.getNivel());
                    return idiomaDTO;
                })
                .collect(Collectors.toList()));
        }
        
        // Mapear skills
        if (curriculo.getSkills() != null) {
            dto.setSkills(curriculo.getSkills().stream()
                .map(skill -> {
                    CurriculoResponseDTO.SkillDTO skillDTO = new CurriculoResponseDTO.SkillDTO();
                    skillDTO.setId(skill.getId());
                    skillDTO.setNome(skill.getNome());
                    skillDTO.setNivel(skill.getNivel());
                    return skillDTO;
                })
                .collect(Collectors.toList()));
        }
        
        return dto;
    }
}
