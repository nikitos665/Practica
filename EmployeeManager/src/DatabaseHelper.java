import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:employees.db";

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS employees (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "last_name TEXT NOT NULL," +
                "first_name TEXT NOT NULL," +
                "middle_name TEXT," +
                "phone TEXT," +
                "position TEXT NOT NULL," +
                "department TEXT," +
                "salary REAL," +
                "hire_date TEXT," +
                "email TEXT," +
                "address TEXT)";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы: " + e.getMessage());
        }
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees ORDER BY last_name, first_name";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("middle_name"),
                        rs.getString("phone"),
                        rs.getString("position"),
                        rs.getString("department"),
                        rs.getDouble("salary"),
                        rs.getString("hire_date"),
                        rs.getString("email"),
                        rs.getString("address")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения сотрудников: " + e.getMessage());
        }

        return employees;
    }

    public static void addEmployee(Employee employee) {
        String sql = "INSERT INTO employees(last_name, first_name, middle_name, phone, " +
                "position, department, salary, hire_date, email, address) " +
                "VALUES(?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getLastName());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setString(3, employee.getMiddleName());
            pstmt.setString(4, employee.getPhone());
            pstmt.setString(5, employee.getPosition());
            pstmt.setString(6, employee.getDepartment());
            pstmt.setDouble(7, employee.getSalary());
            pstmt.setString(8, employee.getHireDate());
            pstmt.setString(9, employee.getEmail());
            pstmt.setString(10, employee.getAddress());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка добавления сотрудника: " + e.getMessage());
        }
    }

    public static void updateEmployee(Employee employee) {
        String sql = "UPDATE employees SET last_name = ?, first_name = ?, middle_name = ?, " +
                "phone = ?, position = ?, department = ?, salary = ?, hire_date = ?, " +
                "email = ?, address = ? WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, employee.getLastName());
            pstmt.setString(2, employee.getFirstName());
            pstmt.setString(3, employee.getMiddleName());
            pstmt.setString(4, employee.getPhone());
            pstmt.setString(5, employee.getPosition());
            pstmt.setString(6, employee.getDepartment());
            pstmt.setDouble(7, employee.getSalary());
            pstmt.setString(8, employee.getHireDate());
            pstmt.setString(9, employee.getEmail());
            pstmt.setString(10, employee.getAddress());
            pstmt.setInt(11, employee.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка обновления сотрудника: " + e.getMessage());
        }
    }

    public static void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Ошибка удаления сотрудника: " + e.getMessage());
        }
    }

    public static Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        Employee employee = null;

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("last_name"),
                        rs.getString("first_name"),
                        rs.getString("middle_name"),
                        rs.getString("phone"),
                        rs.getString("position"),
                        rs.getString("department"),
                        rs.getDouble("salary"),
                        rs.getString("hire_date"),
                        rs.getString("email"),
                        rs.getString("address")
                );
            }
        } catch (SQLException e) {
            System.out.println("Ошибка получения сотрудника: " + e.getMessage());
        }

        return employee;
    }
}
