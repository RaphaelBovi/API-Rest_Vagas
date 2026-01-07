package VagasSkills.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CurriculoRequestDTO {
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "Residência é obrigatória")
    private String residencia;
    
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;
    
    @NotBlank(message = "Nível de escolaridade é obrigatório")
    private String nivelEscolaridade;
    
    private String nomeUniversidade; // Obrigatório se nível superior (validação no service)
    
    private String cargoDesejado;
    
    private BigDecimal pretensaoSalarial;
    
    private Boolean disponibilidadeMudanca = false;
    
    private Boolean disponibilidadeViagem = false;
    
    @Size(max = 15, message = "Máximo de 15 cursos complementares")
    @Valid
    private List<CursoComplementarDTO> cursosComplementares = new ArrayList<>();
    
    @Valid
    private List<IdiomaDTO> idiomas = new ArrayList<>();
    
    @NotEmpty(message = "Pelo menos uma skill é obrigatória")
    @Valid
    private List<SkillDTO> skills = new ArrayList<>();
    
    @Valid
    private List<ExperienciaDTO> experiencias = new ArrayList<>();
    
    @Data
    public static class CursoComplementarDTO {
        @NotBlank(message = "Nome do curso é obrigatório")
        private String nome;
        private String instituicao;
        private Integer cargaHoraria;
    }
    
    @Data
    public static class IdiomaDTO {
        @NotBlank(message = "Nome do idioma é obrigatório")
        private String nome;
        
        @NotBlank(message = "Nível do idioma é obrigatório")
        private String nivel;
    }
    
    @Data
    public static class SkillDTO {
        @NotBlank(message = "Nome da skill é obrigatório")
        private String nome;
        
        @NotBlank(message = "Nível da skill é obrigatório")
        private String nivel;
    }
    
    @Data
    public static class ExperienciaDTO {
        @NotBlank(message = "Cargo é obrigatório")
        private String cargo;
        
        @NotBlank(message = "Empresa é obrigatória")
        private String empresa;
        
        @NotNull(message = "Data de início é obrigatória")
        private LocalDate dataInicio;
        
        private LocalDate dataFim;
        
        private Boolean atualmente = false;
        
        private String descricao;
    }
}
