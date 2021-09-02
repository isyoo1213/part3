package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

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

        // 계좌번호 입력
        Account account;
        while(true){
            System.out.println("\n출금하시려는 계좌번호를 입력하세요.");
            String accNo = scanner.next();
            // TODO: 검색 -> 적금 계좌이면 적금 계좌의 출금 메소드 호출 -> 완료시 break

        }
        // 출금처리
        System.out.println("\n출금할 금액을 입력하세요.");
        // TODO: interestCalculators 이용하 이자 조회 및 출금
        try {

        }catch (Exception e){

        }
    }

    public void deposit(){
        //TODO: 입금 메서드 구현
        // 존재하지 않는 계좌이면 다시 물어보기
        System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");

        String accNo = scanner.next();
        Account account = findAccount(accNo);

        while(account == null){
            System.out.println("존재하지 않거나 없는 계좌번호 입니다. 다시 입력 부탁드립니다.");
            System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");

            accNo = scanner.next();
        }

        // TODO: 입금 처리
        System.out.println("\n입금할 금액을 입력하세요.");

        String input = scanner.next();
        BigDecimal amount = new BigDecimal(input);

        if(amount.compareTo(BigDecimal.ZERO) > 0){
            account.setBalance(account.getBalance().add(amount));

            System.out.printf("입금 완료되었습니다. 계좌 잔액: %s\n", account.getBalance());
        }else{
            System.out.println("잘못 입력하셨습니다.");
        }
    }

    public Account createAccount() throws InputMismatchException {
        //TODO: 계좌 생성하는 메서드 구현
        try {
            // 계좌번호 채번
            // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
            //TODO
            String owner = "박현준";
            BigDecimal balance = new BigDecimal("0");
            String account_num = "0000" + seq++;

            Account account = new Account(account_num, owner, balance);

            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);

            return account;
        }catch (Exception e){
            //TODO: 오류 throw
            e.printStackTrace();

            throw e;
        }
    }

    public Account findAccount(String accNo){
        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현
        CentralBank centralBank = CentralBank.getInstance();

        ArrayList<Account> account_lists = centralBank.getAccountList();

        Account account = null;

        for (int i=0; i<account_lists.size(); i++) {
            if(account_lists.get(i).getAccNo().equals(accNo)){
                account = account_lists.get(i);
                break;
            }
        }

        if(account == null){
            System.out.println("존재하지 않는 계좌번호입니다.");
        }

        return account;
    }

    public void transfer() throws Exception{
        //TODO: 송금 메서드 구현
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록

        Account from_acc = null;
        Account to_acc = null;

        while(from_acc == null) {
            //TODO
            System.out.println("\n송금하시려는 계좌번호를 입력해주세요.");
            String input = scanner.next();
            from_acc = findAccount(input);

            if(from_acc != null){
                break;
            }

            System.out.println("존재하지 않은 계좌이거나 잘못 입력하셨습니다. 다시 입력 부탁드리겠습니다.");
        }

        while(true) {
            //TODO
            System.out.println("\n어느 계좌번호로 보내시려나요?");
            String input = scanner.next();
            to_acc = findAccount(input);

            if(to_acc == null){
                System.out.println("존재하지 않은 계좌이거나 잘못 입력하셨습니다. 다시 입력 부탁드리겠습니다.");
            }else if(from_acc == to_acc){
                System.out.println("\n본인 계좌로의 송금은 입금을 이용해주세요.");
            }else if(to_acc.getCategory().equals("S")){
                System.out.println("\n적금 계좌로는 송금이 불가합니다.");
            }else{
                break;
            }
        }
        //TODO

        while(true) {
            try{
                System.out.printf("\n송금할 금액을 입력하세요. 계좌 잔액: %s\n",from_acc.getBalance());
                int input = scanner.nextInt();
                BigDecimal transfer = new BigDecimal(input);

                if(input < 0){
                    System.out.println("해당 금액은 송금할 수 없는 금액입니다.");
                    continue;
                }else if(from_acc.getBalance().compareTo(transfer) < 0){
                    System.out.println("계좌 잔액이 부족합니다.");
                    continue;
                }

                from_acc.setBalance(from_acc.getBalance().subtract(transfer));
                to_acc.setBalance(to_acc.getBalance().add(transfer));

                break;
            }catch (Exception e){
                System.out.println("송금 금액을 잘못 입력하셨습니다.");
            }
        }
    }
