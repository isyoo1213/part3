package bank;

import account.Account;
import account.Category;
import account.SavingAccount;
import exception.BankException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import static account.Account.*;
import static account.Category.*;

public class Bank {
    protected final Map<Category, InterestCalculator> calculatorMap = new HashMap<>();
    protected static final Scanner scanner = new Scanner(System.in);
    protected static int seq = 0;
    public static final DecimalFormat df = new DecimalFormat("#,###");

    public Bank() {
        createInterestCalculator();
    }

    void createInterestCalculator() {
        calculatorMap.put(N, new BasicInterestCalculator());
        calculatorMap.put(S, new SavingInterestCalculator());
    }

    public void withdraw() {
        Account account;
        BigDecimal amount;
        outer: while(true) {
            System.out.println("\n출금하시려는 계좌번호를 입력하세요.");
            String accNo = scanner.next();

            if(isExistAccount(accNo)) {
                account = findAccount(accNo);
                while(true) {
                    System.out.println("\n출금할 금액을 입력하세요.");
                    if(scanner.hasNextBigDecimal()) {
                        amount = scanner.nextBigDecimal();
                        if(account.isPossibleWithdraw(amount)) {
                            break outer;
                        } else {
                            System.out.println("\n해당금액은 출금할 수 없습니다. 금액과 잔고를 다시 확인하세요.");
                        }
                    } else {
                        System.out.println("\n잘못된 금액 형식입니다. 다시 입력하세요.");
                        scanner.next();
                    }
                }
            } else {
                System.out.println("\n입력하신 계좌를 찾을 수 없습니다.");
            }
        }

        InterestCalculator calculator = calculatorMap.get(account.getCategory());
        BigDecimal interest = calculator.getInterest(amount);

        if(account.getCategory() == S) {
            ((SavingBank)this).withdraw((SavingAccount)account, amount, interest);
        } else {
            BigDecimal balance = account.withdraw(amount);
            System.out.println(df.format(amount.add(interest)) + " 원을 출금하였습니다.");
            System.out.println("추가 이자는 " + df.format(interest) + " 입니다.");
            System.out.println(account.getOwner() + "님의 " + account.getAccNo() + "계좌 잔고는 " + balance + " 원입니다.");
        }
    }

    public void deposit() {
        Account account;
        BigDecimal amount;
        outer: while(true) {
            System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");
            String accNo = scanner.next();

            if(isExistAccount(accNo)) {
                account = findAccount(accNo);
                while(true) {
                    System.out.println("\n입금할 금액을 입력하세요.");
                    if(scanner.hasNextBigDecimal()) {
                        amount = scanner.nextBigDecimal();
                        if(isValidAmount(amount)) {
                            break outer;
                        } else {
                            System.out.println("\n금액은 0이나 마이너스금액을 입금할 수 없습니다.");
                        }
                    } else {
                        System.out.println("\n잘못된 금액 형식입니다. 다시 입력하세요.");
                        scanner.next();
                    }
                }
            } else {
                System.out.println("\n입력하신 계좌를 찾을 수 없습니다.");
            }
        }

        BigDecimal balance = account.deposit(amount);
        System.out.println(account.getOwner() + "님의 " + account.getAccNo() + "계좌 잔고는 " + df.format(balance) + " 원입니다.");
    }

    public Account createAccount() throws InputMismatchException {
        try {
            System.out.println("\n예금계좌를 발급합니다. 이름을 입력해주세요.");
            String owner = scanner.next();

            Account account = Account.create(seqNumbering(), owner, new BigDecimal("0"));
            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);
            return account;
        } catch (Exception e) {
            throw e;
        }
    }

    public Account findAccount(String accNo) {
        CentralBank centralBank = CentralBank.getInstance();

        List<Account> accounts = centralBank.getAccountList();
        for(Account account : accounts) {
            if(account.getAccNo().equals(accNo)) {
                if(account.isActive()) {
                    return account;
                } else {
                    throw new BankException("해당 계좌는 비활성화 상태입니다.");
                }
            }
        }

        throw new BankException("해당 계좌번호를 찾을 수 없습니다.");
    }

    public boolean isExistAccount(String accNo) {
        CentralBank centralBank = CentralBank.getInstance();

        boolean isExist = false;
        List<Account> accounts = centralBank.getAccountList();
        for(Account account : accounts) {
            if(account.getAccNo().equals(accNo)) {
                if(account.isActive()) {
                    isExist = true;
                }
            }
        }

        return isExist;
    }

    public void transfer() {
        while(true) {
            System.out.println("\n송금하시려는 계좌번호를 입력해주세요.");
            String accNo = scanner.next();
            Account account;
            if(isExistAccount(accNo)) {
                account = findAccount(accNo);
            } else {
                System.out.println("\n입력하신 계좌를 찾을 수 없습니다.");
                continue;
            }

            System.out.println("\n어느 계좌번호로 보내시려나요?");
            String targetAccNo = scanner.next();
            Account targetAccount;
            if(isExistAccount(targetAccNo)) {
                targetAccount = findAccount(targetAccNo);
            } else {
                System.out.println("\n입력하신 계좌를 찾을 수 없습니다.");
                continue;
            }

            if(account.equals(targetAccount)) {
                throw new BankException("본인 계좌로의 송금은 입금을 이용해주세요.");
            }

            if(account.getCategory() == S) {
                throw new BankException("적금 계좌로는 송금이 불가합니다.");
            }

            BigDecimal amount;
            while(true) {
                System.out.println("\n송금할 금액을 입력하세요.");
                if(scanner.hasNextBigDecimal()) {
                    amount = scanner.nextBigDecimal();
                    if(account.isPossibleWithdraw(amount)) {
                        break;
                    } else {
                        System.out.println("\n해당금액은 출금할 수 없습니다. 금액과 잔고를 다시 확인하세요.");
                    }
                } else {
                    System.out.println("\n잘못된 금액 형식입니다. 다시 입력하세요.");
                    scanner.next();
                }
            }

            BigDecimal balance = account.withdraw(amount);
            System.out.println(df.format(amount)  + " 원을 송금하였습니다.");
            System.out.println(account.getOwner() + "님의 " + account.getAccNo() + "계좌 잔고는 " + df.format(balance) + " 원입니다.");
            BigDecimal targetBalance = targetAccount.deposit(amount);
            System.out.println(targetAccount.getOwner() + "님의 " + targetAccount.getAccNo() + "계좌 잔고는 " + df.format(targetBalance) + " 원입니다.");
            break;
        }
    }

    String seqNumbering() {
        return "0000" + (++seq);
    }

}
