package org.serratec.api.trabalhofinalgrupo1.service;

import java.util.List;
import java.util.Optional;

import org.serratec.api.trabalhofinalgrupo1.DTO.ComentarioDTO;
import org.serratec.api.trabalhofinalgrupo1.DTO.ComentarioInserirDTO;
import org.serratec.api.trabalhofinalgrupo1.exception.UsuarioException;
import org.serratec.api.trabalhofinalgrupo1.model.Comentario;
import org.serratec.api.trabalhofinalgrupo1.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ComentarioService {

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Transactional
	public List<Comentario> findAll() {
		List<Comentario> comentarios = comentarioRepository.findAll();
		return comentarios;
	}
	
	@Transactional
	public Comentario findById(Long id) {
		Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
		if (comentarioOpt.isEmpty()) {
			return null;
		}
		Comentario comentario = comentarioOpt.get();
		return comentario;
	}
	
	@Transactional
	public ComentarioDTO inserir(ComentarioInserirDTO comentarioInserirDTO) {
		
		Comentario comentario = new Comentario();
		comentario.setTexto(comentarioInserirDTO.getTexto());
		comentario.setPostagem(comentarioInserirDTO.getPostagem());
		comentario.setDataCriacao(comentarioInserirDTO.getDataCriacao());

		comentario = comentarioRepository.save(comentario);

		ComentarioDTO comentarioDTO = new ComentarioDTO(comentario);
		
		if(comentarioDTO.getPostagem() == null) {
			 throw new UsuarioException("O id da Postagem é obrigatório.");
		}
		return comentarioDTO;
	}
	
	@Transactional
    public boolean deletar(Long id) {
        Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
        if (comentarioOpt.isEmpty()) {
            throw new UsuarioException("Comentario não encontrado.");
        }
        comentarioRepository.delete(comentarioOpt.get());
        return true;
    }
	
	@Transactional
	public Comentario atualizar(Long id, Comentario comentario) {
        Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
        if (comentarioOpt.isEmpty()) {
            return null;
        }

        Comentario comentarioExistente = comentarioOpt.get();
        
        comentarioExistente.setTexto(comentario.getTexto());
        comentarioExistente.setDataCriacao(comentario.getDataCriacao());
        comentarioExistente.setPostagem(comentario.getPostagem());

        comentarioRepository.save(comentarioExistente);

        return comentarioExistente;
    }
}