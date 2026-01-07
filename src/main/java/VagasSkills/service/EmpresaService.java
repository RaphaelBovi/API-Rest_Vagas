package VagasSkills.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import VagasSkills.dto.EmpresaRequestDTO;
import VagasSkills.dto.EmpresaResponseDTO;
import VagasSkills.entity.Empresa;
import VagasSkills.exception.CurriculoNotFoundException;
import VagasSkills.repository.EmpresaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpresaService {
    
    private final EmpresaRepository empresaRepository;
    
    @Transactional
    public EmpresaResponseDTO criarEmpresa(EmpresaRequestDTO request) {
        Empresa empresa = new Empresa();
        empresa.setNome(request.getNome());
        empresa.setDescricao(request.getDescricao());
        empresa.setSite(request.getSite());
        
        Empresa saved = empresaRepository.save(empresa);
        return toResponseDTO(saved);
    }
    
    public EmpresaResponseDTO buscarPorId(Long id) {
        Empresa empresa = empresaRepository.findById(id)
            .orElseThrow(() -> new CurriculoNotFoundException("Empresa não encontrada com id: " + id));
        return toResponseDTO(empresa);
    }
    
    public List<EmpresaResponseDTO> listarTodas() {
        return empresaRepository.findAll().stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public EmpresaResponseDTO atualizarEmpresa(Long id, EmpresaRequestDTO request) {
        Empresa empresa = empresaRepository.findById(id)
            .orElseThrow(() -> new CurriculoNotFoundException("Empresa não encontrada com id: " + id));
        
        empresa.setNome(request.getNome());
        empresa.setDescricao(request.getDescricao());
        empresa.setSite(request.getSite());
        
        Empresa saved = empresaRepository.save(empresa);
        return toResponseDTO(saved);
    }
    
    @Transactional
    public void deletarEmpresa(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new CurriculoNotFoundException("Empresa não encontrada com id: " + id);
        }
        empresaRepository.deleteById(id);
    }
    
    private EmpresaResponseDTO toResponseDTO(Empresa empresa) {
        EmpresaResponseDTO dto = new EmpresaResponseDTO();
        dto.setId(empresa.getId());
        dto.setNome(empresa.getNome());
        dto.setDescricao(empresa.getDescricao());
        dto.setSite(empresa.getSite());
        return dto;
    }
}

