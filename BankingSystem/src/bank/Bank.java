package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bank {
    //TODO: Bank 클래스는 출금, 입금, 송금, 계좌 생성, 계좌 검색 기능들을 갖고 있습니다.
    protected static Scanner scanner = new Scanner(System.in);
    //계좌번호 순차적 부여를 위한 static 선언
    protected static int seq = 0;
    public static DecimalFormat df = new DecimalFormat("#,###");

    //뱅킹 시스템의 기능들
    public void withdraw() throws Exception {
        //TODO: 출금 메서드 구현
        //TODO: key, value 형태의 HashMap을 이용하여 interestCalculators 구현
        //여기서 key: category, value: 각 category의 InterestCalculator 인스턴스
        HashMap<String, InterestCalculator> hashmap = new HashMap<String, InterestCalculator>();
        hashmap.put("N", (InterestCalculator) new BasicInterestCalculator());
        hashmap.put("S", (InterestCalculator) new SavingInterestCalculator());

        // 계좌번호 입력
        Account account;
        try {
            while (true) {
                System.out.println("\n출금하시려는 계좌번호를 입력하세요.");
                String accNo = scanner.next();
                // TODO: 검색 -> 적금 계좌이면 적금 계좌의 출금 메소드 호출 -> 완료시 break
                account = findAccount(accNo);
                if (account == null) {
                    System.out.println("\n존재하지 않는 계좌입니다. 다시 입력해주세요");
                }
                if (account != null && account.getCategory().equals("N")) {
                    break;
                }
                if (account != null && account.getCategory().equals("S")) {
                    SavingBank savingBank = new SavingBank();
                    savingBank.withdraw((SavingAccount) account);
                    break;
                }
            }
            // 출금처리
            System.out.println("\n출금할 금액을 입력하세요.");
            BigDecimal amount = scanner.nextBigDecimal();

            //출금액이 0일 때 에러 발생
            if (amount.equals(BigDecimal.valueOf(0))) {
                throw new Exception("출금액을 입력하세요.");
            }
            // 출금액이 잔액보다 크거나, 0보다 작을 때 에러 발생
            if (account.getBalance().compareTo(amount) < 0 || amount.compareTo(BigDecimal.valueOf(0)) < 0) {
                throw new Exception("출금액을 확인해주세요.");
            }
            // TODO: interestCalculators 이용하여 이자 조회 및 출금
            BigDecimal interestRatio = null;
            if (account.getCategory().equals("N")) {
                interestRatio = hashmap.get("N").getInterest(account.getBalance());
            }
            if (account.getCategory().equals("S")) {
                interestRatio = hashmap.get("S").getInterest(account.getBalance());
            }

            account.withdraw(amount);

            System.out.printf("%s원이 출금되었습니다. 잔액: %s원 | 이자: %s원", df.format(amount), df.format(account.getBalance()), amount.multiply(interestRatio).setScale(0, RoundingMode.CEILING));

        } catch (InputMismatchException e) {
            System.out.println("숫자를 입력해주세요.");
            scanner.nextLine();
            withdraw();
        } catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("예상치 못한 오류로 출금을 다시 시도합니다.");
            withdraw();
        }
    }

    public void deposit() {
        //TODO: 입금 메서드 구현
        Account account;
        try {
            while (true) {
                System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");
                String inputAccNo = scanner.next();
                // 존재하지 않는 계좌이면 다시 물어보기
                account = findAccount(inputAccNo);
                if (account == null) {
                    System.out.println("\n존재하지 않는 계좌입니다.");
                }
                if (account != null) {
                    break;
                }
            }
            // TODO: 입금 처리
            System.out.println("\n입금하실 금액을 입력하세요.");
            BigDecimal putMoney = scanner.nextBigDecimal();

            account.deposit(putMoney);

            System.out.println("입급이 완료되었습니다. 현재 계좌 잔액은 " + df.format(account.getBalance()) + "원 입니다.");

        } catch (InputMismatchException e) {
            System.out.println("숫자를 입력해주세요.");
            scanner.nextLine();
            deposit();
        } catch (Exception e) {
            System.out.println("예상치 못한 오류로 입금을 다시 시도합니다.");
            scanner.nextLine();
            deposit();
        }

    }

    public Account createAccount() {
        //TODO: 계좌 생성하는 메서드 구현
        // 계좌번호 채번
        // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
        //TODO
        Account account = new Account();

        System.out.print("이름: ");
        account.setOwner(scanner.next());

        seq += 1;
        account.setAccNo("0000" + seq);

        String owner = account.getOwner();
        System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);

        return account;
    }

    public Account findAccount(String accNo) {
        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현
        Account account = null;
        CentralBank centralBank = CentralBank.getInstance();

        int sizeOfBank = centralBank.getAccountList().size();
        for (int i = 0; i < sizeOfBank; i++) {
            if (accNo.compareTo(centralBank.getAccountList().get(i).getAccNo()) == 0) {
                account = centralBank.getAccountList().get(i);
                break;
            }
        }
        return account;
    }

    public void transfer() throws Exception {
        //TODO: 송금 메서드 구현
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록
        //TODO
        Account senderAccount;
        Account receiverAccount;
        try {
            while (true) {
                System.out.println("\n송금하시려는 계좌번호를 입력해주세요.");
                String senderAccNo = scanner.next();
                senderAccount = findAccount(senderAccNo);
                if (senderAccount == null) {
                    throw new Exception("\n존재하지 않는 계좌입니다. 다시 입력해주세요");
                }
                if (senderAccount != null) {
                    break;
                }
            }
            //TODO
            while (true) {
                System.out.println("\n어느 계좌번호로 보내시려나요?");
                String receiverAccNo = scanner.next();
                receiverAccount = findAccount(receiverAccNo);
                if (receiverAccount == null) {
                    throw new Exception("\n존재하지 않는 계좌입니다. 다시 입력해주세요");
                }
                if (receiverAccount != null) {
                    break;
                }
            }
            //TODO
            if (senderAccount.getAccNo().equals(receiverAccount.getAccNo())) {
                throw new Exception("\n본인 계좌로의 송금은 입금을 이용해주세요.");
            }
            //TODO
            if (receiverAccount.getCategory().equals("S")) {
                throw new Exception("\n적금 계좌로는 송금이 불가합니다.");
            }
            SavingBank savingBank = new SavingBank();
            savingBank.withdraw((SavingAccount) senderAccount);
            //TODO
            System.out.println("\n송금할 금액을 입력하세요.");
            BigDecimal amount = scanner.nextBigDecimal();

            // 송금액이 0일 때 에러 발생
            if (amount.equals(BigDecimal.valueOf(0))) {
                throw new Exception("송금액을 입력하세요.");
            }
            // 송금액이 잔액보다 크거나, 0보다 작을 때 에러 발생
            if (senderAccount.getBalance().compareTo(amount) < 0 || amount.compareTo(BigDecimal.valueOf(0)) < 0) {
                throw new Exception("송금액을 확인해주세요.");
            }
            senderAccount.withdraw(amount);
            receiverAccount.deposit(amount);

            System.out.println("송금이 완료되었습니다. 현재 계좌 잔액은 " + df.format(senderAccount.getBalance()) + "원 입니다.");

        } catch (InputMismatchException e) {
            System.out.println("숫자를 입력해주세요.");
            scanner.nextLine();
            transfer();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("예상치 못한 오류로 입금을 다시 시도합니다.");
            scanner.nextLine();
            transfer();
        }
    }

}
