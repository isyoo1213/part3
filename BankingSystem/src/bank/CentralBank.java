package bank;

import account.Account;

import java.util.ArrayList;

public class CentralBank {
    //은행시스템의 계좌들을 관리하는 중앙은행 클래스입니다.
    //TODO: 싱글톤 패턴으로 설계합니다.
    private static CentralBank centralBank = null;

    private CentralBank() {
    }

    //TODO: accountList(Account로 이루어진 ArrayList)
    private ArrayList<Account> accountList = new ArrayList<>();

    //TODO: BANK_NAME(은행명)
    public final String BANK_NAME = "CentralBank";

    //TODO: getInstance 함수
    public static CentralBank getInstance() {
        if (centralBank == null)
            centralBank = new CentralBank();
        return centralBank;
    }

    //TODO: accountList getter/setter
    public ArrayList<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(ArrayList<Account> accountList) {
        this.accountList = accountList;
    }
}
