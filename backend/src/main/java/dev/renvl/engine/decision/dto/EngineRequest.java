package dev.renvl.engine.decision.dto;

public class EngineRequest {
    private String personalCode;
    private Integer loanAmount;
    private Integer loanPeriod;

    public EngineRequest(String personalCode, Integer loanAmount, Integer loanPeriod) {
        this.personalCode = personalCode;
        this.loanAmount = loanAmount;
        this.loanPeriod = loanPeriod;
    }

    public String getPersonalCode() {
        return personalCode;
    }

    public void setPersonalCode(String personalCode) {
        this.personalCode = personalCode;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanPeriod() {
        return loanPeriod;
    }

    public void setLoanPeriod(Integer loanPeriod) {
        this.loanPeriod = loanPeriod;
    }

    @Override
    public String toString() {
        return "EngineRequest{" +
                "personalCode='" + personalCode + '\'' +
                ", loanAmount=" + loanAmount +
                ", loanPeriod=" + loanPeriod +
                '}';
    }
}
