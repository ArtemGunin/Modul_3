package com.nix11.service;

import com.nix11.model.Student;
import com.nix11.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;

public class StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentService.class);
    private static final Random RANDOM = new Random();

    private final StudentRepository studentRepository;

    private static StudentService instance;

    private StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService(StudentRepository.getInstance());
        }
        return instance;
    }

    public Student createStudent() {
        int lowestStudentAge = 16;
        Student student = new Student();
        student.setName(UUID.randomUUID().toString().substring(1, 6));
        student.setSurname(UUID.randomUUID().toString().substring(1, 6));
        student.setAge(lowestStudentAge + RANDOM.nextInt(104));
        student.setEntryDate(LocalDate.of(
                LocalDate.now().getYear() - RANDOM.nextInt(6),
                1 + RANDOM.nextInt(12),
                1 + RANDOM.nextInt(31)));
        return student;
    }

    public void studentsWithMarkMoreThen(double lowerBound) {
        System.out.println("Students whose GPA is higher than " + lowerBound + " :");
        Map<Student, Double> students = studentRepository.getStudentsWithAverageMarkMoreThen(lowerBound);
        students.keySet().forEach(student -> System.out.println("Average student score "
                + String.format("%.2f", students.get(student)) + " " + student));
    }

    public void createAndSaveStudents(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("count must been more then 0");
        }
        List<Student> students = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Student student = createStudent();
            students.add(student);
            LOGGER.info("new " + students.get(i).toString());
        }
        studentRepository.saveAll(students);
    }

    public void updateStudent(Student student) {
        studentRepository.update(student);
    }

    public void deleteStudent(String id) {
        studentRepository.delete(id);
    }

    public List<Student> getAllStudents() {
        return studentRepository.getAll();
    }

    public Optional<Student> getStudentById(String id) {
        return studentRepository.getById(id);
    }
}
