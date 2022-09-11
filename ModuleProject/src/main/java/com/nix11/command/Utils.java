package com.nix11.command;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Utils {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static int getUserInput(List<String> names, String operation) {
        int exitCompensation = 1;
        int length = names.size();
        int userChoice = -1;
        do {
            try {
                System.out.println(operation);
                System.out.println("Please enter number between 1 and " + length + ".");
                for (int i = 1; i <= length; i++) {
                    System.out.printf("%d) %s%n", i, names.get(i - exitCompensation));
                }
                System.out.println("Enter \"0\" to Exit.");
                final String line = READER.readLine();
                if (!StringUtils.isNumeric(line)) {
                    System.out.println("Incorrect number!!!\n");
                    continue;
                }
                int input = Integer.parseInt(line);
                if (input >= 0 && input <= length) {
                    userChoice = input;
                } else {
                    System.out.println("Incorrect number!!!\n");
                }
            } catch (IOException e) {
                System.out.println("Input is not valid\n");
            }
        } while (userChoice == -1);
        return userChoice - exitCompensation;
    }
}
