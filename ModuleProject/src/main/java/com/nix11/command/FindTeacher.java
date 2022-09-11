package com.nix11.command;

import com.nix11.service.TeacherService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class FindTeacher implements Command {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public boolean execute() {
        String whatCriterion = "Select a teacher search criterion:";
        final List<String> names = List.of(new String[]{"Name", "Surname"});
        final int userInput = Utils.getUserInput(names, whatCriterion);
        if (userInput != -1) {
            boolean continueOperations = true;
            do {
                try {
                    String findBy = "Enter the parameter you would like to search for a teacher by:";
                    System.out.println(findBy + "\nTo exit press \"0\"");
                    String name = READER.readLine();
                    if (!name.equals("0")) {
                        switch (userInput) {
                            case 0 -> TeacherService.getInstance().teacherByName(name);
                            case 1 -> TeacherService.getInstance().teacherBySurname(name);
                        }
                        return true;
                    } else {
                        continueOperations = false;
                    }
                } catch (IOException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            } while (continueOperations);
            return true;
        }
        return false;
    }
}
