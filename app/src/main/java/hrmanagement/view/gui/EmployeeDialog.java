package hrmanagement.view.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hrmanagement.model.Employee;
import hrmanagement.model.FullTimeEmployee;
import hrmanagement.model.PartTimeEmployee;

public class EmployeeDialog extends JDialog {
    private final JTextField idField = new JTextField(10);
    private final JTextField nameField = new JTextField(10);
    private final JTextField ageField = new JTextField(10);
    private final JTextField phoneField = new JTextField(10);
    private final JTextField emailField = new JTextField(10);
    private final JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Full-Time", "Part-Time"});
    private final JTextField salaryField = new JTextField(10);
    private final JTextField hourlyRateField = new JTextField(10);
    private final JTextField hoursWorkedField = new JTextField(10);

    private final JLabel salaryLabel = new JLabel("Salary:");
    private final JLabel hourlyRateLabel = new JLabel("Hourly Rate:");
    private final JLabel hoursWorkedLabel = new JLabel("Hours Worked:");

    private Employee employee = null;

    public EmployeeDialog(Frame owner, String title, Employee existingEmployee) {
        super(owner, title, true);
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(owner);

        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        fieldsPanel.add(new JLabel("ID:"));
        fieldsPanel.add(idField);
        fieldsPanel.add(new JLabel("Name:"));
        fieldsPanel.add(nameField);
        fieldsPanel.add(new JLabel("Age:"));
        fieldsPanel.add(ageField);
        fieldsPanel.add(new JLabel("Phone:"));
        fieldsPanel.add(phoneField);
        fieldsPanel.add(new JLabel("Email:"));
        fieldsPanel.add(emailField);
        fieldsPanel.add(new JLabel("Type:"));
        fieldsPanel.add(typeComboBox);
        fieldsPanel.add(salaryLabel);
        fieldsPanel.add(salaryField);
        fieldsPanel.add(hourlyRateLabel);
        fieldsPanel.add(hourlyRateField);
        fieldsPanel.add(hoursWorkedLabel);
        fieldsPanel.add(hoursWorkedField);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        typeComboBox.addActionListener(e -> toggleFields());
        okButton.addActionListener(e -> saveEmployee());
        cancelButton.addActionListener(e -> setVisible(false));

        if (existingEmployee != null) {
            populateFields(existingEmployee);
        }
        toggleFields();
    }

    private void toggleFields() {
        boolean isFullTime = "Full-Time".equals(typeComboBox.getSelectedItem());
        salaryLabel.setVisible(isFullTime);
        salaryField.setVisible(isFullTime);
        hourlyRateLabel.setVisible(!isFullTime);
        hourlyRateField.setVisible(!isFullTime);
        hoursWorkedLabel.setVisible(!isFullTime);
        hoursWorkedField.setVisible(!isFullTime);
    }

    private void populateFields(Employee emp) {
        idField.setText(emp.getId());
        idField.setEditable(false); // ID is not editable
        nameField.setText(emp.getName());
        ageField.setText(String.valueOf(emp.getAge()));
        phoneField.setText(emp.getPhone());
        emailField.setText(emp.getEmail());

        if (emp instanceof FullTimeEmployee) {
            typeComboBox.setSelectedItem("Full-Time");
            salaryField.setText(String.valueOf(((FullTimeEmployee) emp).getSalary()));
        } else if (emp instanceof PartTimeEmployee) {
            typeComboBox.setSelectedItem("Part-Time");
            hourlyRateField.setText(String.valueOf(((PartTimeEmployee) emp).getHourlyRate()));
            hoursWorkedField.setText(String.valueOf(((PartTimeEmployee) emp).getHoursWorked()));
        }
    }

    private void saveEmployee() {
        try {
            String id = idField.getText();
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String phone = phoneField.getText();
            String email = emailField.getText();

            if ("Full-Time".equals(typeComboBox.getSelectedItem())) {
                double salary = Double.parseDouble(salaryField.getText());
                employee = new FullTimeEmployee(id, name, age, phone, email, salary);
            } else {
                double hourlyRate = Double.parseDouble(hourlyRateField.getText());
                int hoursWorked = Integer.parseInt(hoursWorkedField.getText());
                employee = new PartTimeEmployee(id, name, age, phone, email, hourlyRate, hoursWorked);
            }
            setVisible(false);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for age and salary/rate/hours.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Employee getEmployee() {
        return employee;
    }
}
