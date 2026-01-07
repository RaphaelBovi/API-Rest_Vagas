package VagasSkills.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidaturas")
@Data
public class Candidatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id", nullable = false)
    private Curriculo curriculo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga;
    
    @Column(nullable = false)
    private LocalDateTime dataCandidatura;
    
    @PrePersist
    protected void onCreate() {
        dataCandidatura = LocalDateTime.now();
    }
}

