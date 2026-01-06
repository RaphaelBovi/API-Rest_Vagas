package VagasSkills.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cursos_complementares")
@Data
public class CursoComplementar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    private String instituicao;
    private Integer cargaHoraria;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculo_id", nullable = false)
    private Curriculo curriculo;
    
    // getters, setters, constructors
}