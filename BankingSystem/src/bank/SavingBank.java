package bank;

import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;

public class SavingBank extends Bank {

    public void withdraw(SavingAccount account) throws Exception{
        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.
    }

    // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의

    @Override
    public SavingAccount createAccount() throws OwnerException{

        System.out.println("\n적금 계좌 생성을 시작합니다.");
        System.out.println("생성하시려는 적금계좌의 계좌주 성함을 입력해주세요.");
        String newOwner = scanner.next();

        BigDecimal newGoalAmount = new BigDecimal("0");
        boolean createActive = true;
        while(createActive){
            System.out.println("\n적금 계좌의 목표 금액을 입력해주세요.(100만원 이상)");
            String strGoalAmount = scanner.next();
            if (Integer.parseInt(strGoalAmount) < 1000000){
                System.out.println("100만원 이상의 금액을 입력해주세요.");
                createActive = false;
            } else {
                newGoalAmount = new BigDecimal(strGoalAmount);
                createActive = false;
            }
        }

        String newAccNo = String.format("%04d", this.getCountAcc());
        BigDecimal newBalance = new BigDecimal("0");

        SavingAccount account = new SavingAccount(newAccNo, newOwner, newBalance, newGoalAmount);
        account.setActive(true);
        account.setCategory("S");

        System.out.printf("\n%s님 적금 계좌가 발급되었습니다.\n", newOwner);
        account.getAccountInfo(account); //출력

        this.setCountAcc(this.getCountAcc() + 1);

        return account;

    }
}