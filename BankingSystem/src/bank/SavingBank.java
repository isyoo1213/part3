package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;

public class SavingBank extends Bank {

    public void withdraw(SavingAccount account) throws Exception{
        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.
        BigDecimal goalAmount = account.getGoalAmount();
        BigDecimal balance = account.getBalance();
        if(balance.compareTo(goalAmount)<0){
            System.out.printf("적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.\n",goalAmount.toString());
            throw new Exception("적금 인출은 잔고가 목표 금액보다 커야 가능합니다.");
        }
    }
    // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의
    @Override
    public SavingAccount createAccount() throws NoSuchElementException{
        SavingAccount account;
        try{
            // 계좌번호 채번
            System.out.println("계좌 소유자 이름을 입력하세요: ");
            String owner = scanner.nextLine();

            //목표 금액 입력
            System.out.println("적금 목표 금액을 입력하세요.");
            BigDecimal goalAmount = new BigDecimal(scanner.nextLine());
            // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
            seq++;
            account = new SavingAccount(df.format(new BigDecimal(seq)), owner, BigDecimal.ZERO, goalAmount);
            CentralBank.getInstance().getAccountList().add(account);
            //TODO
            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);
            System.out.printf("계좌번호는 %s입니다.", account.getAccNo());
            return account;
        } catch (NumberFormatException e) {
            //TODO: 오류 throw
            System.out.println("숫자를 입력하셔야 합니다!");
            System.out.println("적금 계좌 생성을 다시 시작합니다.\n");
            account = createAccount();
            return account;
        }
    }
}