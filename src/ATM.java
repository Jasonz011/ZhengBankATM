import java.util.Scanner;
public class ATM {
    private Scanner scan;
    private Customer customer;
    private Account savings;
    private Account checking;

    public ATM() {}

    public void start() {
        welcome();
        mainMenu();
    }

    public void welcome() {
        // welcomes customer to the atm
        scan = new Scanner(System.in);
        System.out.println("Welcome to the ATM!");
        System.out.print("Enter your name: ");
        String name = scan.nextLine();
        System.out.print("Enter your 4 digit PIN: ");
        int pin = scan.nextInt();

        // if pin isn't 4 digits long exactly, the customer needs to re-enter it
        while (String.valueOf(pin).length() != 4) {
            System.out.print("Please enter a 4 digit PIN and try again: ");
            pin = scan.nextInt();
        }

        // creates savings and checking account for customer
        customer = new Customer(name, pin);
        savings = new Account(customer, "savings");
        checking = new Account(customer, "checking");
        System.out.println("Account successfully created!");
        next();
    }

    public void login() {
        System.out.print("Please enter the PIN: ");
        int pin = scan.nextInt();

        while (pin != customer.getPIN()) {
            System.out.print("Hmm, the PIN you entered didn't match the one we have on file. Please try again: ");
            pin = scan.nextInt();
        }
        System.out.println("Login successful!");
    }
    
