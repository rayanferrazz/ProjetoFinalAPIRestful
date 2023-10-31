package org.serratec.api.trabalhofinalgrupo1.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.serratec.api.trabalhofinalgrupo1.DTO.UsuarioDTO;
import org.serratec.api.trabalhofinalgrupo1.DTO.UsuarioInserirDTO;
import org.serratec.api.trabalhofinalgrupo1.exception.EmailException;
import org.serratec.api.trabalhofinalgrupo1.exception.SenhaException;
import org.serratec.api.trabalhofinalgrupo1.exception.UsuarioException;
import org.serratec.api.trabalhofinalgrupo1.model.Usuario;
import org.serratec.api.trabalhofinalgrupo1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<UsuarioDTO> findAll() {
		List<Usuario> usuarios = usuarioRepository.findAll();

		List<UsuarioDTO> usuariosDTO = usuarios.stream().map(usuario -> new UsuarioDTO(usuario))
				.collect(Collectors.toList());
		return usuariosDTO;
	}

	public UsuarioDTO findById(Long id) {
		Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
		if (usuarioOpt.isEmpty()) {
			return null;
		}
		UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioOpt.get());
		return usuarioDTO;
	}
	
	public UsuarioDTO atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return null;
        }

        Usuario usuarioExistente = usuarioOpt.get();

        usuarioExistente.setNome(usuarioDTO.getNome());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setSobrenome(usuarioDTO.getSobrenome());
        usuarioExistente.setDataNascimento(usuarioDTO.getDataNascimento());

        usuarioRepository.save(usuarioExistente);

        return new UsuarioDTO(usuarioExistente);
    }

	@Transactional
	public UsuarioDTO inserir(UsuarioInserirDTO usuarioInserirDTO) throws EmailException {
		if (!usuarioInserirDTO.getSenha().equalsIgnoreCase(usuarioInserirDTO.getConfirmaSenha())) {
			throw new SenhaException("Senha e Confirma Senha devem ser iguais");
		}

		Usuario usuarioEmailExistente = usuarioRepository.findByEmail(usuarioInserirDTO.getEmail());
		if (usuarioEmailExistente != null) {
			throw new EmailException("Email já cadastrado.");
		}

		Usuario usuario = new Usuario();
		usuario.setNome(usuarioInserirDTO.getNome());
		usuario.setSobrenome(usuarioInserirDTO.getSobrenome());
		usuario.setEmail(usuarioInserirDTO.getEmail());
		usuario.setSenha(usuarioInserirDTO.getSenha());
		usuario.setDataNascimento(usuarioInserirDTO.getDataNascimento());

		usuario = usuarioRepository.save(usuario);

		UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
		return usuarioDTO;
	}

	@Transactional
    public boolean deletar(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            throw new UsuarioException("Usuario não encontrado.");
        }
        usuarioRepository.delete(usuarioOpt.get());
        return true;
    }
}