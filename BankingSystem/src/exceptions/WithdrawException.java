package exceptions;

import bank.Bank;

import java.math.BigDecimal;

public class WithdrawException extends Exception {
    protected WithdrawException(String message) { super(message); }
    public WithdrawException(BigDecimal requestAmount, BigDecimal balance) {
        super(String.format("요청한 금액(%s원)이 잔액(%s원)보다 많습니다.", Bank.convertToString(requestAmount), Bank.convertToString(balance)));
    }
}
