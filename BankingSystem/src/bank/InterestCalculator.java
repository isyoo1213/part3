package bank;

import java.math.BigDecimal;

public interface InterestCalculator {
    //TODO: 잔액에 대한 이자 금액을 반환하는 메서드 ( BigDecimal getInterest(balance))를 선언합니다.
     static final BigDecimal RANK1 = BigDecimal.valueOf(10000000);
     static final BigDecimal RANK2 = BigDecimal.valueOf(5000000);
     static final BigDecimal RANK3 = BigDecimal.valueOf(1000000);
     static final BigDecimal RANK4 = BigDecimal.valueOf(10000);

    BigDecimal getInterest(BigDecimal balance);
}
