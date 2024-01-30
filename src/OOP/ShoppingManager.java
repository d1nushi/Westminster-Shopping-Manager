package OOP;


public interface ShoppingManager {
    void displayMenu();
    void addNewProduct();
    void deleteProduct();
    void printListOfProducts();
    void saveProductsToFile(String fileName);
    void loadFromFile(String fileName);

}
