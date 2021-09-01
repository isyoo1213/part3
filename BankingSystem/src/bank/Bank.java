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
    public static DecimalFormat df = new DecimalFormat("#,###원");

    // 뱅킹 시스템의 기능들
    public void withdraw() throws Exception {
        //TODO: 출금 메서드 구현
        //TODO: key, value 형태의 HashMap을 이용하여 interestCalculators 구현
        //여기서 key: category, value: 각 category의 InterestCalculator 인스턴스
        HashMap<String, InterestCalculator> interestSelector = new HashMap<>();
        interestSelector.put("N", new BasicInterestCalculator());
        interestSelector.put("S", new SavingInterestCalculator());

        // 계좌번호 입력
        Account account;
        //while(ture) : 별도 조건 없이 break 나 return까지 계속 반복한다는 뜻이다.
        while(true){
            System.out.println("\n출금하시려는 계좌번호를 입력하세요.");
            String accNo = scanner.next();
            // TODO: 검색 -> 적금 계좌이면 적금 계좌의 출금 메소드 호출 -> 완료시 break
            //아래 준비한 findAccount() 메서드로 찾은 계좌를 account로 세팅한다.
            account = findAccount(accNo);
            //account를 못찾으면 null값으로 세팅된다.
            if (account == null) {
                System.out.println("계좌번호 입력 오류");
            }
            //account의 category 값이 S이면 try문으로 이동한다.
            else if (account.getCategory().equals("S")){
                try {
                    //try문이 정상적으로 끝나면 break 하고 아래 출금으로 이동한다.
                    new SavingBank().withdraw((SavingAccount) account);
                    break;
                }
                //try문에서 Exception을 받으면 메서드 전부 깨고 return한다.
                catch (Exception e) {
                    return;
                }
            }
            else {
                break;
            }
        }

        // 출금처리
        // TODO: interestCalculators 이용할 이자 조회 및 출금
        try {
            //다음 try문을 시행하고 오류가 나오면 catch로 간다.
            System.out.println("\n출금할 금액을 입력하세요.");
            //숫자만 입력해야하는데 문자도 입력하는것을 고려하여 먼저 문자열로받아 문자를 전부 제거한다.
            String withdrawAmountini = scanner.next().replaceAll("[^0-9]","");
            //0~9 숫자를 제외한 문자를 전부 제거하고 숫자를 BigDecimal 로 변환한다.
            BigDecimal withdrawAmount = new BigDecimal(withdrawAmountini);
            //숫자가 입력이 안되거나 계좌 잔액보다 큰 금액이 입력된 경우 다시 입력을 유도한다.
            while (withdrawAmountini == "" || withdrawAmount.compareTo(account.getBalance()) > 0) {
                System.out.printf("\n출금액이 잔액(%s)보다 크거나 잘못 입력되었습니다. 다시 입력해주세요.\n", df.format(account.getBalance()));
                withdrawAmountini = scanner.next().replaceAll("[^0-9]","");
                withdrawAmount = new BigDecimal(withdrawAmountini);
            }
            //출금
            account.withdraw(withdrawAmount);
            System.out.printf("출금액 %s원 출금완료\n", df.format(withdrawAmount));
            //이자는 출처가 없다.     셀렉터로         카테고리값을 get하여 계산기를 고르고  계산기에 잔액을 입력한값을        출금액에 곱해서 이자를 구한다.
            BigDecimal interest = interestSelector.get(account.getCategory()).getInterest(account.getBalance()).multiply(withdrawAmount);
            //이자가 구해진 경우 시행한다.
            if (interest != null) {
                System.out.printf("이자 %s를 지급하였습니다.", df.format(interest));
                System.out.printf("\n%s님 계좌%s의 잔고: %s",account.getOwner(),account.getAccNo(), df.format(account.getBalance()));
            }
            //이자를 구하지 못한경우 예외처리
            else {
                throw new Exception();
            } return;
        }catch (Exception e){
            System.out.println("\n출금 중 오류발생, 다시 시도해주세요.");
        }
    }

    public void deposit(){
        //TODO: 입금 메서드 구현
        System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");
        String accNo = scanner.next();

        // 존재하지 않는 계좌이면 다시 물어보기
        Account account = findAccount(accNo);
        while (account == null) {
            System.out.println("계좌번호 오류입니다. 다시 입력해주세요.");
            accNo = scanner.next();
            account = findAccount(accNo);
        }

        // TODO: 입금 처리
        System.out.println("\n입금할 금액을 입력하세요.");
        String depositamountini = scanner.next().replaceAll("[^0-9]","");
        while(depositamountini == "") {
            System.out.println("입금할 금액을 다시 숫자로 입력해주세요");
            depositamountini = scanner.next().replaceAll("[^0-9]","");
        }
        BigDecimal depositamount = new BigDecimal(depositamountini);
        findAccount(accNo).deposit(depositamount);
        System.out.printf("\n%s원을 입금하였습니다.",df.format(depositamount));
        System.out.printf("\n%s님 계좌%s의 잔고: %s",account.getOwner(),account.getAccNo(), df.format(account.getBalance()));
    }

    public Account createAccount() throws InputMismatchException {
        //TODO: 계좌 생성하는 메서드 구현
        try {
            System.out.println("일반계좌를 생성합니다.");
            // 계좌번호 채번
            // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
            BigDecimal balance = null;
            String accNo = String.format(new DecimalFormat("0000").format(++seq));

            // 소유자 입력
            System.out.println("소유주명: ");
            String owner = scanner.next();
            //TODO
            //거치금액 설정
            System.out.println("금액을 거치하시겠습니까? (Y or N)");
            String answer = scanner.next();
            if (answer.equals("Y")||answer.equals("y")) {
                System.out.println("예치하실 금액을 입력해주세요.");
                String balanceini = scanner.next();
                //예치금액에 문자가 들어갈경우 제거
                String numberedbalanceini = balanceini.replaceAll("[^0-9]","");
                //예치금액에 숫자가 없을경우 재입력
                while(numberedbalanceini == "") {
                    System.out.println("예치 금액을 숫자로 다시 입력해주세요");
                    balanceini = scanner.next();
                    numberedbalanceini = balanceini.replaceAll("[^0-9]","");
                }
                balance = new BigDecimal(numberedbalanceini);
            }
            else {
                balance = new BigDecimal(0);
                System.out.println("금액을 예치하지 않습니다. 입금기능을 이용해주세요");
            }
            Account account = new Account(accNo, owner, balance);
            System.out.printf("\n%s님 계좌가 발급되었습니다.\n", owner);
            return account;
        }catch (Exception e){
            //TODO: 오류 throw
            System.out.println("계좌생성 에러");
        }
        return null;
    }

    public Account findAccount(String accNo){
        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현
        ArrayList<Account> acclist = CentralBank.getInstance().accountList;
        Account findTargetAccount = null;
        for (Account a : acclist) {
            if (a.getAccNo().equals(accNo)) {
                findTargetAccount = a;
                break;
            }
        } return findTargetAccount;
    }

    public void transfer() throws Exception{
        //TODO: 송금 메서드 구현
        // 잘못 입력하거나 예외처리시 다시 입력가능하도록
        //TODO
        try {
            System.out.println("\n송금하시려는 계좌번호를 입력해주세요.");
            String transferFrom = scanner.next();
            //TODO
            System.out.println("\n어느 계좌번호로 보내시려나요?");
            String transferTo = scanner.next();
            //TODO
            if (transferTo.equals(transferFrom)) {
                System.out.println("\n본인 계좌로의 송금은 입금을 이용해주세요.");
                return;
            }
            //TODO
            Account accountFrom = findAccount(transferFrom) ;
            Account accountTo = findAccount(transferTo);
            if(accountFrom.getCategory().equals("S") || accountTo.getCategory().equals("S")) {
                System.out.println("\n적금 계좌로는 송금이 불가합니다.");
                return;
            }

            //TODO
            System.out.println("\n송금할 금액을 입력하세요.");
            String transferAmountini = scanner.next().replaceAll("[^0-9]","");
            BigDecimal transferAmount = new BigDecimal(transferAmountini);
            while (transferAmountini == "" || transferAmount.compareTo(accountFrom.getBalance()) > 0) {
                System.out.println("송금할 금액이 잘못 입력되었거나 잔액보다 많습니다. 다시 입력해주세요");
                transferAmountini = scanner.next().replaceAll("[^0-9]","");
                transferAmount = new BigDecimal(transferAmountini);
            }
            try {
                accountFrom.withdraw(transferAmount);
                accountTo.deposit(transferAmount);
                System.out.printf("\n%s을 %s님에게 송금하였습니다.",df.format(transferAmount), accountTo.getOwner());
                System.out.printf("\n%s님의 계좌(%s)의 잔고: %s",accountFrom.getOwner(),accountFrom.getAccNo(), df.format(accountFrom.getBalance()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            System.out.println("송금에 문제가 발생하여 송금을 종료합니다.");
        }
    }
}
