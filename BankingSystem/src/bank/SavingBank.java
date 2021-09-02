package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SavingBank extends Bank {

    public void withdraw(SavingAccount account) throws Exception{
        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.
    	try {
    		if(account.getGoalAmount().compareTo(account.getBalance()) < 0){
    			System.out.println("적금계좌 규칙에 어긋납니다.");
    			return;
    		}
    		
    	}catch (Exception e) {
    		e.printStackTrace();    		
    		System.out.println("적금 계좌의 목표 금액에 도달하지 않아 출금이 불가능합니다");
    	}
    }
    // TODO: 목표금액을 입력받아서 SavingAccount 객체 생성하도록 재정의
    
    
    @Override
    public SavingAccount createAccount() throws NoSuchElementException{
    	String owner;
    	BigDecimal goalAmount;
    	Scanner scan = new Scanner(System.in);
    	
    	System.out.println("계좌주 이름을 설정하세요");
    	owner = scan.next();
    	
    	System.out.println("목표금액을 입력하세요");
    	goalAmount = scan.nextBigDecimal();
    	
        try{
        	//SavingAccount account = new SavingAccount(String.valueOf(df2.format(++seq)), owner, BigDecimal.ZERO, goalAmount);
        	SavingAccount account = new SavingAccount(String.valueOf(("0000")+(++seq)), owner, BigDecimal.ZERO, goalAmount);
        	System.out.printf("\n%s님 계좌가 발급되었습니다.\n", account.getOwner());
            return account;
        }catch (Exception e){
            //TODO: 오류 throw
        	e.printStackTrace();
        	return null;
        }		
    }
    
}