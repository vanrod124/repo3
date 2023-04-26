import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class MainController {

    private MainView view;
    private List<Customer> customers;
    private List<Product> products;
    private ProductTableModel productTableModel;
    private FileManager fileManager;

    public MainController() {
        customers = new ArrayList<>();
        products = new ArrayList<>();
        productTableModel = new ProductTableModel(products);
        fileManager = new FileManager();
        loadProductsFromFile("products.txt");
        view = new MainView(this);
    }

    public void setView(MainView view) {
        this.view = view;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addCustomer(Customer newCustomer) {
        customers.add(newCustomer);
    }

    public void updateCustomer(int rowIndex, Customer updatedCustomer) {
        customers.set(rowIndex, updatedCustomer);
    }

    public void deleteCustomer(Customer customer) {
        customers.remove(customer);
    }

    public Customer findCustomerByName(String name) {
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }

    public Customer getMostValuableCustomer() {
        Customer mostValuable = null;
        int maxPoints = 0;

        for (Customer customer : customers) {
            if (customer.getLoyaltyPoints() > maxPoints) {
                maxPoints = customer.getLoyaltyPoints();
                mostValuable = customer;
            }
        }

        return mostValuable;
    }

    public void addProduct(Product newProduct) {
        products.add(newProduct);
        productTableModel.fireTableDataChanged();
        saveProductsToFile("products.txt");
    }
    

    public void updateProduct(int rowIndex, Product updatedProduct) {
        products.set(rowIndex, updatedProduct);
        productTableModel.fireTableDataChanged();
        saveProductsToFile("products.txt");
    }

    public void deleteProduct(Product product) {
        products.remove(product);
        productTableModel.fireTableDataChanged();
        saveProductsToFile("products.txt");
    }

    public Product findProductByName(String name) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
    
    public ProductTableModel getProductTableModel() {
        return productTableModel;
    }
    
    private void loadProductsFromFile(String filename) {
        ArrayList<String> data = fileManager.loadFromFile(filename);
        for (String line : data) {
            String[] fields = line.split(",");
            if (fields.length == 3) {
                String name = fields[0];
                double price = Double.parseDouble(fields[1]);
                int quantity = Integer.parseInt(fields[2]);
                Product product = new Product(name, price, quantity);
                products.add(product);
            }
        }
        productTableModel.fireTableDataChanged();
    }
    
    private void saveProductsToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Product product : products) {
                writer.println(product.getName() + "," + product.getPrice() + "," + product.getQuantity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
