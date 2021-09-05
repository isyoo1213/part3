package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

public class SavingBank extends Bank {

    public void withdraw(SavingAccount account) throws Exception{
        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.
        BigDecimal balance = account.getBalance();
        BigDecimal goalAmount = account.getGoalAmount();
        BigDecimal total = balance.subtract(goalAmount);
        if (goalAmount.compareTo(total) >= 0){
            System.out.println("\n출금할 금액을 입력하세요.");
            BigDecimal amount = scanner.nextBigDecimal();
            try {
                if(withdrawAccount.getBalance().compareTo(amount) >= 0) {
                    balance = balance.add(amount);
                    withdrawAccount.setBalance(balance);
                    System.out.printf(amount + "원이 출금 되었습니다.");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("출금 금액이 목표금액을 초과했습니다.");
            withdraw();
        }
    }
    // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의
    @Override
    public Account createAccount() throws NoSuchElementException{
        SavingAccount account = null;
        try{
            System.out.println("고객님의 이름을 입력해주세요");
            String owner = scanner.next();
            System.out.println("목표 금액을 입력해주세요");
            BigDecimal goalAmount = scanner.nextBigDecimal();
            account = new SavingAccount("0000"+seq,owner,goalAmount,goalAmount);
            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);
            System.out.println("고객님의 계좌번호는 "+account.getAccNo()+"입니다.");
            seq++;

            return account;
        }catch (Exception e){
            //TODO: 오류 throw
            e.printStackTrace();
            return createAccount();
        }
    }
}