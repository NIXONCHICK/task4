package task4.terminal;

import task4.exceptions.AccountIsLockedException;
import task4.exceptions.InsufficientFundsException;
import task4.exceptions.InvalidAmountException;
import task4.exceptions.InvalidPinException;

public class TerminalImpl implements Terminal {
    private final TerminalServer server;
    private final PinValidator pinValidator;

    private boolean isLocked = false;
    private long unlockTime = 0;
    private int failedAttempts = 0;
    private final StringBuilder pinInput = new StringBuilder();

    public TerminalImpl(TerminalServer server, PinValidator pinValidator) {
        this.server = server;
        this.pinValidator = pinValidator;
    }

    @Override
    public void enterPin(char digit) throws InvalidPinException, AccountIsLockedException {
        if (isLocked) {
            long remainingTime = (unlockTime - System.currentTimeMillis()) / 1000;
            if (remainingTime > 0) {
                throw new AccountIsLockedException("Account is locked. Try again in " + remainingTime + " seconds.");
            } else {
                isLocked = false;
                failedAttempts = 0;
            }
        }

        if (!Character.isDigit(digit)) {
            throw new InvalidPinException("PIN must be numeric. Please try again.");
        }

        pinInput.append(digit);
        if (pinInput.length() == 4) {
            if (pinValidator.validate(pinInput.toString())) {
                pinInput.setLength(0); // PIN validated
            } else {
                pinInput.setLength(0);
                failedAttempts++;
                if (failedAttempts >= 3) {
                    lockAccount(); // Устанавливаем блокировку
                    throw new AccountIsLockedException("Account is locked due to 3 invalid attempts. Try again in 10 seconds.");
                } else {
                    throw new InvalidPinException("Invalid PIN. You have " + (3 - failedAttempts) + " attempts left.");
                }
            }
        }
    }

    @Override
    public double checkBalance() throws AccountIsLockedException {
        ensureAccountUnlocked();
        return server.getBalance();
    }

    @Override
    public void deposit(double amount) throws AccountIsLockedException, InvalidAmountException {
        ensureAccountUnlocked();
        if (amount % 100 != 0) {
            throw new InvalidAmountException("Amount must be a multiple of 100.");
        }
        server.deposit(amount);
    }

    @Override
    public void withdraw(double amount) throws AccountIsLockedException, InvalidAmountException, InsufficientFundsException {
        ensureAccountUnlocked();
        if (amount % 100 != 0) {
            throw new InvalidAmountException("Amount must be a multiple of 100.");
        }
        if (!server.withdraw(amount)) {
            throw new InsufficientFundsException("Insufficient funds on your account.");
        }
    }

    private void ensureAccountUnlocked() throws AccountIsLockedException {
        if (isLocked) {
            long remainingTime = (unlockTime - System.currentTimeMillis()) / 1000;
            throw new AccountIsLockedException("Account is locked. Try again in " + remainingTime + " seconds.");
        }
    }

    private void lockAccount() {
        isLocked = true;
        unlockTime = System.currentTimeMillis() + 10000;
    }
}
