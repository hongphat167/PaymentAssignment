
import java.util.Date;

public class Bill {
    private static int no = 1;
    private final int id;
    private final String type;
    private final double amount;
    private final Date dueDate;
    private String state;
    private final String provider;

    public Bill(String type, double amount, Date dueDate, String provider) {
        this.id = no++;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = "NOT_PAID";
        this.provider = provider;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProvider() {
        return provider;
    }
}
