
import java.util.Scanner;

public class RunApplication {
    private final PaymentService paymentService;

    public RunApplication() {
        this.paymentService = new PaymentService();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String command = scanner.nextLine();
            paymentService.handle(command);
        }
    }
}
