import java.util.Scanner;
public class ATM {
    private Scanner scan;
    private Customer customer;
    private final String nextAction = "---------------------------";
    public ATM() {}

    public void start() {
        welcome();
        login();
        mainMenu();
    }

    public void welcome() {
        scan = new Scanner(System.in);
        System.out.println("Welcome to the ATM!");
        System.out.print("Enter your name: ");
        String name = scan.nextLine();
        System.out.print("Enter your 4 digit PIN: ");
        int pin = scan.nextInt();
        // if pin isn't 4 digits long exactly, the customer needs to re-enter it
        while (String.valueOf(pin).length() != 4) {
            System.out.println("Please enter a 4 digit PIN and try again: ");
            pin = scan.nextInt();
        }

        customer = new Customer(name, pin);
        Account savings = new Account(customer, "savings");
        Account checking = new Account(customer, "checking");
        System.out.println("Account successfully created!");
        next();
    }

    public void login() {
        System.out.println("Please enter the PIN: ");
        while (scan.nextInt() != customer.getPIN()) {
            scan.nextLine();
            System.out.print("Hmm, the PIN you entered didn't match the one we have on file. Please try again!");
            scan.nextInt();
        }
        System.out.println("Login successful!");
        next();
    }
    
    public void mainMenu() {
        int option = 0;
        while (option != 7) {
            next();
            System.out.println("1. Withdraw money");
            System.out.println("2. Deposit money");
            System.out.println("3. Transfer money between accounts");
            System.out.println("4. Get account balances");
            System.out.println("5. Get transaction history");
            System.out.println("6. Change PIN");
            System.out.println("7. Exit");
            next();
        }
    }

    private void next() {
        System.out.println(nextAction);
    }
}
