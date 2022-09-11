package com.nix11.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mark {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String markId;
    @ManyToOne()
    @JoinColumn(name = "studentId")
    private Student student;
    @OneToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private Double value;
}