    public void mainMenu() {
        int option = 0;
        String account = "";
        double withdrawAmount = 0;

        while (option != 7) {
            // after each action, the customer has to re-enter their pin to log in
            login();

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
                System.out.print("Which account would you like to withdraw money from, (s)avings or (c)hecking? ");
                account = scan.nextLine();

                // savings
                if (account.equalsIgnoreCase("s")) {
                    System.out.print("How much money would you like to withdraw(has to be multiple of 5)? ");
                    withdrawAmount = scan.nextDouble();

                    if (savings.getBalance() - withdrawAmount < 0) {
                        // if the customer enters an amount greater than their current balance, than no money is withdrawn
                        System.out.println("Transaction failed due to insufficient funds. No money was withdrawn.");
                        TransactionHistory.addToHistory("Transaction failed due to insufficient funds. No money was withdrawn.");
                        System.out.println(TransactionHistory.newTransactionID());

                    } else if (withdrawAmount % 5 == 0) {
                        System.out.print("How many of these bills would you like in 20s? ");
                        int numberOfTwenties = scan.nextInt();
                        scan.nextLine();
                        if (numberOfTwenties * 20 > withdrawAmount) {
                            // if the customer enters an invalid amount, no money gets withdrawn
                            System.out.println("This amount of $20 bills exceeds the amount that you are trying to withdraw.");
                            System.out.println("Transaction failed due to insufficient funds. No money was withdrawn.");
                            TransactionHistory.addToHistory("Transaction failed due to insufficient funds. No money was withdrawn.");
                            System.out.println(TransactionHistory.newTransactionID());

                        } else {
                            // withdraw successful, the customer is told how many of each bill they receive
                            int numberOfFives = (int) ((withdrawAmount - (numberOfTwenties * 20)) / 5);
                            System.out.println("Here are " + numberOfTwenties + " twenties and " +  numberOfFives + " fives ");
                            savings.changeBalance(-withdrawAmount);
                            System.out.println("Transaction successful!");
                            System.out.println("Withdrawn $" + withdrawAmount + " from savings");
                            TransactionHistory.addToHistory("Withdrawn $" + withdrawAmount + " from savings");
                            System.out.println(TransactionHistory.newTransactionID());
                            getBalances();

                        }
                    } else {
                        // if the customer enters an amount that isn't divisible by 5, no money gets withdrawn
                        System.out.println("Please enter an amount of money that is a multiple of 5!");
                        System.out.println("Transaction failed due invalid option. No money was withdrawn.");
                        System.out.println(TransactionHistory.newTransactionID());
                        TransactionHistory.addToHistory("Transaction failed due invalid option. No money was withdrawn.");

                    }
                } else if (account.equalsIgnoreCase("c")) {
                    System.out.print("How much money would you like to withdraw(has to be multiple of 5)? ");
                    withdrawAmount = scan.nextDouble();

                    if (checking.getBalance() - withdrawAmount < 0) {
                        // if the customer enters an amount greater than their current balance, than no money is withdrawn
                        System.out.println("Transaction failed due to insufficient funds. No money was withdrawn.");
                        TransactionHistory.addToHistory("Transaction failed due invalid option. No money was withdrawn.");
                        System.out.println(TransactionHistory.newTransactionID());

                    } else if (withdrawAmount % 5 == 0) {
                        System.out.print("How many of these bills would you like in 20s? ");
                        int numberOfTwenties = scan.nextInt();
                        scan.nextLine();

                        if (numberOfTwenties * 20 > withdrawAmount) {
                            // if the customer enters an invalid amount, no money gets withdrawn
                            System.out.println("This amount of $20 bills exceeds the amount that you are trying to withdraw.");
                            System.out.println("Transaction failed due to invalid option. No money was withdrawn.");
                            System.out.println(TransactionHistory.newTransactionID());
                            TransactionHistory.addToHistory("Transaction failed due invalid option. No money was withdrawn.");

                        } else {
                            // withdraw successful, the customer is told how many of each bill they receive
                            int numberOfFives = (int) ((withdrawAmount - (numberOfTwenties * 20)) / 5);
                            System.out.println("Here are " + numberOfTwenties + " twenties and " +  numberOfFives + " fives");
                            checking.changeBalance(-withdrawAmount);
                            System.out.println("Transaction successful!");
                            System.out.println("Withdrawn $" + withdrawAmount + " from checking");
                            TransactionHistory.addToHistory("Withdrawn $" + withdrawAmount + " from savings");
                            System.out.println(TransactionHistory.newTransactionID());
                            getBalances();

                        }
                    } else {
                        // if the customer enters an invalid amount, no money gets withdrawn
                        System.out.println("Please enter an amount of money that is a multiple of 5!");
                        System.out.println("Transaction failed due to insufficient funds. No money was withdrawn.");
                        TransactionHistory.addToHistory("Transaction failed due to insufficient funds. No money was withdrawn.");
                        System.out.println(TransactionHistory.newTransactionID());
                    }
                } else {
                    System.out.println("Invalid option. Choose either s for savings or c for checking.");
                }

            } else if (option == 2) { // depositing
                double depositAmount = 0;
                System.out.print("Which account would you like to deposit money into, (s)avings or (c)hecking? ");
                account = scan.nextLine();

                if (account.equalsIgnoreCase("s")) {
                    System.out.println("Enter deposit amount: ");
                    depositAmount = scan.nextDouble();
                    savings.changeBalance(depositAmount);
                    System.out.println("Transaction successful. $" + depositAmount + " deposited into savings account.");
                    TransactionHistory.addToHistory("Transaction successful. $" + depositAmount + " deposited into savings account.");
                    System.out.println(TransactionHistory.newTransactionID());

                } else if (account.equalsIgnoreCase("c")) {
                    System.out.println("Enter deposit amount: ");
                    depositAmount = scan.nextDouble();
                    checking.changeBalance(depositAmount);
                    System.out.println("Transaction successful. $" + depositAmount + " deposited into checking account.");
                    TransactionHistory.addToHistory("Transaction successful. $" + depositAmount + " deposited into checking account.");
                    System.out.println(TransactionHistory.newTransactionID());
                } else {
                    System.out.println("Invalid option. Choose either s for savings or c for checking.");
                }

            } else if (option == 3) { // transferring money
                System.out.print("Which account would you like to transfer money from, (s)avings or (c)hecking?");
                account = scan.nextLine();

                // customer needs to enter a valid amount
                if (account.equalsIgnoreCase("s")) {
                    System.out.println("Enter transfer amount: ");
                    double transferAmount = scan.nextDouble();
                    scan.nextLine();

                    if (savings.getBalance() < transferAmount) {
                        System.out.println("Transaction failed due to insufficient funds. No money was withdrawn.");
                        TransactionHistory.addToHistory("Transaction failed due to insufficient funds. No money was withdrawn.");
                        System.out.println(TransactionHistory.newTransactionID());

                    } else {
                        savings.changeBalance(-transferAmount);
                        checking.changeBalance(transferAmount);
                        System.out.println("Transaction successful! " +transferAmount+ " transferred from savings account to checking account");
                        TransactionHistory.addToHistory("Transaction successful! " +transferAmount+ " transferred from savings account to checking account");
                        System.out.println(TransactionHistory.newTransactionID());

                    }
                } else if (account.equalsIgnoreCase("c")) {
                    System.out.println("Enter transfer amount: ");
                    double transferAmount = scan.nextDouble();
                    scan.nextLine();

                    if (checking.getBalance() < transferAmount) {
                        System.out.println("Transaction failed due to insufficient funds. No money was withdrawn.");
                        TransactionHistory.addToHistory("Transaction failed due to insufficient funds. No money was withdrawn.");
                        System.out.println(TransactionHistory.newTransactionID());

                    } else {
                        checking.changeBalance(-transferAmount);
                        savings.changeBalance(transferAmount);
                        System.out.println("Transaction successful! " + transferAmount + " transferred from checking account to savings account");
                        TransactionHistory.addToHistory("Transaction successful! " + transferAmount + " transferred from checking account to savings account");
                        System.out.println(TransactionHistory.newTransactionID());

                    }
                } else {
                    System.out.println("Invalid option. Choose either s for savings or c for checking.");
                }

            } else if (option == 4) { // showing balances
                getBalances();
                System.out.println("Checked balances.");
                TransactionHistory.addToHistory("Checked balances.");
                System.out.println(TransactionHistory.newSecurityID());

            } else if (option == 5) { // showing Transaction history
                TransactionHistory.getTransactionHistory();
                System.out.println("Checked transaction history.");
                TransactionHistory.addToHistory("Checked transaction history.");
                System.out.println(TransactionHistory.newSecurityID());

            } else if (option == 6) { // changing PIN
                // Setting new PIN that has to be 4 digits long and different from customer's current pin
                int pin = 0;
                System.out.println("Enter new pin: ");
                pin = scan.nextInt();

                if (String.valueOf(pin).length() != 4) {
                    System.out.println("Action failed. Please enter a 4 digit PIN.");
                    TransactionHistory.addToHistory("Action failed. Please enter a 4 digit PIN.");
                    System.out.println(TransactionHistory.newSecurityID());

                } else if (pin == customer.getPIN()) {
                    System.out.println("Action failed. Please enter a new 4 digit PIN.");
                    TransactionHistory.addToHistory("Action failed. Please enter a new 4 digit PIN.");
                    System.out.println(TransactionHistory.newSecurityID());

                } else {
                    System.out.println("Action successful. Pin successfully changed.");
                    TransactionHistory.addToHistory("Action successful. Pin successfully changed.");
                    System.out.println(TransactionHistory.newSecurityID());
                    customer.setPIN(pin);

                }
            } else if (option == 7) { // Exit option
                System.out.println("Thank you for using the ATM service, goodbye!");
            } else {
                System.out.println("Invalid option! Please enter a valid menu option!");
            }
            next();
        }
    }

    private void getBalances() {
        System.out.println(savings.getAccountType() + ": $" + savings.getBalance() + ", " + checking.getAccountType() + ": $" + checking.getBalance());
    }

    private void next() {
        String nextAction = "---------------------------";
        System.out.println(nextAction);
    }
}
