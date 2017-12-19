package ar.com.incluit.liqui;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ar.com.incluit.domain.AbstractParameter;
import ar.com.incluit.liqui.changelog.Column;
import ar.com.incluit.liqui.visitor.ColumnBuilderVisitor;

@Component
public class ColumnBuilder {

	@Value("${dateFormat}")
	private String dateFormat;

	public List<Column> obtenerColumns(AbstractParameter tipo, int i) {
		ColumnBuilderVisitor visitor = new ColumnBuilderVisitor(i, dateFormat);

		tipo.accept(visitor);

		return visitor.build();
	}

}
