package code;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ExpenseDAO extends Expense{
    private List<Expense> expenses;

    public ExpenseDAO(String label, double amount, LocalDate date) {
        super(label, amount, date);
    }

        public List processExpenses() {
            // 1. Retourner uniquement les dépenses remboursables non remboursées
            List<Expense> reimbursableNonReimbursed = expenses.stream()
                    .filter(expense -> expense.isReimbursable() && !expense.isReimbursed())
                    .collect(Collectors.toList());

            // 2. Retourner le total de toutes les dépenses récurrentes
            double totalRecurrent = expenses.stream()
                    .filter(Expense::isRecurrent)
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

            return processExpenses();
        }
}
