package exception;

/**
 * 은행 비즈니스로직 예외 정의
 * */
public class BankException extends RuntimeException {
    public BankException(String message) {
        super(message);
    }
}
