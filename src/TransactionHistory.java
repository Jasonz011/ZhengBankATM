import java.util.ArrayList;
public class TransactionHistory {
    private static final ArrayList<String> messages = new ArrayList<>();
    private static final ArrayList<String> transactionNumber = new ArrayList<>();
    private static final ArrayList<String> securityNumber = new ArrayList<>();
    private static int transactionOnes;
    private static int transactionTens;
    private static int transactionHundreds;
    private static int transactionThousands;
    private static int securityOnes;
    private static int securityTens;
    private static int securityHundreds;
    private static int securityThousands;

    private TransactionHistory() {}

    public static String newTransactionID() {
        // adds one to one's digit, and if any digit reaches 10, then it carries over to the next digit
        transactionOnes++;
        if (transactionOnes == 10) {
            transactionOnes = 0;
            transactionTens++;
        }
        if (transactionTens == 10) {
            transactionTens = 0;
            transactionHundreds++;
        }
        if (transactionHundreds == 10) {
            transactionHundreds = 0;
            transactionThousands++;
        }
        // adds transaction number to history
        String number = "A" + transactionThousands + transactionHundreds + transactionTens + transactionOnes;
        messages.add(number);
        transactionNumber.add(String.valueOf(transactionThousands + transactionHundreds + transactionTens + transactionOnes));
        return number;
    }

    public static String newSecurityID() {
        securityOnes++;
        if (transactionOnes == 10) {
            securityOnes = 0;
            securityTens++;
        }
        if (transactionTens == 10) {
            securityTens = 0;
            securityHundreds++;
        }
        if (transactionHundreds == 10) {
            securityHundreds = 0;
            securityThousands++;
        }
        // adds security number to history
        String number = "S" + securityThousands + securityHundreds + securityTens + securityOnes;
        messages.add(number);
        securityNumber.add(String.valueOf(securityThousands + securityHundreds + securityTens + securityOnes));
        return number;
    }

    public static void getTransactionHistory() {
        for (String s : messages) {
            System.out.println(s);
        }
    }

    public static void addToHistory(String s) {
        messages.add(s);
    }
}
