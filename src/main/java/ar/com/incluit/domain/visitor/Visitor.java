package ar.com.incluit.domain.visitor;

import ar.com.incluit.domain.Estado;
import ar.com.incluit.domain.Mensaje;
import ar.com.incluit.domain.ResolutorTransaction;
import ar.com.incluit.domain.Tipo;

public interface Visitor {

	void visit(Estado estado);
	void visit(Tipo estado);
	void visit(Mensaje estado);
	void visit(ResolutorTransaction estado);
	
}
