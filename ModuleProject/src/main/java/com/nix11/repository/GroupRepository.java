package com.nix11.repository;

import com.nix11.config.HibernateFactoryUtils;
import com.nix11.model.Groups;
import com.nix11.model.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GroupRepository implements CrudRepository<Groups> {

    private static final SessionFactory SESSION_FACTORY = HibernateFactoryUtils.getSessionFactory();

    private static GroupRepository instance;

    public static GroupRepository getInstance() {
        if (instance == null) {
            instance = new GroupRepository();
        }
        return instance;
    }

    public List<Groups> getGroupsByNamePartialMatch(String name) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<Groups> groups = session
                .createQuery("select groups from Groups groups where groupname like '%" + name + "%'", Groups.class)
                .list();
        session.getTransaction().commit();
        session.close();
        return groups;
    }

    public Map<String, Integer> getCountStudentsOfGroups() {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        Map<String, Integer> studentsCount = new HashMap<>();
        session
                .createQuery("select groups from Groups groups", Groups.class)
                .getResultList()
                .forEach(groups -> studentsCount.put(groups.getGroupName(), groups.getStudents().size()));
        session.getTransaction().commit();
        session.close();
        return studentsCount;
    }

    public Map<String, Double> getGradePointAverageOfGroups() {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        Map<String, Double> studentsCount = new HashMap<>();
        session
                .createQuery("select groups from Groups groups", Groups.class)
                .getResultList()
                .forEach(groups -> studentsCount.put(groups.getGroupName(), groupGradePointAverage(groups.getStudents())));
        session.getTransaction().commit();
        session.close();
        return studentsCount;
    }

    public double groupGradePointAverage(List<Student> students) {
        double marksSum = students.stream()
                .map(student -> MarkRepository.getInstance().studentGPA(student.getStudentId()))
                .reduce(0.0, Double::sum);
        return marksSum / students.size();
    }

    @Override
    public void save(Groups group) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.save(group);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveAll(List<Groups> groups) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        for (Groups group : groups) {
            session.save(group);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Groups group) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.update(group);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(String id) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        session.createQuery("delete from Groups where id = :value")
                .setParameter("value", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Groups> getAll() {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        List<Groups> groups = session
                .createQuery("select group from Groups group", Groups.class)
                .list();
        session.getTransaction().commit();
        session.close();
        return groups;
    }

    @Override
    public Optional<Groups> getById(String id) {
        Session session = SESSION_FACTORY.openSession();
        session.beginTransaction();
        Optional<Groups> group = Optional.ofNullable(session.get(Groups.class, id));
        session.getTransaction().commit();
        session.close();
        return group;
    }
}
