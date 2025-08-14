package hrmanagement.model;

public class FullTimeEmployee extends Employee implements Payable {
    private double salary;

    public FullTimeEmployee(String id, String name, int age, String phone, String email, double salary) {
        super(id, name, age, phone, email);
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public double calculateSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "FullTimeEmployee [" + super.toString() + ", salary=" + salary + "]";
    }
}
