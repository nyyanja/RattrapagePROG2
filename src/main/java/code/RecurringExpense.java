package code;

import java.time.LocalDate;

public class RecurringExpense extends Expense {
    private Reccurence reccurence;

    public RecurringExpense(String label, double amount, LocalDate date, Reccurence reccurence) {
        super(label, amount, date);
        this.reccurence = reccurence;
    }

    @Override
    public String toString() {
        return super.toString() + "RecurringExpense{" + "reccurence=" + reccurence + '}';
    }

}
