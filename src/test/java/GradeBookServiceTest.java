import gradebook.GradeBookService;
import gradebook.GradeBookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.school.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GradeBookServiceTest {

    public GradeBookService gradeBookService;

    @BeforeEach
    void init() {
        gradeBookService = new GradeBookServiceImpl();
    }

    @Test
    void shouldReturnAllStudentsFromClass() {
        List<Student> students = gradeBookService.getStudents(Year.FIRST, Branch.B);
        assertThat(students == null);
    }

    @Test
    void shouldAddStudent() {
        Student student = new Student("Pala", "Polchlopek");
        gradeBookService.addStudent(student, Year.FIRST, Branch.B);
        List<Student> students = gradeBookService.getStudents(Year.FIRST, Branch.B );
        assertThat(students)
                .usingFieldByFieldElementComparator()
                .contains(student);
    }

    @Test
    void shouldNotAddTwoTheSameStudents() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.A);
        Student student = new Student("Monia", "Niewidzialna");
        gradeBookService.addStudent(student, Year.FIRST, Branch.A);

        assertThatIllegalArgumentException().isThrownBy(() -> {
            gradeBookService.addStudent(student, Year.FIRST, Branch.A);
        });
    }

    @Test
    void shouldFindStudent() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.B);
        Student student1 = new Student("Pala", "Polchlopek");
        gradeBookService.addStudent(student1, Year.FIRST, Branch.B);
        List<Student> students = gradeBookService.getStudents(Year.FIRST, Branch.B);
        Student student2 = new Student("Pala", "Polchlopek");
        assertThat(students)
                .usingFieldByFieldElementComparator()
                .contains(student2);
    }

    @Test
    void shouldReturnStudentsFromDifferentClazz() {
        Clazz clazz1 = new Clazz(Year.FIRST, Branch.A);
        Clazz clazz2 = new Clazz(Year.FIRST, Branch.B);
        Student student1 = new Student("Monia", "Niewidzialna");
        Student student2 = new Student("Monia", "Niewidzialna");
        gradeBookService.addStudent(student1, Year.FIRST, Branch.A);
        gradeBookService.addStudent(student2, Year.FIRST, Branch.B);
        List<Student> students1 = gradeBookService.getStudents(Year.FIRST, Branch.A);
        List<Student> students2 = gradeBookService.getStudents(Year.FIRST, Branch.B);
        assertEquals(students1.size(), 1);
        assertEquals(students2.size(), 1);
    }

    @Test
    void shouldReturnTwoStudentsFromClazz() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.A);
        Student student1 = new Student("Monia", "Niewidzialna");
        Student student2 = new Student("Monia", "Niewidzialna1");
        gradeBookService.addStudent(student1, Year.FIRST, Branch.A);
        gradeBookService.addStudent(student2, Year.FIRST, Branch.A);
        List<Student> students = gradeBookService.getStudents(Year.FIRST, Branch.A);
        assertEquals(2, students.size());
    }

    @Test
    void shoulNotFindStudentInClazzByBranch() {
        Student student = new Student("Monia", "Niewidzialna");
        gradeBookService.addStudent(student, Year.FIRST, Branch.A);
        List<Student> students = gradeBookService.getStudents(Year.FIRST, Branch.B);
        assertThat(students)
                .usingFieldByFieldElementComparator()
                .doesNotContain(student);
    }

    @Test
    void shoulNotFindStudentInClazzByYear() {
        Clazz clazz1 = new Clazz(Year.FIRST, Branch.A);
        Clazz clazz2 = new Clazz(Year.SECOND, Branch.A);
        Student student = new Student("Monia", "Niewidzialna");
        gradeBookService.addStudent(student, Year.FIRST, Branch.A);
        List<Student> students = gradeBookService.getStudents(Year.SECOND, Branch.A);
        assertThat(students)
                .usingFieldByFieldElementComparator()
                .doesNotContain(student);
    }

    @Test
    void shouldReturnEmptyWhenThereIsNoMarks() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.B);
        Student student = new Student("Pala", "Polchlopek");
        List<Mark> marks = gradeBookService.getStudentMarks(student, Subject.ENGLISH, Year.FIRST, Branch.B);
        assertThat(marks
                .isEmpty());
    }

    @Test
    void shouldAddMark() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.B);
        Student student = new Student("Pala", "Polchlopek");
        Mark mark = new Mark(student, 4.5, Subject.ENGLISH);
        gradeBookService.addMark(mark, Year.FIRST, Branch.B);
        List<Mark> marks = gradeBookService.getStudentMarks(student, Subject.ENGLISH, Year.FIRST, Branch.B);
        assertThat(marks)
                .usingFieldByFieldElementComparator()
                .contains(mark);
    }

    @Test
    void shouldAddTwoMarksOneStudent() {
        Student student = new Student("Pala", "Polchlopek");
        Mark mark1 = new Mark(student, 4.5, Subject.ENGLISH);
        Mark mark2 = new Mark(student, 5.0, Subject.ENGLISH);
        gradeBookService.addMark(mark1, Year.FIRST, Branch.B);
        gradeBookService.addMark(mark2, Year.FIRST, Branch.B);
        List<Mark> marks = gradeBookService.getStudentMarks(student, Subject.ENGLISH, Year.FIRST, Branch.B);
        assertEquals(marks.size(), 2);
    }

    @Test
    void shouldAvgIsNull() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.B);
        Student student = new Student("Pala", "Polchlopek");
        Double average = gradeBookService.getAvgCurs(student, Subject.ENGLISH, Year.FIRST, Branch.B);
        assertThat(average == null);
    }

    @Test
    void shouldAvgIs4() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.B);
        Student student = new Student("Pala", "Polchlopek");
        Mark mark1 = new Mark(student, 4.0, Subject.ENGLISH);
        Mark mark2 = new Mark(student, 5.0, Subject.ENGLISH);
        Mark mark3 = new Mark(student, 3.0, Subject.ENGLISH);
        gradeBookService.addMark(mark1, Year.FIRST, Branch.B);
        gradeBookService.addMark(mark2, Year.FIRST, Branch.B);
        gradeBookService.addMark(mark3, Year.FIRST, Branch.B);
        Double average = gradeBookService.getAvgCurs(student, Subject.ENGLISH, Year.FIRST, Branch.B);
        assertThat(average.equals(4.0));
    }

    @Test
    void shouldReturnNullWhenThereIsNotStudentsInGradeBook() {
        Clazz clazz = new Clazz(Year.SECOND, Branch.A);
        Student student = gradeBookService.getStudent("Monia", "Niewidzialna", Year.SECOND, Branch.A);
        assertThat(student == null);
    }

    @Test
    void shouldReturnStudent() {
        Clazz clazz = new Clazz(Year.SECOND, Branch.A);
        Student student = new Student("Monia", "Niewidzialna");
        gradeBookService.addStudent(student, Year.SECOND, Branch.A);
        Student studentFromGradeBook = gradeBookService.getStudent("Monia", "Niewidzialna", Year.SECOND, Branch.A);
        assertThat(studentFromGradeBook)
                .isEqualTo(student);
    }

    @Test
    void shouldReturnNullWhenThereIsNoStudentById() {
        Clazz clazz = new Clazz(Year.SECOND, Branch.A);
        Student student = gradeBookService.getStudent(0, Year.SECOND, Branch.A);
        assertThat(student == null);
    }

    @Test
    void shouldReturnStudentById() {
        Clazz clazz = new Clazz(Year.SECOND, Branch.A);
        Student student = new Student("Monia", "Niewidzialna");
        gradeBookService.addStudent(student, Year.SECOND, Branch.A);
        Student studentFromGradeBook = gradeBookService.getStudent(0, Year.SECOND, Branch.A);
        assertThat(studentFromGradeBook.equals(student));
    }

//    @Test
//    void shouldAddTimeTable(){
//        Clazz clazz = new Clazz(Year.SECOND, Branch.A);
//        List<Subject> subjects = new ArrayList<>(Arrays.asList(Subject.ENGLISH, Subject.HISTORY, Subject.MATH));
//        SubjectsOnDay subjectsOnDay = new SubjectsOnDay(DayOfWeek.MONDAY, subjects);
//        gradeBookService.addTimeTable(clazz, subjectsOnDay);
//    }
}