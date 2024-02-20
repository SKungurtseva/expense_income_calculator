package expense_income_calculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileTxt extends FileInformation {
    FileTxt(String name) {
        super(name);
    }
    FileTxt(File file) {
        super(file);
    }

    @Override
    public void openFile() {
        //System.out.println("open text file");
        Scanner scanner = new Scanner(System.in);
        System.out.println("This is text file. Do you want to read in console? (y/n): ");
        String answer = scanner.next();
        if (answer.equals("y")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
                String next = reader.readLine();
                while (next != null) {
                    System.out.println(next);
                    next = reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException("Error when reading text file");
            }
        } else {
            super.openFile();
        }
    }
}