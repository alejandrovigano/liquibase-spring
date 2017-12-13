package ar.com.incluit.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Grupo_Estado")
public class GrupoEstado extends AbstractGrupo {

	@Id
	private Integer idGrupoEstado;

	public Integer getIdGrupoEstado() {
		return idGrupoEstado;
	}

	public void setIdGrupoEstado(Integer idGrupoEstado) {
		this.idGrupoEstado = idGrupoEstado;
	}

	@Override
	public Integer getId() {
		return getIdGrupoEstado();
	}

	@Override
	public String getPrefix() {
		return "estado";
	}

}
