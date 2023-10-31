package org.serratec.api.trabalhofinalgrupo1.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Comentario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_comentario")
	private Long id;
	
	@Column(length = 255)
	@NotBlank(message = "O texto é obrigatório")
	private String texto;
	
	@Column(name = "data_criacao")
	@NotNull(message = "A data de criação é obrigatória")
	private Date dataCriacao;
	
	@ManyToOne
	@JoinColumn (name = "id_postagem")
	private Postagem postagem;
	
	public Comentario() {
	}	

	public Comentario(Long id, String texto, Date dataCriacao, Postagem postagem) {
		this.id = id;
		this.texto = texto;
		this.dataCriacao = dataCriacao;
		this.postagem = postagem;
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

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Postagem getPostagem() {
		return postagem;
	}

	public void setPostagem(Postagem postagem) {
		this.postagem = postagem;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataCriacao, id, postagem, texto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comentario other = (Comentario) obj;
		return Objects.equals(dataCriacao, other.dataCriacao) && Objects.equals(id, other.id)
				&& Objects.equals(postagem, other.postagem) && Objects.equals(texto, other.texto);
	}
}