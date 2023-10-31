package org.serratec.api.trabalhofinalgrupo1.model;

import java.util.Objects;
import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class RelacaoFK implements Serializable {
	
	private static final long serialVersionUID = -8095780082535069412L;
	
	@ManyToOne
	@JoinColumn(name = "seguido")
	private Usuario seguido; 
	
	@ManyToOne
	@JoinColumn(name = "seguidor")
	private Usuario seguidor;

	public Usuario getSeguido() {
		return seguido;
	}

	public void setSeguido(Usuario seguido) {
		this.seguido = seguido;
	}

	public Usuario getSeguidor() {
		return seguidor;
	}

	public void setSeguidor(Usuario seguidor) {
		this.seguidor = seguidor;
	}
	
	public RelacaoFK(Usuario seguido, Usuario seguidor) {
		this.seguido = seguido;
		this.seguidor = seguidor;
	}
	
	public RelacaoFK() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(seguido, seguidor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelacaoFK other = (RelacaoFK) obj;
		return Objects.equals(seguido, other.seguido) && Objects.equals(seguidor, other.seguidor);
	} 
}