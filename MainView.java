import javax.swing.*;
import java.awt.*;

public class MainView {
    private MainController controller;
    private JOptionPane mainMenuDialog;

    public MainView(MainController controller) {
        this.controller = controller;
    }

    public void init() {
        while (!showLoginDialog()) {
            JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
        showMainMenu();
    }

    private boolean showLoginDialog() {
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
    
        // Create a JLabel with the image
        ImageIcon imageIcon = new ImageIcon("o.png");
        JLabel imageLabel = new JLabel(imageIcon);
    
        int result = JOptionPane.showConfirmDialog(null, panel, "Login",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, imageIcon);
                // Pass the imageLabel as the message argument
    
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
    
            String expectedUsername = "1";
            String expectedPassword = "1";
    
            return username.equals(expectedUsername) && password.equals(expectedPassword);
        }
    
        return false;
    }
    
    


    private void showMainMenu() {
        String[] options = { "Manage Products", "Manage Customers", "Loyalty Program", "Exit" };
        mainMenuDialog = new JOptionPane("Convenience Corner Inventory:", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, options, options[0]);

        JDialog dialog = mainMenuDialog.createDialog("Main Menu");
        dialog.setVisible(true);
        Object selectedValue = mainMenuDialog.getValue();

        switch ((String) selectedValue) {
            case "Manage Products":
                dialog.dispose();
                showProductManagement();
                break;
            case "Manage Customers":
                dialog.dispose();
                showCustomerManagement();
                break;
            case "Loyalty Program":
                dialog.dispose();
                showLoyaltyProgram();
                break;
            case "Exit":
                System.exit(0);
                break;
            default:
                break;
        }
    }

    private void showProductManagement() {
        JFrame productFrame = new JFrame("Product Management");
        productFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        productFrame.setSize(800, 400);
    
        String[] columnNames = { "Name", "Price", "Quantity" };
        Object[][] data = new Object[controller.getProducts().size()][3];
    
        for (int i = 0; i < controller.getProducts().size(); i++) {
            Product product = controller.getProducts().get(i);
            data[i][0] = product.getName();
            data[i][1] = product.getPrice();
            data[i][2] = product.getQuantity();
        }
    
        JTable productTable = new JTable(data, columnNames);
        productTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(productTable);
        productFrame.add(scrollPane, BorderLayout.CENTER);
    
        // Create buttons for adding, updating, and deleting products
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");
    
        // Add action listeners for buttons
        addButton.addActionListener(e -> showAddProductDialog());
        updateButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                showUpdateProductDialog(selectedRow);
            }
        });
        deleteButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow >= 0) {
                controller.deleteProduct(controller.getProducts().get(selectedRow));
            }
        });
        backButton.addActionListener(e -> {
            productFrame.dispose();
            showMainMenu();
        });
    
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        productFrame.add(buttonPanel, BorderLayout.SOUTH);
    
        productFrame.setLocationRelativeTo(null);
        productFrame.setVisible(true);
    }
    
private void showAddProductDialog() {
    JTextField nameField = new JTextField(20);
    JTextField priceField = new JTextField(20);
    JTextField quantityField = new JTextField(20);

    JPanel panel = new JPanel(new GridLayout(0, 1));
    panel.add(new JLabel("Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Price:"));
    panel.add(priceField);
    panel.add(new JLabel("Quantity:"));
    panel.add(quantityField);

    int result = JOptionPane.showConfirmDialog(null, panel, "Add Product",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        String name = nameField.getText();
        double price = Double.parseDouble(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        Product newProduct = new Product(name, price, quantity);
        controller.addProduct(newProduct);
    }
}

    
    
    private void showUpdateProductDialog(int rowIndex) {
        // ... (code for the showUpdateProductDialog method)
    }

    // ...

    private void showAddCustomerDialog() {
        JTextField nameField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField balanceField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("Balance:"));
        panel.add(balanceField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add Customer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText();
            String address = addressField.getText();
            double balance = Double.parseDouble(balanceField.getText());
            Customer newCustomer = new Customer(name, address, balance);
            controller.addCustomer(newCustomer);
        }

    }

    // ...

    private void showCustomerManagement() {
        JFrame customerFrame = new JFrame("Customer Management");
        customerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        customerFrame.setSize(800, 400);
    
        CustomerTableModel customerTableModel = new CustomerTableModel(controller.getCustomers());
        JTable customerTable = new JTable(customerTableModel);
        customerTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(customerTable);
        customerFrame.add(scrollPane, BorderLayout.CENTER);
    
        // Create buttons for adding, updating, and deleting customers
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");
    
        // Add action listeners for buttons
        addButton.addActionListener(e -> showAddCustomerDialog());
        updateButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow >= 0) {
                showUpdateCustomerDialog(selectedRow);
            }
        });
        deleteButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow >= 0) {
                controller.deleteCustomer(controller.getCustomers().get(selectedRow));
            }
        });
        backButton.addActionListener(e -> {
            customerFrame.dispose();
            showMainMenu();
        });
    
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        customerFrame.add(buttonPanel, BorderLayout.SOUTH);
    
        customerFrame.setLocationRelativeTo(null);
        customerFrame.setVisible(true);
    }
    

    private void showUpdateCustomerDialog() {
        // ... (code for the showLoyaltyProgram method)
    }

    private void hideMainMenu() {
        if (mainMenuDialog != null) {
            mainMenuDialog.setVisible(false);
        }
    }

    // ...

}
