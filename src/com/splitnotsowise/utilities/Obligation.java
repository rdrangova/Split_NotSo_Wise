package com.splitnotsowise.utilities;

public class Obligation {
    private double amount;
    private String groupName;
    private String reason;

    public Obligation(double amount, String groupName, String reason) {
        this.amount = amount;
        this.groupName = groupName;
        this.reason = reason;
    }

    public Obligation(double amount, String reason) {
        this.amount = amount;
        this.reason = reason;
        this.groupName = "no group";
        //this(d)
    }

    public double getAmount() {
        return amount;
    }

    public String getGroupName() {
        return groupName;
    }


    public String getReason() {
        return reason;
    }

}
