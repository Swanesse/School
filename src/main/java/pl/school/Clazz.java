package pl.school;

import lombok.AllArgsConstructor;
import lombok.Builder;


@AllArgsConstructor
@Builder
public class Clazz {
    private Year year;
    private Branch branch;
}
