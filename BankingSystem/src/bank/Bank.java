package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Bank {
    //TODO: Bank 클래스는 출금, 입금, 송금, 계좌 생성, 계좌 검색 기능들을 갖고 있습니다.
    protected static Scanner scanner = new Scanner(System.in);
    protected static int seq = 1;
    public static DecimalFormat df = new DecimalFormat("#,###");

    // 뱅킹 시스템의 기능들
    public void withdraw() throws Exception {
        //TODO: 출금 메서드 구현
        //TODO: key, value 형태의 HashMap을 이용하여 interestCalculators 구현
        //여기서 key: category, value: 각 category의 InterestCalculator 인스턴스
        HashMap<String, InterestCalculator> interestCalculator = new HashMap<>();
        interestCalculator.put("N",new BasicInterestCalculator());
        interestCalculator.put("S",new SavingInterestCalculator());
        // 계좌번호 입력
        Account account;
        while(true){
            System.out.println("\n출금하시려는 계좌번호를 입력하세요.");
            String accNo = scanner.next();
            // TODO: 검색 -> 적금 계좌이면 적금 계좌의 출금 메소드 호출 -> 완료시 break
            account = findAccount(accNo);
            if(account.getCategory().equals("S")){
                ((SavingBank)this).withdraw((SavingAccount)account );
                break;
            }else{
                break;
            }

        }
        // 출금처리
        System.out.println("\n출금할 금액을 입력하세요.");
        // TODO: interestCalculators 이용하 이자 조회 및 출금
        try {
            BigDecimal withdraw = scanner.nextBigDecimal();
            BigDecimal balance = account.getBalance();
            BigDecimal interest = interestCalculator.get(account.getCategory()).getInterest(balance);
            account.withdraw(withdraw);
            System.out.printf("현재 이자는 %s 원입니다. %s원이 출금되어 잔액은 %s원입니다. \n",balance.multiply(interest),withdraw,account.getBalance());

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void deposit(){
        //TODO: 입금 메서드 구현
        // 존재하지 않는 계좌이면 다시 물어보기
        System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");
        String accNo = scanner.next();
        Account account = findAccount(accNo);
        while(account==null){
        System.out.println("\n입금하시려는 계좌번호를 다시 입력해주세요.");
        accNo = scanner.next();
        account = findAccount(accNo);
        }
        // TODO: 입금 처리
        System.out.println("\n입금할 금액을 입력하세요.");
        BigDecimal depo = scanner.nextBigDecimal();
        if(depo.compareTo(BigDecimal.ZERO)<=0){
            System.out.println("입금할 금액을 잘못 입력하셨습니다.");
        }
        else{
            account.deposit(depo);
        }
    }

    public Account createAccount() throws InputMismatchException {
        //TODO: 계좌 생성하는 메서드 구현
        try {
            // 계좌번호 채번
            // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
            //TODO
            Account account = new Account();
            String AccNo = String.format(new DecimalFormat("0000").format(seq++));
            System.out.println("소유자 이름을 입력하세요.");
            String owner = scanner.next();
            System.out.println("처음 입금할 금액을 입력하세요.");
            BigDecimal balance = scanner.nextBigDecimal();
            account = new Account(AccNo,owner,balance);
            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);
            return account;
        }catch (Exception e){
            //TODO: 오류 throw
            System.err.print(e);
            return null;
        }
    }

    public Account findAccount(String accNo){
        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현
        Account account = new Account();
        for(Account acc : CentralBank.getInstance().getAccountList()){
            if(acc.getAccNo().equals(accNo)){
                account = acc;
            }
        }
        return account;
    }

    public void transfer() throws Exception {
        //TODO: 송금 메서드 구현
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록
        //TODO
        System.out.println("\n송금하시려는 계좌번호를 입력해주세요.");
        String accNo = scanner.next();
        Account account = new Account();
        account = findAccount(accNo);
        while (account == null) {
            System.out.println("\n송금하시려는 계좌번호를 다시 입력해주세요.");
            accNo = scanner.next();
            account = findAccount(accNo);
        }
        //TODO
        System.out.println("\n어느 계좌번호로 보내시려나요?");
        String rAccNo = scanner.next();
        Account rAccount = new Account();
        rAccount = findAccount(rAccNo);
        while (rAccount == null) {
            System.out.println("\n어느 계좌번호로 보내시려나요?");
            rAccNo = scanner.next();
            rAccount = findAccount(rAccNo);
        }
        //TODO
        if (rAccount.equals(account)) {
            System.out.println("\n본인 계좌로의 송금은 입금을 이용해주세요.");
        }
        //TODO
        else if (rAccount.getCategory().equals("S")) {
            System.out.println("\n적금 계좌로는 송금이 불가합니다.");
        }
        //TODO
        else {
            System.out.println("\n송금할 금액을 입력하세요.");
            BigDecimal rem = scanner.nextBigDecimal();
            while (rem.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("\n송금할 금액을 다시 입력하세요.");
                rem = scanner.nextBigDecimal();
            }
            account.withdraw(rem);
            rAccount.deposit(rem);
            System.out.printf("%s님의 %s계좌로 %s원이 송금되어 %s님의 계좌 %s의 잔액은 %s원 입니다. \n",rAccount.getOwner(),rAccount.getAccNo(),rem,account.getOwner(),account.getAccNo(),account.getBalance());
            //TODO
        }
    }
    }
