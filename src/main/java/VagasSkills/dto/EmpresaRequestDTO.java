package VagasSkills.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmpresaRequestDTO {
    
    @NotBlank(message = "Nome da empresa é obrigatório")
    private String nome;
    
    private String descricao;
    
    private String site;
}

