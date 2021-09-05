package status;

import bank.Bank;

import java.math.BigDecimal;

public class WithdrawStatus extends Exception {
    protected WithdrawStatus(String message) {
        super(message);
    }

    public WithdrawStatus(BigDecimal remitAmount, BigDecimal balance) {
        super(String.format("요청한 금액(%s원)이 잔액(%s원)보다 많습니다.", Bank.bigDecimalToString(remitAmount), Bank.bigDecimalToString(balance)));
    }
}
