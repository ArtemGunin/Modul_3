package com.nix11.repository;

import com.nix11.config.HibernateFactoryUtils;
import com.nix11.model.Subject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class SubjectRepository implements CrudRepository<Subject> {

    private static final SessionFactory SESSION_FACTORY = HibernateFactoryUtils.getSessionFactory();

    private static SubjectRepository instance;

    public static SubjectRepository getInstance() {
        if (instance == null) {
            instance = new SubjectRepository();
        }
        return instance;
    }

    public Subject getTopPerformingSubject() {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        MarkRepository markRepository = MarkRepository.getInstance();
        final double[] topPerformingMark = {0};
        final Subject[] topPerformingSubject = new Subject[1];
        session.createQuery("select subject from Subject subject", Subject.class)
                .getResultList().forEach(subject -> {
                    double mark = markRepository.subjectGPA(subject.getSubjectId());
                    if (topPerformingMark[0] < mark) {
                        topPerformingMark[0] = mark;
                        topPerformingSubject[0] = subject;
                    }
                });
        session.getTransaction().commit();
        session.close();
        return topPerformingSubject[0];
    }

    public Subject getWorstPerformingSubject() {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        MarkRepository markRepository = MarkRepository.getInstance();
        final double[] topPerformingMark = {6.0};
        final Subject[] topPerformingSubject = new Subject[1];
        session.createQuery("select subject from Subject subject", Subject.class)
                .getResultList().forEach(subject -> {
                    double mark = markRepository.subjectGPA(subject.getSubjectId());
                    if (topPerformingMark[0] > mark) {
                        topPerformingMark[0] = mark;
                        topPerformingSubject[0] = subject;
                    }
                });
        session.getTransaction().commit();
        session.close();
        return topPerformingSubject[0];
    }

    @Override
    public void save(Subject subject) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.save(subject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveAll(List<Subject> subjects) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        for (Subject subject : subjects) {
            session.save(subject);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Subject subject) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.update(subject);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(String id) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.createQuery("delete from Subject where id = :value")
                .setParameter("value", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Subject> getAll() {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<Subject> subjects = session
                .createQuery("select subject from Subject subject", Subject.class)
                .list();
        session.getTransaction().commit();
        session.close();
        return subjects;
    }

    @Override
    public Optional<Subject> getById(String id) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        Optional<Subject> subject = Optional.ofNullable(session.get(Subject.class, id));
        session.getTransaction().commit();
        session.close();
        return subject;
    }
}
