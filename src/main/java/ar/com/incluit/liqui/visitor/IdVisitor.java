package ar.com.incluit.liqui.visitor;

import ar.com.incluit.domain.*;
import ar.com.incluit.domain.visitor.Visitor;
import ar.com.incluit.liqui.changelog.Column;
import ar.com.incluit.liqui.changelog.ValueColumn;
import org.apache.commons.lang3.StringEscapeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IdVisitor implements Visitor {

	private Integer index;

	@Override
	public void visit(Estado estado) {
		index = estado.getIdEstado();
	}

	@Override
	public void visit(Tipo tipo) {
		index = tipo.getIdTipo();
	}

	@Override
	public void visit(Mensaje mensaje) {
		index = mensaje.getIdMensaje();
	}

	@Override
	public void visit(CicloFacturacion cicloFacturacion) {
		index = cicloFacturacion.getIdCicloFacturacion();
	}

	@Override
	public void visit(CanalAdhesion canalAdhesion) {
		index = canalAdhesion.getIdCanal();
	}

	@Override
	public void visit(ResolutorTransaction resolutor) {
		index = resolutor.getIdResolutor();
	}

	public Integer getId(){
		return index;
	}


}
