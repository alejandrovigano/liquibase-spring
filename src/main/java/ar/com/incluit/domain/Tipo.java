package ar.com.incluit.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.com.incluit.domain.visitor.Visitor;

@Entity
@Table(name = "Tipo")
public class Tipo extends AbstractParameter {

	@Id
	private Integer idTipo;
	@ManyToOne
	@JoinColumn(name = "id_grupo_tipo")
	private GrupoTipo grupoTipo;

	public Integer getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}

	public GrupoTipo getGrupoTipo() {
		return grupoTipo;
	}

	public void setGrupoTipo(GrupoTipo grupoTipo) {
		this.grupoTipo = grupoTipo;
	}

	@Override
	public AbstractGrupo getAbstractGrupoTipo() {
		return getGrupoTipo();
	}
	
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
