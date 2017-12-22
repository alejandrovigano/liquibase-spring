package ar.com.incluit.domain;

import ar.com.incluit.domain.visitor.Visitor;

import javax.persistence.*;

@Entity
@Table(name = "Ciclo_Facturacion")
public class CicloFacturacion extends AbstractParameter {

	@Id
	private Integer idCicloFacturacion;

	private Integer diaDeEjecucion;

	private Integer diaInicio;

	private Integer diaTopeIncluido;

	private Integer mesesDesplazamiento;

	public Integer getIdCicloFacturacion() {
		return idCicloFacturacion;
	}

	public void setIdCicloFacturacion(Integer idCicloFacturacion) {
		this.idCicloFacturacion = idCicloFacturacion;
	}

	public Integer getMesesDesplazamiento() {
		return mesesDesplazamiento;
	}

	public void setMesesDesplazamiento(Integer mesesDesplazamiento) {
		this.mesesDesplazamiento = mesesDesplazamiento;
	}

	public Integer getDiaDeEjecucion() {
		return diaDeEjecucion;
	}

	public void setDiaDeEjecucion(Integer diaDeEjecucion) {
		this.diaDeEjecucion = diaDeEjecucion;
	}

	public Integer getDiaInicio() {
		return diaInicio;
	}

	public void setDiaInicio(Integer diaInicio) {
		this.diaInicio = diaInicio;
	}

	public Integer getDiaTopeIncluido() {
		return diaTopeIncluido;
	}

	public void setDiaTopeIncluido(Integer diaTopeIncluido) {
		this.diaTopeIncluido = diaTopeIncluido;
	}

	@Override
	public AbstractGrupo getAbstractGrupoTipo() {
		return new NoGrupo("ciclo");
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
