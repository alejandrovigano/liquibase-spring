package ar.com.incluit.liqui;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ar.com.incluit.domain.AbstractParameter;
import ar.com.incluit.liqui.changelog.Insert;

@Component
public class InsertBuilder {

	@Autowired
	private ColumnBuilder columnBuilder;

	@Autowired
	private TableResolver tableResolver;

	@Value("${schema}")
	private String schemaName;

	public List<Insert> buildInserts(List<? extends AbstractParameter> parameters) {

		List<Insert> inserts = new ArrayList<>();

		int i = 0;
		for (AbstractParameter tipo : parameters) {
			Insert insert = new Insert();
			insert.setSchemaName(schemaName);
			insert.setTableName(tableResolver.obtenerTabla(tipo.getAbstractGrupoTipo()));
			insert.setColumns(columnBuilder.obtenerColumns(tipo, i));

			inserts.add(insert);
			i++;
		}
		return inserts;
	}

}
