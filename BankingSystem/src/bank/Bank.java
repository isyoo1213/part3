package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class Bank {
    //TODO: Bank 클래스는 출금, 입금, 송금, 계좌 생성, 계좌 검색 기능들을 갖고 있습니다.
    protected static Scanner scanner = new Scanner(System.in);
    protected static int seq = 0;
    public static DecimalFormat df = new DecimalFormat("#,###");

    // 뱅킹 시스템의 기능들
    public void withdraw() throws Exception {
        //TODO: 출금 메서드 구현
        //TODO: key, value 형태의 HashMap을 이용하여 interestCalculators 구현
        //여기서 key: category, value: 각 category의 InterestCalculator 인스턴스
        Map<String, InterestCalculator> interestCalculators = new HashMap<>();
        interestCalculators.put("N", new BasicInterestCalculator());
        interestCalculators.put("S", new SavingInterestCalculator());

        // 계좌번호 입력
        Account account;
        boolean flag = true;
        while (true) {
            System.out.println("\n출금하시려는 계좌번호를 입력하세요.");
            String accNo = scanner.next();
            account = findAccount(accNo);
            // TODO: 검색 -> 적금 계좌이면 적금 계좌의 출금 메소드 호출 -> 완료시 break
            if (account.getCategory().equals("S")) {
                SavingBank savingBank = new SavingBank();
                savingBank.withdraw((SavingAccount) account);
                flag = false;
                break;
            } else if (account.getCategory().equals("N")) {
                break;
            }
        }
        // 출금처리
        if (flag) {
            System.out.println("\n출금할 금액을 입력하세요.");
            BigDecimal amount = scanner.nextBigDecimal();
            // TODO: interestCalculators 이용하 이자 조회 및 출금
            try {
                BigDecimal amount2 = account.withdraw(amount);
                BigDecimal interest = interestCalculators.get(account.getCategory()).getInterest(account.getBalance()).multiply(account.getBalance());
                // 이자를 여기다가 붙혀줘야 할까...?
                // 소수점 없애기 위해 .intValue() 후 다시 new BigDecimal
//                account.setBalance(new BigDecimal(account.getBalance().add(interest).intValue()));
                if (amount2 != null)
                    System.out.printf("%s원 출금에 성공하였습니다. \n현재 잔액 : %s, 이자 : %s", amount, account.getBalance(), interest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deposit() throws Exception {
        //TODO: 입금 메서드 구현
        // 존재하지 않는 계좌이면 다시 물어보기
        Account account;
        System.out.println("\n입금하시려는 계좌번호를 입력해 주세요.");
        account = findAccount(scanner.next());
        while (account == null) {
            System.out.println("계좌번호를 다시 입력해 주세요.");
            account = findAccount(scanner.next());
        }
        // TODO: 입금 처리
        System.out.println("\n입금할 금액을 입력하세요.");
        BigDecimal amount = scanner.nextBigDecimal();
        account.deposit(amount);
        System.out.printf("%s원 입금에 성공하였습니다. \n현재 잔액 : %s", amount, account.getBalance());
    }

    public Account createAccount() throws InputMismatchException {
        //TODO: 계좌 생성하는 메서드 구현
        Account account;
        try {
            System.out.println("예금주 성함을 입력해 주세요.");
            String owner = scanner.next();
            // 계좌번호 채번
            // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
            //TODO

            String accNo = String.format(new DecimalFormat("0000").format(seq++));
            account = new Account(accNo, owner, BigDecimal.ZERO);
            CentralBank.getInstance().getAccountList().add(account);
            System.out.printf("\n%s님 계좌가 발급되었습니다.\n발급된 계좌번호 : %s\n", owner, account.getAccNo());
            return account;
        } catch (Exception e) {
            //TODO: 오류 throw
            throw e;
        }
    }

    public Account findAccount(String accNo) throws Exception {
        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현
        CentralBank centralBank = CentralBank.getInstance();
        ArrayList<Account> accounts = centralBank.getAccountList();
        Account account;

        for (int i = 0; i < accounts.size(); i++) {
            account = accounts.get(i);
            if (account.getAccNo().equals(accNo)) {
                return account;
            }
        }
        return null;
    }

    public void transfer() throws Exception {
        //TODO: 송금 메서드 구현
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록
        //TODO
        System.out.println("\n송금하시려는 계좌번호를 입력해주세요.");
        Account myAccount = findAccount(scanner.next());
        while (myAccount == null) {
            System.out.println("\n계좌번호를 다시 입력해주세요.");
            myAccount = findAccount(scanner.next());
        }
        //TODO
        System.out.println("\n어느 계좌번호로 보내시려나요?");
        Account toAccount = findAccount(scanner.next());
        while (toAccount == null) {
            System.out.println("\n계좌번호를 다시 입력해주세요.");
            toAccount = findAccount(scanner.next());
        }
        //TODO
        if (toAccount.equals(myAccount)) {
            System.out.println("\n본인 계좌로의 송금은 입금을 이용해주세요.");
            return;
        }
        //TODO
        else if (toAccount.getCategory().equals("S")) {
            System.out.println("\n적금 계좌로는 송금이 불가합니다.");
            return;
        }
        //TODO
        System.out.println("\n송금할 금액을 입력하세요.");
        //TODO
        BigDecimal amount = scanner.nextBigDecimal();
        BigDecimal isMyAccountWithdraw = myAccount.withdraw(amount);
        if (isMyAccountWithdraw != null) {
            toAccount.setBalance(toAccount.getBalance().add(amount));
            System.out.printf("송금 받은 계좌의 잔액 : %s", toAccount.getBalance());
        }
    }
}
