import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
public class EmployeeDetailPanel  extends JPanel {
    private MainFrame mainFrame;
    private Employee currentEmployee;

    private JLabel nameLabel;
    private JLabel positionLabel;
    private JLabel departmentLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel salaryLabel;
    private JLabel hireDateLabel;
    private JLabel addressLabel;

    public EmployeeDetailPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JButton backButton = new JButton("← Назад");
        backButton.addActionListener(e -> mainFrame.showEmployeeList());
        headerPanel.add(backButton, BorderLayout.WEST);

        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(nameLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(new CompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 15, 20);

        addInfoField(infoPanel, gbc, "Должность:", positionLabel = new JLabel(), 0);
        addInfoField(infoPanel, gbc, "Отдел:", departmentLabel = new JLabel(), 1);
        addInfoField(infoPanel, gbc, "Телефон:", phoneLabel = new JLabel(), 2);
        addInfoField(infoPanel, gbc, "Email:", emailLabel = new JLabel(), 3);
        addInfoField(infoPanel, gbc, "Зарплата:", salaryLabel = new JLabel(), 4);
        addInfoField(infoPanel, gbc, "Дата найма:", hireDateLabel = new JLabel(), 5);
        addInfoField(infoPanel, gbc, "Адрес:", addressLabel = new JLabel(), 6);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 15, 20);
        infoPanel.add(addressLabel, gbc);

        add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton editButton = new JButton("Редактировать");
        editButton.addActionListener(e -> editEmployee());

        JButton deleteButton = new JButton("Удалить");
        deleteButton.setForeground(Color.RED);
        deleteButton.addActionListener(e -> deleteEmployee());

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addInfoField(JPanel panel, GridBagConstraints gbc,
                              String labelText, JLabel valueLabel, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        valueLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(valueLabel, gbc);
    }

    public void loadEmployee(int employeeId) {
        currentEmployee = DatabaseHelper.getEmployeeById(employeeId);

        if (currentEmployee != null) {
            nameLabel.setText(currentEmployee.getFullName());
            positionLabel.setText(currentEmployee.getPosition() != null ?
                    currentEmployee.getPosition() : "Не указано");
            departmentLabel.setText(currentEmployee.getDepartment() != null ?
                    currentEmployee.getDepartment() : "Не указано");
            phoneLabel.setText(currentEmployee.getPhone() != null ?
                    currentEmployee.getPhone() : "Не указано");
            emailLabel.setText(currentEmployee.getEmail() != null ?
                    currentEmployee.getEmail() : "Не указано");
            salaryLabel.setText(currentEmployee.getSalary() > 0 ?
                    String.format("%,.2f ₽", currentEmployee.getSalary()) : "Не указано");
            hireDateLabel.setText(currentEmployee.getHireDate() != null ?
                    currentEmployee.getHireDate() : "Не указано");
            addressLabel.setText(currentEmployee.getAddress() != null ?
                    currentEmployee.getAddress() : "Не указано");
        }
    }

    private void editEmployee() {
        if (currentEmployee != null) {
            EmployeeEditDialog dialog = new EmployeeEditDialog(currentEmployee, mainFrame);
            dialog.setVisible(true);
        }
    }

    private void deleteEmployee() {
        if (currentEmployee != null) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Вы уверены, что хотите удалить сотрудника " + currentEmployee.getFullName() + "?",
                    "Подтверждение удаления",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                DatabaseHelper.deleteEmployee(currentEmployee.getId());
                mainFrame.showEmployeeList();
                JOptionPane.showMessageDialog(this,
                        "Сотрудник успешно удален",
                        "Успех",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

}
