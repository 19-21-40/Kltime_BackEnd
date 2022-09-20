package com.example.demo.Repository;

import com.example.demo.domain.Department;
import com.example.demo.domain.Lecture;
import com.example.demo.domain.Student;
import com.example.demo.domain.StudentLecture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StudentRepository {

    private final EntityManager em;

    public Student save(Student student) {
        em.persist(student);
        return student;
    }

    public Student findById(Long id) {
        return em.find(Student.class, id);
    }

    public Student findByNumber(String number){
        return em.createQuery("select s from Student s where s.number=:number",Student.class)
                .setParameter("number",number)
                .getSingleResult();
    }

    public Student findByNumberAndPassword(String number,String password){
        return em.createQuery("select s from Student s where s.number=:number and s.password=:password",Student.class)
                .setParameter("number",number)
                .setParameter("password",password)
                .getSingleResult();
    }

    public Student findByIdWithLecture(Long id) {
        return em.createQuery("select s from Student s join fetch s.myLectures sl join fetch sl.lecture l where s.id =:id", Student.class)
                .setParameter("id", id)
                .getSingleResult();
    }


    public List<Student> findByLecture(Lecture lecture) {
        return em.createQuery("select s from Student s left join s.myLectures sl where sl.lecture =:lecture", Student.class)
                .setParameter("lecture", lecture)
                .getResultList();
    }
//
    public List<Student> findByLectureAndDept(Lecture lecture, Department dept) {
        return em.createQuery("select s from Student s left join s.myLectures sl where sl.lecture =:lecture and s.department =:dept", Student.class)
                .setParameter("lecture", lecture)
                .setParameter("dept", dept)
                .getResultList();

    }

    public List<Student> findByLectureAndDeptAndGrade(Lecture lecture, Department dept, int grade) {
        return em.createQuery("select s from Student s left join s.myLectures sl "
                + "where sl.lecture =:lecture and s.department =:dept and s.grade =:grade", Student.class)
                .setParameter("lecture", lecture)
                .setParameter("dept", dept)
                .setParameter("grade", grade)
                .getResultList();

    }

    public Boolean existsByNumber(String number){
        return em.createQuery("select count(s)>0 from Student s where s.number=:number",Boolean.class)
                .setParameter("number",number)
                .getSingleResult();
    }
}
