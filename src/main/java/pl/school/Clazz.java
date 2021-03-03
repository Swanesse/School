package pl.school;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Builder
@Getter
public class Clazz {
    private Year year;
    private Branch branch;
    private final List<Student> students = new ArrayList<>();
    private final List<Mark> marks = new ArrayList<>();
    private final Timetable timetable = new Timetable();
}
