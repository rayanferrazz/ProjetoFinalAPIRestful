package org.serratec.api.trabalhofinalgrupo1.DTO;

import java.util.Date;

import org.serratec.api.trabalhofinalgrupo1.model.Postagem;

public class ComentarioInserirDTO {
	
	private String texto;
	private Postagem postagem; 
	private Date dataCriacao;
	
	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public Postagem getPostagem() {
		return postagem;
	}
	
	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}
	
	public Date getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
}