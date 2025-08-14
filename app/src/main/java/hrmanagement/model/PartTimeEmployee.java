package hrmanagement.model;

public class PartTimeEmployee extends Employee implements Payable {
    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String id, String name, int age, String phone, String email, double hourlyRate, int hoursWorked) {
        super(id, name, age, phone, email);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public String toString() {
        return "PartTimeEmployee [" + super.toString() + ", hourlyRate=" + hourlyRate + ", hoursWorked=" + hoursWorked + "]";
    }
}
