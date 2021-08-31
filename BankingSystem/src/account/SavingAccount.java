package account;

import bank.Bank;

import java.math.BigDecimal;

import static bank.Bank.countAcc;

//TODO: SavingAccount는 Account에서 상속을 받습니다.
public class SavingAccount extends Account{
    //TODO: 적금 계좌 클래스의 속성은 목표금액 속성을 추가로 가집니다.
    private BigDecimal goalAmount = new BigDecimal("100000");
//    private static String strGoalAmount = goalAmount.toString();

    public SavingAccount(){
        //TODO: 카테고리를 S로 설정해 줍니다.
        this.category = "S";
    }

    public SavingAccount(String accNo, String owner, BigDecimal balance) {
        // TODO
        String newAccNo = String.format("%04d", countAcc);
        String newOwner = "손님"+countAcc;
        BigDecimal newBalance = new BigDecimal("0");

        this.setActive(true);
        this.setCategory("S");

        this.accNo = accNo;
        this.owner = newOwner;
        this.balance = newBalance;

    }

    //TODO: GoalAmount getter 구현

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

//    public String getStrGoalAmount() {
//        strGoalAmount = goalAmount.toString();
//        return strGoalAmount;
//    }

    //TODO: getAccountInfo를 재정의하여 "목표 금액"도 노출해줍니다.

    public void getAccountInfo(SavingAccount account) {
        //TODO
        System.out.printf("\n계좌종류: %s | 계좌번호: %s | 계좌주명: %s | 잔액: %s원 | 목표금액: %s원\n", account.category, account.accNo, account.owner, account.balance, account.goalAmount);
    }
}
