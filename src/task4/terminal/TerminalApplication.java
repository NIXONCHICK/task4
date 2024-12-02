package task4.terminal;

import java.util.Scanner;

import task4.exceptions.AccountIsLockedException;
import task4.exceptions.InsufficientFundsException;
import task4.exceptions.InvalidAmountException;
import task4.exceptions.InvalidPinException;

public class TerminalApplication {
    public static void main(String[] args) {
        TerminalServer server = new TerminalServer();
        PinValidator pinValidator = new PinValidator("1234");
        Terminal terminal = new TerminalImpl(server, pinValidator);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Terminal!");

        while (true) {
            try {
                // Ввод PIN-кода
                System.out.print("Enter PIN (digit by digit): ");
                for (int i = 0; i < 4; i++) {
                    char digit = scanner.nextLine().charAt(0);
                    terminal.enterPin(digit);
                }

                System.out.println("Access granted.");

                // Меню операций
                while (true) {
                    System.out.println("1. Check Balance");
                    System.out.println("2. Deposit Money");
                    System.out.println("3. Withdraw Money");
                    System.out.println("4. Exit");
                    System.out.print("Choose an option: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    if (choice == 1) {
                        System.out.println("Balance: " + terminal.checkBalance());
                    } else if (choice == 2) {
                        System.out.print("Enter amount to deposit: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();
                        terminal.deposit(amount);
                        System.out.println("Deposit successful!");
                    } else if (choice == 3) {
                        System.out.print("Enter amount to withdraw: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();
                        terminal.withdraw(amount);
                        System.out.println("Withdrawal successful!");
                    } else if (choice == 4) {
                        System.out.println("Thank you for using the Terminal!");
                        break;
                    } else {
                        System.out.println("Invalid option. Please try again.");
                    }
                }
            } catch (AccountIsLockedException | InvalidPinException | InvalidAmountException |
                     InsufficientFundsException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }
}
