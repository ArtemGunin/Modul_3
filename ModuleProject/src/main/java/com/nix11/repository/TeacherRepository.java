package com.nix11.repository;

import com.nix11.config.HibernateFactoryUtils;
import com.nix11.model.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class TeacherRepository implements CrudRepository<Teacher> {

    private static final SessionFactory SESSION_FACTORY = HibernateFactoryUtils.getSessionFactory();

    private static TeacherRepository instance;

    public static TeacherRepository getInstance() {
        if (instance == null) {
            instance = new TeacherRepository();
        }
        return instance;
    }

    public List<Teacher> getTeacherByName(String name) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<Teacher> teachers = session
                .createQuery("select teacher from Teacher teacher where name = : name", Teacher.class)
                .setParameter("name", name)
                .list();
        session.getTransaction().commit();
        session.close();
        return teachers;
    }

    public List<Teacher> getTeacherBySurname(String surname) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<Teacher> teachers = session
                .createQuery("select teacher from Teacher teacher where surname = : surname", Teacher.class)
                .setParameter("surname", surname)
                .list();
        session.getTransaction().commit();
        session.close();
        return teachers;
    }

    @Override
    public void save(Teacher teacher) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.save(teacher);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveAll(List<Teacher> teachers) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        for (Teacher teacher : teachers) {
            session.save(teacher);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Teacher teacher) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.update(teacher);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(String id) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.createQuery("delete from Teacher where id = :value")
                .setParameter("value", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Teacher> getAll() {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<Teacher> teachers = session
                .createQuery("select teacher from Teacher teacher", Teacher.class)
                .list();
        session.getTransaction().commit();
        session.close();
        return teachers;
    }

    @Override
    public Optional<Teacher> getById(String id) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        Optional<Teacher> teacher = Optional.ofNullable(session.get(Teacher.class, id));
        session.getTransaction().commit();
        session.close();
        return teacher;
    }
}
