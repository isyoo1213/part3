package bank;

import java.math.BigDecimal;

// TODO: InterestCalculator 인터페이스 구현한 SavingInterestCalculator 클래스
public class SavingInterestCalculator implements InterestCalculator{
//    public BigDecimal getInterest(BigDecimal balance) {
//        // TODO: 적금 계좌의 경우 잔액이 100만원 이상은 이자율이 50%, 그 외에는 1% 입니다.
//    }

    @Override
    public BigDecimal getInsterest(BigDecimal balance) {

        BigDecimal interest;
        BigDecimal tier1 = new BigDecimal("10000000");
        BigDecimal tier1Ratio = new BigDecimal("0.5");
        BigDecimal tier5Ratio = new BigDecimal("0.01");

        if(balance.compareTo(tier1) >= 0){
            interest = balance.multiply(tier1Ratio);
        } else {
            interest = balance.multiply(tier5Ratio);
        }
        return interest;

    }
}
