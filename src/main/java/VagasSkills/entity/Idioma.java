package VagasSkills.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "idiomas")
@Data
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String nivel; // Básico, Intermediário, Avançado, Fluente
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id", nullable = false)
    private Curriculo curriculo;
    
    // getters, setters, constructors
}