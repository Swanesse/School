package pl.school;

import lombok.Getter;

import java.util.List;

@Getter
public class Timetable {
    private List<SubjectsOnDay> subjectsOnDays;
    private Clazz clazz;
}
