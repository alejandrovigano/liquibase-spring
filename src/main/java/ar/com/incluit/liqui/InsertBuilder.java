package ar.com.incluit.liqui;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ar.com.incluit.domain.GrupoTipo;
import ar.com.incluit.domain.Tipo;
import ar.com.incluit.liqui.changelog.Insert;

@Component
public class InsertBuilder {

	@Autowired
	private ColumnBuilder columnBuilder;

	@Autowired
	private TableResolver tableResolver;

	public List<Insert> buildInserts(List<Tipo> tipos) {

		List<Insert> inserts = new ArrayList<>();

		for (Tipo tipo : tipos) {
			Insert insert = new Insert();
			insert.setTableName(tableResolver.obtenerTabla(tipo.getGrupoTipo()));
			insert.setColumns(columnBuilder.obtenerColumns(tipo));

			inserts.add(insert);
		}
		return inserts;
	}

}
