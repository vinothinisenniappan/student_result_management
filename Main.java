import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();

        // Ensure table exists
        dao.createTableIfNotExists();

        // Insert a sample student
        Student alice = new Student("Alice", 88, 1001, 'A');
        int newId = dao.insert(alice);
        System.out.println("Inserted student with id: " + newId);

        // List all students
        List<Student> all = dao.findAll();
        System.out.println("All students:");
        for (Student s : all) System.out.println(s);

        // Find by id
        Student fetched = dao.findById(newId);
        System.out.println("Fetched by id: " + fetched);

        // Update marks and grade
        fetched.setMarks(92);
        fetched.setGrade('A');
        boolean updated = dao.update(fetched);
        System.out.println("Update success: " + updated);

        // Delete
        boolean deleted = dao.delete(newId);
        System.out.println("Delete success: " + deleted);
    }
}
