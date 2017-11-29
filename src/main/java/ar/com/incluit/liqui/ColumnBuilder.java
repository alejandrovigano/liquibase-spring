package ar.com.incluit.liqui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ar.com.incluit.domain.Tipo;
import ar.com.incluit.liqui.changelog.Column;
import ar.com.incluit.liqui.changelog.ValueColumn;

@Component
public class ColumnBuilder {

	private static final String NULL = "null";
	@Value("${dateFormat}")
	private String dateFormat;

	public List<Column> obtenerColumns(Tipo tipo, int i) {
		List<Column> columnas = new ArrayList<>();

		columnas.add(obtenerColumna("id", i)); // TODO VER
		columnas.add(obtenerColumna("description", tipo.getDescripcion()));
		columnas.add(obtenerColumna("code", tipo.getCodigo()));
		columnas.add(obtenerColumna("create_date", tipo.getFechaAlta()));
		columnas.add(obtenerColumna("modify_date", tipo.getFechaModificacion()));
		columnas.add(obtenerColumna("create_user", tipo.getUsuarioAlta()));
		columnas.add(obtenerColumna("modify_user", tipo.getUsuarioModificacion()));
		columnas.add(obtenerColumna("version", tipo.getVersion()));
		columnas.add(obtenerColumna("is_active", true)); // TODO VER

		return columnas;
	}

	private Column obtenerColumna(String name, boolean value) {
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
