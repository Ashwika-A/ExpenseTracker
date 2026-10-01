import java.time.LocalDate;

/**
 * Represents a single expense record.
 * Each expense has an amount, a category (like Food, Travel) and a date.
 */
public class Expense {
    private double amount;
    private String category;
    private LocalDate date;

    public Expense(double amount, String category, LocalDate date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    // Converts the expense to one line of text so it can be saved in a file
    // Example: 250.0,Food,2026-10-01
    public String toFileString() {
        return amount + "," + category + "," + date;
    }

    // Converts one line from the file back into an Expense object
    public static Expense fromFileString(String line) {
        String[] parts = line.split(",");
        double amount = Double.parseDouble(parts[0]);
        String category = parts[1];
        LocalDate date = LocalDate.parse(parts[2]);
        return new Expense(amount, category, date);
    }

    @Override
    public String toString() {
        return String.format("%-12s %-15s Rs. %.2f", date, category, amount);
    }
}