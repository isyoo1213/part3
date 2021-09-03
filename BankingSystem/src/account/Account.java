package account;

import bank.Bank;

import java.math.BigDecimal;

public class Account {
    //TODO: 일반 계좌 클래스의 속성은 계좌종류(N: 예금계좌, S:적금계좌), 계좌번호, 소유자, 잔액, 활성화 여부 5가지 입니다.
    private String category;    //category;             // String 계좌 종류
    private String accNo;       // accNo;               // String 계좌번호
    private String owner;       // owner;               // String 소유자
    private BigDecimal balance = BigDecimal.valueOf(0); // balance;             // BigDecimal 잔액
    private boolean isActive;   // isActive;            // boolean 활성화 여부

    public Account() {
        //TODO: 일반 계좌의 활성화 여부를 True로, 계좌 종류를 "N"(NORMAL을 의미) 설정해줍니다.
        this.isActive = true;
        this.category = "N";
    }

    public Account(String accNo, String owner, BigDecimal balance) {
        //TODO
        this.accNo = accNo;
        this.owner = owner;
        this.balance = balance;
        this.isActive = true;
        this.category = "N";
    }

    // TODO: 일반 계좌 클래스의 각 속성에 getter/setter를 제공합니다.

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
        //TODO: 계좌의 기본 정보를 아래 형태로 출력해줍니다.
        //계좌종류: %s | 계좌번호: %s | 계좌주명: %s | 잔액: %s원
        System.out.printf("\n계좌종류: %s | 계좌번호: %s | 계좌주명: %s | 잔액: %s원", category, accNo, owner, balance);
    }

    public BigDecimal withdraw(BigDecimal amount, String howTo) throws Exception {
        //TODO: 출금액을 받아서 출금하는 기본 메소드입니다. this를 이용해 구현해보세요.
        // 나가는 금액이 0일 때 에러 발생
        if (amount.equals(BigDecimal.valueOf(0))) {
            throw new Exception(howTo + "액을 입력하세요.");
        }
        // 나가는 금액이 잔액보다 크거나, 0보다 작을 때 에러 발생
        if (getBalance().compareTo(amount) < 0 || amount.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new Exception(howTo + "액을 확인해주세요.");
        }
        setBalance(this.balance.subtract(amount));
        return amount;
    }

    public BigDecimal deposit(BigDecimal amount) throws Exception {
        //TODO: 입금액을 받아서 입금하는 기본 메소드입니다. this를 이용해 구현해보세요.
        // 입금액이 0일 때 에러 발생
        if (amount.equals(BigDecimal.valueOf(0))) {
            throw new Exception("입금액을 입력하세요.");
        }
        // 입금액이 0보다 작을 때 에러 발생
        if (amount.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new Exception("입금액을 확인해주세요.");
        }
        setBalance(this.balance.add(amount));
        return amount;
    }
}