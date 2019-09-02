package com.splitnotsowise;

public class Payment {
    private double amount;
    private String reason;
    private String status;

    public Payment(double amount, String reason, String status) {
        this.amount = amount;
        this.reason = reason;
        this.status = status;

    }

    public double getAmount() {
        return amount;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }


}
