import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ExpenseManager {
    private static final String FILE_NAME = "expenses.csv";

    // ArrayList keeps all expenses in the order they were added
    private ArrayList<Expense> expenses = new ArrayList<>();

    // Add a new expense and save immediately
    public void addExpense(Expense expense) {
        expenses.add(expense);
        saveToFile();
    }

    // Print every expense
    public void viewAll() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        System.out.println("\n--- All Expenses ---");
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    // Add up all the amounts
    public double getTotal() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    // HashMap: key = category name, value = total spent in that category
    public Map<String, Double> getCategoryTotals() {
        Map<String, Double> totals = new HashMap<>();
        for (Expense e : expenses) {
            String category = e.getCategory();
            double current = totals.getOrDefault(category, 0.0);
            totals.put(category, current + e.getAmount());
        }
        return totals;
    }

    // Print total spending and the category-wise breakdown
    public void showSummary() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        System.out.println("\n--- Summary ---");
        System.out.printf("Total spending: Rs. %.2f%n", getTotal());

        System.out.println("\nSpending by category:");
        Map<String, Double> totals = getCategoryTotals();
        String topCategory = "";
        double topAmount = 0;
        for (Map.Entry<String, Double> entry : totals.entrySet()) {
            System.out.printf("  %-15s Rs. %.2f%n", entry.getKey(), entry.getValue());
            if (entry.getValue() > topAmount) {
                topAmount = entry.getValue();
                topCategory = entry.getKey();
            }
        }
        System.out.println("\nHighest spending category: " + topCategory);
    }

    // Write all expenses to the file (one expense per line)
    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                writer.println(e.toFileString());
            }
        } catch (IOException ex) {
            System.out.println("Error saving data: " + ex.getMessage());
        }
    }

    // Read expenses from the file when the program starts
    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; // first run, nothing to load
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    expenses.add(Expense.fromFileString(line));
                }
            }
            System.out.println("Loaded " + expenses.size() + " saved expense(s).");
        } catch (Exception ex) {
            System.out.println("Error loading data: " + ex.getMessage());
        }
    }
}