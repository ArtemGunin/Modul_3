package com.nix11.service;

import com.nix11.model.Subject;
import com.nix11.model.Teacher;
import com.nix11.repository.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class TeacherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherService.class);
    private static final Random RANDOM = new Random();

    private final TeacherRepository teacherRepository;

    private static TeacherService instance;

    private TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public static TeacherService getInstance() {
        if (instance == null) {
            instance = new TeacherService(TeacherRepository.getInstance());
        }
        return instance;
    }

    public void createAndSaveTeacher(Subject subject) {
        int lowestTeacherAge = 24;
        Teacher teacher = new Teacher();
        teacher.setName(UUID.randomUUID().toString().substring(1, 6));
        teacher.setSurname(UUID.randomUUID().toString().substring(1, 6));
        teacher.setAge(lowestTeacherAge + RANDOM.nextInt(104));
        teacher.setSubject(subject);
        teacherRepository.save(teacher);
        LOGGER.info("new " + teacher);
    }

    public void teacherByName(String name) {
        System.out.println("Teachers with name " + name + " :");
        System.out.println(TeacherRepository.getInstance().getTeacherByName(name));
    }

    public void teacherBySurname(String surname) {
        System.out.println("Teachers with name " + surname + " :");
        System.out.println(TeacherRepository.getInstance().getTeacherBySurname(surname));
    }

    public void updateTeacher(Teacher teacher) {
        teacherRepository.update(teacher);
    }

    public void deleteTeacher(String id) {
        teacherRepository.delete(id);
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.getAll();
    }

    public Optional<Teacher> getTeacherById(String id) {
        return teacherRepository.getById(id);
    }
}
