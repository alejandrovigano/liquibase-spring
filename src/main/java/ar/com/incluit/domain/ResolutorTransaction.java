package ar.com.incluit.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ar.com.incluit.domain.visitor.Visitor;

@Entity
@Table(name = "Resolutor_Transaccion")
@AttributeOverride(name = "codigo", column = @Column(name = "codigo_resolutor"))
public class ResolutorTransaction extends AbstractParameter {

	@Id
	private Integer idResolutor;

	public Integer getIdResolutor() {
		return idResolutor;
	}

	public void setIdResolutor(Integer idResolutor) {
		this.idResolutor = idResolutor;
	}

	@Override
	public AbstractGrupo getAbstractGrupoTipo() {
		return new NoGrupo("resolutor");
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
