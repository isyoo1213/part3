package bank;

import account.Account;

import java.util.ArrayList;
import java.util.Arrays;

public class CentralBank {
    //은행시스템의 계좌들을 관리하는 중앙은행 클래스입니다.
    //TODO: 싱글톤 패턴으로 설계합니다.
    //TODO: accountList(Account로 이루어진 ArrayList)
    //TODO: BANK_NAME(은행명)
    private static ArrayList<Account> accountList;
    private static final String BANK_NAME = "Hyuns bro";
    //TODO: getInstance 함수

    public static void getInstance(){
        accountList = new ArrayList<Account>();
    }

    //TODO: accountList getter/setter

    public ArrayList<Account> getaccountList(){
        return accountList;
    }

    public void setaccountList(ArrayList<Account> accountList){
        accountList = accountList;
    }
}
