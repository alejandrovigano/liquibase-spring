package ar.com.incluit.liqui.visitor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.incluit.domain.*;
import org.apache.commons.lang3.StringEscapeUtils;

import ar.com.incluit.domain.visitor.Visitor;
import ar.com.incluit.liqui.changelog.Column;
import ar.com.incluit.liqui.changelog.ValueColumn;

public class ColumnBuilderVisitor implements Visitor {

	private static final String NULL = "null";
	private String dateFormat;
	private List<Column> columnas = new ArrayList<>();
	private int index;

	public ColumnBuilderVisitor(int index, String dateFormat) {
		this.index = index;
		this.dateFormat = dateFormat;
	}

	public List<Column> build() {
		return columnas;
	}

	@Override
	public void visit(Estado estado) {
		index = estado.getIdEstado();
		defaultColumnBuild(estado);
	}

	@Override
	public void visit(Tipo tipo) {
		index = tipo.getIdTipo();
		defaultColumnBuild(tipo);
	}

	@Override
	public void visit(Mensaje mensaje) {
		index = mensaje.getIdMensaje();
		mensajeColumnBuild(mensaje);
	}

	@Override
	public void visit(CicloFacturacion cicloFacturacion) {
		index = cicloFacturacion.getIdCicloFacturacion();
		cicloColumnBuild(cicloFacturacion);
	}

	@Override
	public void visit(CanalAdhesion canalAdhesion) {
		index = canalAdhesion.getIdCanal();
		canalColumnBuild(canalAdhesion);
	}

	@Override
	public void visit(ResolutorTransaction resolutor) {
		index = resolutor.getIdResolutor();
		defaultColumnBuild(resolutor);
	}

	private void canalColumnBuild(CanalAdhesion canalAdhesion) {
		index = canalAdhesion.getIdCanal();
		String code = canalAdhesion.getDescripcion().toUpperCase()
				.replace(" ", "_")
				.replace("(", "")
				.replace(")", "");

		canalAdhesion.setCodigo(code);

		defaultColumnBuild(canalAdhesion);

		columnas.add(obtenerColumna("nivel_riesgo", canalAdhesion.getNivelRiesgo()));
		if(canalAdhesion.getPermitePromocion() != null){
			columnas.add(obtenerColumna("permite_promocion", canalAdhesion.getPermitePromocion()));
		}else{
			columnas.add(obtenerColumna("permite_promocion", "null"));
		}

		if(canalAdhesion.getPermiteDevolucion() != null){
			columnas.add(obtenerColumna("permite_devolucion", canalAdhesion.getPermiteDevolucion()));
		}else{
			columnas.add(obtenerColumna("permite_devolucion", "null"));
		}

	}

	private void cicloColumnBuild(CicloFacturacion cicloFacturacion) {
		index = cicloFacturacion.getIdCicloFacturacion();
		cicloFacturacion.setCodigo("CICLO_" + index);

		defaultColumnBuild(cicloFacturacion);

		columnas.add(obtenerColumna("dia_de_ejecucion", cicloFacturacion.getDiaDeEjecucion()));
		columnas.add(obtenerColumna("dia_inicio", cicloFacturacion.getDiaInicio()));
		columnas.add(obtenerColumna("dia_tope_incluido", cicloFacturacion.getDiaTopeIncluido()));

		columnas.add(obtenerColumna("meses_desplazamiento", cicloFacturacion.getMesesDesplazamiento()));

	}

	private void mensajeColumnBuild(Mensaje mensaje) {
		defaultColumnBuild(mensaje);
		columnas.add(obtenerColumna("evento", mensaje.getEvento()));
		columnas.add(obtenerColumna("mensaje", mensaje.getMensaje()));
		columnas.add(obtenerColumna("formato", mensaje.getFormato()));
		columnas.add(obtenerColumna("motivo_rechazo", mensaje.getMotivoRechazo()));
		if(mensaje.getFlagReintentoTx() != null) {
			columnas.add(obtenerColumna("flag_reintento_tx", mensaje.getFlagReintentoTx()));
		}else {
			columnas.add(obtenerColumna("flag_reintento_tx", "null"));
		}
	}

	private void defaultColumnBuild(AbstractParameter parameter) {
		columnas.add(obtenerColumna("id", index));
		columnas.add(obtenerColumna("description", parameter.getDescripcion() != null ? parameter.getDescripcion() : " "));
		columnas.add(obtenerColumna("code", parameter.getCodigo()));
		columnas.add(obtenerColumna("create_date", parameter.getFechaAlta()));
		columnas.add(obtenerColumna("modify_date",
				parameter.getFechaModificacion() != null ? parameter.getFechaModificacion()
						: parameter.getFechaAlta()));
		columnas.add(obtenerColumna("create_user", parameter.getUsuarioAlta()));
		columnas.add(obtenerColumna("modify_user", parameter.getUsuarioModificacion()));
		columnas.add(obtenerColumna("version", parameter.getVersion()));
		columnas.add(obtenerColumna("is_active", true)); // TODO VER
	}

	private Column obtenerColumna(String name, Boolean value) {
		return buildColumn(name, value);
	}

	private Column obtenerColumna(String name, Integer value) {
		return buildColumn(name, value != null ? String.valueOf(value) : NULL);
	}

	private Column obtenerColumna(String name, Date fecha) {
		return buildColumn(name, fecha != null ? new SimpleDateFormat(dateFormat).format(fecha) : NULL);
	}

	private Column obtenerColumna(String name, String value) {
		return buildColumn(name, value != null ? value : NULL);
	}

	private Column buildColumn(String name, String value) {
		Column column = new Column();
		ValueColumn valueColumn = new ValueColumn();
		valueColumn.setName(name);
		valueColumn.setValue(StringEscapeUtils.escapeJava(value));
		column.setColumn(valueColumn);

		return column;
	}

	private Column buildColumn(String name, Boolean value) {
		Column column = new Column();
		ValueColumn valueColumn = new ValueColumn();
		valueColumn.setName(name);
		valueColumn.setValueBoolean(value);
		column.setColumn(valueColumn);

		return column;
	}

}
