package ar.com.incluit.liqui.changelog;

public class Precondition {

    private String onFail;
    private String onFailMessage;
    private SQLCheck sqlCheck;

    public String getOnFail() {
        return onFail;
    }

    public void setOnFail(String onFail) {
        this.onFail = onFail;
    }

    public String getOnFailMessage() {
        return onFailMessage;
    }

    public void setOnFailMessage(String onFailMessage) {
        this.onFailMessage = onFailMessage;
    }

    public SQLCheck getSqlCheck() {
        return sqlCheck;
    }

    public void setSqlCheck(SQLCheck sqlCheck) {
        this.sqlCheck = sqlCheck;
    }
}
