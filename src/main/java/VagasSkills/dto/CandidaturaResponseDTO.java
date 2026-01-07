package VagasSkills.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CandidaturaResponseDTO {
    
    private Long id;
    private Long curriculoId;
    private Long vagaId;
    private LocalDateTime dataCandidatura;
    private String mensagem;
}

