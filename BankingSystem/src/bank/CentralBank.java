package bank;

import account.Account;

import java.util.ArrayList;
import java.util.List;

public class CentralBank {
    public static final String BANK_NAME = "CENTRAL_BANK";

    private static final CentralBank CENTRAL_BANK = new CentralBank();

    private CentralBank() {}

    private List<Account> accountList = new ArrayList<>();

    public static CentralBank getInstance() {
        return CENTRAL_BANK;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }
}
