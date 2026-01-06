package VagasSkills.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import VagasSkills.dto.CurriculoRequestDTO;
import VagasSkills.dto.CurriculoResponseDTO;
import VagasSkills.dto.VagaResponseDTO;
import VagasSkills.entity.Curriculo;
import VagasSkills.entity.CursoComplementar;
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
    
    @Transactional
    public CurriculoResponseDTO criarCurriculo(CurriculoRequestDTO request) {
        // Validar máximo de 15 cursos complementares
        if (request.getCursosComplementares() != null && request.getCursosComplementares().size() > 15) {
            throw new MaxCursosExcedidoException(request.getCursosComplementares().size(), 15);
        }
        
        Curriculo curriculo = new Curriculo();
        curriculo.setNome(request.getNome());
        curriculo.setResidencia(request.getResidencia());
        curriculo.setDataNascimento(request.getDataNascimento());
        curriculo.setNivelEscolaridade(request.getNivelEscolaridade());
        
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
        
        Curriculo saved = curriculoRepository.save(curriculo);
        return toResponseDTO(saved);
    }
    
    public List<VagaResponseDTO> buscarVagasPorCurriculo(Long id) {
        Curriculo curriculo = curriculoRepository.findById(id)
            .orElseThrow(() -> new CurriculoNotFoundException(id));
        return adzunaApiService.buscarVagas(curriculo);
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
