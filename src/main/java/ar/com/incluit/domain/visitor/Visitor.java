package ar.com.incluit.domain.visitor;

import ar.com.incluit.domain.*;

public interface Visitor {

	void visit(Estado estado);
	void visit(Tipo tipo);
	void visit(Mensaje mensaje);
	void visit(ResolutorTransaction resolutorTransaction);
	void visit(CicloFacturacion cicloFacturacion);
	void visit(CanalAdhesion canalAdhesion);

}
