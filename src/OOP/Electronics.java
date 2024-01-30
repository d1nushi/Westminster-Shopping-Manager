package OOP;

import java.io.Serializable;

public class Electronics extends Product implements Serializable {
    private String brand;
    private int warrantyPeriod;

    //Constructor


    public Electronics(String productId, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productId, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }
    public Electronics(Electronics other) {
        super(other);
        this.brand = other.brand;
        this.warrantyPeriod = other.warrantyPeriod;
    }

    //Getter setter for brand

    public String getBrand() {

        return brand;
    }

    public void setBrand(String brand) {

        this.brand = brand;
    }

    //Getter setter for warranty

    public int getWarrantyPeriod() {

        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {

        this.warrantyPeriod = warrantyPeriod;
    }
    public void getCategoryString(){
        System.out.println("Clothing");
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Brand: " + getBrand() +
                ", Warranty Period: " + getWarrantyPeriod() + " months";
    }
}
