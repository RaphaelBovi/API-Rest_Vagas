package VagasSkills.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class VagaResponseDTO {
    
    private Long id;
    private String titulo;
    private EmpresaDTO empresa;
    private String localizacao;
    private BigDecimal salario;
    private String modalidade;
    private String tipoContrato;
    private String nivelExperiencia;
    private String descricao;
    private List<String> requisitos = new ArrayList<>();
    private List<String> beneficios = new ArrayList<>();
    private LocalDateTime dataPublicacao;
    private Boolean destaque;
    private String url;
    
    @Data
    public static class EmpresaDTO {
        private Long id;
        private String nome;
    }
}
