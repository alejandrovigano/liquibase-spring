package ar.com.incluit.domain;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractGrupo {

	private String nombre;
	private Date fechaAlta;
	private String usuarioAlta;
	private Date fechaModificacion;
	private String usuarioModificacion;
	private Date fechaBaja;
	private String usuarioBaja;
	private String version;

	@Transient
	public abstract Integer getId();

	@Transient
	public abstract String getPrefix();

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}

	public Date getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(Date fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public String getUsuarioBaja() {
		return usuarioBaja;
	}

	public void setUsuarioBaja(String usuarioBaja) {
		this.usuarioBaja = usuarioBaja;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
