package code;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ExpenseDAOTest {
    private ExpenseDAO expenseDAO;

    @Before
    public void setUp() {
        List<Expense> expenses = Arrays.asList(
                new RefundableExpense("Frais de voyage", 150.0, LocalDate.of(2025, 7, 1), false), // Remboursable, non remboursé, > 100
                new RefundableExpense("Équipement", 200.0, LocalDate.of(2025, 7, 2), true),      // Remboursable, remboursé, > 100
                new RecurringExpense("Abonnement logiciel", 50.0, LocalDate.of(2025, 7, 3), Reccurence.Monthly), // Récurrent, <= 100
                new RecurringExpense("Loyer bureau", 1000.0, LocalDate.of(2025, 7, 4), Reccurence.Yearly),     // Récurrent, > 100
                new Expense("Fournitures", 80.0, LocalDate.of(2025, 7, 5))                       // Non remboursable, non récurrent, <= 100
        );
        expenseDAO = new ExpenseDAO(expenses);
    }

    @Test
    public void testProcessExpenses_ReimbursableNonReimbursed() {
        ExpenseDAO.ExpenseSummary summary = expenseDAO.processExpenses();
        List<Expense> reimbursableNonReimbursed = summary.getReimbursableNonReimbursedExpenses();

        assertEquals(1, reimbursableNonReimbursed.size());
        assertEquals("Frais de voyage", reimbursableNonReimbursed.get(0).getLabel());
        assertEquals(150.0, reimbursableNonReimbursed.get(0).getAmount(), 0.001);
        assertFalse(((RefundableExpense) reimbursableNonReimbursed.get(0)).isRefunded());
    }

    @Test
    public void testProcessExpenses_TotalRecurrentExpenses() {
        ExpenseDAO.ExpenseSummary summary = expenseDAO.processExpenses();
        double totalRecurrent = summary.getTotalRecurrentExpenses();

        assertEquals(1050.0, totalRecurrent, 0.001); // 50 + 1000
    }

    @Test
    public void testProcessExpenses_LabelsOver100() {
        ExpenseDAO.ExpenseSummary summary = expenseDAO.processExpenses();
        List<String> labelsOver100 = summary.getLabelsOver100();

        assertEquals(3, labelsOver100.size());
        assertTrue(labelsOver100.contains("Frais de voyage"));
        assertTrue(labelsOver100.contains("Équipement"));
        assertTrue(labelsOver100.contains("Loyer bureau"));
    }

    @Test
    public void testProcessExpenses_TotalAllExpenses() {
        ExpenseDAO.ExpenseSummary summary = expenseDAO.processExpenses();
        double totalAllExpenses = summary.getTotalAllExpenses();

        assertEquals(1480.0, totalAllExpenses, 0.001); // 150 + 200 + 50 + 1000 + 80
    }

    @Test
    public void testProcessExpenses_EmptyList() {
        ExpenseDAO emptyDAO = new ExpenseDAO(null);
        ExpenseDAO.ExpenseSummary summary = emptyDAO.processExpenses();

        assertTrue(summary.getReimbursableNonReimbursedExpenses().isEmpty());
        assertEquals(0.0, summary.getTotalRecurrentExpenses(), 0.001);
        assertTrue(summary.getLabelsOver100().isEmpty());
        assertEquals(0.0, summary.getTotalAllExpenses(), 0.001);
    }
}