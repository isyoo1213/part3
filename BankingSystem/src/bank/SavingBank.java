package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;

public class SavingBank extends Bank {

    public void withdraw(SavingAccount account) throws Exception {
        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.

        // 잔액이 목표금액 미만일시 에러발생
        if (account.getBalance().compareTo(account.getGoalAmount()) < 0){
            throw new Exception ("적금 계좌는 잔액이 목표 금액(" + df.format(account.getGoalAmount()) + "원) 이상이어야 출금 가능합니다.");
        }
    }
        // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의
        @Override
        public SavingAccount createAccount () throws NoSuchElementException {
        try{
            SavingAccount account = new SavingAccount();

            System.out.print("이름: ");
            account.setOwner(scanner.next());

            seq += 1;
            account.setAccNo("0000" + seq);

            System.out.print("목표금액을 입력해주세요: ");
            account.setGoalAmount(scanner.nextBigDecimal());

            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", account.getOwner());

            return account;
        }
        catch (){
            //TODO: 오류 throw
        }
    }
}