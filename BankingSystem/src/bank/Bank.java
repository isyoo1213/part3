package bank;

import account.Account;
import account.SavingAccount;
import status.WithdrawStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bank {
    //TODO: Bank 클래스는 출금, 입금, 송금, 계좌 생성, 계좌 검색 기능들을 갖고 있습니다.
    protected static Scanner scanner = new Scanner(System.in);
    protected static int seq = 0;
    public static DecimalFormat df = new DecimalFormat("#,###");

    public static String bigDecimalToString(BigDecimal val) {
        return df.format(val.setScale(0, RoundingMode.FLOOR));
    }

    // 뱅킹 시스템의 기능들
    public void withdraw() throws Exception {
        //TODO: 출금 메서드 구현
        //TODO: key, value 형태의 HashMap을 이용하여 interestCalculators 구현
        //여기서 key: category, value: 각 category의 InterestCalculator 인스턴스
        HashMap<String, InterestCalculator> interestCalculators = new HashMap<>();
        interestCalculators.put("N", new BasicInterestCalculator());
        interestCalculators.put("S", new SavingInterestCalculator());

        // 계좌번호 입력
        System.out.printf("[출금]\n");
        Account account;
        InterestCalculator interestCalculator;
        while (true) {
            // TODO: 검색 -> 적금 계좌이면 적금 계좌의 출금 메소드 호출 -> 완료시 break
            account = remitAccountNumber("출금하시려는 계좌번호를 입력하세요.");

            if (account.getCategory().equals("S")) {
                try {
                    if (this instanceof SavingBank) ((SavingBank) this).withdraw((SavingAccount) account);
                    else new SavingBank().withdraw((SavingAccount) account);
                } catch (WithdrawStatus e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                interestCalculator = interestCalculators.get("S");
            } else {
                interestCalculator = interestCalculators.get("N");
            }
            break;

        }
        System.out.println("[출금 계좌 정보]");
        account.getAccountInfo();
        // 출금처리
        // TODO: interestCalculators 이용하 이자 조회 및 출금
        BigDecimal amount = remitAmount("출금할 금액을 입력하세요.");
        int withdrawCheck = amount.compareTo(account.getBalance());


        try {
            if (withdrawCheck <= 0) {
                System.out.printf("이자는 %s원 입니다.\n", bigDecimalToString(interestCalculator.getInterest(account.getBalance())));
                account.withdraw(amount);
                System.out.printf("%s원이 출금되었습니다.\n", amount);
            } else {
                System.out.println("출금이 안되며, 계좌 잔액을 확인 해주세요.");
            }

        } catch (WithdrawStatus e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("[출금 계좌 정보]");
        account.getAccountInfo();

    }

    public void deposit() {
        //TODO: 입금 메서드 구현
        // 존재하지 않는 계좌이면 다시 물어보기
        System.out.printf("[입금]\n");
        // 계좌가 존재하지 않으면 다시 물어보기
        Account account = remitAccountNumber("입금하시려는 계좌번호를 입력하세요.");
        System.out.println("입금하시려는 계좌 정보입니다.");
        account.getAccountInfo();

        // TODO: 입금 처리
        BigDecimal amount = remitAmount("입금할 금액을 입력하세요.");
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
            String accNo = "0000" + seq;
            System.out.println("계좌 생성 시작");
            String owner = remitAccountName();
            account = new Account(accNo, owner, BigDecimal.ZERO);
            System.out.printf("%s님 계좌가 발급되었습니다.\n", owner);
            account.getAccountInfo();
        } catch (InputMismatchException e) {
            //TODO: 오류 throw
            throw e;
        }
        seq++;
        return account;
    }

    public Account findAccount(String accNo) {
        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현
        for (Account account : CentralBank.getInstance().getAccountList()) {
            if (account.getAccNo().equals(accNo)) return account;
        }
        return null;
    }

    public void transfer() throws Exception {
        //TODO: 송금 메서드 구현
        System.out.println("[송금]");
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록
        do {
            //TODO
            Account sendAccount = remitAccountNumber("송금하시려는 계좌번호를 입력해주세요.");
            if (sendAccount.getCategory().equals("S")) {
                try {
                    if (this instanceof SavingBank) ((SavingBank) this).withdraw((SavingAccount) sendAccount);
                    else new SavingBank().withdraw((SavingAccount) sendAccount);
                } catch (WithdrawStatus e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }
            sendAccount.getAccountInfo();
            //TODO
            Account recipientAccount = remitAccountNumber("어느 계좌번호로 보내시려나요?");
            System.out.println("보내시려는 계좌 정보입니다.");
            recipientAccount.getAccountInfo();
            //TODO
            if (sendAccount.equals(recipientAccount)) {
                System.out.println("본인 계좌로의 송금은 입금을 이용해주세요.");
                return;
            }
            //TODO
            if (recipientAccount.getCategory().equals("S")) {
                System.out.println("적금 계좌로는 송금이 불가합니다.");
                return;
            }
            //TODO
            BigDecimal amount = remitAmount("송금할 금액을 입력하세요.");
            //TODO
            try {
                sendAccount.withdraw(amount);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            recipientAccount.deposit(amount);
            System.out.printf("%s원이 출금되었습니다.\n", amount);
            System.out.println("[송금 계좌 정보]");
            sendAccount.getAccountInfo();
            System.out.println("[입금 계좌 정보]");
            recipientAccount.getAccountInfo();
            break;
        } while (true);
    }

    protected Account remitAccountNumber(String requestMessage) {
        Account account;
        do {
            System.out.println(requestMessage);
            String withdrawAccount = scanner.next();
            account = findAccount(withdrawAccount);

            if (account == null) {
                System.out.println("존재하지 않는 계좌번호입니다.");
            } else {
                if (account.isActive()) return account;
                System.out.println("삭제된 계좌번호입니다.");
            }
        } while (true);
    }

    protected String remitAccountName() {
        String name;
        do {
            System.out.println("계좌주명을 입력하세요.");
            name = scanner.next();
            if (name.matches("[가-힣]+")) return name;
            System.out.println("한글만 지원합니다.");
        } while (true);
    }

    protected BigDecimal remitAmount(String remitMessage) {
        BigDecimal amount;
        do {
            System.out.println(remitMessage);
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
        } while (true);
    }
}
