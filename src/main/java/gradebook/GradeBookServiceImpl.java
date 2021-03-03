package gradebook;

import pl.school.*;

import java.time.DayOfWeek;
import java.util.*;

public class GradeBookServiceImpl implements GradeBookService {

    private final List<GradeBook> gradeBooks = new ArrayList<>();

    @Override
    public void addStudent(Student student, Year year, Branch branch) {
        GradeBook gradeBook = findGradeBookAndAddWhenNone(year, branch);
        for (Student studentInGradeBook : gradeBook.getClazz().getStudents()) {
            if ((student.getName().equals(studentInGradeBook.getName())) &&
                    (student.getSurname().equals(studentInGradeBook.getSurname()))) {
                throw new IllegalArgumentException("This student already exist in this class. Are you stupid or something?");
            }
        }
        gradeBook.getClazz().getStudents().add(student);
    }

    @Override
    public List<Student> getStudents(Year year, Branch branch) {
        GradeBook gradeBook = findGradeBook(year, branch);

        if (gradeBook != null) {
            return gradeBook.getClazz().getStudents();
        }
        return null;
    }

    @Override
    public void addMark(Mark mark, Year year, Branch branch) {
        GradeBook gradeBook = findGradeBook(year, branch);

        if (gradeBook != null) {
            gradeBook.getClazz().getMarks().add(mark);
        }
    }

    @Override
    public List<Mark> getStudentMarks(Student student, Subject subject, Year year, Branch branch) {
        GradeBook gradeBook = findGradeBook(year, branch);
        List<Mark> studentMarks = new ArrayList<>();

        if (gradeBook != null) {
            List<Mark> allMarks = gradeBook.getClazz().getMarks();
            for (Mark mark : allMarks) {
                if (mark.getStudent().equals(student) && mark.getSubject().equals(subject)) {
                    studentMarks.add(mark);
                }
            }
        }
        return studentMarks;
    }

    @Override
    public Student getStudent(Integer id, Year year, Branch branch) {
        GradeBook gradeBook = findGradeBook(year, branch);

        if (gradeBook != null) {
            return gradeBook.getClazz().getStudents().get(id);
        }
        return null;
    }

    @Override
    public Student getStudent(String name, String surname, Year year, Branch branch) {
        GradeBook gradeBook = findGradeBook(year, branch);

        if (gradeBook != null) {
            for (Student student : gradeBook.getClazz().getStudents()) {
                if (student.getName().equals(name) && student.getSurname().equals(surname)) {
                    return student;
                }
            }
        }
        return null;
    }

    @Override
    public Double getAvgCurs(Student student, Subject subject, Year year, Branch branch) {
        double sum = 0.0;
        int numberOfMarks = 0;
        GradeBook gradeBook = findGradeBook(year, branch);

        if (gradeBook != null) {
            for (Mark mark : gradeBook.getClazz().getMarks()) {
                if (mark.getStudent().equals(student) && mark.getSubject().equals(subject)) {
                    sum += mark.getMark();
                    numberOfMarks += 1;
                }
            }
            return sum / numberOfMarks;
        }
        return 0.0;
    }

    @Override
    public void addTimeTable(Clazz clazz, SubjectsOnDay subjectsOnDay, Year year, Branch branch) {
        GradeBook gradeBook = findGradeBook(year, branch);

        if (gradeBook != null) {
            List<Subject> subjects = new ArrayList<>(Arrays.asList(Subject.MATH, Subject.PE, Subject.HISTORY));
            SubjectsOnDay subjectsOnDay2 = new SubjectsOnDay(DayOfWeek.MONDAY, subjects);
            gradeBook.getClazz().getTimetable().getSubjectsOnDays().add(subjectsOnDay);
        }
    }

    @Override
    public Timetable getTimeTableForClass(Year year, Branch branch) {
        GradeBook gradeBook = findGradeBook(year, branch);

        if (gradeBook != null) {
            return gradeBook.getClazz().getTimetable();
        }
        return null;
    }

    private GradeBook findGradeBookAndAddWhenNone(Year year, Branch branch) {
        for (GradeBook gradeBook : gradeBooks) {
            if (gradeBook.getClazz().getYear().equals(year) && gradeBook.getClazz().getBranch().equals(branch)) {
                return gradeBook;
            }
        }
        Clazz clazz = new Clazz(year, branch);
        GradeBook gradeBook = new GradeBook(clazz);
        this.gradeBooks.add(gradeBook);
        return gradeBook;
    }

    private GradeBook findGradeBook(Year year, Branch branch) {
        for (GradeBook gradeBook : gradeBooks) {
            if (gradeBook.getClazz().getYear().equals(year) && gradeBook.getClazz().getBranch().equals(branch)) {
                return gradeBook;
            }
        }
        return null;
    }
}
