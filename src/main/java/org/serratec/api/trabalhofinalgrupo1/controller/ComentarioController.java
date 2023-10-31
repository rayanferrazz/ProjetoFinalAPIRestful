package org.serratec.api.trabalhofinalgrupo1.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.serratec.api.trabalhofinalgrupo1.DTO.ComentarioDTO;
import org.serratec.api.trabalhofinalgrupo1.DTO.ComentarioInserirDTO;
import org.serratec.api.trabalhofinalgrupo1.model.Comentario;
import org.serratec.api.trabalhofinalgrupo1.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {
	
	@Autowired
	ComentarioService comentarioService; //injeção de dependencia
		
	@GetMapping
	public ResponseEntity<List<Comentario>> listar() {
		return ResponseEntity.ok(comentarioService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Comentario> buscar(@PathVariable Long id) {
		Comentario comentario = comentarioService.findById(id);
		if (comentario == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(comentario);
	}
	
	@PostMapping
	public ResponseEntity<ComentarioDTO> inserir(@Valid @RequestBody ComentarioInserirDTO comentarioInserirDTO) {
		ComentarioDTO comentarioDTO = comentarioService.inserir(comentarioInserirDTO);
			
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(comentarioDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(comentarioDTO);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Comentario> atualizar(@Valid @PathVariable Long id, @RequestBody Comentario comentario) 
	{
        Comentario comentarioAtualizado = comentarioService.atualizar(id, comentario);

        if (comentarioAtualizado != null) {
            return ResponseEntity.ok(comentarioAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Comentario> deletar(@PathVariable Long id) {
        boolean result = comentarioService.deletar(id);
        if (!result) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}