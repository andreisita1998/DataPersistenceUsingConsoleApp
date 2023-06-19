import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DatabaseManager dbManager = new DatabaseManager();

    public static void main(String[] args) {
        System.out.println("Welcome to Employee Management System");

        boolean quit = false;
        while (!quit) {
            System.out.println("Please select an option:");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. View All Employees");
            System.out.println("5. Search Employee by Criteria");
            System.out.println("6. Quit");

            int option = getIntInput();
            switch (option) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    updateEmployee();
                    break;
                case 4:
                    viewAllEmployees();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 5:
                    searchEmployeeByCriteria();
                    break;
                case 6:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        dbManager.closeConnection();
        System.out.println("Thank you for using Employee Management System.");
    }

    private static void addEmployee() {
        System.out.println("Add Employee:");
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Age: ");
        int age = getIntInput();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Salary: ");
        double salary = getDoubleInput();
        Employee employee = new Employee(firstName, age, address, salary);
        dbManager.addEmployee(employee);
        System.out.println("Employee added successfully.");
    }

    private static void updateEmployee() {
        System.out.println("Update Employee:");
        System.out.print("Enter ID of employee to update: ");
        int id = getIntInput();
        List<Employee> employees = dbManager.getEmployeesByCriteria("id", Integer.toString(id));
        if (employees.isEmpty()) {
            System.out.println("Employee not found.");
        } else {
            Employee employee = employees.get(0);
            System.out.print("New First Name (leave empty to keep existing): ");
            String firstName = scanner.nextLine();
            if (!firstName.isEmpty()) {
                employee.setFirstName(firstName);
            }
            System.out.print("New Age (leave empty to keep existing): ");
            String ageStr = scanner.nextLine();
            if (!ageStr.isEmpty()) {
                int age = Integer.parseInt(ageStr);
                employee.setAge(age);
            }
            System.out.print("New Address (leave empty to keep existing): ");
            String address = scanner.nextLine();
            if (!address.isEmpty()) {
                employee.setAddress(address);
            }
            System.out.print("New Salary (leave empty to keep existing): ");
            String salaryStr = scanner.nextLine();
            if (!salaryStr.isEmpty()) {
                double salary = Double.parseDouble(salaryStr);
                employee.setSalary(salary);
            }
            dbManager.updateEmployee(employee);
            System.out.println("Employee updated successfully.");
            System.out.println("Updated record:");
            List<Employee> updatedEmployees = dbManager.getEmployeesByCriteria("id", Integer.toString(id));
            Employee updatedEmployee = updatedEmployees.get(0);
            System.out.format("%-4d%-10s%-30s%.2f\n", updatedEmployee.getId(),
                    updatedEmployee.getFirstName(), updatedEmployee.getAddress(), updatedEmployee.getSalary());
        }
    }



    private static void viewAllEmployees() {
        List<Employee> employees = dbManager.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("All Employees:");
            for (Employee employee : employees) {
                System.out.println(employee.getId() + "\t" + employee.getFirstName() + "\t" + employee.getAge() + "\t" + employee.getAddress() + "\t" + employee.getSalary());
            }
        }
    }

    private static void searchEmployeeByCriteria() {
        System.out.println("Search Employee by Criteria:");
        System.out.println("Please select a search criteria:");
        System.out.println("1. First Name");
        System.out.println("2. Age");
        System.out.println("3. Address");
        System.out.println("4. Salary");
        int option = getIntInput();
        String criteria = "";
        switch (option) {
            case 1:
                System.out.print("Enter First Name: ");
                criteria = scanner.nextLine();
                break;
            case 2:
                System.out.print("Enter Age: ");
                criteria = Integer.toString(getIntInput());
                break;
            case 3:
                System.out.print("Enter Address: ");
                criteria = scanner.nextLine();
                break;
            case 4:
                System.out.print("Enter Salary: ");
                criteria = Double.toString(getDoubleInput());
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }
        List<Employee> employees = dbManager.getEmployeesByCriteria(getSearchCriteria(option), criteria);
        if (employees.isEmpty()) {
            System.out.println("No employees found for the given criteria.");
        } else {
            System.out.println("Employees Found:");
            for (Employee employee : employees) {
                System.out.println(employee.toString());
            }
        }
    }

    private static int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter an integer: ");
            }
        }
    }

    private static double getDoubleInput() {
        while (true) {
            try {
                String input = scanner.nextLine();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a decimal number: ");
            }
        }
    }

    private static String getSearchCriteria(int option) {
        switch (option) {
            case 1:
                return "firstName";
            case 2:
                return "age";
            case 3:
                return "address";
            case 4:
                return "salary";
            default:
                return "";
        }
    }

    private static void deleteEmployee() {
        System.out.println("Delete Employee:");
        System.out.print("Enter ID of employee to delete: ");
        int id = getIntInput();
        dbManager.deleteEmployee(id);
    }

}