import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Entry point of the program. Shows a menu and calls ExpenseManager.
 */
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();
        manager.loadFromFile();

        while (true) {
            System.out.println("\n===== Expense Tracker =====");
            System.out.println("1. Add expense");
            System.out.println("2. View all expenses");
            System.out.println("3. Show summary");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    addExpense(sc, manager);
                    break;
                case "2":
                    manager.viewAll();
                    break;
                case "3":
                    manager.showSummary();
                    break;
                case "4":
                    System.out.println("Goodbye! Your data is saved.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option. Please enter 1-4.");
            }
        }
    }

    private static void addExpense(Scanner sc, ExpenseManager manager) {
        try {
            System.out.print("Amount: ");
            double amount = Double.parseDouble(sc.nextLine().trim());
            if (amount <= 0) {
                System.out.println("Amount must be greater than 0.");
                return;
            }

            System.out.print("Category (e.g. Food, Travel, Bills): ");
            String category = sc.nextLine().trim();
            if (category.isEmpty() || category.contains(",")) {
                System.out.println("Category cannot be empty or contain commas.");
                return;
            }

            System.out.print("Date (YYYY-MM-DD) or press Enter for today: ");
            String dateInput = sc.nextLine().trim();
            LocalDate date = dateInput.isEmpty() ? LocalDate.now() : LocalDate.parse(dateInput);

            manager.addExpense(new Expense(amount, category, date));
            System.out.println("Expense added and saved!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a number.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Use the format YYYY-MM-DD.");
        }
    }
}