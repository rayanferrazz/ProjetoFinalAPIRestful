package org.serratec.api.trabalhofinalgrupo1.service;

import java.util.List;
import java.util.Optional;

import org.serratec.api.trabalhofinalgrupo1.DTO.PostagemDTO;
import org.serratec.api.trabalhofinalgrupo1.DTO.PostagemInserirDTO;
import org.serratec.api.trabalhofinalgrupo1.exception.UsuarioException;
import org.serratec.api.trabalhofinalgrupo1.model.Postagem;
import org.serratec.api.trabalhofinalgrupo1.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostagemService {
	
	@Autowired
	PostagemRepository postagemRepository;
	
	public List<Postagem> findAll() {
		List<Postagem> postagens = postagemRepository.findAll();
		return postagens;
	}
	
	public Postagem findById(Long id) {
		Optional<Postagem> postagemOpt = postagemRepository.findById(id);
		if (postagemOpt.isEmpty()) {
			return null;
		}
		Postagem postagem = postagemOpt.get();
		return postagem;
	}
	
	@Transactional
	public PostagemDTO inserir(PostagemInserirDTO postagemInserirDTO){
		
		Postagem postagem = new Postagem();
		postagem.setConteudo(postagemInserirDTO.getConteudo());
		postagem.setUsuario(postagemInserirDTO.getUsuario());
		postagem.setDataCriacao(postagemInserirDTO.getDataCriacao());

		postagem = postagemRepository.save(postagem);

		PostagemDTO postagemDTO = new PostagemDTO(postagem);
		
		if(postagemDTO.getUsuario() == null) {
			 throw new UsuarioException("O id do usuário é obrigatório.");
		}
		return postagemDTO;
	}
	
	@Transactional
    public boolean deletar(Long id) {
        Optional<Postagem> postagemOpt = postagemRepository.findById(id);
        if (postagemOpt.isEmpty()) {
            throw new UsuarioException("Postagem não encontrada.");
        }
        postagemRepository.delete(postagemOpt.get());
        return true;
    }
	
	@Transactional
	public Postagem atualizar(Long id, Postagem postagem) {
        Optional<Postagem> postagemOpt = postagemRepository.findById(id);
        if (postagemOpt.isEmpty()) {
            return null;
        }

        Postagem postagemExistente = postagemOpt.get();
        
        postagemExistente.setConteudo(postagem.getConteudo());
        postagemExistente.setDataCriacao(postagem.getDataCriacao());
        postagemExistente.setUsuario(postagem.getUsuario());

        postagemRepository.save(postagemExistente);

        return postagemExistente;
    }
}