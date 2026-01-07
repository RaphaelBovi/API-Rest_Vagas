package VagasSkills.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "experiencias")
@Data
public class Experiencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String cargo;
    
    @Column(nullable = false)
    private String empresa;
    
    @Column(nullable = false)
    private LocalDate dataInicio;
    
    private LocalDate dataFim;
    
    @Column(nullable = false)
    private Boolean atualmente = false;
    
    @Column(columnDefinition = "TEXT")
    private String descricao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id", nullable = false)
    private Curriculo curriculo;
}

