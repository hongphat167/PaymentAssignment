import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountService {
    private final Account account;
    private final List<Transaction> transactions;

    public AccountService() {
        this.account = new Account();
        this.transactions = new ArrayList<>();
    }

    public void addFunds(double amount) {
        account.addFunds(amount);
    }

    public double getBalance() {
        return account.getBalance();
    }

    public boolean payBill(Bill bill) {
        if (account.deductFunds(bill.getAmount())) {
            bill.setState("PAID");
            transactions.add(new Transaction(bill.getAmount(), new Date(), "PROCESSED", bill.getId()));
            return true;
        }
        return false;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
