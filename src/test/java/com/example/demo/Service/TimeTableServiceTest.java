package com.example.demo.Service;

import com.example.demo.Repository.LectureRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Repository.TimeTableRepository;
import com.example.demo.domain.Lecture;
import com.example.demo.domain.Student;
import com.example.demo.domain.TimeTable;
import com.example.demo.domain.TimeTableLecture;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class TimeTableServiceTest {

    @Autowired TimeTableService timeTableService;
    @Autowired LectureService lectureService;
    @Autowired
    TimeTableRepository timeTableRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    EntityManager em;


    @Test
//    @Rollback(false)
    public void 시간표_추가() throws Exception {
        //given
        //when
        Long timetableId = timeTableService.addTimeTable(5L,1,true,"시간표1",30L);
        //then
        TimeTable getTimeTable = timeTableRepository.findOne(timetableId);
        assertEquals("시간표 추가", timetableId,getTimeTable.getId());
    }

    //원래 delete쿼리가 안나가나...?
    @Test
//    @Rollback(false)
    public void 시간표_삭제() throws Exception {
        //given
//        Long timetableId = timeTableService.addTimeTable(5L,1,true,"시간표1",30L);
        //when
        timeTableService.deleteTimeTable(5L,35L);
        //then
    }

    @Test
    @Rollback(false)
    public void 기본시간표_변경() throws Exception {
        //given
        Long timetableId1 = timeTableService.addTimeTable(5L,1,true,"시간표1",30L);
        Long timetableId2 = timeTableService.addTimeTable(5L,1,false,"시간표2",30L);
        //when
        timeTableService.changePrimary(5L,2,timetableId2);
        //then
        Student student = studentRepository.findById(5L);
        TimeTable primaryTimeTable = timeTableRepository.findByStudentAndGradeAndSemesterAndPrimary(student,2,1,true);
        assertEquals("기본시간표 변경", timetableId2,primaryTimeTable.getId());
    }

    @Test
//    @Rollback(false)
    public void 중복_기본시간표_예외() throws Exception {
        //given
        Long timetableId1 = timeTableService.addTimeTable(5L,1,true,"시간표1",30L);
        Long timetableId2 = timeTableService.addTimeTable(5L,1,true,"시간표2",30L);
        //when
        IllegalStateException e = assertThrows(IllegalStateException.class,()-> timeTableService.changePrimary(5L,1,timetableId2));
        //then
        assertEquals("기본 시간표가 이미 존재합니다.", e.getMessage());
    }

    //이건 중복되도 상관없겠지..?
    @Test
    @Rollback(false)
    public void 시간표_이름_변경() throws Exception {
        //given
        Long timetableId = timeTableService.addTimeTable(5L,1,true,"시간표1",30L);
        //when
        timeTableService.changeTimeTableName(5L,timetableId,"시간표12");
        //then
        TimeTable timeTable = timeTableRepository.findOne(timetableId);
        assertEquals("시간표 이름 변경", "시간표12", timeTable.getTableName());
    }

}