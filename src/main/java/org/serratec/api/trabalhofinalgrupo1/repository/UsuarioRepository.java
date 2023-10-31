package org.serratec.api.trabalhofinalgrupo1.repository;

import org.serratec.api.trabalhofinalgrupo1.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Usuario findByEmail(String email);

	//JPQL - querys customizadas (usam a propriedade do próprio objeto para fazer as consultas, e não as nativas do bd)
    @Query("SELECT u FROM Usuario u WHERE UPPER(u.nome) LIKE UPPER(CONCAT('%', :paramNome, '%'))") //UPPER - bota tudo paraletra maipuscula para fazer a comparação
    Page<Usuario> buscarPorNome(String paramNome, Pageable pageable); // a query será chamada pelo controller
}