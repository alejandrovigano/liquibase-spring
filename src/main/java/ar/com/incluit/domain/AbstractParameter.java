package ar.com.incluit.domain;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import ar.com.incluit.domain.visitor.Visitable;

@MappedSuperclass
public abstract class AbstractParameter implements Visitable{

	private String codigo;
	private String descripcion;
	private Date fechaAlta;
	private String usuarioAlta;
	private Date fechaModificacion;
	private String usuarioModificacion;
	private Date fechaBaja;
	private String usuarioBaja;
	private Integer version;

	@Transient
	public abstract AbstractGrupo getAbstractGrupoTipo();
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
