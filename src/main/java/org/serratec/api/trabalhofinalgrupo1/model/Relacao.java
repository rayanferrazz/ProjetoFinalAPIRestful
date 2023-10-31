package org.serratec.api.trabalhofinalgrupo1.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Relacao {
	
	@EmbeddedId
	@JsonBackReference
	private RelacaoFK id;
	
	@Column(name = "data_inicio_seguimento")
	private Date dataInicioSeguimento;

	public Relacao() {
	}
	
	public Relacao(Usuario seguidor, Usuario seguido, Date dataInicioSeguimento) {
		this.id.setSeguidor(seguidor);
		this.id.setSeguido(seguido);
		this.dataInicioSeguimento = dataInicioSeguimento;
	}

	public RelacaoFK getId() {
		return id;
	}

	public void setId(RelacaoFK id) {
		this.id = id;
	}

	public Date getDataInicioSeguimento() {
		return dataInicioSeguimento;
	}

	public void setDataInicioSeguimento(Date dataInicioSeguimento) {
		this.dataInicioSeguimento = dataInicioSeguimento;
	}
}