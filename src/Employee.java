public class Employee {
    private int id;
    private String firstName;
    private int age;
    private String address;
    private double salary;

    public Employee(int id, String firstName, int age, String address, double salary) {
        this.id = id;
        this.firstName = firstName;
        this.age = age;
        this.address = address;
        this.salary = salary;
    }

    public Employee(String firstName, int age, String address, double salary) {
        this.firstName = firstName;
        this.age = age;
        this.address = address;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public Employee setId(int id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", salary=" + salary +
                '}';
    }
}