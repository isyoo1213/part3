package bank;

import account.SavingAccount;

import java.math.BigDecimal;

public class SavingBank extends Bank {

    public void withdraw(SavingAccount account, BigDecimal amount, BigDecimal interest) {
        if(isGoalAmount(account)) {
            BigDecimal balance = account.withdraw(amount);
            System.out.println(df.format(amount.add(interest)) + " 원을 출금하였습니다.");
            System.out.println("추가 이자는 " + df.format(interest) + " 입니다.");
            System.out.println(account.getOwner() + "님의 " + account.getAccNo() + "계좌 잔고는 " + df.format(balance) + " 원입니다.");
        } else {
            System.out.printf("적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.\n", account.getGoalAmount());
        }
    }

    @Override
    public SavingAccount createAccount(String owner) {
        // 계좌번호 채번
        // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
        SavingAccount account = SavingAccount.create(seqNumbering(), owner, new BigDecimal("0"), new BigDecimal("100000"));
        System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);
        return account;
    }

    private boolean isGoalAmount(SavingAccount account) {
        return account.getBalance().compareTo(account.getGoalAmount()) >= 0;
    }
}