package bank;

import account.SavingAccount;
import java.math.BigDecimal;

public class SavingBank extends Bank {

    public boolean withdraw(SavingAccount account) throws AmountException {

        boolean savingWithdrawActive = true;

        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.

        try {
            if (account.getBalance().compareTo(account.getGoalAmount()) < 0) {
                savingWithdrawActive = false;
                throw new AmountException("적금 계좌의 잔액이 적금 목표 금액을 채우지 못했습니다.");
            }
        } catch (AmountException e){
            System.out.println(e.getMessage());
        }
        return savingWithdrawActive;
    }

    // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의

    @Override
    public SavingAccount createAccount() throws OwnerException, AmountException {

        boolean savingCreateAccountActive = true;
        boolean createActive = true;
        String newOwner = null;
        BigDecimal newGoalAmount = new BigDecimal("0");

        System.out.println("\n적금 계좌 생성을 시작합니다.");

        while(createActive){

            System.out.println("생성하시려는 적금계좌의 계좌주 성함을 입력해주세요.");
            newOwner = scanner.next();

            try {
                if (!newOwner.matches("[가-힣]{2,4}")) {
                    createActive = true;
                    throw new OwnerException("이름은 완성된 한글 2~4자로만 입력할 수 있습니다.");
                } else {
                    while(createActive){
                        System.out.println("\n적금 계좌의 목표 금액을 입력해주세요.(100만원 이상)");
                        String strGoalAmount = scanner.next();

                        try {
                            if (!strGoalAmount.matches("[0-9]+")) {
                                createActive = true;
                                throw new AmountException("금액은 0 이상의 소수점을 제외한 숫자 0~9의 조합으로만 입력해주세요.");
                            } else if (Integer.parseInt(strGoalAmount) < 1000000) {
                                createActive = true;
                                throw new AmountException("100만원 이상의 금액을 입력해주세요.");
                            } else {
                                savingCreateAccountActive = true;
                                newGoalAmount = new BigDecimal(strGoalAmount);
                                createActive = false;
                            }
                        } catch (AmountException e){
                            savingCreateAccountActive = false;
                            System.out.println(e.getMessage());
                        }
                    }
                }
            } catch (OwnerException e) {
                savingCreateAccountActive = false;
                System.out.println(e.getMessage());
            }

        }

        if(savingCreateAccountActive) {
            //적금 계좌번호 생성
            String newAccNo = String.format("%04d", Bank.getSeq());
            BigDecimal newBalance = new BigDecimal("0");
            //적금 계좌 인스턴스 생성
            SavingAccount account = new SavingAccount(newAccNo, newOwner, newBalance, newGoalAmount);

            System.out.printf("\n%s님 적금 계좌가 발급되었습니다.\n", newOwner);
            account.getAccountInfo(account); //출력

            Bank.setSeq(Bank.getSeq() + 1);

            return account;

        } else {
            return null;
        }

    }
}