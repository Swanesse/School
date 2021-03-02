package gradebook;

import pl.school.*;

import java.time.DayOfWeek;
import java.util.*;

public class GradeBookServiceImpl implements GradeBookService {

    private final GradeBook gradeBook = new GradeBook();

    @Override
    public void addStudent(Student student) {
        for (Student studentInGradeBook : gradeBook.getStudents()) {
            if ((student.getName().equals(studentInGradeBook.getName())) &&
                    (student.getSurname().equals(studentInGradeBook.getSurname())) &&
                        (student.getClazz().equals(studentInGradeBook.getClazz()))) {
                throw new IllegalArgumentException("This student already exist in this class. Are you stupid or something?");
            }
        }
        gradeBook.getStudents().add(student);
    }

    @Override
    public Set<Student> getStudents(Clazz clazz) {
        Set<Student> students = new HashSet<>();
        for (Student student : gradeBook.getStudents()) {
            if (student.getClazz().equals(clazz)) {
                students.add(student);
            }
        }
        return students;
    }

    @Override
    public void addMark(Mark mark) {
        gradeBook.getMarks().add(mark);
    }

    @Override
    public List<Mark> getStudentMarks(Student student, Subject subject) {
        List<Mark> allMarks = gradeBook.getMarks();
        List<Mark> studentMarks = new ArrayList<>();
        for (Mark mark : allMarks) {
            if (mark.getStudent().equals(student) && mark.getSubject().equals(subject)) {
                studentMarks.add(mark);
            }
        }
        return studentMarks;
    }

    @Override
    public Student getStudent(Integer id, Clazz clazz) {
        for (int i = 0; i < gradeBook.getStudents().size(); i++) {
            if(i == id && gradeBook.getStudents().get(i).getClazz().equals(clazz)){
                return gradeBook.getStudents().get(i);
            }
        }
        return null;
    }

    @Override
    public Student getStudent(String name, String surname, Clazz clazz) {
        for (Student student : gradeBook.getStudents()) {
            if(student.getName().equals(name) && student.getSurname().equals(surname) && student.getClazz().equals(clazz)){
                return student;
            }
        }
        return null;
    }

    @Override
    public Double getAvgCurs(Student student, Subject subject) {
        double sum = 0.0;
        int numberOfMarks = 0;
        for (Mark mark : gradeBook.getMarks()) {
            if (mark.getStudent().equals(student) && mark.getSubject().equals(subject)) {
                sum += mark.getMark();
                numberOfMarks += 1;
            }
        }
        return sum / numberOfMarks;
    }

    @Override
    public void addTimeTable(Clazz clazz, SubjectsOnDay subjectsOnDay) {
        List<Subject> subjects = new ArrayList<>(Arrays.asList(Subject.MATH, Subject.PE, Subject.HISTORY));
        SubjectsOnDay subjectsOnDay2 = new SubjectsOnDay(DayOfWeek.MONDAY, subjects);
        gradeBook.getTimetable().getSubjectsOnDays().add(subjectsOnDay);
    }

    @Override
    public Timetable getTimeTableForClass(Clazz clazz) {
        gradeBook.getTimetable();
        return null;
    }
}
