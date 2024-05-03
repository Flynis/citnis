package ru.dyakun.citnis.model.data;

public class Statistics {

    private String maxDebtAts;
    private double maxDebt;
    private String maxDebtorAts;
    private double maxDebtor;
    private String minDebtorAts;
    private double minDebtors;
    private String intercityLeader;

    public String getMaxDebtAts() {
        return maxDebtAts;
    }

    public void setMaxDebtAts(String maxDebtAts) {
        this.maxDebtAts = maxDebtAts;
    }

    public double getMaxDebt() {
        return maxDebt;
    }

    public void setMaxDebt(double maxDebt) {
        this.maxDebt = maxDebt;
    }

    public String getMaxDebtorAts() {
        return maxDebtorAts;
    }

    public void setMaxDebtorAts(String maxDebtorAts) {
        this.maxDebtorAts = maxDebtorAts;
    }

    public double getMaxDebtor() {
        return maxDebtor;
    }

    public void setMaxDebtor(double maxDebtor) {
        this.maxDebtor = maxDebtor;
    }

    public String getMinDebtorAts() {
        return minDebtorAts;
    }

    public void setMinDebtorAts(String minDebtorAts) {
        this.minDebtorAts = minDebtorAts;
    }

    public String getIntercityLeader() {
        return intercityLeader;
    }

    public void setIntercityLeader(String intercityLeader) {
        this.intercityLeader = intercityLeader;
    }

    public double getMinDebtors() {
        return minDebtors;
    }

    public void setMinDebtors(double minDebtors) {
        this.minDebtors = minDebtors;
    }

}
