package bank;

import account.SavingAccount;
import status.SavingAccountWithdrawStatus;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

public class SavingBank extends Bank {

    public void withdraw(SavingAccount account) throws SavingAccountWithdrawStatus {
        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.
        BigDecimal goalAmount = account.getGoalAmount();
        BigDecimal balance = account.getBalance();
        if (balance.compareTo(goalAmount) < 0) {
            throw new SavingAccountWithdrawStatus(balance, goalAmount);
        }

    }

    // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의
    @Override
    public SavingAccount createAccount() throws NoSuchElementException {
        SavingAccount account = null;
        try {
            String accNo = "0000" + seq;
            System.out.printf("\n계좌 생성을 시작합니다.\n");
            String owner = remitAccountName();
            BigDecimal goalAmount = remitAmount("목표금액을 입력하세요.");
            account = new SavingAccount(accNo, owner, BigDecimal.ZERO, goalAmount);
            System.out.printf("%s님 계좌가 발급되었습니다.\n", owner);
            account.getAccountInfo();
        } catch (Exception e) {
            //TODO: 오류 throw
            throw e;
        }
        seq++;
        return account;
    }
}