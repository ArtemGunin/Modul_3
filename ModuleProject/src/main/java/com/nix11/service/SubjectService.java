package com.nix11.service;

import com.nix11.model.Subject;
import com.nix11.repository.MarkRepository;
import com.nix11.repository.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SubjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectService.class);
    private static final Random RANDOM = new Random();

    private final SubjectRepository subjectRepository;

    private static SubjectService instance;

    private SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public static SubjectService getInstance() {
        if (instance == null) {
            instance = new SubjectService(SubjectRepository.getInstance());
        }
        return instance;
    }

    public void worstAndBestPerformingSubjects() {
        Subject bestMarkSubject = SubjectRepository.getInstance().getTopPerformingSubject();
        Subject worstMarkSubject = SubjectRepository.getInstance().getWorstPerformingSubject();
        double maxMark = MarkRepository.getInstance().subjectGPA(bestMarkSubject.getSubjectId());
        double minMark = MarkRepository.getInstance().subjectGPA(worstMarkSubject.getSubjectId());
        System.out.println("\nTop performing subject is - "
                + bestMarkSubject + ", with mark = " + String.format("%.2f", maxMark));
        System.out.println("\nWorst performing subject is - "
                + worstMarkSubject + ", with mark = " + String.format("%.2f", minMark) + "\n");
    }

    public Subject createSubject() {
        Subject subject = new Subject();
        subject.setTitle(UUID.randomUUID().toString().substring(1, 6));
        subject.setCode(RANDOM.nextInt(1000000));
        return subject;
    }

    public void createAndSaveSubjects(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("count must been more then 0");
        }
        List<Subject> subjects = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Subject subject = createSubject();
            subjects.add(subject);
            LOGGER.info("new " + subjects.get(i).toString());
        }
        subjectRepository.saveAll(subjects);
    }

    public void updateSubject(Subject subject) {
        subjectRepository.update(subject);
    }

    public void deleteSubject(String id) {
        subjectRepository.delete(id);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.getAll();
    }

    public Optional<Subject> getSubjectById(String id) {
        return subjectRepository.getById(id);
    }
}
