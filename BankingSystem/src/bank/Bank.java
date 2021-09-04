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
    	HashMap<String, InterestCalculator> interest = new HashMap<>();
    	interest.put("N", new BasicInterestCalculator());
    	interest.put("S", new SavingInterestCalculator());
    	
        // 계좌번호 입력
        Account account;
        while(true){ // 계좌 검색
            System.out.println("\n출금하시려는 계좌번호를 입력하세요.");
            String accNo = scanner.next();
            account = findAccount(accNo);
            // TODO: 검색 -> 적금 계좌이면 적금 계좌의 출금 메소드 호출 -> 완료시 break
                        
            if(account == null) {
            	System.out.println("없는 계좌번호입니다. 다시 계좌번호 입력창으로 보내드리겠습니다.");
            	continue;
            }else if(account.getCategory().equals("S")) { // Account가 적금일 때
            	if(account instanceof SavingAccount) {
	        		Bank bank = new SavingBank();
	        		SavingBank savingBank = (SavingBank)bank;
	        		if(savingBank.withdraw((SavingAccount)account)==null) { // 적금계좌조회시 규정에 어긋나면 메뉴 입력창으로 보내기
	        			return;            	
	        		}
	        		else {
	        			System.out.println("계좌를 찾았습니다.");
	        			break;
	        		}
            	}
            }
            else {
            	System.out.println("계좌를 찾았습니다.");
            	break;
            }
        }
        
        while(true) {
        	
        	// 돈이 하나도 없을 때
        	if(account.getBalance().signum() == 0)
        	{
        		System.out.println("미리 조회해보니 돈이 하나도 없는 계좌입니다. 종료하겠습니다.");
        		break;
        	}
        	
	        // 출금처리
	        System.out.println("\n출금할 금액을 입력하세요.");
	        // TODO: interestCalculators 이용할 이자 조회 및 출금
	        BigDecimal amount = scanner.nextBigDecimal();
	        
	        
	        // 출금액이 0일 때
	        if (amount.signum() == 0) 
	        {
	        	System.out.println("출금액을 0으로 입력하시면 안됩니다. 다시 입력해주세요.");
	        	continue;
	        }
	        
	        // 소수를 입력 했을 때
	    	if(amount.scale() > 0) {
	    		System.out.println("소수를 입금액으로 입력 할 수 없습니다. 입금액을 다시 입력해주세요.");
	    		continue;
	    	}
	        
	        // 출금액을 음수로 넣었을 때
	        if (amount.signum() < 0) {
	        	System.out.println("음수를 출금액으로 입력 할 수 없습니다. 출금액을 다시 입력해주세요.");
	        }
	        
	        // 출금액이 잔액보다 클 때
			if(account.getBalance().compareTo(amount) < 0 ) 
			{
				System.out.println("출금액이 잔액보다 더 큽니다. 다시 입력해주세요.");
				continue;
			}
			
			// 이자
			BigDecimal acc_interest = interest.get(account.getCategory()).getInterest(account.getBalance());
			
	        try {
	        	System.out.println("출금 진행하겠습니다.");
	        	account.withdraw(amount);
	        	System.out.printf("%s계좌에 남아있는 잔액은 %s원 입니다. 현재 이자는 %s입니다.\n"
	        			, account.getAccNo(), df.format(account.getBalance()), df.format(acc_interest.multiply(account.getBalance())));
	        	break;
	        }catch (Exception e){
	        	System.out.println("출금 실패하였습니다. 다시 처음부터 진행하겠습니다.");
	        	continue;
	        }
        }
    }

    public void deposit(){
        //TODO: 입금 메서드 구현
        // 존재하지 않는 계좌이면 다시 물어보기
    	Account account;
    	
    	while(true) { // 계좌 검색
	        System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");
	        String depositacNo = scanner.next();
	        account = findAccount(depositacNo);
	        
	        if(account == null) {
	        	System.out.println("없는 계좌번호입니다. 다시 계좌번호 입력창으로 보내드리겠습니다.");
	        	continue;
	        }
	        else {
	        	System.out.println("계좌를 찾았습니다.");
            	break;
	        }
    	}
    	
	    while(true) {    
	        // TODO: 입금 처리
	        System.out.println("\n입금할 금액을 입력하세요.");
	        BigDecimal amount = scanner.nextBigDecimal();
	        
	        // 입금액이 0일 때
	    	if(amount.signum() == 0) { 
	    		System.out.println("입금액을 0으로 입력하시면 섭섭합니다. 다시 입력해주세요.");
	    		continue;
	    	}
	    	
	    	// 입금액을 음수값을 입력 했을 때 
	    	if(amount.signum() < 0) {
	    		System.out.println("음수를 입금액으로 입력 할 수 없습니다. 입금액을 다시 입력해주세요.");
	    		continue;
	    	}
	    	
	    	// 입금액을 소수를 입력 했을 때
	    	if(amount.scale() > 0) {
	    		System.out.println("소수를 입금액으로 입력 할 수 없습니다. 입금액을 다시 입력해주세요.");
	    		continue;
	    	}
	    	
	        try {
				account.deposit(amount);
				System.out.printf("%s계좌에 남아있는 잔액은 %s원 입니다."
						, account.getAccNo(), df.format(account.getBalance()));
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }

    public Account createAccount() throws InputMismatchException {
        //TODO: 계좌 생성하는 메서드 구현
    	while(true) {
	    	System.out.println("계좌주 이름을 설정하세요");
	    	String owner = scanner.next();
	        try {
	            // 계좌번호 채번
	            // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다. seq는 1부터 시작
	        	Account account = new Account(String.valueOf(("0000")+(++seq)), owner, BigDecimal.ZERO);
	            //TODO
	            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", account.getOwner());
	            return account;	            
	            
	        }catch (InputMismatchException e){
	            //TODO: 오류 throw
	        	System.out.println(e);
	        	System.out.println("계좌 생성 실패하였습니다. 계좌주명 입력란으로 돌아갑니다");
	        	continue;
	        }
    	}
    }

    public Account findAccount(String accNo){
        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현
    	CentralBank centralBank = CentralBank.getInstance();
    	
    	ArrayList<Account> account_lists = centralBank.getAccountList();
    	
    	Account account = null;
    	
    	for(Account account_list : account_lists) {
    		if(account_list.getAccNo().equals(accNo)) {
    			account = account_list;
    			break;
    		}
    	}
    	
    	if(account == null) {
    		System.out.println("존재하지 않는 계좌입니다.");
    		return null;
    	}
    	
        return account;
    }

    public void transfer() throws Exception{
        //TODO: 송금 메서드 구현
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록
        //TODO
    	Account senderAccount;
    	String senderAccNo;
    	Account receiverAccount;
    	String receiverAccNo;
    	
    	sender:while(true) { // 보내는 계좌 찾기
	    	System.out.println("\n송금하시려는 계좌번호를 입력해주세요.");
	    	senderAccNo = scanner.next(); 
	    	senderAccount = findAccount(senderAccNo);
	    	
	    	if(senderAccount == null) {
	    		System.out.println("이용하시려는 계좌를 찾을 수 없으니 다시 계좌 입력란으로 보내겠습니다.");
	    		continue sender;
	    	}
	    	else if(senderAccount instanceof SavingAccount) {
	    		System.out.println("적금계좌에서 다른계좌로 송금이 불가능하니 다시 계좌 입력란으로 보내겠습니다.");
	    		continue sender;
	    	}	    		
	    	else {
	    		System.out.println("계좌를 찾았습니다.");
	    		break;
	    	}
    	}
    	
    	
    	
    	// 돈이 하나도 없을 때
    	if(senderAccount.getBalance().signum() == 0)
    	{
    		System.out.println("미리 조회해보니 돈이 하나도 없는 계좌입니다. 종료하겠습니다.");
    		return;
    	}
	    	
	    receive:while(true) { // 받는 계좌 찾기
	        //TODO
	        System.out.println("\n어느 계좌번호로 보내십니까?");
	        receiverAccNo = scanner.next();
	        receiverAccount = findAccount(receiverAccNo);
	        if(receiverAccount == null) {
	    		System.out.println("송금 받는 계좌를 찾을 수 없으니 다시 입력란으로 보내겠습니다.");
	    		continue receive;
	    	}
	        else {
	        	System.out.println("계좌를 찾았습니다.");
	        	break;
	        }
	    }
	    
        //TODO
        if(senderAccount.getAccNo().equals(receiverAccount.getAccNo())) {
        	System.out.println("\n본인 계좌로의 송금은 입금을 이용해주세요. 종료하겠습니다.");
        	return;
        }
	        
        //TODO
        while(true) {
	        System.out.println("\n송금할 금액을 입력하세요.");
	        BigDecimal amount = scanner.nextBigDecimal();
	        
	        // 송금액을 0 입력 했을 때
	        if(amount.signum() == 0) {
	        	System.out.println("송금액을 0원 입력하시면 섭섭해 합니다");
	        	continue;
	        }
	        
	        // 송금액을 음수로 입력 했을 때
	        if(amount.signum() < 0) {
	        	System.out.println("음수를 송금액으로 입력 할 수 없습니다. 송금액을 다시 입력해주세요.");
	        	continue;
	        }
	        
	        // 송금액을 소수를 입력 했을 때
	    	if(amount.scale() > 0) {
	    		System.out.println("소수를 입금액으로 입력 할 수 없습니다. 입금액을 다시 입력해주세요.");
	    		continue;
	    	}
	        
	    	// 송금액이 잔액보다 클 때
	        if(senderAccount.getBalance().compareTo(amount) < 0) {
	        	System.out.println("송금액이 잔액보다 더 큽니다. 다시 입력해주세요.");
	        	continue;
	        }
	        
	        senderAccount.withdraw(amount);
	        System.out.printf("송금하는 계좌인 %s계좌에 남아있는 잔액은 %s원 입니다.\n"
					, senderAccount.getAccNo(), df.format(senderAccount.getBalance()));
	        receiverAccount.deposit(amount);
	        System.out.printf("송금받는 계좌인 %s계좌에 남아있는 잔액은 %s원 입니다.\n"
	        		, receiverAccount.getAccNo(), df.format(receiverAccount.getBalance()));
	        
	        System.out.println("송금을 종료합니다");
	        return;
	    }
        //TODO
    }
  }

 


