package bank;

import account.SavingAccount;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SavingBank extends Bank {

    public void withdraw(SavingAccount account) throws Exception {
        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.
        BigDecimal interest = interestCalculators.get("S").getInterest(account.getBalance());
        boolean isWithdrawable = account.getBalance().compareTo(account.getGoalAmount().multiply(interest)) >= 0;

        if (!isWithdrawable) {
            throw new Exception("적금 계좌는 잔액이 목표 금액(%s 원) 이상이어야 출금 가능합니다.");
        }  else {
            System.out.println("출금할 금액을 입력하세요");
            BigDecimal amount = scanner.nextBigDecimal();
            account.withdraw(amount);
            System.out.printf("이자 포함 출금액은: " + df.format(amount.multiply(interest)) + "입니다");
        }
    }

    // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의
    @Override
    public SavingAccount createAccount(String owner) throws NoSuchElementException{
        try {
            System.out.printf( "%s 계좌의 목표 금액을 입력하세요 ", owner );
            BigDecimal goal =  scanner.nextBigDecimal();
            return new SavingAccount("0000".concat(Integer.toString(++seq)), owner, DEFAULT_VALUE, goal);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return null;
            //TODO: 오류 throw
        }
    }
}