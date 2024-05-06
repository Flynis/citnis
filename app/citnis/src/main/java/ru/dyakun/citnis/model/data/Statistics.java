package ru.dyakun.citnis.model.data;

public class Statistics {

    private String maxDebtAts;
    private double maxDebt;
    private String maxDebtorAts;
    private int maxDebtors;
    private String minDebtorAts;
    private int minDebtors;
    private String intercityLeader;
    private int callsCount;

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

    public int getMaxDebtors() {
        return maxDebtors;
    }

    public void setMaxDebtors(int maxDebtors) {
        this.maxDebtors = maxDebtors;
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

    public int getMinDebtors() {
        return minDebtors;
    }

    public void setMinDebtors(int minDebtors) {
        this.minDebtors = minDebtors;
    }

    public int getCallsCount() {
        return callsCount;
    }

    public void setCallsCount(int callsCount) {
        this.callsCount = callsCount;
    }

}
