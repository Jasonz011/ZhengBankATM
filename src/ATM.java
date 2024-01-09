import java.util.Scanner;
public class ATM {
    private Scanner scan;
    private Customer customer;
    private Account savings;
    private Account checking;

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
        savings = new Account(customer, "savings");
        checking = new Account(customer, "checking");
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
        String account = "";
        double withdrawAmount = 0;
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
            System.out.print("Enter choice: ");
            option = scan.nextInt();
            scan.nextLine();
            if (option == 1) { // withdrawing
                System.out.print("Which account would you like to withdraw money from, (s)avings or (c)hecking?");
                account = scan.nextLine();
                // savings
                if (account.equalsIgnoreCase("s")) {
                    System.out.print("How much money would you like to withdraw(has to be multiple of 5)?");
                    withdrawAmount = scan.nextDouble();
                    if (savings.getBalance() - withdrawAmount < 0) {
                        System.out.println("insufficient funds!");
                    } else if (withdrawAmount % 5 == 0) {
                        System.out.print("How many of these bills would you like in 20s?");
                        int numberOfTwenties = scan.nextInt();
                        scan.nextLine();
                        if (numberOfTwenties * 20 > withdrawAmount) {
                            System.out.println("This amount of $20 bills exceeds the amount that you are trying to withdraw.");
                        } else {
                            int numberOfFives = (int) ((withdrawAmount - numberOfTwenties * 20) % 5);
                            System.out.println("Here are " + numberOfTwenties + " twenties and " +  numberOfFives + " fives");
                            savings.changeBalance(-withdrawAmount);
                        }
                    } else {
                        System.out.println("Please enter an amount of money that is a multiple of 5!");
                    }
                } else if (account.equalsIgnoreCase("c")) {
                    System.out.print("How much money would you like to withdraw(has to be multiple of 5)?");
                    withdrawAmount = scan.nextDouble();
                    if (checking.getBalance() - withdrawAmount < 0) {
                        System.out.println("You don't have that much money in your account!");
                    } else if (withdrawAmount % 5 == 0) {
                        System.out.print("How many of these bills would you like in 20s?");
                        int numberOfTwenties = scan.nextInt();
                        scan.nextLine();
                        if (numberOfTwenties * 20 > withdrawAmount) {
                            System.out.println("This amount of $20 bills exceeds the amount that you are trying to withdraw.");
                        } else {
                            int numberOfFives = (int) ((withdrawAmount - numberOfTwenties * 20) % 5);
                            System.out.println("Here are " + numberOfTwenties + " twenties and " +  numberOfFives + " fives");
                            checking.changeBalance(-withdrawAmount);
                        }
                    } else {
                        System.out.println("Please enter an amount of money that is a multiple of 5!");
                    }
                } else {
                    System.out.println("Invalid option. Choose either s for savings or c for checking.");
                }
            } else if (option == 2) {
                System.out.print("Which account would you like to deposit money into, (s)avings or (c)hecking?");
                account = scan.nextLine();
                if (account.equalsIgnoreCase("s")) {
                    System.out.println("Enter deposit amount: ");
                    savings.changeBalance(scan.nextDouble());
                } else if (account.equalsIgnoreCase("c")) {
                    System.out.println("Enter deposit amount: ");
                    checking.changeBalance(scan.nextDouble());
                } else {
                    System.out.println("Invalid option. Choose either s for savings or c for checking.");
                }
            } else if (option == 3) {
                System.out.print("Which account would you like to transfer money from, (s)avings or (c)hecking?");
                account = scan.nextLine();
                if (account.equalsIgnoreCase("s")) {
                    System.out.println("Enter transfer amount: ");
                    double transferAmount = scan.nextDouble();
                    scan.nextLine();
                    if (savings.getBalance() < transferAmount) {
                        System.out.println("insufficient funds!");
                    } else {
                        savings.changeBalance(-transferAmount);
                        checking.changeBalance(transferAmount);
                        System.out.println("Transaction successful! " +transferAmount+ " transferred from savings account to checking account");
                    }
                } else if (account.equalsIgnoreCase("c")) {
                    System.out.println("Enter transfer amount: ");
                    double transferAmount = scan.nextDouble();
                    scan.nextLine();
                    if (checking.getBalance() < transferAmount) {
                        System.out.println("insufficient funds!");
                    } else {
                        checking.changeBalance(-transferAmount);
                        savings.changeBalance(transferAmount);
                        System.out.println("Transaction successful! " + transferAmount + " transferred from checking account to savings account");
                    }
                } else {
                    System.out.println("Invalid option. Choose either s for savings or c for checking.");
                }
            } else if (option == 4) {
                System.out.println(savings.getAccountType() + ": " + savings.getBalance() + ", " + checking.getAccountType() + ": " + checking.getBalance());
            } else if (option == 5) {

            } else if (option == 6) {
                int pin = 0;
                System.out.println("Enter new pin: ");
                pin = scan.nextInt();
                if (String.valueOf(pin).length() != 4) {
                    System.out.println("Please enter a 4 digit PIN.");
                } else {
                    System.out.println("Pin successfully changed.");
                    customer.setPIN(pin);
                }
            } else if (option == 7) {
                System.out.println("Thank you for using the ATM service, goodbye!");
            } else {
                System.out.println("Invalid option! Please enter a valid menu option!");

            }
        }
    }

    private void next() {
        String nextAction = "---------------------------";
        System.out.println(nextAction);
    }
}
