package task4.terminal;

import task4.exceptions.AccountIsLockedException;
import task4.exceptions.InsufficientFundsException;
import task4.exceptions.InvalidAmountException;
import task4.exceptions.InvalidPinException;

public interface Terminal {
    void enterPin(char digit) throws InvalidPinException, AccountIsLockedException;

    double checkBalance() throws AccountIsLockedException;

    void deposit(double amount) throws AccountIsLockedException, InvalidAmountException;

    void withdraw(double amount) throws AccountIsLockedException, InvalidAmountException, InsufficientFundsException;
}
