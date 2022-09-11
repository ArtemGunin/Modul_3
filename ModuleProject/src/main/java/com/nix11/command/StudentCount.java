package com.nix11.command;

import com.nix11.service.GroupService;

public class StudentCount implements Command {
    @Override
    public boolean execute() {
        GroupService.getInstance().getStudentsCountOfGroups();
        return true;
    }
}
