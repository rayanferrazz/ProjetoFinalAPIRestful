package org.serratec.api.trabalhofinalgrupo1.DTO;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.serratec.api.trabalhofinalgrupo1.model.Postagem;

public class PostagemDTO {
	
	private Long id;
	private String conteudo;
	@NotNull(message = "O id do usuario é obrigatório")
	private Long usuario;
	
	Date dataCriacao = new Date();
	
	public PostagemDTO(Postagem postagem) {
		this.conteudo = postagem.getConteudo();
		this.usuario = postagem.getUsuario().getId();
		this.dataCriacao = postagem.getDataCriacao();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}	
}