package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class SavingBank extends Bank {

    public void withdraw(SavingAccount account) throws Exception {
        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.
        if (account.getBalance().compareTo(account.getGoalAmount()) >= 0) {
            throw new Exception("출금할 계좌가 존재하지 않습니다.");
        } else {
            System.out.print("출금할 금액을 입력하세요 : ");
            BigDecimal amount = scanner.nextBigDecimal();
            if (account.getBalance().compareTo(amount) >= 0 && account.getBalance().compareTo(account.getGoalAmount()) >= 0) {
                account.withdraw(amount);
                System.out.printf("출금액은 %s원 입니다.\n잔액 : %s", amount, account.getBalance());
            } else {
                if (account.getBalance().compareTo(amount) < 0)
                    System.out.println("잔액이 부족합니다.");
                else
                    System.out.println("목표 금액보다 잔액이 적습니다.");
            }
        }
    }

    // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의
    @Override
    public SavingAccount createAccount() throws NoSuchElementException {
        try {
            System.out.println("예금주 성함을 입력해 주세요.");
            String owner = scanner.next();

            String accNo = new DecimalFormat("0000").format(seq++);

            System.out.println("\n목표 금액을 설정하세요. ");
            BigDecimal goalAmount = scanner.nextBigDecimal();
            SavingAccount account = new SavingAccount(accNo, owner, BigDecimal.ZERO, goalAmount);
            CentralBank.getInstance().getAccountList().add(account);
            return account;
        } catch (Exception e) {
            //TODO: 오류 throw
            throw e;
        }
    }
}