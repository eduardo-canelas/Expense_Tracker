import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Expense {
    String category;
    double amount;

    Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }
}

class ExpenseTracker {
    private ArrayList<Expense> expenses;

    ExpenseTracker() {
        this.expenses = new ArrayList<>();
    }

    void addExpense(String category, double amount) {
        Expense expense = new Expense(category, amount);
        expenses.add(expense);
        System.out.println("Expense added successfully!");
    }

    void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
        } else {
            System.out.println("Expenses:");
            for (Expense expense : expenses) {
                System.out.println("Category: " + expense.category + ", Amount: $" + expense.amount);
            }
        }
    }

    void writeExpensesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("expenses.txt", true))) {
            for (Expense expense : expenses) {
                writer.write(expense.category + "," + expense.amount + "\n");
            }
            System.out.println("Expenses written to expenses.txt");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}

public class MyExpenses {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseTracker expenseTracker = new ExpenseTracker();

        // Hook to add a shutdown hook to write expenses to file before exiting
        Runtime.getRuntime().addShutdownHook(new Thread(expenseTracker::writeExpensesToFile));

        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter expense category: ");
                    String category = scanner.next();
                    System.out.print("Enter expense amount: $");
                    double amount = scanner.nextDouble();
                    expenseTracker.addExpense(category, amount);
                    break;

                case 2:
                    expenseTracker.viewExpenses();
                    break;

                case 3:
                    System.out.println("Exiting Expense Tracker. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
