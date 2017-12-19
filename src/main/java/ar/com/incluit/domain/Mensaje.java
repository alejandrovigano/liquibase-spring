package ar.com.incluit.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ar.com.incluit.domain.visitor.Visitor;

@Entity
@Table(name = "mensaje")
@AttributeOverride(name = "codigo", column = @Column(name = "codigo_mensaje"))
public class Mensaje extends AbstractParameter {

	@Id
	private Integer idMensaje;
	private String evento;
	private String mensaje;
	private String formato;
	private String motivoRechazo;
	private Boolean flagReintentoTx;

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public Boolean getFlagReintentoTx() {
		return flagReintentoTx;
	}

	public void setFlagReintentoTx(Boolean flagReintentoTx) {
		this.flagReintentoTx = flagReintentoTx;
	}

	public Integer getIdMensaje() {
		return idMensaje;
	}

	public void setIdMensaje(Integer idMensaje) {
		this.idMensaje = idMensaje;
	}

	@Override
	public AbstractGrupo getAbstractGrupoTipo() {
		return new NoGrupo("mensaje");
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
