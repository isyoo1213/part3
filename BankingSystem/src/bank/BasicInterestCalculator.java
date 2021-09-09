package bank;

import java.math.BigDecimal;

// TODO: InterestCalculator 인터페이스 구현한 BasicInterestCalculator 클래스

public class BasicInterestCalculator implements InterestCalculator{

    @Override
    public BigDecimal getInsterest(BigDecimal balance) {

        accountBalanceTierList.add(BigDecimal.valueOf(10000000));
        accountBalanceTierList.add(BigDecimal.valueOf(5000000));
        accountBalanceTierList.add(BigDecimal.valueOf(1000000));
        accountBalanceTierList.add(BigDecimal.valueOf(10000));

        accountInterestRatioTierList.add(BigDecimal.valueOf(0.5));
        accountInterestRatioTierList.add(BigDecimal.valueOf(0.07));
        accountInterestRatioTierList.add(BigDecimal.valueOf(0.04));
        accountInterestRatioTierList.add(BigDecimal.valueOf(0.02));
        accountInterestRatioTierList.add(BigDecimal.valueOf(0.01));

        BigDecimal interest;
        if(balance.compareTo(accountBalanceTierList.get(0)) >= 0){
            interest = balance.multiply(accountInterestRatioTierList.get(0));
        } else if(balance.compareTo(accountBalanceTierList.get(1)) >= 0){
            interest = balance.multiply(accountInterestRatioTierList.get(1));
        } else if(balance.compareTo(accountBalanceTierList.get(2)) >= 0){
            interest = balance.multiply(accountInterestRatioTierList.get(2));
        } else if(balance.compareTo(accountBalanceTierList.get(3)) >= 0){
            interest = balance.multiply(accountInterestRatioTierList.get(3));
        } else {
            interest = balance.multiply(accountInterestRatioTierList.get(4));
        }
        return interest;

    }

}
