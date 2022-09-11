package com.nix11.command;

import lombok.Getter;

@Getter
public enum Commands {
    FIND_GROUP("Find group", new FindGroup()),
    STUDENT_COUNT("Get student count of group", new StudentCount()),
    GROUP_GPA("Grade point average of groups", new GroupGPA()),
    FIND_TEACHER("Find teacher by name or surname", new FindTeacher()),
    WORST_BEST_SUBJECT("Get subject with the worst and best performance", new WorstBestSubject()),
    STUDENTS_MARK_MORE_THEN("Students with mark more then", new StudentMarkMoreThen());

    private final String name;
    private final Command command;

    Commands(String name, Command command) {
        this.name = name;
        this.command = command;
    }
}
