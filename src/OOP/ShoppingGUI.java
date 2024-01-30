package OOP;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ShoppingGUI {
    public static JFrame frame;
    private static DefaultTableModel tableModel;
    private static JPanel productInfoPanel;


    public static void openGUI(List<Product>products) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //JLabel
                JLabel label = new JLabel();
                label.setText("Select Product Category");

                frame = new JFrame(" Westminster Shopping Centre");
                frame.setSize(900, 600);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                frame.setLayout(new FlowLayout());
                frame.add(label);

                // Add the drop-down menu
                String[] productTypes = {"All", "Electronics", "Clothing"};
                JComboBox<String> productTypeComboBox = new JComboBox<>(productTypes);
                productTypeComboBox.addActionListener(event ->filterProducts(products, productTypeComboBox.getSelectedItem().toString()));
                frame.add(productTypeComboBox,BorderLayout.NORTH);

                // Add a button to view the shopping cart
                JButton shoppingCartButton = new JButton("Shopping Cart");
                shoppingCartButton.addActionListener(event -> ShoppingCart.viewShoppingCart());
                frame.add(shoppingCartButton, BorderLayout.LINE_START);

                // Add a table to display product information
                tableModel = new DefaultTableModel();
                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                scrollPane.setPreferredSize(new Dimension(800, 200));
                frame.add(scrollPane, BorderLayout.CENTER);

                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                table.setDefaultRenderer(Object.class, centerRenderer);

                //Add a line separator between the table and the panel
                JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
                frame.add(separator, BorderLayout.SOUTH);
                // Add a separator
                frame.add(new JSeparator(JSeparator.HORIZONTAL));


                //Add a panel for product info
                productInfoPanel = new JPanel();
                productInfoPanel.setLayout(new BoxLayout(productInfoPanel, BoxLayout.Y_AXIS));
                frame.add(productInfoPanel, BorderLayout.SOUTH);

                // Add a listener to detect selection changes in the table
                table.getSelectionModel().addListSelectionListener(event -> {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        showProductDetails(products.get(selectedRow));
                    }
                });

                updateTable(products);

                frame.setVisible(true);
            }
        });
    }

    private static void showProductDetails(Product product) {
        // Clear existing content
        productInfoPanel.removeAll();

        // Add new details
        JLabel productDetailLabel = new JLabel("Selected Product - Details");
        JLabel productIdLabel = new JLabel("Product ID: " + product.getProductId());
        JLabel categoryLabel = new JLabel("Category: " + getCategoryString(product));
        JLabel productNameLabel = new JLabel("Product Name: " + product.getProductName());
        JLabel priceLabel = new JLabel("Price(£): " + product.getPrice());
        JButton addToShoppingCartButton = new JButton("Add to Shopping Cart");
        addToShoppingCartButton.addActionListener(event -> ShoppingCart.addToShoppingCart(product));


        productInfoPanel.add(productDetailLabel);
        productInfoPanel.add(productIdLabel);
        productInfoPanel.add(categoryLabel);
        productInfoPanel.add(productNameLabel);
        productInfoPanel.add(priceLabel);


        if (product instanceof Electronics) {
            JLabel brandLabel = new JLabel("Brand: " + ((Electronics) product).getBrand());
            JLabel warrantyLabel = new JLabel("Warranty Period: " + ((Electronics) product).getWarrantyPeriod() + " months");
            JLabel availableItems = new JLabel("Items Available: " + String.valueOf(product.getAvailableItems()));
            productInfoPanel.add(brandLabel);
            productInfoPanel.add(warrantyLabel);
            productInfoPanel.add(availableItems);
            productInfoPanel.add(addToShoppingCartButton);


        } else if (product instanceof Clothing) {
            JLabel sizeLabel = new JLabel("Size: " + ((Clothing) product).getSize());
            JLabel colorLabel = new JLabel("Color: " + ((Clothing) product).getColour());
            JLabel availableItems = new JLabel("Items Available: " +  String.valueOf(product.getAvailableItems()));
            productInfoPanel.add(sizeLabel);
            productInfoPanel.add(colorLabel);
            productInfoPanel.add(availableItems);
            productInfoPanel.add(addToShoppingCartButton);

        }

        // Repaint the panel
        productInfoPanel.revalidate();
        productInfoPanel.repaint();
    }



    public static String getProductInfo(Product product) {
        if (product instanceof Electronics) {
            Electronics electronicsProduct = (Electronics) product;
            return "<html>" +
                    electronicsProduct.getProductId() + "<br>" +
                    electronicsProduct.getProductName() + "<br>" +
                    electronicsProduct.getBrand() + "<br>" +
                    electronicsProduct.getWarrantyPeriod() +
                    "<html>";

        }else {
            Clothing clothingProduct = (Clothing) product;
            return "<html>" +
                    clothingProduct.getProductId() + "<br>" +
                    clothingProduct.getProductName() + "<br>" +
                    clothingProduct.getSize() + "<br>" +
                    clothingProduct.getColour() +
                    "<html>";

        }
    }
    private static void updateTable(List<Product> products){
        tableModel.setColumnIdentifiers(new String[]{"Product ID", "Name", "Category", "Price(£)", "Info"});
        tableModel.setRowCount(0);
        for(Product product : products){
            Vector<String> rowData = new Vector<>();
            rowData.add(product.getProductId());
            rowData.add(product.getProductName());
            rowData.add(getCategoryString(product));
            rowData.add(String.valueOf(product.getPrice()));
            if (product instanceof Electronics){
                String brand = ((Electronics) product).getBrand();
                int warrantyPeriod = ((Electronics)product).getWarrantyPeriod();
                rowData.add(brand+", \n" + warrantyPeriod + "months warranty");
            }else if(product instanceof Clothing){
                String size = ((Clothing)product).getSize();
                String color = ((Clothing)product).getColour();
                rowData.add(size+", " + color);
            }
            tableModel.addRow(rowData);
            if (product.getAvailableItems()<3) {
                for (int i = 0; i < rowData.size(); i++) {
                    rowData.set(i, "<html><font color = 'red'>" + rowData.get(i) + "</font></html>");
                }
            }
        }
    }
    private static String getCategoryString(Product product){
        if (product instanceof Electronics){
            return "Electronics";
        } else{
            return "Clothing";
        }
    }
    private static void filterProducts(List<Product> products, String category){
        List<Product> filteredProducts;

        switch(category){
            case "Electronics":
                filteredProducts = ProductFilter.filterElectronics(products);
                break;
            case "Clothing":
                filteredProducts = ProductFilter.filterClothing(products);
                break;
            default:
                filteredProducts = products;
        }
        updateTable(filteredProducts);

        //check if a row is selected in the table
        int selectedRow = frame.getFocusOwner() instanceof JTable?
                ((JTable)frame.getFocusOwner()).getSelectedRow():-1;

        //show details if selected
        if (selectedRow >= 0 && selectedRow < filteredProducts.size()) {
            showProductDetails(filteredProducts.get(selectedRow));
        }
    }


    public class ProductFilter{
        public static List<Product> filterElectronics(List<Product> products){
            List<Product> electronics = new ArrayList<>();
            for(Product product : products){
                if(product instanceof Electronics){
                    electronics.add(product);
                }
            }
            return electronics;
        }

        public static List<Product> filterClothing(List<Product> products){
            List<Product> clothing = new ArrayList<>();
            for(Product product : products){
                if(product instanceof Clothing){
                    clothing.add(product);
                }
            }
            return clothing;
        }

    }


}







