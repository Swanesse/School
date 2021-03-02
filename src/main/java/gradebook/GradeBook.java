package gradebook;


import lombok.Getter;
import pl.school.Mark;
import pl.school.Student;
import pl.school.Timetable;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GradeBook {
    private final List<Student> students = new ArrayList<>();
    private final List<Mark> marks = new ArrayList<>();
    private final Timetable timetable = new Timetable();
}
