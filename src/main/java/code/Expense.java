package code;

import java.time.LocalDate;

public class Expense {
    private String label;
    private double amount;
    private LocalDate date;

    public Expense(String label, double amount, LocalDate date) {
        this.label = label;
        this.amount = amount;
        this.date = date;
    }

    public String getLabel() {
        return label;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isLargeExpense() {
        return amount > 100.0;
    }

    public  String toString() {
        return label + "\t" + amount + "\t" + date;
    }

    protected boolean isReimbursable() {
        return false;
    }

    protected boolean isReimbursed() {
        return false;
    }

    public boolean isRecurrent() {
        return false;
    }
}
