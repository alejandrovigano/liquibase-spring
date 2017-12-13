package ar.com.incluit.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Grupo_Tipo")
public class GrupoTipo extends AbstractGrupo {

	@Id
	private Integer idGrupoTipo;

	public Integer getIdGrupoTipo() {
		return idGrupoTipo;
	}

	public void setIdGrupoTipo(Integer idGrupoTipo) {
		this.idGrupoTipo = idGrupoTipo;
	}

	@Override
	public Integer getId() {
		return getIdGrupoTipo();
	}

	@Override
	public String getPrefix() {
		return "tipo";
	}

}
