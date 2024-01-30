package OOP;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class ShoppingCart {
    private List<Product> products;

    private boolean firstPurchase;
    private static ShoppingCart shoppingCart;
    private static JTable cartTable;
    private Map<String,Integer> categoryCount;

    public ShoppingCart(){
        this.products= new ArrayList<>();

        this.firstPurchase = true;
        this.categoryCount = new HashMap<>();
    }
    public static void viewShoppingCart() {
        if (shoppingCart == null || shoppingCart.getProducts().isEmpty()) {
            JOptionPane.showMessageDialog(ShoppingGUI.frame, "cart empty");
            return;
        }

        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setSize(600,600);
        shoppingCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shoppingCartFrame.setResizable(false);

        //Create a panel to centre the table
        JPanel centralPane = new JPanel(new FlowLayout(FlowLayout.CENTER));

        //Create a table to display cart
        JTable cartTable = new JTable();
        DefaultTableModel cartTableModel = new DefaultTableModel();
        cartTableModel.setColumnIdentifiers(new String[]{"Product", "Quantity", "Price(£)"});
        cartTable.setModel(cartTableModel);
        cartTable.setRowHeight(100);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        cartTable.setDefaultRenderer(Object.class, centerRenderer);

        //Populate the table with items from cart
        List<Product> cartItems = shoppingCart.getProducts();
        double totalPrice = 0.0;
        double totalDiscount = 0.0;
        Map<String, Integer> categoryCount = new HashMap<>();

        for (Product item : cartItems) {
            Vector<String> rowData = new Vector<>();
            rowData.add(ShoppingGUI.getProductInfo(item));
            rowData.add(String.valueOf(item.getQuantity()));

            double itemTotalPrice = item.getPrice() * item.getQuantity();
            rowData.add(String.valueOf(itemTotalPrice));

            totalPrice += itemTotalPrice;
            cartTableModel.addRow(rowData);

            //Update category count
            String category = getCategoryString(item);
            categoryCount.put(category, categoryCount.getOrDefault(category,0)+ item.getQuantity());
        }

        //Apply the discounts
        for (Product item : cartItems) {
            String category = getCategoryString(item);
            int itemCount = categoryCount.getOrDefault(category,0);
            if (itemCount >= 3) {
                double discount = 0.20;
                double itemTotalPrice = item.getPrice() * item.getQuantity();
                double itemDiscount = itemTotalPrice * discount;
                item.setTotalPrice(itemTotalPrice - itemDiscount);
                totalDiscount += itemDiscount;
            }
        }

        // Calculate final price
        double firstPurchaseDiscount = shoppingCart.isFirstPurchase() ? 0.10 : 0.0;
        double discountedTotalPrice = totalPrice - totalDiscount;
        double finalPrice = discountedTotalPrice - (discountedTotalPrice * firstPurchaseDiscount);

        // Create a panel for price and discount labels
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));

        //Display discounts and final price
        JLabel totalPriceLabel = new JLabel("Total: " + totalPrice + "£");
        JLabel discountsLabel = new JLabel("Discounts:");
        JLabel firstPurchaseDiscountLabel = new JLabel("First Purchase Discount(10%): " + (firstPurchaseDiscount * totalPrice) + "£");
        JLabel threeItemsSameCategoryDiscountLabel = new JLabel("Three Items in Same Category Discount(20%): " + totalDiscount + "£");
        JLabel finalPriceLabel = new JLabel("Final Price: " + finalPrice + "£");

        pricePanel.add(totalPriceLabel);
        pricePanel.add(new JLabel("")); // Add an empty label for spacing
        pricePanel.add(discountsLabel);
        pricePanel.add(firstPurchaseDiscountLabel);
        pricePanel.add(threeItemsSameCategoryDiscountLabel);
        pricePanel.add(finalPriceLabel);

        //Add the table to the centralPanel
        centralPane.add(new JScrollPane(cartTable));

        //Add the centralPanel and pricePanel to the frame
        shoppingCartFrame.setLayout(new BorderLayout());
        shoppingCartFrame.add(centralPane, BorderLayout.CENTER);
        shoppingCartFrame.add(pricePanel, BorderLayout.SOUTH);

        shoppingCartFrame.setVisible(true);
    }

    private static void updateShoppingCartTable() {
        if (cartTable == null) {
            System.out.println("cartTable is null"); // debugging
            return;
        }

        DefaultTableModel cartTableModel = (DefaultTableModel) cartTable.getModel();
        cartTableModel.setRowCount(0);

        List<Product> cartItems = shoppingCart.getProducts();
        for (Product item : cartItems) {
            Vector<String> rowData = new Vector<>();
            rowData.add(ShoppingGUI.getProductInfo(item));
            rowData.add(String.valueOf(item.getQuantity()));
            rowData.add(String.valueOf(item.getTotalPrice()));
            cartTableModel.addRow(rowData);
        }
    }

    public static void addToShoppingCart(Product product){
        if(shoppingCart == null){
            shoppingCart = new ShoppingCart();
        }

        // Prompt user for quantity
        String quantityStr = JOptionPane.showInputDialog(ShoppingGUI.frame, "Enter quantity:", "Quantity", JOptionPane.PLAIN_MESSAGE);
        if (quantityStr == null || quantityStr.isEmpty()) {

            return;
        }

        try {
            int quantity = Integer.parseInt(quantityStr);
            shoppingCart.addProduct(product, quantity);
            JOptionPane.showMessageDialog(ShoppingGUI.frame, "Added to cart");
            updateShoppingCartTable();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(ShoppingGUI.frame, "Invalid quantity", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    public void addProduct(Product product, int quantity){
        //Check if the product already in the cart
        for(Product cartProduct : products){
            if(cartProduct.getProductId().equals(product.getProductId())){
                cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
                cartProduct.setTotalPrice(cartProduct.getPrice() + cartProduct.getQuantity());
                return;
            }
        }
        //Product not in cart, add new item with quantity
        Product newCartItem;
        if (product instanceof Electronics){
            newCartItem = new Electronics((Electronics) product);
        }else {
            newCartItem = new Clothing((Clothing) product);
        }
        newCartItem.setQuantity(quantity);
        products.add(newCartItem);
        updateCategoryCount(product);
    }

    public List<Product> getProducts(){

        return new ArrayList<>(products);
    }

    public boolean isFirstPurchase(){
        return firstPurchase;
    }

    private static String getCategoryString(Product product){
        if (product instanceof Electronics){
            return "Electronics";
        }else{
            return "Clothing";
        }
    }

    private void updateCategoryCount(Product product){
        String category = getCategoryString(product);
        categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
    }
}
