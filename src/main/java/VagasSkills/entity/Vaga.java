package VagasSkills.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vagas")
@Data
public class Vaga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titulo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
    
    @Column(nullable = false)
    private String localizacao; // "Cidade, Estado"
    
    private BigDecimal salario;
    
    @Column(nullable = false)
    private String modalidade; // Presencial, Remoto, Híbrido
    
    @Column(nullable = false)
    private String tipoContrato; // CLT, PJ, Estágio, Temporário
    
    @Column(nullable = false)
    private String nivelExperiencia; // Estagiário, Júnior, Pleno, Sênior, Especialista
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @ElementCollection
    @CollectionTable(name = "vaga_requisitos", joinColumns = @JoinColumn(name = "vaga_id"))
    @Column(name = "requisito")
    private List<String> requisitos = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "vaga_beneficios", joinColumns = @JoinColumn(name = "vaga_id"))
    @Column(name = "beneficio")
    private List<String> beneficios = new ArrayList<>();
    
    @Column(nullable = false)
    private LocalDateTime dataPublicacao;
    
    @Column(nullable = false)
    private Boolean destaque = false;
    
    private String url; // URL original da vaga
    
    @OneToMany(mappedBy = "vaga", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidatura> candidaturas = new ArrayList<>();
}

