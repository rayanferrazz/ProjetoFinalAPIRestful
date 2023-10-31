package org.serratec.api.trabalhofinalgrupo1.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.api.trabalhofinalgrupo1.DTO.RelacaoDTO;
import org.serratec.api.trabalhofinalgrupo1.model.Relacao;
import org.serratec.api.trabalhofinalgrupo1.model.RelacaoFK;
import org.serratec.api.trabalhofinalgrupo1.model.Usuario;
import org.serratec.api.trabalhofinalgrupo1.repository.RelacaoRepository;
import org.serratec.api.trabalhofinalgrupo1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RelacaoService {
	
	@Autowired
	private RelacaoRepository relacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public List<RelacaoDTO> findAll() {
		List<Relacao> relacoes = relacaoRepository.findAll();

		List<RelacaoDTO> relacaoDTOs = relacoes.stream().map(relacao -> new RelacaoDTO(relacao))
				.collect(Collectors.toList());
		return relacaoDTOs;
	}
	
	public RelacaoDTO findById(Long id) {
		Optional<Relacao> usuarioOpt = relacaoRepository.findById(id);
		if (usuarioOpt.isEmpty()) {
			return null;
		}
		RelacaoDTO relacaoDTO = new RelacaoDTO(usuarioOpt.get());
		return relacaoDTO;
	}
	
	public List<RelacaoDTO> findBySeguido(Long id) {
		List<Relacao> relacoes = relacaoRepository.findBySeguido(id);
		List<RelacaoDTO> relacaoDTOs = relacoes.stream().map(relacao -> new RelacaoDTO(relacao))
				.collect(Collectors.toList());
		return relacaoDTOs;
	}
	
	public List<RelacaoDTO> findBySeguidor(Long id) {
		List<Relacao> relacoes = relacaoRepository.findBySeguidor(id);
		List<RelacaoDTO> relacaoDTOs = relacoes.stream().map(relacao -> new RelacaoDTO(relacao))
				.collect(Collectors.toList());
		return relacaoDTOs;
	}
	
	@Transactional
	public RelacaoDTO inserir(Long seguidoId, Long seguidorId) {
		Optional<Usuario> seguido = usuarioRepository.findById(seguidoId);
		Optional<Usuario> seguidor = usuarioRepository.findById(seguidorId);
		
		//validar se já existe
		
		Relacao relacao = new Relacao();
		relacao.setDataInicioSeguimento(new Date());
		RelacaoFK relacaoFK = new RelacaoFK();
		relacaoFK.setSeguido(seguido.get());
		relacaoFK.setSeguidor(seguidor.get());
		relacao.setId(relacaoFK);
		

		relacao = relacaoRepository.save(relacao);
		return new RelacaoDTO(relacao);
	}
	
	@Transactional
	public boolean deletar(Long seguidoId, Long seguidorId) {
		relacaoRepository.deleteBySeguidoAndSeguidor(seguidoId, seguidorId);
		return true;
	}
}