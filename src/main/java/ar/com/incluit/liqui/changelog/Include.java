package ar.com.incluit.liqui.changelog;

public class Include {
	private String file;
	private boolean relativeToChangelogFile = true;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public boolean isRelativeToChangelogFile() {
		return relativeToChangelogFile;
	}

	public void setRelativeToChangelogFile(boolean relativeToChangelogFile) {
		this.relativeToChangelogFile = relativeToChangelogFile;
	}

}
