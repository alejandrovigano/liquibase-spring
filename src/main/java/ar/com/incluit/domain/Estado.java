package ar.com.incluit.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "estadox")
public class Estado extends AbstractParameter {

	@Id
	private Integer idEstado;
	@ManyToOne
	@JoinColumn(name = "id_grupo_estado")
	private GrupoEstado grupoEstado;

	public Integer getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	public GrupoEstado getGrupoEstado() {
		return grupoEstado;
	}

	public void setGrupoEstado(GrupoEstado grupoEstado) {
		this.grupoEstado = grupoEstado;
	}

	@Override
	public AbstractGrupo getAbstractGrupoTipo() {
		return getGrupoEstado();
	}

}
