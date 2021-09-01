package bank;

import java.math.BigDecimal;

// TODO: InterestCalculator 인터페이스 구현한 BasicInterestCalculator 클래스
public class BasicInterestCalculator implements InterestCalculator {
    public BigDecimal getInterest(BigDecimal balance) {
        BigDecimal interest;
        // TODO:
        //  예금 계좌의 경우 잔액이 1000만원 이상은 이자율이 50%,
        //  500만원 이상은 7%, 100만원 이상은 4%, 1만원 이상은 2%, 그 외에는 1% 입니다.
        if (balance.compareTo(new BigDecimal("10000000")) >= 0)
            interest = new BigDecimal("0.5");
        else if (balance.compareTo(new BigDecimal("5000000")) >= 0)
            interest = new BigDecimal("0.07");
        else if (balance.compareTo(new BigDecimal("1000000")) >= 0)
            interest = new BigDecimal("0.04");
        else if (balance.compareTo(new BigDecimal("10000")) >= 0)
            interest = new BigDecimal("0.02");
        else
            interest = new BigDecimal("0.01");
        return interest;
    }
}
