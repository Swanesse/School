import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GradeBookServiceTest {

    GradeBookService gradeBookService;
    Student student;

    @BeforeEach
    void setUp(){
        gradeBookService = new GradeBookService();

        student = new Student("Monika", "Mańko");

        gradeBookService.createGradeBook(
                SchoolYear.FIFTH,
                Branch.B,
                new Timetable(),
                List.of(student),
                List.of(SchoolSubject.ENGLISH, SchoolSubject.HISTORY),
                new ArrayList<>(Arrays.asList(new Mark(student, 4.5, SchoolSubject.ENGLISH),
                        new Mark(student, 4, SchoolSubject.ENGLISH),
                        new Mark(student, 5, SchoolSubject.ENGLISH),
                        new Mark(student, 6, SchoolSubject.ENGLISH),
                        new Mark(student, 1, SchoolSubject.ENGLISH),
                        new Mark(student, 3.5, SchoolSubject.ENGLISH)))
        );
    }


    @Test
    void shouldReturnGradeBook(){
        GradeBook gradeBook = gradeBookService.getGradeBookByStudent(student);
        assertEquals(gradeBook, gradeBookService.gradeBookList.get(0));
    }

    @Test
    void shouldSetOneGradeToGradeBook(){
        Mark mark = new Mark(student, 4.5, SchoolSubject.ENGLISH);
        gradeBookService.setGrade(mark);
        GradeBook gradeBook = gradeBookService.getGradeBookByStudent(student);
        assertEquals(gradeBook.marks.get(6), mark);
    }

    @Test
    void shouldGetAllGradesForEnglishForOneStudent(){
        List<Double> marks = gradeBookService.getGrades(student, SchoolSubject.ENGLISH);
        assertEquals(List.of(4.5, 4.0, 5.0, 6.0, 1.0, 3.5), marks);
    }
}