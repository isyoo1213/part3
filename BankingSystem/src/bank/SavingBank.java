package bank;

import account.Account;
import account.SavingAccount;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class SavingBank extends Bank {
	
	
	
    public Account withdraw(SavingAccount account) throws Exception{
        // TODO: Account의 출금 메서드에서 잔액/목표 금액 체크하여 조금 다르게 구현
        // throws Exception 적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.
    	try {
    		if(account.getGoalAmount().compareTo(account.getBalance()) > 0){
    			throw new Exception();
    		}
    		else {
    			System.out.println("적금 계좌의 규정에 어긋나지 않습니다.");
    			return account;
    		}
    		
    	}catch (Exception e) {
    		System.out.printf("적금 계좌는 잔액이 목표 금액(%s원) 이상이어야 출금 가능합니다.", account.getGoalAmount());
    		return null;
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
    	
    	re:while(true) {
    		System.out.println("목표금액을 입력하세요");
    		
    		try {
    			goalAmount = scan.nextBigDecimal();
    		}catch(InputMismatchException e) {
    			scan.next();
    			System.out.println("목표금액을 잘못입력하셨습니다. 다시 입력창으로 보내겠습니다.");
    			continue;
    		}
    		
    		if(goalAmount.signum() < 0) {
    			System.out.println("음수를 입력할 수 없습니다.");
    		}
    		
    		if(goalAmount.scale() > 0) {
    			System.out.println("소수는 입력할 수 없습니다.");
    			continue;
    		}
    		
    		break;
    	}
    	
    	
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