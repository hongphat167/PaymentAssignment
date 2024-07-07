import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class PaymentService {
    private final AccountService accountService;
    private final BillService billService;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public PaymentService() {
        this.accountService = new AccountService();
        this.billService = new BillService();
        initData();
    }

    private void initData() {
        try {
            billService.addBill(new Bill("ELECTRIC", 200000, dateFormat.parse("25/10/2020"), "EVN HCMC"));
            billService.addBill(new Bill("WATER", 175000, dateFormat.parse("30/10/2020"), "SAVACO HCMC"));
            billService.addBill(new Bill("INTERNET", 800000, dateFormat.parse("30/11/2020"), "VNPT"));
        } catch (ParseException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void handle(String command) {
        String[] input = command.split(" ");
        switch (input[0]) {
            case "CASH_IN":
                double amount = Double.parseDouble(input[1]);
                accountService.addFunds(amount);
                System.out.println("Your available balance: " + accountService.getBalance());
                break;
            case "LIST_BILL":
                listBills();
                break;
            case "PAY":
                payBills(input);
                break;
            case "DUE_DATE":
                listDueDates();
                break;
            case "SCHEDULE":
                schedulePayment(input);
                break;
            case "LIST_PAYMENT":
                if (input.length > 1 && input[1].equalsIgnoreCase("PENDING")) {
                    listPayments("PENDING");
                } else {
                    listPayments(null);
                }
                break;
            case "SEARCH_BILL_BY_PROVIDER":
                searchBillByProvider(input[1]);
                break;
            case "EXIT":
                System.out.println("Good bye!");
                System.exit(0);
                break;
            default:
                System.out.println("Unknown command!");
        }
    }

    private void listBills() {
        List<Bill> bills = billService.getBills();
        System.out.println("Bill No.\tType\tAmount\tDue Date\tState\tPROVIDER\"");
        for (Bill bill : bills) {
            System.out.println(bill.getId() + "\t" +
                    bill.getType() + "\t" +
                    bill.getAmount() + "\t" +
                    dateFormat.format(bill.getDueDate()) + "\t" +
                    bill.getState() + "\t" +
                    bill.getProvider());
        }
    }

    private void payBills(String[] input) {
        double totalAmount = 0;
        List<Bill> billsToPay = new ArrayList<>();

        for (int i = 1; i < input.length; i++) {
            int billId = Integer.parseInt(input[i]);
            Bill bill = billService.getBillById(billId);
            if (bill != null) {
                billsToPay.add(bill);
                totalAmount += bill.getAmount();
            } else {
                System.out.println("Sorry! Not found a bill with id " + billId);
            }
        }

        if (accountService.getBalance() >= totalAmount) {
            for (Bill bill : billsToPay) {
                if (accountService.payBill(bill)) {
                    System.out.println("Payment has been completed for Bill with id " + bill.getId());
                } else {
                    System.out.println("Sorry! Not enough funds to proceed with payment for Bill with id " + bill.getId());
                }
            }
        } else {
            System.out.println("Sorry! Not enough fund to proceed with payment bill.");
        }
        System.out.println("Your current balance is: " + accountService.getBalance());
    }

    private void listDueDates() {
        List<Bill> bills = billService.getBills();
        bills.sort(Comparator.comparing(Bill::getDueDate));
        System.out.println("Bill No.\tType\tAmount\tDue Date\tState\tPROVIDER");
        for (Bill bill : bills) {
            if ("NOT_PAID".equals(bill.getState())) {
                System.out.println(bill.getId() + "\t" +
                        bill.getType() + "\t" +
                        bill.getAmount() + "\t" +
                        dateFormat.format(bill.getDueDate()) + "\t" +
                        bill.getState() + "\t" +
                        bill.getProvider());
            }
        }
    }

    private void schedulePayment(String[] input) {
        int billId = Integer.parseInt(input[1]);
        String dateString = input[2];
        try {
            Date date = dateFormat.parse(dateString);
            Bill bill = billService.getBillById(billId);
            if (bill != null) {
                System.out.println("Payment for bill id " + billId + " is scheduled on " + dateString);
            } else {
                System.out.println("Sorry! Not found a bill with such id");
            }
        } catch (Exception e) {
            System.out.println("Invalid date format! Please use dd/MM/yyyy");
        }
    }

    private void listPayments(String state) {
        List<Bill> bills = billService.getBills();
        List<Transaction> transactions = new ArrayList<>();

        for (Bill bill : bills) {
            String transactionState = bill.getState().equals("NOT_PAID") ? "PENDING" : "PROCESSED";
            transactions.add(new Transaction(bill.getAmount(), bill.getDueDate(), transactionState, bill.getId()));
        }

        System.out.println("No.\tAmount\tPayment Date\tState\tBill Id");

        int no = 1;
        for (Transaction transaction : transactions) {
            if (state == null || transaction.getState().equalsIgnoreCase(state)) {
                System.out.println(no + "\t" +
                        transaction.getAmount() + "\t" +
                        dateFormat.format(transaction.getPaymentDate()) + "\t" +
                        transaction.getState() + "\t" +
                        transaction.getBillId());
                no++;
            }
        }
    }

    private void searchBillByProvider(String provider) {
        List<Bill> bills = billService.getBillsByProvider(provider);
        System.out.println("Bill No.\tType\tAmount\tDue Date\tState\tPROVIDER");
        for (Bill bill : bills) {
            System.out.println(bill.getId() + "\t" +
                    bill.getType() + "\t" +
                    bill.getAmount() + "\t" +
                    dateFormat.format(bill.getDueDate()) + "\t" +
                    bill.getState() + "\t" +
                    bill.getProvider());
        }
    }
}
