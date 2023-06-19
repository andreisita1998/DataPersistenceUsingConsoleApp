import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost/testttt?autoReconnect=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private Connection connection;


    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection successfully");
        } catch (SQLException e) {
            System.out.println("Connection dead");
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(Employee employee) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO employees (id, firstName, age, address, salary) VALUES (null, ?, ?, ?, ?)")) {
            statement.setString(1, employee.getFirstName());
            statement.setInt(2, employee.getAge());
            statement.setString(3, employee.getAddress());
            statement.setDouble(4, employee.getSalary());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE employees SET firstName=?, age=?, address=?, salary=? WHERE id=?")) {
            statement.setString(1, employee.getFirstName());
            statement.setInt(2, employee.getAge());
            statement.setString(3, employee.getAddress());
            statement.setDouble(4, employee.getSalary());
            statement.setInt(5, employee.getId());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee record updated successfully.");
            } else {
                System.out.println("Employee record not updated.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating employee record: " + e.getMessage());
        }
    }
    public void deleteEmployee(int id) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM employees WHERE id=?")) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee record deleted successfully.");
            } else {
                System.out.println("Employee record not deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting employee record: " + e.getMessage());
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM employees");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                double salary = resultSet.getDouble("salary");
                employees.add(new Employee(id,firstName, age, address, salary));
            }
        } catch (SQLException e) {
            System.out.println("Error getting employee records: " + e.getMessage());
        }
        return employees;
    }

    public List<Employee> getEmployeesByCriteria(String criteria, String value) {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employees WHERE " + criteria + "=?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, value);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                int age = resultSet.getInt("age");
                String address = resultSet.getString("address");
                double salary = resultSet.getDouble("salary");
                employees.add(new Employee(id, firstName, age, address, salary));
            }
        } catch (SQLException e) {
            System.out.println("Error getting employee records: " + e.getMessage());
        }
        return employees;
    }

}