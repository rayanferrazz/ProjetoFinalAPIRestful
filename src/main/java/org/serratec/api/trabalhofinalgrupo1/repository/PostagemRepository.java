package org.serratec.api.trabalhofinalgrupo1.repository;

import org.serratec.api.trabalhofinalgrupo1.model.Postagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long>{
}