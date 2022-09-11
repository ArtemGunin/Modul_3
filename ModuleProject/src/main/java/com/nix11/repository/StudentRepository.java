package com.nix11.repository;

import com.nix11.config.HibernateFactoryUtils;
import com.nix11.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentRepository implements CrudRepository<Student> {

    private static final SessionFactory SESSION_FACTORY = HibernateFactoryUtils.getSessionFactory();

    private static StudentRepository instance;

    public static StudentRepository getInstance() {
        if (instance == null) {
            instance = new StudentRepository();
        }
        return instance;
    }

    public Map<Student, Double> getStudentsWithAverageMarkMoreThen(double lowerBound) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        Map<Student, Double> studentsWithMarkMoreThen = session
                .createQuery("select student from Student student", Student.class)
                .getResultList().stream()
                .filter(student -> MarkRepository.getInstance().studentGPA(student.getStudentId()) > lowerBound)
                .collect(Collectors.toMap(student -> student,
                        student1 -> MarkRepository.getInstance().studentGPA(student1.getStudentId())));
        session.getTransaction().commit();
        session.close();
        return studentsWithMarkMoreThen;
    }

    @Override
    public void save(Student student) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.save(student);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveAll(List<Student> students) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        for (Student student : students) {
            session.save(student);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Student student) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.update(student);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(String id) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.createQuery("delete from Student where id = :value")
                .setParameter("value", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Student> getAll() {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<Student> students = session
                .createQuery("select student from Student student", Student.class)
                .list();
        session.getTransaction().commit();
        session.close();
        return students;
    }

    @Override
    public Optional<Student> getById(String id) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        Optional<Student> student = Optional.ofNullable(session.get(Student.class, id));
        session.getTransaction().commit();
        session.close();
        return student;
    }
}
