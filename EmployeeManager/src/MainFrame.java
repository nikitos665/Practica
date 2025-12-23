import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private EmployeeListPanel listPanel;
    private EmployeeDetailPanel detailPanel;

    public MainFrame() {
        setTitle("Учет сотрудников");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        listPanel = new EmployeeListPanel(this);
        detailPanel = new EmployeeDetailPanel(this);

        contentPanel.add(listPanel, "LIST");
        contentPanel.add(detailPanel, "DETAIL");

        add(contentPanel);
        showEmployeeList();
    }

    public void showEmployeeList() {
        listPanel.refreshEmployeeList();
        cardLayout.show(contentPanel, "LIST");
    }

    public void showEmployeeDetail(int employeeId) {
        detailPanel.loadEmployee(employeeId);
        cardLayout.show(contentPanel, "DETAIL");
    }

    public void refreshEmployeeList() {
        listPanel.refreshEmployeeList();
    }
}
