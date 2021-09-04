package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SavingBank extends Bank {

    public void withdraw(SavingAccount account) throws Exception{
        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.
        int pos = account.getBalance().compareTo(account.getGoalAmount());
        if(pos<0){
            System.out.println("잔액이 목표 금액보다 적어 출금이 불가능합니다.");
        }

    }
    // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의
    @Override
    public SavingAccount createAccount() throws NoSuchElementException{
        try{
            SavingAccount savingAccount = new SavingAccount();
            String AccNo = String.format(new DecimalFormat("0000").format(seq++));
            System.out.println("소유자 이름을 입력하세요.");
            String owner = scanner.next();
            System.out.println("처음 입금할 금액을 입력하세요.");
            BigDecimal balance = scanner.nextBigDecimal();
            System.out.println("목표 금액을 입력하세요.");
            BigDecimal goalAmount = scanner.nextBigDecimal();
            savingAccount = new SavingAccount(AccNo,owner,balance,goalAmount);
            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);

            return savingAccount;
        }catch (Exception e){
            //TODO: 오류 throw
            System.out.println(e);
            return null;
        }
    }
}