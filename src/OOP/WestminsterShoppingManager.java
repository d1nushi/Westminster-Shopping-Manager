package OOP;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {
    public List<Product> products;
    private static final int MaxNoOfProducts = 50;
    public Scanner scanner;

    //private static final String file;
    public WestminsterShoppingManager() {
        this.products = new ArrayList<>();

    }

    public List<Product> getProducts() {
        return this.products;
    }

    @Override
    public void displayMenu() {
        System.out.println(" ");
        System.out.println("----------Westminster Shopping Manager----------");
        System.out.println("1. Add a new Product");
        System.out.println("2. Delete a Product");
        System.out.println("3. Print the list of products");
        System.out.println("4. Save in a File");
        System.out.println("5. Open GUI");
        System.out.println("6. Exit");

    }

    public void addNewProduct() {
        if (products.size() >= MaxNoOfProducts) {
            System.out.println("Cannot add more products.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the type of product to add: ");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");

        int choice;
        while(true){
            try{
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt((scanner.nextLine()));

                if(choice == 1 || choice == 2){
                    break;
                }else{
                    System.out.println("Invalid choice. Please enter 1 for Electronics and 2 for Clothing");
                }
            }catch(NumberFormatException exception){
                System.out.println("Invalid input. Please enter a valid number");
            }
        }

        switch (choice) {
            case 1:
                addElectronics();
                break;
            case 2:
                addClothing();
                break;
        }

    }

    private void addElectronics() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Electronics Product Details:");

        String productId;
        do {
            System.out.print("Product ID: ");
            productId = scanner.nextLine();
            if (productId.isEmpty()) {
                System.out.println("Product ID cannot be empty. Please enter a valid ID.");
            }
        } while (productId.isEmpty());

        System.out.print("Product Name: ");
        String productName = scanner.nextLine();

        int availableItems;
        do {
            System.out.print("AvailableItems: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid integer");
                scanner.next();
            }
            availableItems = scanner.nextInt();
            if (availableItems <= 0) {
                System.out.println("Available items should be greater than 0. Please enter a valid value. ");
            }
        } while (availableItems <= 0);

        double price;
        do {
            System.out.print("Price: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid price");
                scanner.next();
            }
            price = scanner.nextDouble();
            if (price <= 0) {
                System.out.println("Price should be greater than o. Please enter a valid value.");
            }
        }while (price <= 0);
        scanner.nextLine();

        String brand;
        do{
            System.out.print("Brand: ");
            brand = scanner.nextLine();
            if(brand.isEmpty()){
                System.out.println("Brand cannot be empty. Please enter a valid brand.");
            }
        }while (brand.isEmpty());

        int warrantyPeriod;
        do {
            System.out.print("Warranty Period: ");
            while(!scanner.hasNextInt()){
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
            warrantyPeriod = scanner.nextInt();
            if (warrantyPeriod < 0) {
                System.out.println("Warranty period should be 0 or greater. Please enter a valid value.");
            }
        }while(warrantyPeriod <0);

        Electronics electronics = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
        products.add(electronics);

        System.out.println("Electronics product added successfully.");
    }

    private void addClothing() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Clothing Product Details:");

        String productId;
        do {
            System.out.print("Product ID: ");
            productId = scanner.nextLine().trim();
            if (productId.isEmpty()) {
                System.out.println("Product ID cannot be empty. Please enter a valid ID.");
            }
        } while (productId.isEmpty());

        System.out.print("Product Name: ");
        String productName = scanner.nextLine();

        int availableItems;
        do {
            System.out.print("Available Items: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid integer");
                scanner.next();
            }
            availableItems = scanner.nextInt();
            if (availableItems <= 0) {
                System.out.println("Available items should be greater than 0. Please enter a valid value. ");
            }
        } while (availableItems <= 0);

        double price;
        do {
            System.out.print("Price: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid price");
                scanner.next();
            }
            price = scanner.nextDouble();
            if (price <= 0) {
                System.out.println("Price should be greater than 0. Please enter a valid value.");
            }
        }while (price <= 0);
        scanner.nextLine();

        String size;
        do {
            System.out.print("Size (S/M/L): ");
            size = scanner.nextLine();
            if (size.isEmpty()) {
                System.out.println("Size cannot be empty. Please enter a valid size.");
            }
        }while (size.isEmpty()) ;


         String colour;
         do {
             System.out.print("Colour: ");
             colour = scanner.nextLine();
             if (colour.isEmpty()) {
                 System.out.println("Colour cannot be empty. Please enter a colour.");
             }
         }while (colour.isEmpty());


         Clothing clothing = new Clothing(productId, productName, availableItems, price, size, colour);
         products.add(clothing);

         System.out.println("Clothing product added successfully.");
        }
    

    public void deleteProduct() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the product ID: ");
        String productIdToDelete = scanner.nextLine();

        Iterator<Product> iterator = products.iterator();
        boolean productFound = false;
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getProductId().equals(productIdToDelete)) {
                iterator.remove();
                System.out.println("Product Deleted ");
                System.out.println(product);
                productFound = true;
                break;
            }
        }
        if (!productFound) {
            System.out.println("Product not found.");
        }
        System.out.println("number of products left: " + products.size());
    }
    public void printListOfProducts() {
            Collections.sort(products, Comparator.comparing(Product::getProductId));

            System.out.println("List of products in the System");
            for(Product product : products){
                if(product instanceof Electronics){
                    System.out.println("Electronics Product:");
                }else if(product instanceof Clothing){
                    System.out.println("Clothing product:");
                }
                System.out.println(product);
            }
    }

    public void saveProductsToFile(String fileName){
        fileName = "products.txt";
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true))){
            for(Product product : products){

                if (product instanceof Electronics){
                    writer.write("Electronic Product:  " +"\n");
                }else if (product instanceof Clothing){
                    writer.write("Clothing Product: " +"\n");
                }

                writer.write("Product ID- " + product.getProductId()+", ");
                writer.write("product Name- " + product.getProductName()+", ");
                writer.write("Available Items- " + product.getAvailableItems()+", ");
                writer.write("Price- " + product.getPrice()+", ");

                if (product instanceof Electronics){
                    writer.write("Brand- " + ((Electronics)product).getBrand()+", ");
                    writer.write("Warranty Period- " + ((Electronics)product).getWarrantyPeriod()+"\n");
                }else if (product instanceof Clothing){
                    writer.write("Size- " + ((Clothing)product).getSize()+", ");
                    writer.write("Colour- " + ((Clothing)product).getColour()+"\n");
                }
            }
            System.out.println("Product list saved to file: " + fileName);
        } catch (IOException exception) {
            System.out.println("Error saving products list to file: " + fileName);
            exception.printStackTrace();
        }
    }

    public void loadFromFile(String fileName) {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("Electronics Product:")) {
                    addElectronicsFromTextFile(reader);
                } else if (line.equals("Clothing product:")) {
                    addClothingFromTextFile(reader);
                }
            }
            System.out.println("Product list loaded from text file: " + fileName);
        } catch (FileNotFoundException exception) {
            System.out.println("No previous data found. Starting with an empty product list.");
            products = new ArrayList<>();
        } catch (IOException exception) {
            exception.printStackTrace();
            // Handle the exception according to your application's needs
            products = new ArrayList<>();
        }

        }
    private void addElectronicsFromTextFile(BufferedReader reader) throws IOException {
        String productId = reader.readLine();
        String productName = reader.readLine();
        int availableItems = Integer.parseInt(reader.readLine());
        double price = Double.parseDouble(reader.readLine());
        String brand = reader.readLine();
        int warrantyPeriod = Integer.parseInt(reader.readLine());

        Electronics electronics = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
        products.add(electronics);
    }

    private void addClothingFromTextFile(BufferedReader reader) throws IOException {
        String productId = reader.readLine();
        String productName = reader.readLine();
        int availableItems = Integer.parseInt(reader.readLine());
        double price = Double.parseDouble(reader.readLine());
        String size = reader.readLine();
        String colour = reader.readLine();

        Clothing clothing = new Clothing(productId, productName, availableItems, price, size, colour);
        products.add(clothing);
    }

    public void openGUI(List<Product>products){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ShoppingGUI.openGUI(products);
            }
        });
    }


    public static void main (String[]args){
            WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            // Load products from file when the application starts
            shoppingManager.loadFromFile("products.dat");

            while (!exit) {
                shoppingManager.displayMenu();
                System.out.println("Enter your choice(1-5):");

            if(scanner.hasNextInt()){

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        shoppingManager.addNewProduct();
                        break;
                    case 2:
                        shoppingManager.deleteProduct();
                        break;
                    case 3:
                        shoppingManager.printListOfProducts();
                        break;
                    case 4:
                        shoppingManager.saveProductsToFile("products.txt");
                        break;
                    case 5:
                        shoppingManager.openGUI(shoppingManager.getProducts());
                        break;
                    case 6:
                        System.out.println("Exiting the shopping manager");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid number.");
                }
            }else{
                System.out.println("Invalid input.Please enter an valid option");
                scanner.next();
            }
            }
            scanner.close();
        }
}

