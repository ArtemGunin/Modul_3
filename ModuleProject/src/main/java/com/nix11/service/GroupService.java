package com.nix11.service;

import com.nix11.model.Groups;
import com.nix11.model.Student;
import com.nix11.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class GroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);

    private final GroupRepository groupRepository;

    private static GroupService instance;

    private GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public static GroupService getInstance() {
        if (instance == null) {
            instance = new GroupService(GroupRepository.getInstance());
        }
        return instance;
    }

    public void gradePointAverageOfGroups() {
        System.out.println("Grade point average of groups:");
        Map<String, Double> GPA = groupRepository.getGradePointAverageOfGroups();
        GPA.keySet().forEach(group -> System.out.println("Grade point average of "
                + group + " group =  " + String.format("%.2f", GPA.get(group))));
    }

    public void getGroupsByNamePartialMatch(String name) {
        groupRepository.getGroupsByNamePartialMatch(name).forEach(System.out::println);
    }

    public void getStudentsCountOfGroups() {
        Map<String, Integer> counts = groupRepository.getCountStudentsOfGroups();
        counts.keySet().forEach(group -> System.out.println("Group " + group + " has " + counts.get(group) + " students"));
    }

    public void createAndSaveGroup(List<Student> students) {
        Groups group = new Groups();
        group.setGroupName(UUID.randomUUID().toString().substring(1, 6));
        group.setStudents(students);
        groupRepository.save(group);
        LOGGER.info("new " + group);
    }

    public void updateGroup(Groups group) {
        groupRepository.update(group);
    }

    public void deleteStudent(String id) {
        groupRepository.delete(id);
    }

    public List<Groups> getAllGroups() {
        return groupRepository.getAll();
    }

    public Optional<Groups> getStudentById(String id) {
        return groupRepository.getById(id);
    }
}
