package expense_income_calculator;


import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class FileInformation {
    File file;
    String name;
    boolean isExpense;
    int incomeMax;
    int expenseMax;

    FileInformation(File file) {
        this.file = file;
    }

    FileInformation(String name) {
        this.name = name;
    }

    void enterInfo(LocalDate currentDate) {
        boolean append = true;

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(this.name, append)))) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("What do you want to enter: income or expense? (i/e): ");
            String answer = scanner.next();

            if (answer.equals("i")) {
                System.out.println("Enter a description of the income: ");
                Scanner scanner1 = new Scanner(System.in);
                String item_name = scanner1.nextLine();

                System.out.println("Enter the amount of income: ");
                int income = scanner.nextInt();
                isExpense = false;

                writer.println((currentDate) + " " + item_name + " " + isExpense + " " + income);
            }

            if (answer.equals("e")) {
                System.out.println("Enter a description of the expense: ");
                Scanner scanner1 = new Scanner(System.in);
                String item_name = scanner1.nextLine();

                System.out.println("Enter the expense amount: ");
                int expense = scanner.nextInt();
                isExpense = true;

                writer.println((currentDate) + " " + item_name + " " + isExpense + " " + expense);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openFile() {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(this.file);
        } catch (IOException e) {
            throw new RuntimeException("Error when opening file", e);
        }
    }

    public void printFileInfo() {
        System.out.println("This is file " + file.getName());
        System.out.println("It stored on path: " + file.getAbsolutePath());
    }

    void getIncomeAndExpense() {
        incomeMax = 0;
        expenseMax = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.name))) {
            String next;
            while ((next = reader.readLine()) != null) {
                String[] lines = next.split("\r?\n");
                for (int i = 0; i < lines.length; i++) {
                    String line = lines[i];
                    String[] parts = line.split(" ");

                    String date = parts[0];
                    String itemName = parts[1];
                    boolean isExpense = Boolean.parseBoolean(parts[2]);
                    int sum = Integer.parseInt(parts[3]);

                    if (isExpense == false) {
                        incomeMax += sum;
                    } else {
                        expenseMax += sum;
                    }
                }
            }
            System.out.println("The monthly income is: " + incomeMax);
            System.out.println("The monthly expense is: " + expenseMax);
        } catch (IOException e) {
            throw new RuntimeException("Error when reading text file");
        }
    }
}






