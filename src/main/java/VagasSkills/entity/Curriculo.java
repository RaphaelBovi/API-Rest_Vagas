package VagasSkills.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "curriculos")
@Data
public class Curriculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String residencia; // cidade/estado
    
    @Column(nullable = false)
    private LocalDate dataNascimento;
    
    @Column(nullable = false)
    private String nivelEscolaridade;
    
    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(max = 15)
    private List<CursoComplementar> cursosComplementares = new ArrayList<>();
    
    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Idioma> idiomas = new ArrayList<>();
    
    @OneToMany(mappedBy = "curriculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills = new ArrayList<>();
    
    // getters, setters, constructors
}