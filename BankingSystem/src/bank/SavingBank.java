package bank;

import account.SavingAccount;
import java.math.BigDecimal;

public class SavingBank extends Bank {

    public BigDecimal withdraw(SavingAccount account) throws AccountException, AmountException, BalanceException{

        SavingInterestCalculator savingInterestCalculator = new SavingInterestCalculator();
        InterestCalculator sic = (InterestCalculator) savingInterestCalculator;
        interestCalculatorHashMap.put("S", sic);
        BigDecimal savingWithdrawAmount = null;

        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.
        boolean savingWithrawActive = true;
        while(savingWithrawActive) {
            try {
                if (account.getBalance().compareTo(account.getGoalAmount()) < 0) {
                    throw new AmountException("적금 계좌의 잔액이 적금 목표 금액을 채우지 못했습니다.");
                } else {
                    System.out.println("\n출금할 금액을 입력하세요.");
                    String strAmount = scanner.next();
                    BigDecimal withdrawAmount;

                    if(!strAmount.matches("[0-9]+")){
                        throw new AmountException("금액은 0 이상의 소수점을 제외한 숫자 0~9의 조합으로만 입력해주세요.");
                    } else {
                        withdrawAmount = new BigDecimal(strAmount);
                    }

                    if (withdrawAmount.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new BalanceException("올바른 출금 금액을 입력해주세요.");
                    } else if (account.getBalance().compareTo(withdrawAmount) < 0) {
                        throw new AmountException("잔액이 모자랍니다.");
                    } else {
                        this.findAccount(account.getAccNo()).withdraw(withdrawAmount);
                        account.setBalance(this.findAccount(account.getAccNo()).getBalance());
                        savingWithdrawAmount = withdrawAmount;
                    }
                    break;
                }
            } catch (AmountException e){
                System.out.println(e.getMessage());
            } catch (BalanceException e){
                System.out.println(e.getMessage());
            }
            break;
        }
        return savingWithdrawAmount;
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

        String newAccNo = String.format("%04d", this.getSeq());
        BigDecimal newBalance = new BigDecimal("0");

        SavingAccount account = new SavingAccount(newAccNo, newOwner, newBalance, newGoalAmount);
        account.setActive(true);
        account.setCategory("S");

        System.out.printf("\n%s님 적금 계좌가 발급되었습니다.\n", newOwner);
        account.getAccountInfo(account); //출력

        this.setSeq(this.getSeq() + 1);

        return account;

    }
}