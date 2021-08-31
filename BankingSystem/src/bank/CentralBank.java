package bank;

import account.Account;

import java.math.BigDecimal;
import java.util.ArrayList;

public class CentralBank {
    //은행시스템의 계좌들을 관리하는 중앙은행 클래스입니다.
    //TODO: 싱글톤 패턴으로 설계합니다.
    //TODO: accountList(Account로 이루어진 ArrayList)
    //TODO: BANK_NAME(은행명)
    private static ArrayList<Account> accountList = new ArrayList<>();
    private static CentralBank instance = new CentralBank();

    private CentralBank() {

    }

    //TODO: getInstance 함수
    public static CentralBank getInstance(){
        if(instance == null){
            instance = new CentralBank();
        }
        return instance;
    }

    //TODO: accountList getter/setter
    public ArrayList<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(ArrayList<Account> accountList) {
        this.accountList = accountList;
    }
}
