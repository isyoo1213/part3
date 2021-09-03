package bank;

import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;

public class SavingBank extends Bank {

    public void withdraw(SavingAccount account) throws Exception{
        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.
        try{
            if(account.getGoalAmount().compareTo(account.getBalance()) > 0){
                throw new Exception();
            }
        }catch (Exception e){
            e.printStackTrace();

            System.out.printf("적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.", account.getGoalAmount());
        }
    }
    // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의
    @Override
    public SavingAccount createAccount() throws NoSuchElementException{
        try{
            String owner = "박현준";
            BigDecimal balance = new BigDecimal("0");
            String account_num = "0000" + seq++;
            int goal = -1;

            while(goal < 0){
                System.out.print("목표금액을 설정해주세요 : ");
                goal = scanner.nextInt();

                if(goal > 0){
                    System.out.printf("목표금액이 %d원으로 설정되었습니다.", goal);

                    break;
                }

                System.out.println("목표금액은 음수가 될 수 없습니다. 다시 입력 부탁드리겠습니다.");
            }

            BigDecimal goalAmount = new BigDecimal(goal);

            return new SavingAccount(account_num, owner, balance, goalAmount);
        }catch (Exception e){
            //TODO: 오류 throw
            e.printStackTrace();

            throw e;
        }
    }
}