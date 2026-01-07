package VagasSkills.repository;

import VagasSkills.entity.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
    Optional<Candidatura> findByCurriculoIdAndVagaId(Long curriculoId, Long vagaId);
    List<Candidatura> findByCurriculoId(Long curriculoId);
    List<Candidatura> findByVagaId(Long vagaId);
    boolean existsByCurriculoIdAndVagaId(Long curriculoId, Long vagaId);
}

