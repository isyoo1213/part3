package bank;

import java.math.BigDecimal;

// TODO: InterestCalculator 인터페이스 구현한 BasicInterestCalculator 클래스

public class BasicInterestCalculator implements InterestCalculator{

    @Override
    public BigDecimal getInsterest(BigDecimal balance) {

        BigDecimal interest;
        if(balance.compareTo(tier1) >= 0){
            interest = balance.multiply(tier1Ratio);
        } else if ( balance.compareTo(tier2) >= 0){
            interest = balance.multiply(tier2Ratio);
        } else if ( balance.compareTo(tier3) >= 0){
            interest = balance.multiply(tier3Ratio);
        } else if ( balance.compareTo(tier4) >= 0){
            interest = balance.multiply(tier4Ratio);
        } else {
            interest = balance.multiply(tier5Ratio);
        }
        return interest;

    }

}
