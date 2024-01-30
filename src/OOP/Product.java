package OOP;

public abstract class Product {
    private String productId;
    private String productName;
    private int availableItems;
    private double price;
    private int quantity;
    private double totalPrice;

    //Constructor
    public Product(Product other) {
        this.productId = other.productId;
        this.productName = other.productName;
        this.availableItems = other.availableItems;
        this.price = other.price;
    }

    public Product(String productId, String productName, int availableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
        this.quantity = 0;
        this.totalPrice = this.price * this.quantity;
    }


    //Getter and setter for productId

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    //Getter and setter for productName

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    //Getter and setter for availableItems

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    //Getter and setter for price

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    //Getter and setter for quantity

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = this.price * this.quantity;
    }

    // Getter for total price
    public double getTotalPrice() {
        return this.totalPrice;
    }

    // Setter for total price
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public abstract void getCategoryString();

    @Override
    public String toString() {
        return "Product = " +
                "productId: " + productId +
                ", productName: " + productName +
                ", availableItems: " + availableItems +
                ", price: " + price;
    }
}
