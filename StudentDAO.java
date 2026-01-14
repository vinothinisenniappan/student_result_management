import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS students (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL, " +
                "number INT NOT NULL, " +
                "marks INT NOT NULL, " +
                "grade CHAR(1) NOT NULL" +
                ")";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn != null ? conn.createStatement() : null) {
            if (stmt == null) throw new SQLException("No DB connection");
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed creating table: " + e.getMessage(), e);
        }
    }

    public int insert(Student s) {
        String sql = "INSERT INTO students(name, number, marks, grade) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) : null) {
            if (ps == null) throw new SQLException("No DB connection");
            ps.setString(1, s.getName());
            ps.setInt(2, s.getNumber());
            ps.setInt(3, s.getMarks());
            ps.setString(4, String.valueOf(s.getGrade()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    s.setId(id);
                    return id;
                }
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException("Failed inserting student: " + e.getMessage(), e);
        }
    }

    public boolean update(Student s) {
        String sql = "UPDATE students SET name = ?, number = ?, marks = ?, grade = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {
            if (ps == null) throw new SQLException("No DB connection");
            ps.setString(1, s.getName());
            ps.setInt(2, s.getNumber());
            ps.setInt(3, s.getMarks());
            ps.setString(4, String.valueOf(s.getGrade()));
            ps.setInt(5, s.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed updating student: " + e.getMessage(), e);
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {
            if (ps == null) throw new SQLException("No DB connection");
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Failed deleting student: " + e.getMessage(), e);
        }
    }

    public Student findById(int id) {
        String sql = "SELECT id, name, number, marks, grade FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {
            if (ps == null) throw new SQLException("No DB connection");
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed fetching student: " + e.getMessage(), e);
        }
    }

    public List<Student> findAll() {
        String sql = "SELECT id, name, number, marks, grade FROM students ORDER BY id";
        List<Student> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn != null ? conn.prepareStatement(sql) : null) {
            if (ps == null) throw new SQLException("No DB connection");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Failed fetching students: " + e.getMessage(), e);
        }
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setName(rs.getString("name"));
        s.setNumber(rs.getInt("number"));
        s.setMarks(rs.getInt("marks"));
        String g = rs.getString("grade");
        s.setGrade(g != null && !g.isEmpty() ? g.charAt(0) : 'N');
        return s;
    }
}
