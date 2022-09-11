package com.nix11.command;

import com.nix11.service.GroupService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FindGroup implements Command {

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public boolean execute() {
        String createFrom = "Enter the name of the group, or part of the name you want to search for:";
        boolean continueOperations = true;
        do {
            try {
                System.out.println(createFrom + "\nTo exit press \"0\"");
                String name = READER.readLine();
                if (!name.equals("0")) {
                    GroupService.getInstance().getGroupsByNamePartialMatch(name);
                    return true;
                } else {
                    continueOperations = false;
                }
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        } while (continueOperations);
        return false;
    }
}
