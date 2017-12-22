package ar.com.incluit.domain;

import ar.com.incluit.domain.visitor.Visitor;

import javax.persistence.*;

@Entity
@Table(name = "Canal_Adhesion")
@AttributeOverride(name = "descripcion", column = @Column(name = "nombre"))
public class CanalAdhesion extends AbstractParameter {

	@Id
	private Integer idCanal;

	private String nivelRiesgo;

	private Boolean permitePromocion;

	private Boolean permiteDevolucion;

	public Integer getIdCanal() {
		return idCanal;
	}

	public void setIdCanal(Integer idCanal) {
		this.idCanal = idCanal;
	}

	public String getNivelRiesgo() {
		return nivelRiesgo;
	}

	public void setNivelRiesgo(String nivelRiesgo) {
		this.nivelRiesgo = nivelRiesgo;
	}

	public Boolean getPermitePromocion() {
		return permitePromocion;
	}

	public void setPermitePromocion(Boolean permitePromocion) {
		this.permitePromocion = permitePromocion;
	}

	public Boolean getPermiteDevolucion() {
		return permiteDevolucion;
	}

	public void setPermiteDevolucion(Boolean permiteDevolucion) {
		this.permiteDevolucion = permiteDevolucion;
	}

	@Override
	public AbstractGrupo getAbstractGrupoTipo() {
		return new NoGrupo("canal");
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
