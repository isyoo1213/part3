package account;

import java.math.BigDecimal;

public class SavingAccount extends Account {
    private BigDecimal goalAmount = new BigDecimal(100000);

    public SavingAccount(){
        super.category = "S";
    }

    public SavingAccount(String accNo, String owner, BigDecimal balance, BigDecimal goalAmount) {
        this.accNo = accNo;
        this.owner = owner;
        this.balance = balance;
        this.goalAmount = goalAmount;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }

    public void getAccountInfo(Account account) {
        System.out.printf("\n%s | %s | %s | %s원 | %s원", category, accNo, owner, balance, goalAmount);
    }
}
