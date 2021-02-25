public class Mark {
    private Student student;
    double mark;
    SchoolSubject schoolSubject;

    public Mark(Student student, double mark, SchoolSubject schoolSubject) {
        this.student = student;
        this.mark = mark;
        this.schoolSubject = schoolSubject;
    }

    public Student getStudent() {
        return student;
    }
}
