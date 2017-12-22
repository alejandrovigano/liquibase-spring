package ar.com.incluit.liqui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ar.com.incluit.liqui.changelog.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LiquiChangeLogBuilder {

	@Value("${author}")
	private String author;

	@Value("${comment.master}")
	private String comment;

	public LiquiChangelog buildLiquiInsertChangelog(List<Insert> inserts, List<Precondition> preconditiosn, List<RollBack> rollbacks) {

		LiquiChangelog liquiChangelog = new LiquiChangelog();

		List<ChangeLog> databaseChangeLog = new ArrayList<>();
		ChangeLog log = new ChangeLog();
		ChangeSet changeSet = new ChangeSet();

		changeSet.setId(obtenerIdChangeSet());
		changeSet.setAuthor(author);

		List<Change> changes = new ArrayList<>();
		for (Insert insert : inserts) {
			Change change = new Change();
			change.setInsert(insert);
			changes.add(change);
		}
		changeSet.setPreConditions(preconditiosn);
		changeSet.setRollback(rollbacks);
		changeSet.setChanges(changes);
		log.setChangeSet(changeSet);

		databaseChangeLog.add(log);
		liquiChangelog.setDatabaseChangeLog(databaseChangeLog);

		return liquiChangelog;
	}

	public LiquiChangelog buildFileIncludeChangelog(List<String> files) {
		LiquiChangelog liquiChangelog = new LiquiChangelog();

		List<ChangeLog> databaseChangeLog = new ArrayList<>();

		ChangeLog log = new ChangeLog();
		log.setComment(comment);
		databaseChangeLog.add(log);

		for (String file : files) {
			ChangeLog logFile = new ChangeLog();
			Include include = new Include();
			include.setFile(file);
			logFile.setInclude(include);
			databaseChangeLog.add(logFile);
		}

		liquiChangelog.setDatabaseChangeLog(databaseChangeLog);

		return liquiChangelog;
	}

	private String obtenerIdChangeSet() {
		return new SimpleDateFormat("yyyyMMddS").format(new Date());
	}

}
