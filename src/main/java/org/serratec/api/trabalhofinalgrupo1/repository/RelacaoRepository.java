package org.serratec.api.trabalhofinalgrupo1.repository;

import java.util.List;

import org.serratec.api.trabalhofinalgrupo1.model.Relacao;
import org.serratec.api.trabalhofinalgrupo1.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RelacaoRepository extends JpaRepository<Relacao, Long> {
	//JPQL - querys customizadas (usam a propriedade do próprio objeto para fazer as consultas, e não as nativas do bd)
    @Query("SELECT u FROM Usuario u WHERE UPPER(u.nome) LIKE UPPER(CONCAT('%', :paramNome, '%'))") //UPPER - bota tudo paraletra maipuscula para fazer a comparação
    Page<Usuario> buscarPorNome(String paramNome, Pageable pageable); // a query será chamada pelo controller
    
    @Modifying
    @Query(value="DELETE FROM Relacao r WHERE r.seguido = :seguidoId and r.seguidor = :seguidorId", nativeQuery = true)
    void deleteBySeguidoAndSeguidor(Long seguidoId, Long seguidorId);
    
    @Query(value="SELECT * FROM Relacao r WHERE r.seguido = :seguidoId", nativeQuery = true)
    List<Relacao> findBySeguido(Long seguidoId);
    
    @Query(value="SELECT * FROM Relacao r WHERE r.seguidor = :seguidorId", nativeQuery = true)
    List<Relacao> findBySeguidor(Long seguidorId);
}