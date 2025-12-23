import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
public class EmployeeEditDialog extends JDialog{
    private Employee employee;
    private MainFrame mainFrame;
    private boolean isNewEmployee;

    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField middleNameField;
    private JTextField phoneField;
    private JTextField positionField;
    private JTextField departmentField;
    private JTextField salaryField;
    private JTextField hireDateField;
    private JTextField emailField;
    private JTextArea addressArea;

    public EmployeeEditDialog(Employee employee, MainFrame mainFrame) {
        super((Frame) null, true);
        this.employee = employee;
        this.mainFrame = mainFrame;
        this.isNewEmployee = (employee == null);

        setTitle(isNewEmployee ? "Добавить сотрудника" : "Редактировать сотрудника");
        setSize(500, 600);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        int row = 0;
        addFormField(formPanel, gbc, "Фамилия*:", lastNameField = new JTextField(), row++);
        addFormField(formPanel, gbc, "Имя*:", firstNameField = new JTextField(), row++);
        addFormField(formPanel, gbc, "Отчество:", middleNameField = new JTextField(), row++);
        addFormField(formPanel, gbc, "Телефон:", phoneField = new JTextField(), row++);
        addFormField(formPanel, gbc, "Должность*:", positionField = new JTextField(), row++);
        addFormField(formPanel, gbc, "Отдел:", departmentField = new JTextField(), row++);
        addFormField(formPanel, gbc, "Зарплата:", salaryField = new JTextField(), row++);
        addFormField(formPanel, gbc, "Дата найма (ГГГГ-ММ-ДД):", hireDateField = new JTextField(), row++);
        addFormField(formPanel, gbc, "Email:", emailField = new JTextField(), row++);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Адрес:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        addressArea = new JTextArea(4, 20);
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(addressArea);
        formPanel.add(scrollPane, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(e -> dispose());

        JButton saveButton = new JButton(isNewEmployee ? "Добавить" : "Сохранить");
        saveButton.addActionListener(e -> saveEmployee());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        if (!isNewEmployee) {
            fillFormFields();
        }
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc,
                              String label, JTextField field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
    }

    private void fillFormFields() {
        lastNameField.setText(employee.getLastName());
        firstNameField.setText(employee.getFirstName());
        middleNameField.setText(employee.getMiddleName());
        phoneField.setText(employee.getPhone());
        positionField.setText(employee.getPosition());
        departmentField.setText(employee.getDepartment());
        salaryField.setText(employee.getSalary() > 0 ? String.valueOf(employee.getSalary()) : "");
        hireDateField.setText(employee.getHireDate());
        emailField.setText(employee.getEmail());
        addressArea.setText(employee.getAddress());
    }

    private void saveEmployee() {
        if (lastNameField.getText().trim().isEmpty() ||
                firstNameField.getText().trim().isEmpty() ||
                positionField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Пожалуйста, заполните обязательные поля (Фамилия, Имя, Должность)",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isNewEmployee) {
            Employee empToSave = new Employee(
                    lastNameField.getText().trim(),
                    firstNameField.getText().trim(),
                    middleNameField.getText().trim(),
                    phoneField.getText().trim(),
                    positionField.getText().trim(),
                    departmentField.getText().trim(),
                    parseDouble(salaryField.getText().trim()),
                    hireDateField.getText().trim(),
                    emailField.getText().trim(),
                    addressArea.getText().trim()
            );
            DatabaseHelper.addEmployee(empToSave);
        } else {
            employee.setLastName(lastNameField.getText().trim());
            employee.setFirstName(firstNameField.getText().trim());
            employee.setMiddleName(middleNameField.getText().trim());
            employee.setPhone(phoneField.getText().trim());
            employee.setPosition(positionField.getText().trim());
            employee.setDepartment(departmentField.getText().trim());
            employee.setSalary(parseDouble(salaryField.getText().trim()));
            employee.setHireDate(hireDateField.getText().trim());
            employee.setEmail(emailField.getText().trim());
            employee.setAddress(addressArea.getText().trim());

            DatabaseHelper.updateEmployee(employee);
        }

        mainFrame.refreshEmployeeList();
        dispose();

        JOptionPane.showMessageDialog(mainFrame,
                isNewEmployee ? "Сотрудник успешно добавлен" : "Данные сотрудника обновлены",
                "Успех",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
