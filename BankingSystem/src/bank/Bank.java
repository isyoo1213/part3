package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bank extends BasicInterestCalculator{
    //TODO: Bank 클래스는 출금, 입금, 송금, 계좌 생성, 계좌 검색 기능들을 갖고 있습니다.
    protected static Scanner scanner = new Scanner(System.in);
    protected static int seq = 0;
    public static DecimalFormat df = new DecimalFormat("#,###");
    protected Account depositAccount = null;
    protected BigDecimal balance;
    protected Account withdrawAccount = null;
    private Exception e;

    // 뱅킹 시스템의 기능들
    public void withdraw() throws Exception {
        //TODO: 출금 메서드 구현
        //TODO: key, value 형태의 HashMap을 이용하여 interestCalculators 구현
        //여기서 key: category, value: 각 category의 InterestCalculator 인스턴스
        HashMap<String,InterestCalculator> map = new HashMap<>();
        map.put("N",  new BasicInterestCalculator());
        map.put("S",  new SavingInterestCalculator());


        // 계좌번호 입력
        Account account;
        while(true){
            System.out.println("\n출금하시려는 계좌번호를 입력하세요.");
            String accNo = scanner.next();
            withdrawAccount = findAccount(accNo);
            // TODO: 검색 -> 적금 계좌이면 적금 계좌의 출금 메소드 호출 -> 완료시 break
            if(withdrawAccount.getCategory().equals("S")){
                new SavingBank().withdraw((SavingAccount) withdrawAccount);
                break;
            }


        }
        // 출금처리
        System.out.println("\n출금할 금액을 입력하세요.");
        BigDecimal amount = scanner.nextBigDecimal();

        // TODO: interestCalculators 이용하 이자 조회 및 출금
        try {
            BigDecimal interest = getInterest(withdrawAccount.getBalance());
            if(withdrawAccount.getBalance().compareTo(amount) != -1) {
                balance = balance.subtract(amount);
                depositAccount.setBalance(balance);
                System.out.printf(amount + "원이 출금 되었습니다.");
                System.out.println("출력 후 이자는 "+interest+"입니다.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deposit(){
        //TODO: 입금 메서드 구현
        // 존재하지 않는 계좌이면 다시 물어보기
        try {
            System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");
            String accNo = scanner.next();
            depositAccount = findAccount(accNo);
            if (depositAccount.equals(null)) {
                System.out.println("존재하지 않는 계좌입니다.");
                deposit();
            }

            // TODO: 입금 처리
            System.out.println("\n입금할 금액을 입력하세요.");
            BigDecimal depositMoney = scanner.nextBigDecimal();
            balance = depositAccount.getBalance();
            balance = balance.add(depositMoney);
            depositAccount.setBalance(balance);
        }
        catch(Exception e){
            e.printStackTrace();
            deposit();
        }
    }

    public Account createAccount() throws InputMismatchException {
        //TODO: 계좌 생성하는 메서드 구현
        try {
            // 계좌번호 채번
            // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
            //TODO
            System.out.println("고객님의 이름을 입력해주세요");
            String owner = scanner.next();
            BigDecimal basic = new BigDecimal(0);
            Account account = new Account("0000"+seq,owner,basic);

            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);
            System.out.println("고객님의 계좌번호는 "+account.getAccNo()+"입니다.");
            seq++;

            return account;
        }catch (Exception e){
            //TODO: 오류 throw
            e.printStackTrace();
            return createAccount();
        }

    }

    public Account findAccount(String accNo){
        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현
        for(Account account : CentralBank.getInstance().accountList){
            if(account.getAccNo().equals(accNo)){
                return account;
            }
        }
        return null;
    }

    public void transfer() throws Exception {
        //TODO: 송금 메서드 구현
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록
        //TODO
        Account toAcc = null;
        Account fromAcc = null;


        try {
            System.out.println("\n송금하시려는 계좌번호를 입력해주세요.");
            String accNo = scanner.next();
            toAcc = findAccount(accNo);

            if (toAcc.equals(null)) {
                System.out.println("\n어느 계좌번호로 보내시려나요?");
                accNo = scanner.next();
                toAcc = findAccount(accNo);

                if (toAcc.equals(fromAcc)) {
                    System.out.println("\n본인 계좌로의 송금은 입금을 이용해주세요.");
                } else if (toAcc.getCategory().equals("S")) {
                    System.out.println("\n적금 계좌로는 송금이 불가합니다.");
                } else {
                    throw e;
                }

            } else{
                System.out.println("\n송금할 금액을 입력하세요.");
                BigDecimal transferAmount = scanner.nextBigDecimal();
                toAcc.withdraw(transferAmount);
//                fromAcc.deposit(transferAmount);
            }

        }
        catch (Exception e){
            e.printStackTrace();
            transfer();
        }
    }

    }
