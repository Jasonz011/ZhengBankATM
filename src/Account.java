public class Account {
    private Customer customer;
    private String type;
    private int balance;

    public Account(Customer customer, String type) {
        balance = 0;
        this.customer = customer;
        this.type = type.substring(0, 1).toUpperCase() + type.substring(1) + " account";
    }

    public int getBalance() {
        return balance;
    }

    public String getAccountType() {
        return type;
    }

    public void changeBalance(double amount) {
        if (balance + amount >= 0) {
            balance += amount;
        }
    }

}
