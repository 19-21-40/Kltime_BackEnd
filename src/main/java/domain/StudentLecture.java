package domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "student_lecture")
public class StudentLecture {

    @Id @GeneratedValue
    @Column(name = "student_lecture_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @OneToMany(mappedBy = "studentLecture")
    private List<TableStLecture> tableStLectures;
    private int gpa;
    private String comment;
    private int takesGrade;
    private int takesSemester;
}
