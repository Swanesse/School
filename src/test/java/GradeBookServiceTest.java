import gradebook.GradeBookService;
import gradebook.GradeBookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pl.school.*;

import java.time.DayOfWeek;
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
        Clazz clazz = Clazz.builder()
                .branch(Branch.B)
                .year(Year.FIRST)
                .build();
        Set<Student> students = gradeBookService.getStudents(clazz);
        assertThat(students)
                .isEmpty();
    }

    @Test
    void shouldAddStudent() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.B);
        Student student1 = new Student("Pala", "Polchlopek", clazz);
        gradeBookService.addStudent(student1);
        Set<Student> students = gradeBookService.getStudents(clazz);
        assertThat(students)
                .usingFieldByFieldElementComparator()
                .contains(student1);
    }

    @Test
    void shouldNotAddTwoTheSameStudents() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.A);
        Student student = new Student("Monia", "Niewidzialna", clazz);
        gradeBookService.addStudent(student);

        assertThatIllegalArgumentException().isThrownBy(() -> {
            gradeBookService.addStudent(student);
        });
    }

    @Test
    void shouldFindStudent() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.B);
        Student student1 = new Student("Pala", "Polchlopek", clazz);
        gradeBookService.addStudent(student1);
        Set<Student> students = gradeBookService.getStudents(clazz);
        Student student2 = new Student("Pala", "Polchlopek", clazz);
        assertThat(students)
                .usingFieldByFieldElementComparator()
                .contains(student2);
    }

    @Test
    void shouldReturnStudentsFromDifferentClazz() {
        Clazz clazz1 = new Clazz(Year.FIRST, Branch.A);
        Clazz clazz2 = new Clazz(Year.FIRST, Branch.B);
        Student student1 = new Student("Monia", "Niewidzialna", clazz1);
        Student student2 = new Student("Monia", "Niewidzialna", clazz2);
        gradeBookService.addStudent(student1);
        gradeBookService.addStudent(student2);
        Set<Student> students1 = gradeBookService.getStudents(clazz1);
        Set<Student> students2 = gradeBookService.getStudents(clazz2);
        assertEquals(students1.size(), 1);
        assertEquals(students2.size(), 1);
    }

    @Test
    void shouldReturnTwoStudentsFromClazz() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.A);
        Student student1 = new Student("Monia", "Niewidzialna", clazz);
        Student student2 = new Student("Monia", "Niewidzialna1", clazz);
        gradeBookService.addStudent(student1);
        gradeBookService.addStudent(student2);
        Set<Student> students = gradeBookService.getStudents(clazz);
        assertEquals(2, students.size());
    }

    @Test
    void shoulNotFindStudentInClazzByBranch() {
        Clazz clazz1 = new Clazz(Year.FIRST, Branch.A);
        Clazz clazz2 = new Clazz(Year.FIRST, Branch.B);
        Student student = new Student("Monia", "Niewidzialna", clazz1);
        gradeBookService.addStudent(student);
        Set<Student> students = gradeBookService.getStudents(clazz2);
        assertThat(students)
                .usingFieldByFieldElementComparator()
                .doesNotContain(student);
    }

    @Test
    void shoulNotFindStudentInClazzByYear() {
        Clazz clazz1 = new Clazz(Year.FIRST, Branch.A);
        Clazz clazz2 = new Clazz(Year.SECOND, Branch.A);
        Student student = new Student("Monia", "Niewidzialna", clazz1);
        gradeBookService.addStudent(student);
        Set<Student> students = gradeBookService.getStudents(clazz2);
        assertThat(students)
                .usingFieldByFieldElementComparator()
                .doesNotContain(student);
    }

    @Test
    void shouldReturnEmptyWhenThereIsNoMarks() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.B);
        Student student = new Student("Pala", "Polchlopek", clazz);
        List<Mark> marks = gradeBookService.getStudentMarks(student, Subject.ENGLISH);
        assertThat(marks
                .isEmpty());
    }

    @Test
    void shouldAddMark() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.B);
        Student student = new Student("Pala", "Polchlopek", clazz);
        Mark mark = new Mark(student, 4.5, Subject.ENGLISH);
        gradeBookService.addMark(mark);
        List<Mark> marks = gradeBookService.getStudentMarks(student, Subject.ENGLISH);
        assertThat(marks)
                .usingFieldByFieldElementComparator()
                .contains(mark);
    }

    @Test
    void shouldAddTwoMarksOneStudent() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.B);
        Student student = new Student("Pala", "Polchlopek", clazz);
        Mark mark1 = new Mark(student, 4.5, Subject.ENGLISH);
        Mark mark2 = new Mark(student, 5.0, Subject.ENGLISH);
        gradeBookService.addMark(mark1);
        gradeBookService.addMark(mark2);
        List<Mark> marks = gradeBookService.getStudentMarks(student, Subject.ENGLISH);
        assertEquals(marks.size(), 2);
    }

    @Test
    void shouldAvgIsNull() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.B);
        Student student = new Student("Pala", "Polchlopek", clazz);
        Double average = gradeBookService.getAvgCurs(student, Subject.ENGLISH);
        assertThat(average == null);
    }

    @Test
    void shouldAvgIs4() {
        Clazz clazz = new Clazz(Year.FIRST, Branch.B);
        Student student = new Student("Pala", "Polchlopek", clazz);
        Mark mark1 = new Mark(student, 4.0, Subject.ENGLISH);
        Mark mark2 = new Mark(student, 5.0, Subject.ENGLISH);
        Mark mark3 = new Mark(student, 3.0, Subject.ENGLISH);
        gradeBookService.addMark(mark1);
        gradeBookService.addMark(mark2);
        gradeBookService.addMark(mark3);
        Double average = gradeBookService.getAvgCurs(student, Subject.ENGLISH);
        assertThat(average.equals(4.0));
    }

    @Test
    void shouldReturnNullWhenThereIsNotStudentsInGradeBook() {
        Clazz clazz = new Clazz(Year.SECOND, Branch.A);
        Student student = gradeBookService.getStudent("Monia", "Niewidzialna", clazz);
        assertThat(student == null);
    }

    @Test
    void shouldReturnStudent() {
        Clazz clazz = new Clazz(Year.SECOND, Branch.A);
        Student student = new Student("Monia", "Niewidzialna", clazz);
        gradeBookService.addStudent(student);
        Student studentFromGradeBook = gradeBookService.getStudent("Monia", "Niewidzialna", clazz);
        assertThat(studentFromGradeBook)
                .isEqualTo(student);
    }

    @Test
    void shouldReturnNullWhenThereIsNoStudentById() {
        Clazz clazz = new Clazz(Year.SECOND, Branch.A);
        Student student = gradeBookService.getStudent(0, clazz);
        assertThat(student == null);
    }

    @Test
    void shouldReturnStudentById() {
        Clazz clazz = new Clazz(Year.SECOND, Branch.A);
        Student student = new Student("Monia", "Niewidzialna", clazz);
        gradeBookService.addStudent(student);
        Student studentFromGradeBook = gradeBookService.getStudent(0, clazz);
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