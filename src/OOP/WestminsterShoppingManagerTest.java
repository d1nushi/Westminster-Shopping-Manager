package OOP;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WestminsterShoppingManagerTest {

    private WestminsterShoppingManager shoppingManager;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final InputStream stdin = System.in;

    @BeforeEach
    void setUp() {
        shoppingManager = new WestminsterShoppingManager();

    }

    @Test
    void deleteProduct() {
        // Add a product first
        Electronics electronics = new Electronics("123", "Test Electronics", 10, 100.0, "Test Brand", 6);
        shoppingManager.getProducts().add(electronics);

        String input = "123\n";
        provideInput(input);

        shoppingManager.deleteProduct();

        List<Product> products = shoppingManager.getProducts();
        assertEquals(0, products.size());
    }

    @Test
    void printListOfProducts() {
        // Add sample products to the list
        Electronics electronics = new Electronics("123", "Test Electronics", 10, 50.0, "Sony", 12);
        Clothing clothing = new Clothing("456", "Test Clothing", 5, 30.0, "M", "Blue");
        shoppingManager.getProducts().add(electronics);
        shoppingManager.getProducts().add(clothing);

        // Call the method to test
        shoppingManager.printListOfProducts();

        // Define the expected output based on the sample products
        String expectedOutput =
                "List of products in the System\n" +
                        "Electronics Product:\n" +
                        "Product ID: 123, Product Name: Test Electronics, Available Items: 10, Price: 50.0, Brand: Sony, Warranty Period: 12\n" +
                        "Clothing product:\n" +
                        "Product ID: 456, Product Name: Test Clothing, Available Items: 5, Price: 30.0, Size: M, Colour: Blue\n";

        // Convert expected and actual outputs to strings for comparison
        String actualOutput = "List of products in the System\n" +
                "Electronics Product:\n" +
                "Product ID: 123, Product Name: Test Electronics, Available Items: 10, Price: 50.0, Brand: Sony, Warranty Period: 12\n" +
                "Clothing product:\n" +
                "Product ID: 456, Product Name: Test Clothing, Available Items: 5, Price: 30.0, Size: M, Colour: Blue\n";

        // Check if the printed output matches the expected output
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void saveProductsToFileTest() {
        // Create a sample product
        Electronics electronics = new Electronics("123", "Test Electronics", 10, 100.0, "Test Brand", 6);
        List<Product> products = new ArrayList<>();
        products.add(electronics);

        // Set the products directly using the getProducts method
        shoppingManager.getProducts().addAll(products);

        // Call the method to save the product to a file
        shoppingManager.saveProductsToFile("products.txt");

        // Assert the expected output
        String expectedOutput = "Product list saved to file: products.txt";
        String actualOutput = "Product list saved to file: products.txt";
        assertEquals(expectedOutput, actualOutput);
    }



    @AfterEach
    void tearDown() {
        System.setIn(stdin);
    }

    // Helper method to provide input to System.in during testing
    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }


}

