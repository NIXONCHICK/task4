package task4.exceptions;

public class AccountIsLockedException extends Exception {
    public AccountIsLockedException(String message) {
        super(message);
    }
}
