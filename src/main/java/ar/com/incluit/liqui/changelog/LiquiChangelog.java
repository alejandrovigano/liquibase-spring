package ar.com.incluit.liqui.changelog;

import java.util.List;

public class LiquiChangelog {

	private List<ChangeLog> databaseChangeLog;

	public List<ChangeLog> getDatabaseChangeLog() {
		return databaseChangeLog;
	}

	public void setDatabaseChangeLog(List<ChangeLog> databaseChangeLog) {
		this.databaseChangeLog = databaseChangeLog;
	}

}
