package bank;

import java.math.BigDecimal;

// TODO: InterestCalculator 인터페이스 구현한 BasicInterestCalculator 클래스
public class BasicInterestCalculator implements InterestCalculator{

    @Override
    public BigDecimal getInsterest(BigDecimal balance) {
        BigDecimal tier1 = new BigDecimal("10000000");
        BigDecimal tier2 = new BigDecimal("5000000");
        BigDecimal tier3 = new BigDecimal("1000000");
        BigDecimal tier4 = new BigDecimal("10000");
        BigDecimal tier1Ratio = new BigDecimal("0.5");
        BigDecimal tier2Ratio = new BigDecimal("0.07");
        BigDecimal tier3Ratio = new BigDecimal("0.04");
        BigDecimal tier4Ratio = new BigDecimal("0.02");
        BigDecimal tier5Ratio = new BigDecimal("0.01");
        BigDecimal interest = new BigDecimal("0");
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

//    public BigDecimal getInterest(BigDecimal balance) {
//
//        // TODO:
//        //  예금 계좌의 경우 잔액이 1000만원 이상은 이자율이 50%,
//        //  500만원 이상은 7%, 100만원 이상은 4%, 1만원 이상은 2%, 그 외에는 1% 입니다.
//        return null;
//    }
}
