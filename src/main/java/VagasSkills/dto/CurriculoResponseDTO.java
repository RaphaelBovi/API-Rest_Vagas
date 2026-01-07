package VagasSkills.dto;

import lombok.Data;
import java.math.BigDecimal;
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
    private String nomeUniversidade;
    private String cargoDesejado;
    private BigDecimal pretensaoSalarial;
    private Boolean disponibilidadeMudanca;
    private Boolean disponibilidadeViagem;
    private List<CursoComplementarDTO> cursosComplementares = new ArrayList<>();
    private List<IdiomaDTO> idiomas = new ArrayList<>();
    private List<SkillDTO> skills = new ArrayList<>();
    private List<ExperienciaDTO> experiencias = new ArrayList<>();
    
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
    
    @Data
    public static class ExperienciaDTO {
        private Long id;
        private String cargo;
        private String empresa;
        private LocalDate dataInicio;
        private LocalDate dataFim;
        private Boolean atualmente;
        private String descricao;
    }
}
