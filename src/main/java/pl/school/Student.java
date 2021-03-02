package pl.school;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Student extends Human {
    private final Clazz clazz;

    public Student(String name, String surname, Clazz clazz) {
        super(name, surname);
        this.clazz = clazz;
    }

}
