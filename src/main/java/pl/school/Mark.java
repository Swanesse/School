package pl.school;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Mark {
    private final Student student;
    private final double mark;
    private final Subject subject;
}
