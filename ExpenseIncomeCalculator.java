package expense_income_calculator;


import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ExpenseIncomeCalculator {
    public static void main(String[] args) throws RuntimeException {
        JFileChooser fileChooser;
        FileInformation fileCurrent;
        FileInformation fileNew = null;
        FileInformation opener;
        Scanner scanner = new Scanner(System.in);

        LocalDate currentDate = LocalDate.now();
        Month month = currentDate.getMonth();
        int year = currentDate.getYear();
        String yearStr = String.valueOf(year);
        String name = yearStr + month;

        System.out.println("Enter the file format txt/html/csv: ");
        String format = scanner.nextLine();

        if (format.equals("txt") || format.equals("html") || format.equals("csv")) {
            fileCurrent = getFile(fileNew, name, format);
        } else {
            RuntimeException exception = new RuntimeException("Не верный формат ввода файла!!!");
            throw exception;
        }


        while (true) {
            printMenu();
            int input = scanner.nextInt();

            if (input == 1) {
                fileCurrent.enterInfo(currentDate);
                System.out.println(currentDate);

            } else if (input == 2) {
                fileChooser = new MyFileChooser(); //выбиратель файлов
                fileChooser.showOpenDialog(null); //диалог открытия файлов
                File file = fileChooser.getSelectedFile(); //метод возвращает файл который был выбран

                String[] nameParts = file.getName().split("\\."); //двойное экранирование, делим по точке
                String ext = nameParts[nameParts.length - 1];

                if (ext.equals("txt")) {
                    opener = new FileTxt(file);
                } else if (ext.equals("html")) {
                    opener = new FileHtml(file);
                } else if (ext.equals("csv")) {
                    opener = new FileCsv(file);
                } else {
                    opener = new FileInformation(file);
                }
                executeFile(opener);

            } else if (input == 3) {
                try {
                    System.out.println("Open the current file or enter another one? (y/n): ");
                    String result = scanner.next();
                    if (result.equals("y")) {
                        fileCurrent.getIncomeAndExpense();
                    }

                    if (result.equals("n")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        System.out.print("Enter the date for which period to open the file, in the format yyyy/MM/dd: ");
                        Scanner scanner1 = new Scanner(System.in);
                        String inputDate = scanner1.nextLine();
                        LocalDate date = LocalDate.parse(inputDate, formatter);
                        month = date.getMonth();
                        year = date.getYear();
                        yearStr = String.valueOf(year);
                        name = yearStr + month;
                        System.out.println(name);

                        fileNew = getFile(fileNew, name, format);
                        fileNew.getIncomeAndExpense();
                    }
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }

            } else if (input == 0) {
                System.out.println("The program is completed");
                break;

            } else {
                System.out.println("There is no such command, try again");
            }
        }
    }

    private static void executeFile(FileInformation opener) {
        opener.printFileInfo();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to open file? (y/n)");
        String answer = scanner.next();
        if (answer.equals("y")) {
            opener.openFile();
        } else {
            System.out.println("Program will terminate");
        }
    }

    static void printMenu() {
        String menu = "ГЛАВНОЕ МЕНЮ.\n" +
                "Что вы хотите сделать?\n" +
                "1 - Ввести доход/расход\n" +
                "2 - Открыть файл с помощью диалогового окна\n" +
                "3 - Вывести итог за выбранный месяц\n" +
                "0 - Выход";
        System.out.println(menu);
    }

    static FileInformation getFile(FileInformation fileNew, String name, String answer) {
        if (answer.equals("txt")) {
            fileNew = new FileTxt("C:/Users/Admin/Desktop/" + name + ".txt"); //new FileTxt(name + ".txt");
        }

        if (answer.equals("html")) {
            fileNew = new FileHtml("C:/Users/Admin/Desktop/" + name + ".html"); //new FileTxt(name + ".html");
        }

        if (answer.equals("csv")) {
            fileNew = new FileCsv("C:/Users/Admin/Desktop/" + name + ".csv"); //new FileTxt(name + ".csv");
        }
        return fileNew;
    }
}
