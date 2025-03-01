package src;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;

public class Actions {
    private static Scanner sc = new Scanner(System.in);
    private static LocalDate currentDate = LocalDate.now();
    private static String formattedCurrentDate = currentDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));

    // SORT
    public static void sort(MotorcycleInventory inventory) {
        System.out.println(""); 
        System.out.println("Sort table by:"); 
        System.out.println("1. Brand"); 
        System.out.println("2. Engine Number");
        System.out.print(": ");
       
        String in = sc.nextLine().trim();
        in = returnValidOption(sc, in);
        
        // Perform sorting based on user input
        if (in.equals("1")) {
            sortByBrand(inventory);
        } else {
            sortByEngineNumber(inventory);
        }
    }
    
    // Sort by BRAND
    private static void sortByBrand(MotorcycleInventory inventory) {
        System.out.println(""); 
        System.out.println("Pick order:"); 
        System.out.println("1. Ascending"); 
        System.out.println("2. Descending");
        System.out.print(": ");
        
        String in = sc.nextLine().trim();
        in = returnValidOption(sc, in);
        
        inventory.sortByBrand(in.equals("1")); 
        
        System.out.println("Table sorted.");
    }
    
    // Sort by ENGINE NUMBER
    private static void sortByEngineNumber(MotorcycleInventory inventory) {
        System.out.println(""); 
        System.out.println("Pick order:"); 
        System.out.println("1. Ascending"); 
        System.out.println("2. Descending");
        System.out.print(": ");
        
        String in = sc.nextLine().trim();
        in = returnValidOption(sc, in);
        
        inventory.sortByEngineNumber(in.equals("1")); 
        
        System.out.println("Table sorted.");
    }
    
    // SEARCH
    public static void search(MotorcycleInventory inventory) {
        System.out.println("");
        System.out.println("Pick Search criteria:"); 
        System.out.println("1. Engine Number"); 
        System.out.println("2. Brand");
        System.out.print(": ");
        
        String in = sc.nextLine();
        in = returnValidOption(sc, in);

        if (in.equals("1")) {
            System.out.println("Enter Engine Number"); 
            System.out.print("Search: "); 

            String key = sc.nextLine().trim(); 
            Motorcycle result = inventory.searchByEngineNumber(key);
            if (result != null) {
                System.out.println(result);
            } else {
                System.out.println("No motorcycle found with the given engine number.");
            }
        } else { 
            System.out.println("Enter Brand"); 
            System.out.print("Search: "); 

            String key = sc.nextLine().trim();
            List<Motorcycle> results = inventory.searchByBrand(key);
            if (!results.isEmpty()) {
                for (Motorcycle motorcycle : results) {
                    System.out.println(motorcycle);
                }
            } else {
                System.out.println("No motorcycles found with the given brand.");
            }
        }
    }

    // DELETE an existing record
    public static void delete(MotorcycleInventory inventory) {
	        if (!confirmAction(sc, "DELETE a stock?")) {
	            inventory.displayAll();
	            return;
	        }
	
	        System.out.println("\nDeleting a stock...");
	        System.out.print("Enter the Engine Number of the stock you want to delete: ");
	        String engineNumberInput = sc.nextLine().trim();
	        Motorcycle record = inventory.searchByEngineNumber(engineNumberInput);
	        
	        if (record == null) {
	            inventory.displayAll();
	            System.out.println("ERROR: Engine number doesn't exist in the system.\n");
	            return;
	        }
	        
	        if (record.getPurchaseStatus().equalsIgnoreCase("On-hand") || record.getStockLabel().equalsIgnoreCase("New")) {
	            inventory.displayAll();
	            System.out.println("ERROR: Cannot delete New or On-hand stock. Please select a stock that is Old and Sold.\n");
	            return;
	        }
	        
	        if (!confirmAction(sc, "Are you sure you want to delete stock with Engine Number " + engineNumberInput + "?")) {
	            inventory.displayAll();
	            return;
	        }
	
	        // Perform deletion
	        inventory.deleteStock(engineNumberInput);
	        inventory.displayAll();
	        System.out.println("Stock deleted successfully.\n");
        }  
            
            // INSERT a new record
            public static void add(MotorcycleInventory inventory) {
                if (!confirmAction(sc, "INSERT a new stock?")) {
                    inventory.displayAll();
                    return;
                }
                
                System.out.println(""); 
                System.out.println("Adding a new stock..."); 
                System.out.print("Enter the Engine Number: "); 
                String engineNumber = sc.nextLine().trim();     
                System.out.println("");
                
                // Check if the engine number already exists
                if (inventory.searchByEngineNumber(engineNumber) != null) {
                    inventory.displayAll();
                    System.out.println("ERROR: Engine number already exists in the system.\n");
                    return; // Exit if engine number already exists
                }
                
                String brand = getBrandField(); // Get brand input from user
                String stockLabel = "New"; // Set stock label default value
                String purchaseStatus = "On-hand"; // Set purchase status default value

                // Create a new Motorcycle object and add it to the inventory
                Motorcycle newMotor = new Motorcycle(formattedCurrentDate.toString(), 
                        stockLabel.trim(), brand.trim(), 
                        engineNumber.trim(), purchaseStatus.trim());

                inventory.addStock(newMotor);
                inventory.displayAll();
                System.out.println("Stock added successfully.\n");
            }
            
            // HELPER METHODS
            
            // Returns brand input
            private static String getBrandField() {
                System.out.print("Enter the Brand: ");
                String in = sc.nextLine();
                System.out.println("");
                
                return in;
            }

            // Loop to ensure input is strictly 1 or 2
            private static String returnValidOption(Scanner sc, String in) {
                while (!in.equals("1") && !in.equals("2")) {
                    System.out.println("Invalid input. Please enter 1 or 2.");
                    System.out.print(": ");
                    in = sc.nextLine().trim(); // Read new input
                }
                return in;
            }
            
            // Confirm if to proceed
            public static boolean confirmAction(Scanner scanner, String message) {
                System.out.println("\n" + message);
                System.out.println("1. Yes");
                System.out.println("2. No");
                System.out.print(": ");

                String input = scanner.nextLine().trim();
                while (!input.equals("1") && !input.equals("2")) {
                    System.out.println("Invalid input. Please enter 1 for YES or 2 for NO.");
                    System.out.print(": ");
                    input = scanner.nextLine().trim();
                }
                return input.equals("1");
            }
        }
