import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountServiceTest {
    @Test
    public void test_AddFunds() {
        AccountService accountService = new AccountService();
        accountService.addFunds(1000);
        assertEquals(1000, accountService.getBalance());
    }

    @Test
    public void test_PayBill() {
        AccountService accountService = new AccountService();
        Bill bill = new Bill("ELECTRIC", 500, new Date(), "EVN HCMC");
        accountService.addFunds(1000);
        assertTrue(accountService.payBill(bill));
        assertEquals(500, accountService.getBalance());
    }

    @Test
    public void testPayBillInsufficientFunds() {
        AccountService accountService = new AccountService();
        Bill bill = new Bill("ELECTRIC", 1500, new Date(), "EVN HCMC");
        accountService.addFunds(1000);
        Assertions.assertFalse(accountService.payBill(bill));
        assertEquals(1000, accountService.getBalance());
    }
}
