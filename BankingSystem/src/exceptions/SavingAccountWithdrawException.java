package exceptions;

import bank.Bank;

import java.math.BigDecimal;

public class SavingAccountWithdrawException extends WithdrawException {
    public SavingAccountWithdrawException(BigDecimal balance, BigDecimal goalAmount) {
        super(String.format("적금 계좌는 잔액(%s)이 목표 금액(%s원) 이상이어야 출금 가능합니다.", Bank.convertToString(balance), Bank.convertToString(goalAmount)));
    }
}
