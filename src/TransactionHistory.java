import java.util.ArrayList;

public class TransactionHistory {
    private static ArrayList<String> messages = new ArrayList<>();
    private static ArrayList<String> transactionNumber = new ArrayList<>();
    private static ArrayList<String> securityNumber = new ArrayList<>();
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
        String message = "A" + transactionThousands + transactionHundreds + transactionTens + transactionOnes;
        messages.add(message);
        transactionNumber.add(String.valueOf(transactionThousands + transactionHundreds + transactionTens + transactionOnes));
        return message;
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
        String message = "S" + securityThousands + securityHundreds + securityTens + securityOnes;
        messages.add(message);
        securityNumber.add(String.valueOf(securityThousands + securityHundreds + securityTens + securityOnes));
        return message;
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
