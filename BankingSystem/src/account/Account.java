package account;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Account {
    //TODO: 일반 계좌 클래스의 속성은 계좌종류(N: 예금계좌, S:적금계좌), 계좌번호, 소유자, 잔액, 활성화 여부 5가지 입니다.
    String category; //계좌 종류
    String accNo; //계좌번호
    String owner; //소유자
    BigDecimal balance ; //잔액
    boolean isActive; //활성화여부

    public Account() {
        //TODO: 일반 계좌의 활성화 여부를 True로, 계좌 종류를 "N"(NORMAL을 의미) 설정해줍니다.
        category = "N";
        isActive = true;
        balance = BigDecimal.valueOf(0);
    }
    public Account(String accNo, String owner, BigDecimal balance) {
        //TODO
        category = "N";
        isActive = true;
        this.accNo = accNo;
        this.owner = owner;
        this.balance = balance;
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

    public void getAccountInfo(Account account){
        //TODO: 계좌의 기본 정보를 아래 형태로 출력해줍니다.
        //계좌종류: %s | 계좌번호: %s | 계좌주명: %s | 잔액: %s원]
        DecimalFormat formatted = new DecimalFormat("#,###원");
        System.out.printf("계좌종류: %s | 계좌번호: %s | 계좌주명: %s | 잔액: %s \n", category, accNo, owner, formatted.format(balance));
    }

    public BigDecimal withdraw(BigDecimal amount) throws Exception{
        //TODO: 출금액을 받아서 출금하는 기본 메소드입니다. this를 이용해 구현해보세요.
        this.balance = balance.subtract(amount);
        return balance;
    }
    public BigDecimal deposit(BigDecimal amount){
        //TODO: 입금액을 받아서 입금하는 기본 메소드입니다. this를 이용해 구현해보세요.
        this.balance = balance.add(amount);
        return balance;
    }
}

