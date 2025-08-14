package hrmanagement.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import hrmanagement.model.Employee;

public class EmployeeManager {
    private ArrayList<Employee> employees;
    private static final String FILE_PATH = "employees.dat";

    public EmployeeManager() {
        employees = new ArrayList<>();
        loadEmployees();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        saveEmployees();
    }

    public void updateEmployee(String id, Employee updatedEmployee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(id)) {
                employees.set(i, updatedEmployee);
                saveEmployees();
                return;
            }
        }
    }

    public void deleteEmployee(String id) {
        employees.removeIf(employee -> employee.getId().equals(id));
        saveEmployees();
    }

    public Employee findEmployee(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                return employee;
            }
        }
        return null;
    }

    public ArrayList<Employee> getAllEmployees() {
        return employees;
    }

    public void sortEmployeesByName() {
        Collections.sort(employees, Comparator.comparing(Employee::getName));
    }

    @SuppressWarnings("unchecked")
    private void loadEmployees() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            employees = (ArrayList<Employee>) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, start with an empty list
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveEmployees() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
