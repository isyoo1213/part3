package bank;

import account.SavingAccount;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;


public class SavingBank extends Bank {


    public void withdraw(SavingAccount account) throws Exception {
        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.
        System.out.printf("적금 계좌는 잔액이 목표 금액(%s) 이상이어야 출금 가능합니다.현재 %s님 잔액은 %s 입니다.\n", df.format(account.getGoalAmount()), account.getOwner(), df.format(account.getBalance()));
        if (account.getBalance().compareTo(account.getGoalAmount()) < 0) {
            throw new Exception("잔액이 목표금액보다 적어 출금이 불가능합니다.");
        } else {
            System.out.println("\n출금이 가능합니다.");
        }
    }

    // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의
    @Override
    public SavingAccount createAccount() throws NoSuchElementException{
        try{
            System.out.println("적금 계좌를 생성합니다.");
            BigDecimal balance = null;

            //계좌번호 채번
            String accNo = String.format(new DecimalFormat("0000").format(++seq));

            // 소유자 입력
            System.out.println("소유주명: ");
            String owner = scanner.next();

            //목표금액설정
            System.out.println("목표 금액: ");
            String goalAmountini = scanner.next();
            //목표금액에 문자가 들어갈경우 제거
            String numberedgoalAmountini = goalAmountini.replaceAll("[^0-9]", "");
            //목표금액에 숫자가 없을 경우 재입력
            while (numberedgoalAmountini == "") {
                System.out.println("목표금액을 숫자로 다시 입력해주세요");
                goalAmountini = scanner.next();
                numberedgoalAmountini = goalAmountini.replaceAll("[^0-9]", "");
            }
            BigDecimal goalAmount = new BigDecimal(numberedgoalAmountini);
            System.out.printf("\n목표금액을 %s원으로 설정하였습니다.\n", goalAmount);

            //거치금액 설정
            System.out.println("금액을 거치하시겠습니까? (Y or N)");
            String answer = scanner.next();
            //Y나 y를 입력할경우 예치 시작
            if (answer.equals("Y")||answer.equals("y")) {
                System.out.println("예치하실 금액을 입력해주세요.");
                String balanceini = scanner.next();
                //예치금액에 문자가 들어갈경우 제거
                String numberedbalanceini = balanceini.replaceAll("[^0-9]","");
                //예치금액에 숫자가 없을경우 재입력
                while(numberedbalanceini == "") {
                    System.out.println("예치 금액을 숫자로 다시 입력해주세요");
                    balanceini = scanner.next();
                    numberedbalanceini = balanceini.replaceAll("[^0-9]","");
                }
                balance = new BigDecimal(numberedbalanceini);
            }
            else {
                balance = new BigDecimal(0);
                System.out.println("금액을 예치하지 않습니다. 입금기능을 이용해주세요");
            }
            SavingAccount account = new SavingAccount(accNo, owner, balance, goalAmount);
            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);
            return account;
        }catch (Exception e){
            //TODO: 오류 throw
            System.out.println("계좌생성 에러");
        }
        return null;
    }
}