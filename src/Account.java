public class Account {
    private double balance;

    public Account() {
        this.balance = 0;
    }

    public double getBalance() {
        return balance;
    }

    public void addFunds(double amount) {
        this.balance += amount;
    }

    public boolean deductFunds(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
}
