package OOP;

import java.io.Serializable;

public class Clothing extends Product implements Serializable {
    private String size;
    private String colour;

    //Constructor
    public Clothing(String productId, String productName, int availableItems, double price, String size, String colour) {
        super(productId, productName, availableItems, price);
        this.size = size;
        this.colour = colour;
    }
    public Clothing(Clothing other) {
        super(other);
        this.size = other.size;
        this.colour = other.colour;
    }


    //Getter setter for size

    public String getSize() {

        return size;
    }

    public void setSize(String size) {

        this.size = size;
    }

    //Getter setter for colour

    public String getColour() {

        return colour;
    }

    public void setColour(String colour) {

        this.colour = colour;
    }

    public void getCategoryString(){
        System.out.println("Clothing");
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Size: " + getSize() +
                ", Color: " + getColour();
    }
}
