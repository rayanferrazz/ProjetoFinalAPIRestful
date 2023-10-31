package org.serratec.api.trabalhofinalgrupo1.DTO;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.serratec.api.trabalhofinalgrupo1.model.Comentario;

public class ComentarioDTO {

	private Long id;
	private String texto;
	@NotNull(message = "O id da postagem é obrigatório")
	private Long postagem;
	
	Date dataCriacao = new Date();
	
	public ComentarioDTO(Comentario comentario) {
		this.texto = comentario.getTexto();
		this.postagem = comentario.getPostagem().getId();
		this.dataCriacao = comentario.getDataCriacao();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Long getPostagem() {
		return postagem;
	}

	public void setPostagem(Long postagem) {
		this.postagem = postagem;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}	
}