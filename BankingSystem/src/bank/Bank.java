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

    //계좌번호 생성을 위한 countAcc 변수 생성
    protected static int countAcc = 0;

    //countAcc의 getter, setter 생성
    public static int getCountAcc() {
        return countAcc;
    }

    public static void setCountAcc(int countAcc) {
        Bank.countAcc = countAcc;
    }

    //계좌별 InterestCalculator를 담아줄 HashMap 생성
    HashMap<String, InterestCalculator> interestCalculatorHashMap = new HashMap<>();

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

    public void deposit() throws AccountException, AmountException{

        boolean depositActive = true;
        while(depositActive){
            Account virtualDeopsitAccount;
            //TODO: 입금 메서드 구현
            // 존재하지 않는 계좌이면 다시 물어보기
            // TODO: 입금 처리

            System.out.println("\n입금하시려는 계좌번호를 입력해주세요.");
            String accNo = scanner.next();
            virtualDeopsitAccount = this.findAccount(accNo);

            try{
                if(virtualDeopsitAccount == null){
                    throw new AccountException("올바른 계좌번호를 입력해주세요.");
                } else {
                    System.out.println("\n입금할 금액을 입력하세요.");
                    String strAmount = scanner.next();
                    BigDecimal depositAmount;

                    if(!strAmount.matches("[0-9]+")){
                        throw new AmountException("금액은 0~9의 숫자의 조합으로만 입력해주세요.");
                    } else {
                        depositAmount = new BigDecimal(strAmount);
                    }
                    if (depositAmount.compareTo(BigDecimal.ZERO) <= 0) {
                        throw new AmountException("1원 이상의 입금 금액을 입력해주세요");
                    } else {
                        this.findAccount(accNo).deposit(depositAmount);
                        virtualDeopsitAccount.setBalance(this.findAccount(accNo).getBalance());
                        System.out.println("입금이 완료됐습니다."+"\n"+virtualDeopsitAccount.getAccNo()+ "계좌의 잔액은 "+virtualDeopsitAccount.getBalance()+"원 입니다.");
                    }
                    break;
                }
            } catch ( AccountException e){
                depositActive = false;
                System.out.println(e.getMessage());
            } catch ( AmountException e){
                depositActive = false;
                System.out.println(e.getMessage());
            }
        }
    }

    public  Account createAccount() throws OwnerException {
        //TODO: 계좌 생성하는 메서드 구현
        // 계좌번호 채번
        // 계좌번호는 "0000"+증가한 seq 포맷을 가진 번호입니다.
        //TODO

        if(this.getCountAcc() == 0){
            this.setCountAcc(this.getCountAcc() + 1);
        }

        String newOwner = null;
        boolean createActive = true;
        while(createActive){

            System.out.println("\n일반계좌 생성을 시작합니다.");
            System.out.println("생성하시려는 일반계좌의 계좌주 성함을 입력해주세요.");
            newOwner = scanner.next();

            //계좌주 이름이 완성형 한글이 아닐 경우 exception 처리
            try{
                if(newOwner.matches("[가-힣].+")){
                    createActive = false;
                } else {
                    createActive = true;
                    throw new OwnerException("이름은 완성된 한글로만 입력할 수 있습니다.");
                }
            } catch (OwnerException e ){
                System.out.println(e.getMessage());
            }

        }

        String newAccNo = String.format("%04d", this.getCountAcc());
        BigDecimal newBalance = new BigDecimal("0");

        Account account = new Account(newAccNo, newOwner, newBalance);
        account.setActive(true);
        account.setCategory("N");

        System.out.printf("\n%s님 일반계좌가 발급되었습니다.\n", newOwner);
        account.getAccountInfo(account); //출력

        this.setCountAcc(this.getCountAcc()+1);

        return account;
    }

    public Account findAccount(String accNo){

        //TODO: 계좌리스트에서 찾아서 반환하는 메서드 구현

        boolean validation = false;
        int goalIndex = 0;
        for(Account account : CentralBank.getAccountList()){
            if( account.getAccNo().equals(accNo) ) {
                validation = account.getAccNo().equals(accNo);
                goalIndex = CentralBank.getAccountList().indexOf(account);
                break;
            }
        }
        if(validation){
            return CentralBank.getAccountList().get(goalIndex);
        } else {
            return null;
        }

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
