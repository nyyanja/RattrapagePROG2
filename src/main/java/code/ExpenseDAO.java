package code;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseDAO {
    private List<Expense> expenses;

    public ExpenseDAO(List<Expense> expenses) {
        this.expenses = expenses != null ? expenses : new ArrayList<>();
    }

    public static class ExpenseSummary {
        private final List<Expense> reimbursableNonReimbursedExpenses;
        private final double totalRecurrentExpenses;
        private final List<String> labelsOver100;
        private final double totalAllExpenses;

        public ExpenseSummary(List<Expense> reimbursableNonReimbursedExpenses, double totalRecurrentExpenses,
                              List<String> labelsOver100, double totalAllExpenses) {
            this.reimbursableNonReimbursedExpenses = reimbursableNonReimbursedExpenses;
            this.totalRecurrentExpenses = totalRecurrentExpenses;
            this.labelsOver100 = labelsOver100;
            this.totalAllExpenses = totalAllExpenses;
        }

        public List<Expense> getReimbursableNonReimbursedExpenses() {
            return reimbursableNonReimbursedExpenses;
        }

        public double getTotalRecurrentExpenses() {
            return totalRecurrentExpenses;
        }

        public List<String> getLabelsOver100() {
            return labelsOver100;
        }

        public double getTotalAllExpenses() {
            return totalAllExpenses;
        }
    }

    public ExpenseSummary processExpenses() {
        // 1. Retourner uniquement les dépenses remboursables non remboursées
        List<Expense> reimbursableNonReimbursed = expenses.stream()
                .filter(expense -> expense instanceof RefundableExpense && !((RefundableExpense) expense).isRefunded())
                .collect(Collectors.toList());

        // 2. Retourner le total de toutes les dépenses récurrentes
        double totalRecurrent = expenses.stream()
                .filter(expense -> expense instanceof RecurringExpense)
                .mapToDouble(Expense::getAmount)
                .sum();

        // 3. Retourner une liste des libellés des dépenses strictement supérieures à 100
        List<String> labelsOver100 = expenses.stream()
                .filter(expense -> expense.getAmount() > 100)
                .map(Expense::getLabel)
                .collect(Collectors.toList());

        // 4. Retourner le montant total de toutes les dépenses
        double totalAllExpenses = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        return new ExpenseSummary(reimbursableNonReimbursed, totalRecurrent, labelsOver100, totalAllExpenses);
    }
}