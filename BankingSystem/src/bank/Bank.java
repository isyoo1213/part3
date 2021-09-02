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
        HashMap<String, InterestCalculator> interestCalculators = new HashMap<>();
        interestCalculators.put("N",  new BasicInterestCalculator());
        interestCalculators.put("S",  new SavingInterestCalculator());

        // 계좌번호 입력
        Account account;
        try{
            while (true) {
                System.out.println("\n출금하시려는 계좌번호를 입력하세요.");
                String accNo = scanner.nextLine();
                // TODO: 검색 -> 적금 계좌이면 적금 계좌의 출금 메소드 호출 -> 완료시 break
                account = findAccount(accNo);
                if (account == null)
                    System.out.println("찾으시는 계좌가 없습니다.");
                else if (account.getCategory().equals("S")) {
                    SavingBank savingBank = (SavingBank) this;
                    savingBank.withdraw((SavingAccount) account);
                    break;
                }
                else break;
            }
            // 출금처리
            System.out.println("\n일반 예금에서 출금할 금액을 입력하세요.");
            BigDecimal withdrawMoney = new BigDecimal(scanner.nextLine());
            // TODO: interestCalculators 이용하 이자 조회 및 출금
            account.withdraw(withdrawMoney);
            InterestCalculator interestCalculator = interestCalculators.get(account.getCategory());
            System.out.println("출금 후 잔고 :"+account.getBalance());
            BigDecimal interest = interestCalculator.getInterest(account.getBalance());
            System.out.println("출금 후 이자 :"+account.getBalance().multiply(interest));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("문제가 생겨 다시 출금을 시도합니다.");
            withdraw();
        }
    }

    public void deposit() {
        //TODO: 입금 메서드 구현
        // 존재하지 않는 계좌이면 다시 물어보기
        Account account;
        while (true) {
            System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");
            String accNo = scanner.nextLine();
            account = findAccount(accNo);
            if (account == null)
                System.out.println("입력하신 계좌번호에 맞는 계좌를 찾지 못했습니다.");
            else break;
        }
        try{
            // TODO: 입금 처리
            System.out.println("\n입금할 금액을 입력하세요.");
            BigDecimal depositMoney = new BigDecimal(scanner.nextLine());
            account.deposit(depositMoney);
            System.out.printf("입금 후 잔고는 %s원 입니다!\n", account.getBalance().toString());
        }catch (NumberFormatException e) {
            //TODO: 오류 throw
            System.out.println("숫자를 입력하셔야 합니다!");
            System.out.println("입금 과정을 다시 시작합니다.");
            deposit();
        }
    }

    public Account createAccount() throws InputMismatchException {
        //TODO: 계좌 생성하는 메서드 구현
        Account account;
        // 계좌번호 채번
        System.out.println("계좌 소유자 이름을 입력하세요: ");
        String owner = scanner.nextLine();
        // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
        seq++;
        account = new Account(df.format(new BigDecimal(seq)), owner, BigDecimal.ZERO);
        CentralBank.getInstance().getAccountList().add(account);
        //TODO
        System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);
        System.out.printf("계좌번호는 %s입니다.\n", account.getAccNo());
        return account;
    }

    public Account findAccount(String accNo) {
        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현
        ArrayList<Account> list = CentralBank.getInstance().getAccountList();
        Account account = null;
        for (Account value : list) {
            if (value.getAccNo().equals(accNo)) {
                value.getAccountInfo(value);
                account = value;
                break;
            }
        }
        if (account == null)
            System.out.println("계좌 번호가 " + accNo + "인 계좌를 찾지 못했습니다.");
        return account;
    }

    public void transfer() throws Exception {
        //TODO: 송금 메서드 구현
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록
        //TODO
        Account departAcc = null;
        Account destAcc = null;
        while(departAcc == null){
            System.out.println("\n송금하시려는 계좌번호를 입력해주세요.");
            String departAccNo = scanner.nextLine();
            departAcc = findAccount(departAccNo);
            if (departAcc == null)
                System.out.println("송금하려는 계좌번호가 유효하지 않습니다.");
        }
        while(destAcc == null){
            System.out.println("\n어느 계좌번호로 보내시려나요?");
            String destAccNo = scanner.nextLine();
            destAcc = findAccount(destAccNo);
            if (destAcc == null)
                throw new Exception("보내려는 계좌번호가 유효하지 않습니다.");
        }
        try{
            if (destAcc.getOwner().equals(departAcc.getOwner())) {
                System.out.println("\n본인 계좌로의 송금은 입금을 이용해주세요.");
                throw new Exception("송금 실패.(동일 명의 계좌)");
            }
            //TODO
            if (destAcc.getCategory().equals("S")) {
                System.out.println("\n적금 계좌로는 송금이 불가합니다.");
                throw new Exception("송금 실패.(적금계좌 송금 불가)");
            }
        } catch(Exception e){
            e.printStackTrace();
            transfer();
        }
        System.out.println("\n송금할 금액을 입력하세요.");
        BigDecimal transferAmount = new BigDecimal(scanner.nextLine());
        departAcc.withdraw(transferAmount);
        destAcc.deposit(transferAmount);
    }
}
