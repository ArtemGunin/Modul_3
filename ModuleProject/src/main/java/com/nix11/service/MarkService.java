package com.nix11.service;

import com.nix11.model.Mark;
import com.nix11.model.Student;
import com.nix11.model.Subject;
import com.nix11.repository.MarkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class MarkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarkService.class);
    private static final Random RANDOM = new Random();

    private final MarkRepository markRepository;

    private static MarkService instance;

    private MarkService(MarkRepository markRepository) {
        this.markRepository = markRepository;
    }

    public static MarkService getInstance() {
        if (instance == null) {
            instance = new MarkService(MarkRepository.getInstance());
        }
        return instance;
    }

    public void createAndSaveMark(Student student, Subject subject) {
        Mark mark = new Mark();
        mark.setStudent(student);
        mark.setSubject(subject);
        mark.setValue(RANDOM.nextDouble(1.0, 5.0));
        markRepository.save(mark);
        LOGGER.info("new " + mark);
    }

    public void updateMark(Mark mark) {
        markRepository.update(mark);
    }

    public void deleteMark(String id) {
        markRepository.delete(id);
    }

    public List<Mark> getAllMarks() {
        return markRepository.getAll();
    }

    public Optional<Mark> getMarkById(String id) {
        return markRepository.getById(id);
    }
}
