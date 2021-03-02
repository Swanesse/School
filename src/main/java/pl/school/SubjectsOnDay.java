package pl.school;

import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.List;

@AllArgsConstructor
public class SubjectsOnDay {
    private final DayOfWeek dayOfWeek;
    private final List<Subject> subjects;
}
