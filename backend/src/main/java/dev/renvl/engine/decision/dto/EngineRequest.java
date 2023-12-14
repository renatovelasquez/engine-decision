package dev.renvl.engine.decision.dto;

import javax.validation.constraints.*;

public class EngineRequest {

    @NotBlank(message = "Personal code must not be blank")
    @Pattern(regexp = "[0-9]*", message = "Personal code must contain just numbers")
    private String personalCode;

    @NotNull(message = "Loan amount must not be null")
    @Min(value = 2000, message = "Loan amount must be greater than 2000")
    @Max(value = 10000, message = "Loan amount must be lower than 10000")
    private Integer loanAmount;

    @NotNull(message = "Loan period must not be null")
    @Min(value = 12, message = "Loan period must be greater than 12")
    @Max(value = 60, message = "Loan period must be lower than 60")
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
