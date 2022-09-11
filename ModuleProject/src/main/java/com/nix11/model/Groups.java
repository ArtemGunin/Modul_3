package com.nix11.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Groups {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String groupId;
    private String groupName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Group_students",
            joinColumns = {@JoinColumn(name = "groups", referencedColumnName = "groupId")},
            inverseJoinColumns = {@JoinColumn(name = "student", referencedColumnName = "studentId")}
    )
    private List<Student> students = new ArrayList<>();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "group = " + groupName +
                ", group id = " + groupId + ")";
    }
}
