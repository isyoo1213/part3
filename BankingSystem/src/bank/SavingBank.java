package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.util.NoSuchElementException;

public class SavingBank extends Bank {

    public void withdraw(SavingAccount account, BigDecimal amount, BigDecimal interest) {
        if(isGoalAmount(account)) {
            BigDecimal balance = account.withdraw(amount);
            System.out.println(df.format(amount.add(interest)) + " 원을 출금하였습니다.");
            System.out.println("추가 이자는 " + df.format(interest) + " 입니다.");
            System.out.println(account.getOwner() + "님의 " + account.getAccNo() + "계좌 잔고는 " + df.format(balance) + " 원입니다.");
        } else {
            System.out.printf("적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.\n", account.getGoalAmount());
        }
    }

    @Override
    public SavingAccount createAccount() throws NoSuchElementException {
        try {
            System.out.println("\n적금계좌를 발급합니다. 이름을 입력해주세요.");
            String owner = scanner.next();

            System.out.println("\n목표금액을 설정하시겠습니까? 미설정 시 100,000원이 설정됩니다. (Y/N)");
            String goalAmountSetStatus = scanner.next();

            BigDecimal goalAmount = new BigDecimal("100000");
            while(goalAmountSetStatus.equals("Y")) {
                System.out.println("\n목표금액을 입력해주세요.");
                if(scanner.hasNextBigDecimal()) {
                    BigDecimal temp = scanner.nextBigDecimal();
                    if(Account.isValidAmount(temp)) {
                        goalAmount = temp;
                    } else {
                        System.out.println("\n값은 0 혹은 마이너스금액이 입력될 수 없습니다. 다시 입력해주세요.");
                        continue;
                    }
                    break;
                } else {
                    System.out.println("\n값이 올바르지 않습니다. 금액을 정확히 입력해주세요.");
                    scanner.next();
                }
            }

            SavingAccount account = SavingAccount.create(seqNumbering(), owner, new BigDecimal("0"), goalAmount);
            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);
            return account;
        } catch (Exception e) {
            throw e;
        }
    }

    private boolean isGoalAmount(SavingAccount account) {
        return account.getBalance().compareTo(account.getGoalAmount()) >= 0;
    }
}