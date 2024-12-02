package task4.terminal;

public class PinValidator {
    private final String validPin;

    public PinValidator(String validPin) {
        this.validPin = validPin;
    }

    public boolean validate(String pin) {
        return validPin.equals(pin);
    }
}
