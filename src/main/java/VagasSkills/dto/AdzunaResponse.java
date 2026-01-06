package VagasSkills.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
public class AdzunaResponse {
    
    @JsonProperty("results")
    private List<VagaResponseDTO> results;
    
    @JsonProperty("count")
    private Integer count;
}

