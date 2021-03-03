package pl.school;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Student extends Human {

    public Student(String name, String surname) {
        super(name, surname);
    }

}
