package VagasSkills.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CandidaturaRequestDTO {
    
    @NotNull(message = "ID do currículo é obrigatório")
    private Long curriculoId;
}

