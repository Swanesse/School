package gradebook;

import pl.school.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GradeBookService {

    /**
     * Student can be added only for head master.
     */
    void addStudent(Student student);

    Set<Student> getStudents(Clazz clazz);
    /**
     * Mark can be added only for teachers.
     * It is possible after added student to class another way NotFoundException
     * */
    void addMark(Mark mark);

    /**
     * Mark can be get for all humans.
     * If student wasnt added then NotFoundException
     * */
    List<Mark> getStudentMarks(Student student, Subject subject);

    Student getStudent(Integer id, Clazz clazz);

    Student getStudent(String name, String surname, Clazz clazz);

    Double getAvgCurs(Student student, Subject subject);

    void addTimeTable(Clazz clazz, SubjectsOnDay subjectsOnDay) ;

    Timetable getTimeTableForClass(Clazz clazz);

}
