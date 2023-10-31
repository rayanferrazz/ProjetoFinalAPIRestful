package org.serratec.api.trabalhofinalgrupo1.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.serratec.api.trabalhofinalgrupo1.DTO.RelacaoDTO;
import org.serratec.api.trabalhofinalgrupo1.DTO.RelacaoInserirDTO;
import org.serratec.api.trabalhofinalgrupo1.DTO.UsuarioDTO;
import org.serratec.api.trabalhofinalgrupo1.repository.UsuarioRepository;
import org.serratec.api.trabalhofinalgrupo1.service.RelacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/relacoes")
public class RelacaoController {

	@Autowired
	RelacaoService relacaoService;
		
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@GetMapping
	public ResponseEntity<List<RelacaoDTO>> listar() {
		return ResponseEntity.ok(relacaoService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<RelacaoDTO> buscar(@PathVariable Long id) {
		RelacaoDTO relacaoDTO = relacaoService.findById(id);
		if (relacaoDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(relacaoDTO);
	}
	
	@GetMapping("/buscarSeguidores/{id}")
	public ResponseEntity<List<RelacaoDTO>> buscarSeguidores(@PathVariable Long id) {
		List<RelacaoDTO> relacoes = relacaoService.findBySeguido(id);
		
		return ResponseEntity.ok(relacoes);
	}
	
	@GetMapping("buscarSeguindo/{id}")
	public ResponseEntity<List<RelacaoDTO>> buscarSeguindo(@PathVariable Long id) {
		List<RelacaoDTO> relacoes = relacaoService.findBySeguidor(id);
		
		return ResponseEntity.ok(relacoes);
	}

	@PostMapping
	public ResponseEntity<RelacaoDTO> inserir(@Valid @RequestBody RelacaoInserirDTO relacaoDTO) {
		RelacaoDTO relacaoDTO2 = relacaoService.inserir(relacaoDTO.getSeguidoId(), relacaoDTO.getSeguidorId());
			
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(relacaoDTO2)
				.toUri();
		return ResponseEntity.created(uri).body(relacaoDTO2);
	}

	@DeleteMapping("/{seguidoID}/{seguidorID}")
    public ResponseEntity<UsuarioDTO> deletar(@PathVariable Long seguidoID, @PathVariable Long seguidorID) {
        boolean result = relacaoService.deletar(seguidoID, seguidorID);
        if (!result) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }		
}	