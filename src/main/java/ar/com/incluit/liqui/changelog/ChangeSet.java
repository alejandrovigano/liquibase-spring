package ar.com.incluit.liqui.changelog;


import java.util.List;

public class ChangeSet {

	private String id;
	private String author;
	private List<Precondition> preConditions;
	private List<Change> changes;
	private List<RollBack> rollback;

	public List<Precondition> getPreConditions() {
		return preConditions;
	}

	public void setPreConditions(List<Precondition> preConditions) {
		this.preConditions = preConditions;
	}

	public List<RollBack> getRollback() {
		return rollback;
	}

	public void setRollback(List<RollBack> rollback) {
		this.rollback = rollback;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<Change> getChanges() {
		return changes;
	}

	public void setChanges(List<Change> changes) {
		this.changes = changes;
	}

}
