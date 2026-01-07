package VagasSkills.repository;

import VagasSkills.entity.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    
    // Buscar por palavra-chave (título, descrição, empresa)
    @Query("SELECT v FROM Vaga v WHERE " +
           "LOWER(v.titulo) LIKE LOWER(CONCAT('%', :palavraChave, '%')) OR " +
           "LOWER(v.descricao) LIKE LOWER(CONCAT('%', :palavraChave, '%')) OR " +
           "LOWER(v.empresa.nome) LIKE LOWER(CONCAT('%', :palavraChave, '%'))")
    List<Vaga> buscarPorPalavraChave(@Param("palavraChave") String palavraChave);
    
    // Buscar por região
    @Query("SELECT v FROM Vaga v WHERE " +
           "(:cidade IS NULL OR LOWER(v.localizacao) LIKE LOWER(CONCAT('%', :cidade, '%'))) AND " +
           "(:estado IS NULL OR LOWER(v.localizacao) LIKE LOWER(CONCAT('%', :estado, '%')))")
    List<Vaga> buscarPorRegiao(@Param("cidade") String cidade, @Param("estado") String estado);
    
    // Buscar vagas em destaque
    List<Vaga> findByDestaqueTrueOrderByDataPublicacaoDesc();
    
    // Buscar vagas recentes
    List<Vaga> findTop10ByOrderByDataPublicacaoDesc();
    
    // Buscar com filtros combinados
    @Query("SELECT v FROM Vaga v WHERE " +
           "(:palavraChave IS NULL OR " +
           "  LOWER(v.titulo) LIKE LOWER(CONCAT('%', :palavraChave, '%')) OR " +
           "  LOWER(v.descricao) LIKE LOWER(CONCAT('%', :palavraChave, '%')) OR " +
           "  LOWER(v.empresa.nome) LIKE LOWER(CONCAT('%', :palavraChave, '%'))) AND " +
           "(:cidade IS NULL OR LOWER(v.localizacao) LIKE LOWER(CONCAT('%', :cidade, '%'))) AND " +
           "(:estado IS NULL OR LOWER(v.localizacao) LIKE LOWER(CONCAT('%', :estado, '%'))) AND " +
           "(:tipoContrato IS NULL OR v.tipoContrato = :tipoContrato) AND " +
           "(:modalidade IS NULL OR v.modalidade = :modalidade) AND " +
           "(:nivelExperiencia IS NULL OR v.nivelExperiencia = :nivelExperiencia)")
    List<Vaga> buscarComFiltros(
        @Param("palavraChave") String palavraChave,
        @Param("cidade") String cidade,
        @Param("estado") String estado,
        @Param("tipoContrato") String tipoContrato,
        @Param("modalidade") String modalidade,
        @Param("nivelExperiencia") String nivelExperiencia
    );
}

