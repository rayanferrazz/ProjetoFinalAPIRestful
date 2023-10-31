package org.serratec.api.trabalhofinalgrupo1.DTO;

import java.util.Date;

import org.serratec.api.trabalhofinalgrupo1.model.Relacao;
import org.serratec.api.trabalhofinalgrupo1.model.Usuario;

public class RelacaoDTO {
	
	private Usuario seguido; 
	private Usuario seguidor;
	private Date dataInicioSeguimento;
	
	public RelacaoDTO(Relacao relacao) {
		this.seguido = relacao.getId().getSeguido();
		this.seguidor = relacao.getId().getSeguidor();
		this.dataInicioSeguimento = relacao.getDataInicioSeguimento();
	}
	
	public Usuario getSeguidor() {
		return seguidor;
	}
	public void setSeguidor(Usuario seguidor) {
		this.seguidor = seguidor;
	}
	public Usuario getSeguido() {
		return seguido;
	}
	public void setSeguido(Usuario seguido) {
		this.seguido = seguido;
	}
	public Date getDataInicioSeguimento() {
		return dataInicioSeguimento;
	}
	public void setDataInicioSeguimento(Date dataInicioSeguimento) {
		this.dataInicioSeguimento = dataInicioSeguimento;
	}
}