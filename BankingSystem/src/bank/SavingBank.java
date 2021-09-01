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
        boolean  isWithdrawable;
        BigDecimal goalFlag = BigDecimal.valueOf(0.5); // 목표금액대비 50%가 넘어야 출금가능

        if (account.getBalance().compareTo(account.getGoalAmount().multiply(goalFlag)) > 0) {
            isWithdrawable = true;
        } else {
            isWithdrawable = false;
        }

        if (!isWithdrawable) {
            throw new Exception("적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.");
        }

        super.withdraw();
    }

    // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의
    @Override
    public SavingAccount createAccount(String owner) throws NoSuchElementException{
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("목표 금액을 입력하세요");
            BigDecimal goal =  scanner.nextBigDecimal();
            return new SavingAccount("0000".concat(Integer.toString(++seq)), owner, DEFAULT_VALUE, goal);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return null;
            //TODO: 오류 throw
        }
    }
}