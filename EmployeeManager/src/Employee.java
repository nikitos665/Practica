public class Employee {    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String phone;
    private String position;
    private String department;
    private double salary;
    private String hireDate;
    private String email;
    private String address;

    public Employee(int id, String lastName, String firstName, String middleName,
                    String phone, String position, String department, double salary,
                    String hireDate, String email, String address) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phone = phone;
        this.position = position;
        this.department = department;
        this.salary = salary;
        this.hireDate = hireDate;
        this.email = email;
        this.address = address;
    }

    public Employee(String lastName, String firstName, String middleName,
                    String phone, String position, String department, double salary,
                    String hireDate, String email, String address) {
        this(0, lastName, firstName, middleName, phone, position,
                department, salary, hireDate, email, address);
    }

    // Геттеры
    public int getId() { return id; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getPhone() { return phone; }
    public String getPosition() { return position; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public String getHireDate() { return hireDate; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }

    public String getFullName() {
        return lastName + " " + firstName + " " + (middleName != null ? middleName : "");
    }

    // Сеттеры
    public void setId(int id) { this.id = id; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPosition(String position) { this.position = position; }
    public void setDepartment(String department) { this.department = department; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setHireDate(String hireDate) { this.hireDate = hireDate; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
}
