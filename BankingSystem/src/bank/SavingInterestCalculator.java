package bank;

import java.math.BigDecimal;

// TODO: InterestCalculator 인터페이스 구현한 SavingInterestCalculator 클래스

public class SavingInterestCalculator implements InterestCalculator{

    // TODO: 적금 계좌의 경우 잔액이 100만원 이상은 이자율이 50%, 그 외에는 1% 입니다.

    @Override
    public BigDecimal getInsterest(BigDecimal balance) {

        BigDecimal interest;

        SavingAccountBalanceTierList.add(BigDecimal.valueOf(1000000));

        SavingAccountInterestRatioTierList.add(BigDecimal.valueOf(0.5));
        SavingAccountInterestRatioTierList.add(BigDecimal.valueOf(0.01));

        if(balance.compareTo(SavingAccountBalanceTierList.get(0)) >= 0){
            interest = balance.multiply(SavingAccountInterestRatioTierList.get(0));
        } else {
            interest = balance.multiply(SavingAccountInterestRatioTierList.get(1));
        }
        return interest;

    }

}
