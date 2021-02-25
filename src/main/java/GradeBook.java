import java.util.ArrayList;
import java.util.List;

public class GradeBook {
    SchoolYear schoolYear;
    Branch branch;
    Timetable timetable;
    List<Student> students;
    List<SchoolSubject> subjects;
    List<Mark> marks;

    public GradeBook(SchoolYear schoolYear, Branch branch, Timetable timetable, List<Student> students, List<SchoolSubject> subjects, List<Mark> marks) {
        this.schoolYear = schoolYear;
        this.branch = branch;
        this.timetable = timetable;
        this.students = students;
        this.subjects = subjects;
        this.marks = marks;
    }
}
