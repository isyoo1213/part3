package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;

import static account.Category.S;

public class SavingBank extends Bank {

    @Override
    public void withdraw() {
        // 계좌번호 입력
        Account account;
        while(true){
            System.out.println("\n출금하시려는 계좌번호를 입력하세요.");
            String accNo = scanner.next();
            if(isExistAccount(accNo)) {
                account = findAccount(accNo);
                break;
            } else {
                System.out.println("\n입력하신 계좌를 찾을 수 없습니다.");
            }
        }

        // 출금처리
        BigDecimal amount;
        while(true) {
            System.out.println("\n출금할 금액을 입력하세요.");
            if(scanner.hasNextBigDecimal()) {
                amount = scanner.nextBigDecimal();
                if(!account.isPossibleWithdraw(amount)) {
                    System.out.println("\n해당금액은 출금할 수 없습니다. 금액과 잔고를 다시 확인하세요.");
                    continue;
                }
            } else {
                System.out.println("잘못된 금액 형식입니다. 다시 입력하세요.");
                scanner.next();
                continue;
            }

            if(account.getCategory() == S) {
                SavingAccount savingAccount = (SavingAccount)account;
                int compareTo = account.getBalance().compareTo(savingAccount.getGoalAmount());
                if(compareTo >= 0) {
                    break;
                } else {
                    System.out.printf("적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.\n", savingAccount.getGoalAmount());
                    return;
                }
            } else {
                break;
            }
        }

        InterestCalculator calculator = calculatorMap.get(account.getCategory());
        BigDecimal interest = calculator.getInterest(amount);

        BigDecimal balance = account.withdraw(amount);
        System.out.println(df.format(amount.add(interest)) + " 원을 출금하였습니다.");
        System.out.println("추가 이자는 " + df.format(interest) + " 입니다.");
        System.out.println(account.getOwner() + "님의 " + account.getAccNo() + "계좌 잔고는 " + df.format(balance) + " 원입니다.");
    }

    @Override
    public SavingAccount createAccount(String owner) {
        // 계좌번호 채번
        // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
        SavingAccount account = SavingAccount.create(seqNumbering(), owner, new BigDecimal("0"), new BigDecimal("100000"));
        System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);
        return account;
    }
}