package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
            if(account.getCategory().equals("S")) { // 적금계좌 (지금은 이렇게 구현되있지만 계좌번호를 키값으로하여 적금계좌인지 예금계좌인지 체크
            	account = new SavingAccount();
            	account.withdraw(null);
            	break;
            }
        }
        // 출금처리
        System.out.println("\n출금할 금액을 입력하세요.");
        // TODO: interestCalculators 이용할 이자 조회 및 출금
        BigDecimal amount = scanner.nextBigDecimal();
        
        try {
        	
        }catch (Exception e){

        }
    }

    public void deposit(){
        //TODO: 입금 메서드 구현
        // 존재하지 않는 계좌이면 다시 물어보기
        System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");
        String depositacNo = scanner.next();

        // TODO: 입금 처리
        System.out.println("\n입금할 금액을 입력하세요.");
        BigDecimal amount = scanner.nextBigDecimal();

    }

    public Account createAccount() throws InputMismatchException {
        //TODO: 계좌 생성하는 메서드 구현
    	System.out.println("계좌주의 이름을 설정하세요");
    	String name = scanner.next();
        try {
            // 계좌번호 채번
            // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
        	Account account = new Account("0000"+String.valueOf(seq++), name, BigDecimal.ZERO);
            //TODO
            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", account.getOwner());
            return account;
        }catch (InputMismatchException e){
            //TODO: 오류 throw
        	System.out.println(e);
        	System.out.println("계좌 생성 실패하였습니다. 올바르게 입력해주세요.");
        	return null;
        }
    }

    public Account findAccount(String accNo){
        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현

        return account;
    }

    public void transfer() throws Exception{
        //TODO: 송금 메서드 구현
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록
        //TODO
        System.out.println("\n송금하시려는 계좌번호를 입력해주세요.");
        //TODO
        System.out.println("\n어느 계좌번호로 보내시려나요?");
        //TODO
        System.out.println("\n본인 계좌로의 송금은 입금을 이용해주세요.");
        //TODO
        System.out.println("\n적금 계좌로는 송금이 불가합니다.");
        //TODO
        System.out.println("\n송금할 금액을 입력하세요.");
        //TODO
        }
    }
