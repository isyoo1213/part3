package account;

import bank.Bank;

import java.math.BigDecimal;

public class Account {
    //TODO: 일반 계좌 클래스의 속성은 계좌종류(N: 예금계좌, S:적금계좌), 계좌번호, 소유자, 잔액, 활성화 여부 5가지 입니다.
    //category;             // String 계좌 종류
    // accNo;               // String 계좌번호
    // owner;               // String 소유자
    // balance;             // BigDecimal 잔액
    // isActive;            // boolean 활성화 여부

    public Account() {
        //TODO: 일반 계좌의 활성화 여부를 True로, 계좌 종류를 "N"(NORMAL을 의미) 설정해줍니다.
    }
    public Account(String accNo, String owner, BigDecimal balance) {
        //TODO
    }

    // TODO: 일반 계좌 클래스의 각 속성에 getter/setter를 제공합니다.

    public void getAccountInfo(Account account){
        //TODO: 계좌의 기본 정보를 아래 형태로 출력해줍니다.
        //계좌종류: %s | 계좌번호: %s | 계좌주명: %s | 잔액: %s원
    }

    public BigDecimal withdraw(BigDecimal amount) throws Exception{
        //TODO: 출금액을 받아서 출금하는 기본 메소드입니다. this를 이용해 구현해보세요.

        return amount;
    }
    public BigDecimal deposit(BigDecimal amount){
        //TODO: 입금액을 받아서 입금하는 기본 메소드입니다. this를 이용해 구현해보세요.
        return amount;
    }
}

