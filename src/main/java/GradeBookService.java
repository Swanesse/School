import java.util.ArrayList;
import java.util.List;

public class GradeBookService {
    List<GradeBook> gradeBookList = new ArrayList<>();

    public void createGradeBook(SchoolYear schoolYear, Branch branch, Timetable timetable, List<Student> students, List<SchoolSubject> subjects, List<Mark> marks) {
        GradeBook gradeBook = new GradeBook(
                schoolYear,
                branch,
                timetable,
                students,
                subjects,
                marks
                );
        gradeBookList.add(gradeBook);
    }

    public GradeBook getGradeBookByStudent(Student student){
        for (GradeBook gradeBook : gradeBookList) {
            for (Student element : gradeBook.students) {
                if(element.equals(student)){
                    return gradeBook;
                }
            }
        }
        return null;
    }

    public List<Double> getGrades(Student student, SchoolSubject schoolSubject){
        GradeBook gradeBook = getGradeBookByStudent(student);
        List<Double> grades = new ArrayList<>();

        if (gradeBook != null){
            for (Mark mark : gradeBook.marks) {
                if(mark.schoolSubject == schoolSubject && mark.getStudent().equals(student)){
                    grades.add(mark.mark);
                }
            }
        }
        return grades;
    }

    public void setGrade(Mark mark){
        GradeBook gradeBook = getGradeBookByStudent(mark.getStudent());
        if (gradeBook != null){
            gradeBook.marks.add(mark);
        }
    }
}
