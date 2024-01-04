public class Account {
    private Customer customer;
    private String type;
    private int balance;

    public Account(Customer customer, String type) {
        balance = 0;
        this.customer = customer;
        this.type = type;
    }

    public void changeBalance(int amount) {
        if (balance + amount >= 0) {
            balance += amount;
        } else {
            System.out.println("");
        }
    }
}
