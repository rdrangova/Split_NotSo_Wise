package com.splitnotsowise.utilities;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class User{
    private String username;
    private String password;
    private Map<String, HashSet<Obligation>> obligationsOfCurrentUser;
    private Map<String, HashSet<Obligation>> obligationsToCurrentUser;
    private Map<String, Obligation> paymentHistory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.obligationsOfCurrentUser = new HashMap<>();
        this.obligationsToCurrentUser = new HashMap<>();
        this.paymentHistory = new HashMap<>();
    }

    public Map<String, Obligation> getPaymentHistory() {
        return paymentHistory;
    }

    public void receivePaymentFromFriend(String username, double amount) {

        if (obligationsToCurrentUser.get(username).isEmpty()) {
            System.out.println("Nobody owes you money");
        } else {
            HashSet<Obligation> obligations = obligationsToCurrentUser.get(username);

            for (Obligation obligation : obligations) {
                double newAmount = obligation.getAmount() - amount;
                if (newAmount < 0) {
                    this.addObligationOfCurrentUser(username,
                            new Obligation(-newAmount, username + " overpaid you for " + obligation.getReason()));

                } else if (newAmount > 0) {
                    Obligation newObligationAfterPayment = new Obligation(newAmount, obligation.getReason());
                    obligations.add(newObligationAfterPayment);
                }

                obligations.remove(obligation);

                break;
            }
            obligationsToCurrentUser.put(username, obligations);
        }

    }

    public void payFriend(String username, double amount) {

        if (obligationsOfCurrentUser.isEmpty() || obligationsOfCurrentUser.get(username).isEmpty()) {
            System.out.println("You don't have any obligations to pay");
        } else {

            HashSet<Obligation> obligations = obligationsOfCurrentUser.get(username);

            for (Obligation obligation : obligations) {
                double newAmount = obligation.getAmount() - amount;
                if (newAmount < 0) {
                    this.addObligationToCurrentUser(username,
                            new Obligation(-newAmount, "You overpaid " + username + " for " + obligation.getReason()));
                } else if (newAmount > 0) {
                    Obligation newObligationAfterPayment = new Obligation(newAmount, obligation.getReason());
                    obligations.add(newObligationAfterPayment);
                }

                obligations.remove(obligation);
                paymentHistory.put(username, obligation);

                break;

            }
            obligationsOfCurrentUser.put(username, obligations);
        }

    }

    public void receivePaymentFromGroupMember(String username, String groupName, double amount) {

        if (obligationsToCurrentUser.get(username).isEmpty() || obligationsToCurrentUser.isEmpty()) {
            System.out.println("No obligations to be payed");
        } else {
            HashSet<Obligation> obligations = obligationsToCurrentUser.get(username);

            for (Obligation obligation : obligations) {
                if (obligation.getGroupName().equals(groupName)) {
                    double newAmount = obligation.getAmount() - amount;
                    if (newAmount < 0) {
                        this.addObligationOfCurrentUser(username,
                                new Obligation(-newAmount, groupName, username + " overpaid you for " + obligation.getReason()));

                    } else if (newAmount > 0) {
                        Obligation newObligationAfterPayment = new Obligation(newAmount, groupName, obligation.getReason());
                        obligations.add(newObligationAfterPayment);
                    }

                    obligations.remove(obligation);
                    break;
                }
            }

            obligationsToCurrentUser.put(username, obligations);
        }

    }

    public void payGroup(String username, String groupName, double amount) {

        if (obligationsOfCurrentUser.get(username).isEmpty() || obligationsOfCurrentUser.isEmpty()) {
            System.out.println("You don't have obligations to pay");

        } else {
            HashSet<Obligation> obligations = obligationsOfCurrentUser.get(username);

            for (Obligation obligation : obligations) {
                if (obligation.getGroupName().equals(groupName)) {
                    double newAmount = obligation.getAmount() - amount;
                    if (newAmount < 0) {
                        this.addObligationToCurrentUser(username,
                                new Obligation(-newAmount, groupName,
                                        "You overpaid " + username + " for " + obligation.getReason()));
                    } else if (newAmount > 0) {
                        Obligation newObligationAfterPayment = new Obligation(newAmount, groupName, obligation.getReason());
                        obligations.add(newObligationAfterPayment);
                    }

                    obligations.remove(obligation);
                    paymentHistory.put(username, obligation);

                    break;
                }
            }

            obligationsOfCurrentUser.put(username, obligations);
        }

    }

    public void addObligationOfCurrentUser(String creditor, Obligation newObligation) {
        if (!obligationsOfCurrentUser.containsKey(creditor)) {
            HashSet<Obligation> obligations = new HashSet<>();
            obligations.add(newObligation);
            obligationsOfCurrentUser.put(creditor, obligations);

        } else {
            HashSet<Obligation> obligations = obligationsOfCurrentUser.get(creditor);
            obligations.add(newObligation);
            obligationsOfCurrentUser.put(creditor, obligations);

        }
    }

    public void addObligationToCurrentUser(String debtor, Obligation newObligation) {
        if (!obligationsToCurrentUser.containsKey(debtor)) {
            HashSet<Obligation> obligations = new HashSet<>();
            obligations.add(newObligation);
            obligationsToCurrentUser.put(debtor, obligations);

        } else {
            HashSet<Obligation> obligations = obligationsToCurrentUser.get(debtor);
            obligations.add(newObligation);
            obligationsToCurrentUser.put(debtor, obligations);
        }
    }

    private double getTotalSumOfFriendObligationsToCurrentUser(String friend) {
        if (obligationsToCurrentUser.containsKey(friend)) {
            HashSet<Obligation> obligations = obligationsToCurrentUser.get(friend);

            double sum = 0.0;
            for (Obligation obligation : obligations) {
                if (obligation.getGroupName().equals("no group")) {
                    sum += obligation.getAmount();
                }
            }

            return sum;
        }

        return 0.0;
    }

    private double getTotalSumOfCurrentUserObligationsToFriend(String friend) {
        if (obligationsOfCurrentUser.containsKey(friend)) {
            HashSet<Obligation> obligations = obligationsOfCurrentUser.get(friend);

            double sum = 0.0;
            for (Obligation obligation : obligations) {
                if (obligation.getGroupName().equals("no group")) {
                    sum += obligation.getAmount();
                }
            }

            return sum;
        }

        return 0.0;
    }

    private double getTotalSumOfCurrentUserObligationsToGroupMember(String friend) {
        if (obligationsToCurrentUser.containsKey(friend)) {
            HashSet<Obligation> obligations = obligationsToCurrentUser.get(friend);

            double sum = 0.0;

            for (Obligation obligation : obligations) {
                if (!obligation.getGroupName().equals("no group")) {
                    sum += obligation.getAmount();
                }
            }
            return sum;
        }

        return 0.0;
    }

    private double getTotalSumOfGroupMemberObligationsToCurrentUser(String friend) {
        if (obligationsOfCurrentUser.containsKey(friend)) {
            HashSet<Obligation> obligations = obligationsOfCurrentUser.get(friend);

            double sum = 0.0;
            for (Obligation obligation : obligations) {
                if (!obligation.getGroupName().equals("no group")) {
                    sum += obligation.getAmount();
                }
            }

            return sum;
        }

        return 0.0;
    }

    public double getCurrentUserObligationsToFriend(String friend) {
        double sumCurrentUserOwes = getTotalSumOfCurrentUserObligationsToFriend(friend);
        double sumFriendOwes = getTotalSumOfFriendObligationsToCurrentUser(friend);

        return sumCurrentUserOwes - sumFriendOwes;
    }

    public double getUsersObligationToGroupMember(String friend) {
        double sumIOwe = getTotalSumOfGroupMemberObligationsToCurrentUser(friend);
        double sumFriendOwes = getTotalSumOfCurrentUserObligationsToGroupMember(friend);

        return sumIOwe - sumFriendOwes;
    }

    public String getUsername() {
        return username;
    }

    public boolean isPasswordCorrect(String password) {
        return this.password.equals(password);
    }

    public void printNotifications(PrintWriter writer) {
        writer.println("***No new notifications***");
        //TODO show users notifications from a file and after show up delete them
    }
}
