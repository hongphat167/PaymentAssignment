
import java.util.Date;

public class Transaction {
    private final double amount;
    private final Date paymentDate;
    private final String state;
    private final int billId;

    public Transaction(double amount, Date paymentDate, String state, int billId) {
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.state = state;
        this.billId = billId;
    }

    public double getAmount() {
        return amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public String getState() {
        return state;
    }

    public int getBillId() {
        return billId;
    }
}
