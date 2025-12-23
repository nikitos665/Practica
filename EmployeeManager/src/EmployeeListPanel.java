import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
public class EmployeeListPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel employeesPanel;

    public EmployeeListPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JLabel titleLabel = new JLabel("Список сотрудников");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton addButton = new JButton("+ Добавить сотрудника");
        addButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addButton.addActionListener(e -> showAddEmployeeDialog());
        headerPanel.add(addButton, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        employeesPanel = new JPanel();
        employeesPanel.setLayout(new BoxLayout(employeesPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(employeesPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        refreshEmployeeList();
    }

    public void refreshEmployeeList() {
        employeesPanel.removeAll();

        List<Employee> employees = DatabaseHelper.getAllEmployees();

        if (employees.isEmpty()) {
            JLabel emptyLabel = new JLabel("Сотрудников нет. Добавьте первого сотрудника.");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 14));
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setBorder(new EmptyBorder(50, 0, 0, 0));
            employeesPanel.add(emptyLabel);
        } else {
            for (Employee employee : employees) {
                employeesPanel.add(createEmployeeCard(employee));
                employeesPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        employeesPanel.revalidate();
        employeesPanel.repaint();
    }

    private JPanel createEmployeeCard(Employee employee) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1),
                new EmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(employee.getFullName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel positionLabel = new JLabel(employee.getPosition() +
                (employee.getDepartment() != null ? " • " + employee.getDepartment() : ""));
        positionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        positionLabel.setForeground(Color.GRAY);

        infoPanel.add(nameLabel);
        infoPanel.add(positionLabel);

        JPanel contactPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        contactPanel.setOpaque(false);

        if (employee.getPhone() != null && !employee.getPhone().isEmpty()) {
            JLabel phoneLabel = new JLabel("Телефон: " + employee.getPhone());
            phoneLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            contactPanel.add(phoneLabel);
        }

        infoPanel.add(contactPanel);
        card.add(infoPanel, BorderLayout.CENTER);

        JButton viewButton = new JButton("Подробнее");
        viewButton.addActionListener(e -> mainFrame.showEmployeeDetail(employee.getId()));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(viewButton);
        card.add(buttonPanel, BorderLayout.EAST);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainFrame.showEmployeeDetail(employee.getId());
            }
        });

        return card;
    }

    private void showAddEmployeeDialog() {
        EmployeeEditDialog dialog = new EmployeeEditDialog(null, mainFrame);
        dialog.setVisible(true);
    }

}
