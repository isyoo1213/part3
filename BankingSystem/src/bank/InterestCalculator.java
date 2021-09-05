package bank;

import java.math.BigDecimal;

public interface InterestCalculator {
    //TODO: 잔액에 대한 이자 금액을 반환하는 메서드 ( BigDecimal getInterest(balance))를 선언합니다.

    BigDecimal tier1 = new BigDecimal("10000000");
    BigDecimal tier2 = new BigDecimal("5000000");
    BigDecimal tier3 = new BigDecimal("1000000");
    BigDecimal tier4 = new BigDecimal("10000");
    BigDecimal tier1Ratio = new BigDecimal("0.5");
    BigDecimal tier2Ratio = new BigDecimal("0.07");
    BigDecimal tier3Ratio = new BigDecimal("0.04");
    BigDecimal tier4Ratio = new BigDecimal("0.02");
    BigDecimal tier5Ratio = new BigDecimal("0.01");

    BigDecimal getInsterest(BigDecimal balance);

}
