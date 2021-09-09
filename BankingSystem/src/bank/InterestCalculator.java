package bank;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface InterestCalculator {
    //TODO: 잔액에 대한 이자 금액을 반환하는 메서드 ( BigDecimal getInterest(balance))를 선언합니다.

    ArrayList<BigDecimal> accountBalanceTierList = new ArrayList<>();
    ArrayList<BigDecimal> accountInterestRatioTierList = new ArrayList<>();

    ArrayList<BigDecimal> SavingAccountBalanceTierList = new ArrayList<>();
    ArrayList<BigDecimal> SavingAccountInterestRatioTierList = new ArrayList<>();

    BigDecimal getInsterest(BigDecimal balance);

}
