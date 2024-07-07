import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillServiceTest {
    @Test
    public void test_AddBill() {
        BillService billService = new BillService();
        Bill expectedBill = new Bill("ELECTRIC", 500, new Date(), "EVN HCMC");
        billService.addBill(expectedBill);
        List<Bill> actualBill = billService.getBills();
        assertEquals(1, actualBill.size());
        assertEquals(expectedBill, actualBill.get(0));
    }

    @Test
    public void test_GetBillById() {
        BillService billService = new BillService();
        Bill expectedBill = new Bill("ELECTRIC", 500, new Date(), "EVN HCMC");
        billService.addBill(expectedBill);
        Bill actualBill = billService.getBillById(expectedBill.getId());
        assertEquals(expectedBill, actualBill);
    }

    @Test
    public void test_GetBillsByProvider() {
        BillService billService = new BillService();
        Bill bill1 = new Bill("ELECTRIC", 500, new Date(), "EVN HCMC");
        Bill bill2 = new Bill("WATER", 300, new Date(), "SAVACO HCMC");
        billService.addBill(bill1);
        billService.addBill(bill2);
        List<Bill> bills = billService.getBillsByProvider("SAVACO HCMC");
        assertEquals(1, bills.size());
        assertEquals(bill2, bills.get(0));
    }
}
