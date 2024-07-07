
import java.util.*;

public class BillService {
    private final List<Bill> bills = new ArrayList<>();

    public void addBill(Bill bill) {
        bills.add(bill);
    }

    public List<Bill> getBills() {
        return bills;
    }

    public Bill getBillById(int id) {
        for (Bill bill : bills) {
            if (bill.getId() == id) {
                return bill;
            }
        }
        return null;
    }

    public List<Bill> getBillsByProvider(String provider) {
        List<Bill> result = new ArrayList<>();
        for (Bill bill : bills) {
            if (bill.getProvider().equalsIgnoreCase(provider)) {
                result.add(bill);
            }
        }
        return result;
    }
}
