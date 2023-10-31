package org.serratec.api.trabalhofinalgrupo1.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.serratec.api.trabalhofinalgrupo1.DTO.UsuarioDTO;
import org.serratec.api.trabalhofinalgrupo1.DTO.UsuarioInserirDTO;
import org.serratec.api.trabalhofinalgrupo1.model.Usuario;
import org.serratec.api.trabalhofinalgrupo1.repository.UsuarioRepository;
import org.serratec.api.trabalhofinalgrupo1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UsuarioService usuarioService;
		
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@GetMapping
	public ResponseEntity<List<UsuarioDTO>> listar() {
		return ResponseEntity.ok(usuarioService.findAll());
	}
	
	//paginação - teria que listar o usuário dto ao invés do usuário para não exibir a  senha, como trocar?
    @GetMapping("/paginado") //ao invés de usar uma lista, será usada uma página no método listar
    public ResponseEntity<Page<Usuario>> listar(
            @PageableDefault(sort="nome", direction=Direction.ASC, page=0, size=5) Pageable pageable) { //pageable - objeto do próprio spring
        Page<Usuario> usuarios = usuarioRepository.findAll(pageable); //o findAll agora é passando o pageable e esse método do JpaRepository
        return ResponseEntity.ok(usuarios);
    }
    
    //JPQL - chama a query customizada que está no Repository para pesquisar usuário pelo nome
    @GetMapping("/nome")
    public ResponseEntity<Page<Usuario>> listarPorNome(
        @RequestParam(defaultValue = "") String paramNome, Pageable pageable) {
        Page<Usuario> usuarios = usuarioRepository.buscarPorNome(paramNome, pageable);
        return ResponseEntity.ok(usuarios);
    }
		
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
		UsuarioDTO usuarioDTO = usuarioService.findById(id);
		if (usuarioDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuarioDTO);
	}
		
	@PostMapping
	public ResponseEntity<UsuarioDTO> inserir(@Valid @RequestBody UsuarioInserirDTO usuarioInserirDTO) {
		UsuarioDTO usuarioDTO = usuarioService.inserir(usuarioInserirDTO);
			
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(usuarioDTO.getId())
				.toUri();
		return ResponseEntity.created(uri).body(usuarioDTO);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@Valid @PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) 
	{
        UsuarioDTO usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioDTO);

        if (usuarioAtualizado != null) {
            return ResponseEntity.ok(usuarioAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<UsuarioDTO> deletar(@PathVariable Long id) {
        boolean result = usuarioService.deletar(id);
        if (!result) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}