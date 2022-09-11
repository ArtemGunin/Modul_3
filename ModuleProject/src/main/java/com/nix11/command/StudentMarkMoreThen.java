package com.nix11.command;

import com.nix11.service.StudentService;

import java.util.Scanner;

public class StudentMarkMoreThen implements Command {

    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public boolean execute() {
        String createFrom = "Enter low bound of students mark with local separator:";
        boolean continueOperations = true;
        do {
            try {
                System.out.println(createFrom + "\nTo exit press \"0\"");
                double lowMarkBound = SCANNER.nextDouble();
                if (lowMarkBound > 0) {
                    StudentService.getInstance().studentsWithMarkMoreThen(lowMarkBound);
                    return true;
                } else {
                    continueOperations = false;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } while (continueOperations);
        return false;
    }
}
