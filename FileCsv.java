package expense_income_calculator;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

public class FileCsv extends FileInformation {
    FileCsv(String name) {
        super(name);
    }
    FileCsv(File file) {
        super(file);
    }

    @Override
    public void openFile() {
        //System.out.println("a vot on ya - open csv file");
        Scanner scanner = new Scanner(System.in);
        System.out.println("This is csv file. Do you want to read in console? (y/n): ");
        String answer = scanner.next();
        if (answer.equals("y")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
                String next = reader.readLine();
                while (next != null) {
                    System.out.println(next);
                    next = reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error when reading csv file");
            }
        } else {
            super.openFile();
        }
    }

    @Override
    void enterInfo(LocalDate currentDate) {
        //System.out.println("enter csv file");
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

                writer.println(currentDate + "," + item_name + "," + isExpense + "," + income);
            }

            if (answer.equals("e")) {
                System.out.println("Enter a description of the expense: ");
                Scanner scanner1 = new Scanner(System.in);
                String item_name = scanner1.nextLine();

                System.out.println("Enter the expense amount: ");
                int expense = scanner.nextInt();
                isExpense = true;

                writer.println(currentDate + "," + item_name + "," + isExpense + "," +  expense);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void getIncomeAndExpense() {
        incomeMax = 0;
        expenseMax = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.name))) {
            String next;
            while ((next = reader.readLine()) != null) {
                String[] lines = next.split("\r?\n");
                for (int i = 0; i < lines.length; i++) {
                    String line = lines[i];
                    String[] parts = line.split(",");

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



