package com.nix11.command;

import com.nix11.service.GroupService;

public class GroupGPA implements Command {
    @Override
    public boolean execute() {
        GroupService.getInstance().gradePointAverageOfGroups();
        return true;
    }
}
