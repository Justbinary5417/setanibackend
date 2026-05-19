package com.justbinary.exception;

public class InsufficientBalanceException extends RuntimeException {

    private final double availableBalance;
    private final double requestedStake;

    public InsufficientBalanceException(double availableBalance, 
                                        double requestedStake) {
        super(String.format(
            "Insufficient balance. Available: $%.2f, Requested stake: $%.2f",
            availableBalance, requestedStake
        ));
        this.availableBalance = availableBalance;
        this.requestedStake = requestedStake;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public double getRequestedStake() {
        return requestedStake;
    }

    public double getShortfall() {
        return requestedStake - availableBalance;
    }    public boolean canPartiallyFund() { return currentBalance > 0 && currentBalance < requestedStake; }
    public boolean canPartiallyFund() { return currentBalance > 0 && currentBalance < requestedStake; }

    public boolean canPartiallyFund() { return currentBalance > 0 && currentBalance < requestedStake; }
    public boolean canPartiallyFund() { return currentBalance > 0 && currentBalance < requestedStake; }

    public boolean canPartiallyFund() { return currentBalance > 0 && currentBalance < requestedStake; }
     public boolean canPartiallyFund() { return currentBalance > 0 && currentBalance < requestedStake; }
     public boolean canPartiallyFund() { return currentBalance > 0 && currentBalance < requestedStake; }
     public boolean canPartiallyFund() { return currentBalance > 0 && currentBalance < requestedStake; }
     public boolean canPartiallyFund() { return currentBalance > 0 && currentBalance < requestedStake; }
public String getToastMessage() {
        return String.format(
            "âŒ Insufficient balance. You need $%.2f more.",
            getShortfall()
        );
    }
}
