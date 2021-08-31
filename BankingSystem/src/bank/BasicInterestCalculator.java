package bank;

import java.math.BigDecimal;

public class BasicInterestCalculator implements InterestCalculator {
    @Override
    public BigDecimal getInterest(BigDecimal balance) {
        BigDecimal interest;

        double value = balance.doubleValue();
        if(value < 10_000) {
            interest = balance.multiply(new BigDecimal("0.01"));
        } else if(value < 1_000_000) {
            interest = balance.multiply(new BigDecimal("0.02"));
        } else if(value < 5_000_000) {
            interest = balance.multiply(new BigDecimal("0.04"));
        } else if(value < 10_000_000) {
            interest = balance.multiply(new BigDecimal("0.07"));
        } else {
            interest = balance.multiply(new BigDecimal("0.5"));
        }

        return interest;
    }
}
