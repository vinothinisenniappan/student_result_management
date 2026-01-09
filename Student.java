public class Student {
    private int id;
    private String name;
    private int number;
    private int marks;
    private char grade;

    public Student() {
    }

    public Student(int id, String name, int marks, int number, char grade) {
        this.id = id;
        this.name = name;
        this.marks = marks;
        this.number = number;
        this.grade = grade;
    }

    public Student(String name, int marks, int number, char grade) {
        this.name = name;
        this.marks = marks;
        this.number = number;
        this.grade = grade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public char getGrade() {
        return grade;
    }

    public void setGrade(char grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", marks=" + marks +
                ", grade=" + grade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
