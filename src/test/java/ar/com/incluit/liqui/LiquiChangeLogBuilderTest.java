package ar.com.incluit.liqui;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ar.com.incluit.liqui.changelog.Column;
import ar.com.incluit.liqui.changelog.Insert;
import ar.com.incluit.liqui.changelog.LiquiChangelog;
import ar.com.incluit.liqui.changelog.ValueColumn;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LiquiChangeLogBuilderTest {

	@Autowired
	private LiquiChangeLogBuilder liquiChangeLogBuilder;

	@Test
	public void test() {

		List<Insert> inserts = new ArrayList<>();
		Insert insert = new Insert();

		insert.setTableName("tabla");

		List<Column> columns = new ArrayList<>();
		Column column = new Column();
		ValueColumn valueColumn = new ValueColumn();

		valueColumn.setName("columna1");
		valueColumn.setValue("valor1");

		column.setColumn(valueColumn);
		columns.add(column);
		insert.setColumns(columns);

		inserts.add(insert);
		LiquiChangelog result = liquiChangeLogBuilder.buildLiquiInsertChangelog(inserts);

		assertNotNull(result);
	}
}
