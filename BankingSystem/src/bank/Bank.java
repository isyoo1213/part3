package bank;

import account.Account;
import account.SavingAccount;
import exceptions.WithdrawException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class Bank {
    //TODO: Bank 클래스는 출금, 입금, 송금, 계좌 생성, 계좌 검색 기능들을 갖고 있습니다.
    protected static Scanner scanner = new Scanner(System.in);
    protected static int seq = 0;
    public static DecimalFormat df = new DecimalFormat("#,###");
    public static String convertToString(BigDecimal val) { return df.format(val.setScale(0, RoundingMode.FLOOR));}

    // 뱅킹 시스템의 기능들
    public void withdraw() {
        //TODO: 출금 메서드 구현
        //TODO: key, value 형태의 HashMap을 이용하여 interestCalculators 구현
        //여기서 key: category, value: 각 category의 InterestCalculator 인스턴스
        HashMap<String, InterestCalculator> interestCalculators = new HashMap<>();
        interestCalculators.put("N", new BasicInterestCalculator());
        interestCalculators.put("S", new SavingInterestCalculator());

        // 계좌번호 입력
        System.out.printf("\n출금을 시작합니다.\n");
        Account account;
        InterestCalculator interestCalculator;
        while(true){
            // TODO: 검색 -> 적금 계좌이면 적금 계좌의 출금 메소드 호출 -> 완료시 break
            account = requestAccountNumber("출금하시려는 계좌번호를 입력하세요.");
            if (account.getCategory().equals("S")) {
                try {
                    if (this instanceof SavingBank) ((SavingBank)this).withdraw((SavingAccount) account);
                    else new SavingBank().withdraw((SavingAccount) account);
                } catch (WithdrawException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                interestCalculator = interestCalculators.get("S");
            } else {
                interestCalculator = interestCalculators.get("N");
            }
            break;
        }
        System.out.println("출금하시려는 계좌의 정보입니다.");
        account.getAccountInfo();
        // 출금처리
        BigDecimal amount = requestAmount("출금할 금액을 입력하세요.");
        // TODO: interestCalculators 이용하 이자 조회 및 출금
        try {
            System.out.printf("이자는 %s원 입니다.\n", convertToString(interestCalculator.getInterest(account.getBalance())));
            account.withdraw(amount);
        }catch (WithdrawException e){
            System.out.println(e.getMessage());
            return;
        }
        System.out.printf("%s원이 출금되었습니다.\n", amount);
        System.out.println("출금 계좌 정보");
        account.getAccountInfo();
    }

    public void deposit(){
        //TODO: 입금 메서드 구현
        System.out.printf("\n입금을 시작합니다.\n");
        // 존재하지 않는 계좌이면 다시 물어보기
        Account account = requestAccountNumber("입금하시려는 계좌번호를 입력하세요.");
        System.out.println("입금하시려는 계좌의 정보입니다.");
        account.getAccountInfo();

        // TODO: 입금 처리
        BigDecimal amount = requestAmount("입금할 금액을 입력하세요.");
        account.deposit(amount);
        System.out.printf("%s원이 입금되었습니다.\n", amount);
        System.out.println("입금 계좌 정보");
        account.getAccountInfo();
    }

    public Account createAccount() throws InputMismatchException {
        //TODO: 계좌 생성하는 메서드 구현
        Account account;
        try {
            // 계좌번호 채번
            // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
            //TODO
            String accNo = "0000"+seq;
            System.out.printf("\n계좌 생성을 시작합니다.\n");
            String owner = requestAccountName();
            account = new Account(accNo, owner, BigDecimal.ZERO);
            System.out.printf("%s님 계좌가 발급되었습니다.\n", owner);
            account.getAccountInfo();
        }catch (InputMismatchException e){
            //TODO: 오류 throw
            throw e;
        }
        seq++;
        return account;
    }

    public Account findAccount(String accNo){
        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현
        for (Account account : CentralBank.getInstance().getAccountList()) {
            if (account.getAccNo().equals(accNo)) return account;
        }
        return null;
    }

    public void transfer() {
        //TODO: 송금 메서드 구현
        System.out.printf("\n송금을 시작합니다.\n");
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록
        do {
            //TODO
            Account senderAccount = requestAccountNumber("송금하시려는 계좌번호를 입력해주세요.");
            if (senderAccount.getCategory().equals("S")) {
                try {
                    if (this instanceof SavingBank) ((SavingBank)this).withdraw((SavingAccount) senderAccount);
                    else new SavingBank().withdraw((SavingAccount) senderAccount);
                } catch (WithdrawException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }
            System.out.println("송금하시려는 계좌 정보입니다.");
            senderAccount.getAccountInfo();
            //TODO
            Account receiverAccount = requestAccountNumber("어느 계좌번호로 보내시려나요?");
            System.out.println("보내하시려는 계좌 정보입니다.");
            receiverAccount.getAccountInfo();
            //TODO
            if (senderAccount.equals(receiverAccount)) {
                System.out.println("본인 계좌로의 송금은 입금을 이용해주세요.");
                return;
            }
            //TODO
            if (receiverAccount.getCategory().equals("S")) {
                System.out.println("적금 계좌로는 송금이 불가합니다.");
                return;
            }
            //TODO
            BigDecimal amount = requestAmount("송금할 금액을 입력하세요.");
            //TODO
            try {
                senderAccount.withdraw(amount);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            receiverAccount.deposit(amount);
            System.out.printf("%s원이 출금되었습니다.\n", amount);
            System.out.println("송금 계좌 정보");
            senderAccount.getAccountInfo();
            System.out.println("입금 계좌 정보");
            receiverAccount.getAccountInfo();
            break;
        } while(true);
    }

    protected Account requestAccountNumber(String requestMessage) {
        Account account;
        do {
            System.out.println(requestMessage);
            account = findAccount(scanner.next());
            if (account == null) {
                System.out.println("존재하지 않는 계좌번호입니다.");
            } else {
                if (account.isActive()) return account;
                System.out.println("삭제된 계좌번호입니다.");
            }
        } while(true);
    }

    protected String requestAccountName() {
        String name;
        do {
            System.out.println("계좌주명을 입력하세요.");
            name = scanner.next();
            if (name.matches("[가-힣]+")) return name;
            System.out.println("한글만 지원합니다.");
        } while(true);
    }

    protected BigDecimal requestAmount(String requestMessage) {
        BigDecimal amount;
        do {
            System.out.println(requestMessage);
            try {
                amount = scanner.nextBigDecimal();
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println("숫자 형식이 아닙니다.");
                continue;
            }
            if (amount.scale() > 0) {
                System.out.println("소수는 입력할 수 없습니다.");
                continue;
            }
            return amount;
        } while(true);
    }
}