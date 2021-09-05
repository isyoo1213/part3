package bank;

import java.math.BigDecimal;

// TODO: InterestCalculator 인터페이스 구현한 SavingInterestCalculator 클래스
public class SavingInterestCalculator implements InterestCalculator {
    public BigDecimal getInterest(BigDecimal balance) {
        BigDecimal interest;
        double doubleValue = balance.doubleValue();
        // TODO: 적금 계좌의 경우 잔액이 100만원 이상은 이자율이 50%, 그 외에는 1% 입니다.
        if(doubleValue/1000000 >= 0){
            interest = BigDecimal.valueOf(0.5);
        } else{
            interest = BigDecimal.valueOf(0.01);
        }
        return interest;

    }
}
