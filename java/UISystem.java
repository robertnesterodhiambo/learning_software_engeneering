import javax.swing.*;

public class UISystem {
    public static void main(String[] args) {
        // Create JFrame
        JFrame frame = new JFrame("University Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);

        // Add Student panel
        JPanel addStudentPanel = new JPanel();
        addStudentPanel.setBounds(20, 20, 350, 200);
        addStudentPanel.setLayout(null);
        addStudentPanel.setBorder(BorderFactory.createTitledBorder("Add Student"));

        // Add Student labels and text fields
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(30, 30, 100, 20);
        addStudentPanel.add(firstNameLabel);

        JTextField firstNameTextField = new JTextField();
        firstNameTextField.setBounds(140, 30, 200, 20);
        addStudentPanel.add(firstNameTextField);

        // Repeat for other student attributes...

        // Add Department panel
        JPanel addDepartmentPanel = new JPanel();
        addDepartmentPanel.setBounds(20, 230, 350, 100);
        addDepartmentPanel.setLayout(null);
        addDepartmentPanel.setBorder(BorderFactory.createTitledBorder("Add Department"));

        // Add Department labels and text fields
        JLabel departmentNameLabel = new JLabel("Department Name:");
        departmentNameLabel.setBounds(30, 30, 150, 20);
        addDepartmentPanel.add(departmentNameLabel);

        JTextField departmentNameTextField = new JTextField();
        departmentNameTextField.setBounds(180, 30, 160, 20);
        addDepartmentPanel.add(departmentNameTextField);

        // Repeat for other department attributes...

        // Add panels to frame
        frame.add(addStudentPanel);
        frame.add(addDepartmentPanel);

        // Set frame visibility
        frame.setVisible(true);
    }
}
