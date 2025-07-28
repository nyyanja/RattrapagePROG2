package code;

import java.time.LocalDate;

public class RefundableExpense extends Expense {

    private boolean refunded;

    public RefundableExpense(String label, double amount, LocalDate date, boolean refunded) {
        super(label, amount, date);
        this.refunded = refunded;
    }
    public boolean refund() {
        return this.refunded = true;
    }
}
