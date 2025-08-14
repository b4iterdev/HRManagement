package hrmanagement.view.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import hrmanagement.controller.EmployeeManager;
import hrmanagement.model.Employee;
import hrmanagement.model.FullTimeEmployee;
import hrmanagement.model.PartTimeEmployee;

public class MainFrame extends JFrame {
    private final EmployeeManager manager;
    private final JTable employeeTable;
    private final DefaultTableModel tableModel;
    private final TableRowSorter<DefaultTableModel> sorter;

    public MainFrame() {
        manager = new EmployeeManager();
        setTitle("Human Resources Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Search:"));
        JTextField searchField = new JTextField(20);
        searchPanel.add(searchField);

        // Table
        String[] columnNames = {"ID", "Name", "Age", "Phone", "Email", "Type", "Salary/Rate", "Hours", "Calculated Salary"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 2: // Age
                    case 7: // Hours
                        return Integer.class;
                    case 6: // Salary/Rate
                    case 8: // Calculated Salary
                        return Double.class;
                    default:
                        return String.class;
                }
            }
        };
        employeeTable = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        employeeTable.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING)); // Sort by ID ascending
        sorter.setSortKeys(sortKeys);
        sorter.sort();


        refreshTable();

        // Buttons
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        // Layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }
            private void filter() {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        // Search in ID, Name, Phone, Email columns
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 1, 3, 4));
                    } catch (PatternSyntaxException pse) {
                        System.err.println("Bad regex pattern");
                    }
                }
            }
        });
        addButton.addActionListener(e -> addEmployee());
        updateButton.addActionListener(e -> updateEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        ArrayList<Employee> employees = manager.getAllEmployees();
        for (Employee emp : employees) {
            if (emp instanceof FullTimeEmployee ft) {
                tableModel.addRow(new Object[]{
                        ft.getId(), ft.getName(), ft.getAge(), ft.getPhone(), ft.getEmail(),
                        "Full-Time", ft.getSalary(), null, // Hours are not applicable
                        ft.calculateSalary()
                });
            } else if (emp instanceof PartTimeEmployee pt) {
                tableModel.addRow(new Object[]{
                        pt.getId(), pt.getName(), pt.getAge(), pt.getPhone(), pt.getEmail(),
                        "Part-Time", pt.getHourlyRate(), pt.getHoursWorked(), pt.calculateSalary()
                });
            }
        }
    }

    private void addEmployee() {
        EmployeeDialog dialog = new EmployeeDialog(this, "Add Employee", null);
        dialog.setVisible(true);
        if (dialog.getEmployee() != null) {
            manager.addEmployee(dialog.getEmployee());
            refreshTable();
        }
    }

    private void updateEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            Employee existingEmployee = manager.findEmployee(id);
            if (existingEmployee != null) {
                EmployeeDialog dialog = new EmployeeDialog(this, "Update Employee", existingEmployee);
                dialog.setVisible(true);
                if (dialog.getEmployee() != null) {
                    manager.updateEmployee(id, dialog.getEmployee());
                    refreshTable();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this employee?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                manager.deleteEmployee(id);
                refreshTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
}
