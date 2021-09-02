package account;

import bank.Bank;

import java.math.BigDecimal;
import java.util.Objects;

import static account.Category.*;

public class Account {
    Category category;            // String 계좌 종류
    String accNo;               // String 계좌번호
    String owner;               // String 소유자
    BigDecimal balance;         // BigDecimal 잔액
    boolean isActive;           // boolean 활성화 여부

    Account() {
        isActive = true;
        category = N;
    }

    Account(String accNo, String owner, BigDecimal balance) {
        this();
        this.accNo = accNo;
        this.owner = owner;
        this.balance = balance;
    }

    public static Account create(String accNo, String owner) {
        return new Account(accNo, owner, new BigDecimal("0"));
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
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
        System.out.printf("\n%s | %s | %s | %s원", category, accNo, owner, Bank.df.format(balance));
    }

    public BigDecimal withdraw(BigDecimal amount) {
        setBalance(this.balance.subtract(amount));
        return getBalance();
    }
    public BigDecimal deposit(BigDecimal amount) {
        setBalance(this.balance.add(amount));
        return getBalance();
    }

    public boolean isPossibleWithdraw(BigDecimal amount) {
        return isValidAmount(amount) && getBalance().compareTo(amount) >= 0;
    }

    public static boolean isValidAmount(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accNo, account.accNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accNo);
    }
}
