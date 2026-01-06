package VagasSkills.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CurriculoResponseDTO {
    
    private Long id;
    private String nome;
    private String residencia;
    private LocalDate dataNascimento;
    private String nivelEscolaridade;
    private List<CursoComplementarDTO> cursosComplementares = new ArrayList<>();
    private List<IdiomaDTO> idiomas = new ArrayList<>();
    private List<SkillDTO> skills = new ArrayList<>();
    
    @Data
    public static class CursoComplementarDTO {
        private Long id;
        private String nome;
        private String instituicao;
        private Integer cargaHoraria;
    }
    
    @Data
    public static class IdiomaDTO {
        private Long id;
        private String nome;
        private String nivel;
    }
    
    @Data
    public static class SkillDTO {
        private Long id;
        private String nome;
        private String nivel;
    }
}

