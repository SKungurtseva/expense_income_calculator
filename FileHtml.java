package expense_income_calculator;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHtml extends FileInformation {
    FileHtml(String name) {
        super(name);
    }

    FileHtml(File file) {
        super(file);
    }

    @Override
    public void openFile() {
        //System.out.println("a vot on ya - open html file");
        Scanner scanner = new Scanner(System.in);
        System.out.println("This is html file. Do you want to read in console? (y/n): ");
        String answer = scanner.next();
        if (answer.equals("y")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
                String next = reader.readLine();
                while (next != null) {
                    System.out.println(next);
                    next = reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error when reading html file");
            }
        } else {
            super.openFile();
        }
    }

    @Override
    void enterInfo(LocalDate currentDate) {
        //System.out.println("enter html file");
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

                String strIncome = "<!DOCTYPE html >\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<meta charset=\"UTF-8\">\n" +
                        "<title>Калькулятор доходов</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<table border=\"3\">\n" +
                        //тег создает таблицу
                        "</tr>\n" +
                        "<td>" + currentDate + "</td>\n" +
                        "<td>" + item_name + "</td>\n" +
                        "<td>" + isExpense + "</td>\n" +
                        "<td>" + income + "</td>\n" +
                        "</tr>\n" +
                        "</table>\n" +
                        "</body>\n" +
                        "</html>\n";
                writer.print(strIncome);
            }

            if (answer.equals("e")) {
                System.out.println("Enter a description of the expense: ");
                Scanner scanner1 = new Scanner(System.in);
                String item_name = scanner1.nextLine();

                System.out.println("Enter the expense amount: ");
                int expense = scanner.nextInt();
                isExpense = true;

                String strExpense = "<!DOCTYPE html >\n" +
                        "<html>\n" +
                        "<head>\n" +
                        "<meta charset=\"UTF-8\">\n" +
                        "<title>Калькулятор расходов</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<table border=\"3\">\n" +
                        //тег создает таблицу
                        "</tr>\n" +
                        "<td>" + currentDate + "</td>\n" +
                        "<td>" + item_name + "</td>\n" +
                        "<td>" + isExpense + "</td>\n" +
                        "<td>" + expense + "</td>\n" +
                        "</tr>\n" +
                        "</table>\n" +
                        "</body>\n" +
                        "</html>\n";
                writer.println(strExpense);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    void getIncomeAndExpense() {
        incomeMax = 0;
        expenseMax = 0;
        int endIndex = 0; //что бы выводились каждый раз разные данные, а не одно и то же вводим переменную
        int endIndex1 = 0; //что бы выводились каждый раз разные данные, а не одно и то же вводим переменную
        String falseStr = "<td>false</td>";
        String trueStr = "<td>true</td>";

        String content = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(this.name))) {
            String next;
            while ((next = reader.readLine()) != null) {
                content += next;
            }
            //System.out.println(content);

            List<Integer> incomes = new ArrayList<>();
            List<Integer> expenses = new ArrayList<>();


            while (true) {
                if (content.indexOf(falseStr) != -1) {
                    int falseIndex = content.indexOf("<td>false</td>", endIndex);

                    if (falseIndex < 0) {
                        int trueIndex = content.indexOf("<td>true</td>", endIndex1);
                        if (trueIndex < 0) {
                            break;
                        }
                        int startIndex1 = trueIndex + 10;
                        endIndex1 = content.indexOf("</td>", startIndex1);
                        String expenseValue = (content.substring(startIndex1 + 7, endIndex1));
                        int expense = Integer.parseInt(expenseValue);
                        expenses.add(expense);
                        break;
                    } else {
                        int startIndex = falseIndex + 10;
                        endIndex = content.indexOf("</td>", startIndex);
                        String incomeValue = (content.substring(startIndex + 8, endIndex));
                        int income = Integer.parseInt(incomeValue);
                        incomes.add(income);
                    }
                }
                if (content.indexOf(trueStr) != -1) {
                    int trueIndex = content.indexOf("<td>true</td>", endIndex1);
                    if (trueIndex < 0) {
                        int falseIndex = content.indexOf("<td>false</td>", endIndex);
                        if (falseIndex < 0) {
                            break;
                        }
                        int startIndex = falseIndex + 10;
                        endIndex = content.indexOf("</td>", startIndex);
                        String incomeValue = (content.substring(startIndex + 8, endIndex));
                        int income = Integer.parseInt(incomeValue);
                        incomes.add(income);
                        break;
                    } else {
                        int startIndex1 = trueIndex + 10;
                        endIndex1 = content.indexOf("</td>", startIndex1);
                        String expenseValue = (content.substring(startIndex1 + 7, endIndex1));
                        int expense = Integer.parseInt(expenseValue);
                        expenses.add(expense);
                    }
                }
            }

            for (int income : incomes) {
                incomeMax += income;
            }

            for (int expense : expenses) {
                expenseMax += expense;
            }
            System.out.println("The monthly income is: " + incomeMax);
            System.out.println("The monthly expense is: " + expenseMax);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}





