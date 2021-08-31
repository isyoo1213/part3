package account;

import java.math.BigDecimal;

public class Account {
    String category;            // String 계좌 종류
    String accNo;               // String 계좌번호
    String owner;               // String 소유자
    BigDecimal balance;         // BigDecimal 잔액
    boolean isActive;           // boolean 활성화 여부

    public Account() {
        isActive = true;
        category = "N";
    }

    public Account(String accNo, String owner, BigDecimal balance) {
        this.accNo = accNo;
        this.owner = owner;
        this.balance = balance;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void getAccountInfo(Account account) {
        //계좌종류: %s | 계좌번호: %s | 계좌주명: %s | 잔액: %s원
        System.out.printf("\n%s | %s | %s | %s원", category, accNo, owner, balance);
    }

    public BigDecimal withdraw(BigDecimal amount) throws Exception {
        setBalance(this.balance.subtract(amount));
        return getBalance();
    }
    public BigDecimal deposit(BigDecimal amount) {
        setBalance(this.balance.add(amount));
        return getBalance();
    }
}
