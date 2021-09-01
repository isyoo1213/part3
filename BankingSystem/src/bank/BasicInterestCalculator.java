package bank;

import java.math.BigDecimal;


// TODO: InterestCalculator 인터페이스 구현한 BasicInterestCalculator 클래스
public class BasicInterestCalculator implements InterestCalculator{
    public BigDecimal getInterest(BigDecimal balance) {
        BigDecimal interest;
        // TODO:
        //  예금 계좌의 경우 잔액이 1000만원 이상은 이자율이 50%,
        if (balance.compareTo(RANK1) > 0) {
            interest = BigDecimal.valueOf(1.5);
        } else if (balance.compareTo(RANK2) > 0) {
            interest = BigDecimal.valueOf(1.07);
        } else if (balance.compareTo(RANK3) > 0) {
            interest = BigDecimal.valueOf(1.04);
        } else if (balance.compareTo(RANK4) > 0) {
            interest = BigDecimal.valueOf(1.02);
        } else {
            interest = BigDecimal.valueOf(1.01);
        }
        //  500만원 이상은 7%, 100만원 이상은 4%, 1만원 이상은 2%, 그 외에는 1% 입니다.
        return interest;
    }
}
