package VagasSkills.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VagaResponseDTO {
    
    @JsonProperty("title")
    private String titulo;
    
    @JsonProperty("description")
    private String descricao;
    
    @JsonProperty("company")
    private String empresa;
    
    @JsonProperty("location")
    private String localizacao;
    
    @JsonProperty("salary_min")
    private Double salarioMinimo;
    
    @JsonProperty("salary_max")
    private Double salarioMaximo;
    
    @JsonProperty("created")
    private String dataCriacao;
    
    @JsonProperty("redirect_url")
    private String url;
}


