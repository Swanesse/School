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
    void addStudent(Student student, Year year, Branch branch);

    List<Student> getStudents(Year year, Branch branch);
    /**
     * Mark can be added only for teachers.
     * It is possible after added student to class another way NotFoundException
     * */
    void addMark(Mark mark, Year year, Branch branch);

    /**
     * Mark can be get for all humans.
     * If student wasnt added then NotFoundException
     * */
    List<Mark> getStudentMarks(Student student, Subject subject, Year year, Branch branch);

    Student getStudent(Integer id, Year year, Branch branch);

    Student getStudent(String name, String surname, Year year, Branch branch);

    Double getAvgCurs(Student student, Subject subject, Year year, Branch branch);

    void addTimeTable(Clazz clazz, SubjectsOnDay subjectsOnDay, Year year, Branch branch) ;

    Timetable getTimeTableForClass(Year year, Branch branch);

}
