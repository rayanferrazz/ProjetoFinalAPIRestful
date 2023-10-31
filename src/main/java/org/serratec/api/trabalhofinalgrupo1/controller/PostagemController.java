package org.serratec.api.trabalhofinalgrupo1.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.serratec.api.trabalhofinalgrupo1.DTO.PostagemDTO;
import org.serratec.api.trabalhofinalgrupo1.DTO.PostagemInserirDTO;
import org.serratec.api.trabalhofinalgrupo1.model.Postagem;
import org.serratec.api.trabalhofinalgrupo1.service.PostagemService;
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
@RequestMapping("/postagens")
public class PostagemController {
	
	@Autowired
	PostagemService postagemService;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> listar() {
		return ResponseEntity.ok(postagemService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> buscar(@PathVariable Long id) {
		Postagem postagem = postagemService.findById(id);
		if (postagem == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(postagem);
	}	
	
	@PostMapping
	public ResponseEntity<PostagemDTO> inserir(@Valid @RequestBody PostagemInserirDTO postagemInserirDTO) {
		PostagemDTO postagemDTO = postagemService.inserir(postagemInserirDTO);
			
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(postagemDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(postagemDTO);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<Postagem> atualizar(@Valid @PathVariable Long id, @RequestBody Postagem postagem) 
	{
        Postagem postagemAtualizada = postagemService.atualizar(id, postagem);

        if (postagemAtualizada != null) {
            return ResponseEntity.ok(postagemAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Postagem> deletar(@PathVariable Long id) {
        boolean result = postagemService.deletar(id);
        if (!result) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}