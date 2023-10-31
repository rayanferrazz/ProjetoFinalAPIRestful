package org.serratec.api.trabalhofinalgrupo1.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Postagem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_postagem")
	private Long id;
	
	@Column(length = 255)
	@NotBlank(message = "O Conteúdo é obrigatório")
	private String conteudo;
	
	@Column(name = "data_criacao")
	@NotNull(message = "A data de criação é obrigatória")
	private Date dataCriacao;
	
	@ManyToOne
	@JoinColumn (name = "usuario_id")
	
	@JsonBackReference
	private Usuario usuario;
	
	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
	private Set<Comentario> comentarios = new HashSet<>();	
	
	public Postagem() {
	}	
	
	public Postagem(Long id, String conteudo, Date dataCriacao, Usuario usuario) {
		this.id = id;
		this.conteudo = conteudo;
		this.dataCriacao = dataCriacao;
		this.usuario = usuario;
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

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(conteudo, dataCriacao, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Postagem other = (Postagem) obj;
		return Objects.equals(conteudo, other.conteudo) && Objects.equals(dataCriacao, other.dataCriacao)
				&& Objects.equals(id, other.id);
	}
}