import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/student_db";
            String user = "root";
            String password = "your_password"; // change this

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
