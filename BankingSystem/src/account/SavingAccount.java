package account;

import bank.Bank;

import java.math.BigDecimal;

import static account.Category.*;

public class SavingAccount extends Account {
    private BigDecimal goalAmount = new BigDecimal(100000);

    SavingAccount(){
        super.category = S;
    }

    SavingAccount(String accNo, String owner, BigDecimal balance, BigDecimal goalAmount) {
        this();
        this.accNo = accNo;
        this.owner = owner;
        this.balance = balance;
        this.goalAmount = goalAmount;
    }

    public static SavingAccount create(String accNo, String owner, BigDecimal balance, BigDecimal goalAmount) {
        return new SavingAccount(accNo, owner, balance, goalAmount);
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }

    public void getAccountInfo(Account account) {
        System.out.printf("\n%s | %s | %s | %s원 | %s원", category, accNo, owner, Bank.df.format(balance), Bank.df.format(goalAmount));
    }
}
