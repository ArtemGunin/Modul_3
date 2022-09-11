package com.nix11.command;

import com.nix11.service.SubjectService;

public class WorstBestSubject implements Command {
    @Override
    public boolean execute() {
        SubjectService.getInstance().worstAndBestPerformingSubjects();
        return true;
    }
}
