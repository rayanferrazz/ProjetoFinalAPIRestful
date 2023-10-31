package org.serratec.api.trabalhofinalgrupo1.DTO;

import java.util.Date;

import org.serratec.api.trabalhofinalgrupo1.model.Usuario;

public class PostagemInserirDTO {
	
	private String conteudo;
	private Usuario usuario; 
	private Date dataCriacao;
	
	public String getConteudo() {
		return conteudo;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Date getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
}