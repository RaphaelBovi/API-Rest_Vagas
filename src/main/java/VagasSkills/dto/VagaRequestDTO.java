package VagasSkills.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class VagaRequestDTO {
    
    @NotBlank(message = "Título é obrigatório")
    private String titulo;
    
    @NotNull(message = "ID da empresa é obrigatório")
    private Long empresaId;
    
    @NotBlank(message = "Localização é obrigatória")
    private String localizacao;
    
    private BigDecimal salario;
    
    @NotBlank(message = "Modalidade é obrigatória")
    private String modalidade; // Presencial, Remoto, Híbrido
    
    @NotBlank(message = "Tipo de contrato é obrigatório")
    private String tipoContrato; // CLT, PJ, Estágio, Temporário
    
    @NotBlank(message = "Nível de experiência é obrigatório")
    private String nivelExperiencia; // Estagiário, Júnior, Pleno, Sênior, Especialista
    
    private String descricao;
    
    private List<String> requisitos = new ArrayList<>();
    
    private List<String> beneficios = new ArrayList<>();
    
    private Boolean destaque = false;
    
    private String url;
}

