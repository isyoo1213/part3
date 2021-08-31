package bank;

import java.math.BigDecimal;

public class SavingInterestCalculator implements InterestCalculator {
    @Override
    public BigDecimal getInterest(BigDecimal balance) {
        BigDecimal interest;
        double value = balance.doubleValue();
        if(value < 1_000_000) {
            interest = balance.multiply(new BigDecimal("0.01"));
        } else {
            interest = balance.multiply(new BigDecimal("0.5"));
        }

        return interest;
    }
}
