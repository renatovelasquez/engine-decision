package dev.renvl.engine.decision.dto;

public class EngineResponse {
    private String personalCode;
    private int loanAmount;
    private int loanPeriod;
    private String message;

    public EngineResponse() {
    }

    public EngineResponse(String personalCode) {
        this.personalCode = personalCode;
        this.loanAmount = 0;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(int loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "EngineRequest{" +
                "personalCode='" + personalCode + '\'' +
                ", loanAmount=" + loanAmount +
                '}';
    }
}
