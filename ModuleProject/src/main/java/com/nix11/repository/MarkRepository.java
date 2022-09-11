package com.nix11.repository;

import com.nix11.config.HibernateFactoryUtils;
import com.nix11.model.Mark;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class MarkRepository implements CrudRepository<Mark> {

    private static final SessionFactory SESSION_FACTORY = HibernateFactoryUtils.getSessionFactory();

    private static MarkRepository instance;

    public static MarkRepository getInstance() {
        if (instance == null) {
            instance = new MarkRepository();
        }
        return instance;
    }

    public double subjectGPA(String subjectId) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        double GPA = session
                .createQuery("select avg(value) from Mark mark where subject_id = : subjectid", Double.class)
                .setParameter("subjectid", subjectId)
                .getResultList().get(0);
        session.getTransaction().commit();
        session.close();
        return GPA;
    }

    public double studentGPA(String studentId) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        double GPA = session
                .createQuery("select avg(value) from Mark mark where studentid = : studentid", Double.class)
                .setParameter("studentid", studentId)
                .getSingleResult();
        session.getTransaction().commit();
        session.close();
        return GPA;
    }

    @Override
    public void save(Mark mark) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.save(mark);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveAll(List<Mark> marks) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        for (Mark mark : marks) {
            session.save(mark);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Mark mark) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.update(mark);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(String id) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.createQuery("delete from Mark where id = :value")
                .setParameter("value", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Mark> getAll() {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<Mark> marks = session
                .createQuery("select mark from Mark mark", Mark.class)
                .list();
        session.getTransaction().commit();
        session.close();
        return marks;
    }

    @Override
    public Optional<Mark> getById(String id) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        Optional<Mark> mark = Optional.ofNullable(session.get(Mark.class, id));
        session.getTransaction().commit();
        session.close();
        return mark;
    }
}
