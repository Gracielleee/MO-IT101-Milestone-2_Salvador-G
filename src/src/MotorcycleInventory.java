package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MotorcycleInventory {
    private String path = "motorcycles.csv";
    private TreeNode root;

    // Constructor
    public MotorcycleInventory() {
        root = null;
        readFromFile(); // Load existing motorcycles from file
    }

    // Add stock
    public void addStock(Motorcycle motorcycle) {
        if (searchByEngineNumber(motorcycle.getEngineNumber()) != null) {
            System.out.println("Error: Engine number already exists.");
            return;
        }
        root = addRecursive(root, motorcycle);
        writeToFile(motorcycle); // Write to file when adding
    }

    private TreeNode addRecursive(TreeNode node, Motorcycle motorcycle) {
        if (node == null) {
            return new TreeNode(motorcycle);
        }

        if (motorcycle.getEngineNumber().compareTo(node.motorcycle.getEngineNumber()) < 0) {
            node.left = addRecursive(node.left, motorcycle);
        } else if (motorcycle.getEngineNumber().compareTo(node.motorcycle.getEngineNumber()) > 0) {
            node.right = addRecursive(node.right, motorcycle);
        } else {
            System.out.println("Error: Engine number already exists.");
        }
        return node;
    }

    // Delete stock
    public void deleteStock(String stockLabel) {
        root = deleteRecursive(root, stockLabel);
    }

    private TreeNode deleteRecursive(TreeNode node, String stockLabel) {
        if (node == null) {
            System.out.println("Error: Stock not found.");
            return null;
        }

        if (stockLabel.compareTo(node.motorcycle.getStockLabel()) < 0) {
            node.left = deleteRecursive(node.left, stockLabel);
        } else if (stockLabel.compareTo(node.motorcycle.getStockLabel()) > 0) {
            node.right = deleteRecursive(node.right, stockLabel);
        } else {
            // Node found
            if (node.motorcycle.getPurchaseStatus().equals("sold")) {
                // Node with only one child or no child
                if (node.left == null) return node.right;
                else if (node.right == null) return node.left;

                // Node with two children: Get the inorder successor
                node.motorcycle = minValue(node.right);
                node.right = deleteRecursive(node.right, node.motorcycle.getStockLabel());
            } else {
                System.out.println("Error: Stock cannot be deleted as it is not sold.");
            }
        }
        return node;
    }
    
    private Motorcycle minValue(TreeNode node) {
        Motorcycle minMotorcycle = node.motorcycle;
        while (node.left != null) {
        	minMotorcycle = node.left.motorcycle;
            node = node.left;
        }
        return minMotorcycle;
    }

    // Search by engine number
    public Motorcycle searchByEngineNumber(String engineNumber) {
        return searchRecursiveByEngineNumber(root, engineNumber);
    }

    private Motorcycle searchRecursiveByEngineNumber(TreeNode node, String engineNumber) {
        if (node == null || node.motorcycle.getEngineNumber().equals(engineNumber)) {
            return node != null ? node.motorcycle : null;
        }
        return engineNumber.compareTo(node.motorcycle.getEngineNumber()) < 0
            ? searchRecursiveByEngineNumber(node.left, engineNumber)
            : searchRecursiveByEngineNumber(node.right, engineNumber);
    }

    // Search by brand
    public List<Motorcycle> searchByBrand(String brand) {
        List<Motorcycle> results = new ArrayList<>();
        searchRecursiveByBrand(root, brand, results);
        return results;
    }

    private void searchRecursiveByBrand(TreeNode node, String brand, List<Motorcycle> results) {
        if (node != null) {
            searchRecursiveByBrand(node.left, brand, results);
            if (node.motorcycle.getBrand().equalsIgnoreCase(brand)) {
                results.add(node.motorcycle);
            }
            searchRecursiveByBrand(node.right, brand, results);
        }
    }
    
 // Display all motorcycles
    public void displayAll() {
        List<Motorcycle> sortedList = new ArrayList<>();
        inOrderTraversal(root, sortedList);
        System.out.println("MOTORPH INVENTORY:");
        printColumn();
        for (Motorcycle motorcycle : sortedList) {
            System.out.println(motorcycle);
        }
    }

    private void inOrderTraversal(TreeNode node, List<Motorcycle> sortedList) {
        if (node != null) {
            inOrderTraversal(node.left, sortedList);
            sortedList.add(node.motorcycle);
            inOrderTraversal(node.right, sortedList);
        }
    }

    private void printColumn() {
        System.out.println(" ||       DATE      |   STOCK LABEL   |       BRAND       |      ENGINE NUMBER      |       PURCHASE STATUS   ||");
    }

 // Sort motorcycles by brand using merge sort
    public void sortByBrand(boolean ascending) {
        List<Motorcycle> sortedList = new ArrayList<>();
        inOrderTraversal(root, sortedList); // Get all motorcycles in sorted order by engine number

        // Perform merge sort on the list by brand
        sortedList = mergeSort(sortedList, "brand");

        // Reverse the list if descending order is requested
        if (!ascending) {
            Collections.reverse(sortedList);
        }

        // Print the sorted list
        System.out.println("");
        System.out.println("MOTORPH INVENTORY SORTED BY BRAND:");
        printColumn();
        for (Motorcycle motorcycle : sortedList) {
            System.out.println(motorcycle);
        }
    }

    // Sort motorcycles by engine number using merge sort
    public void sortByEngineNumber(boolean ascending) {
        List<Motorcycle> sortedList = new ArrayList<>();
        inOrderTraversal(root, sortedList); // Get all motorcycles in sorted order by engine number

        // Perform merge sort on the list by engine number
        sortedList = mergeSort(sortedList, "engineNumber");

        // Reverse the list if descending order is requested
        if (!ascending) {
            Collections.reverse(sortedList);
        }

        // Print the sorted list
        System.out.println("");
        System.out.println("MOTORPH INVENTORY SORTED BY ENGINE NUMBER:");
        printColumn();
        for (Motorcycle motorcycle : sortedList) {
            System.out.println(motorcycle);
        }
    }

    // Merge sort method
    private List<Motorcycle> mergeSort(List<Motorcycle> list, String sortBy) {
        if (list.size() <= 1) {
            return list; // Base case: a list of zero or one element is already sorted
        }

        // Split the list into two halves
        int mid = list.size() / 2;
        List<Motorcycle> left = mergeSort(new ArrayList<>(list.subList(0, mid)), sortBy);
        List<Motorcycle> right = mergeSort(new ArrayList<>(list.subList(mid, list.size())), sortBy);

        // Merge the sorted halves
        return merge(left, right, sortBy);
    }

    // Merge method
    private List<Motorcycle> merge(List<Motorcycle> left, List<Motorcycle> right, String sortBy) {
        List<Motorcycle> merged = new ArrayList<>();
        int i = 0, j = 0;

        // Compare and merge the two lists based on the specified sorting criteria
        while (i < left.size() && j < right.size()) {
            Motorcycle m1 = left.get(i);
            Motorcycle m2 = right.get(j);
            int comparison;

            if (sortBy.equals("brand")) {
                comparison = m1.getBrand().compareToIgnoreCase(m2.getBrand());
            } else { // sortBy.equals("engineNumber")
                comparison = m1.getEngineNumber().compareTo(m2.getEngineNumber());
            }

            if (comparison <= 0) {
                merged.add(m1);
                i++;
            } else {
                merged.add(m2);
                j++;
            }
        }

        // Add any remaining elements from the left list
        while (i < left.size()) {
            merged.add(left.get(i));
            i++;
        }

        // Add any remaining elements from the right list
        while (j < right.size()) {
            merged.add(right.get(j));
            j++;
        }

        return merged;
    }
    
    // Write to CSV file
    private void writeToFile(Motorcycle motorcycle) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            writer.write(motorcycle.getDate() + "," + motorcycle.getStockLabel() + "," +
                         motorcycle.getBrand() + "," +
                         motorcycle.getEngineNumber() + "," + // Include engine number
                         motorcycle.getPurchaseStatus());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAllToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            List<Motorcycle> sortedList = new ArrayList<>();
            inOrderTraversal(root, sortedList); // Get sorted motorcycles
            for (Motorcycle motorcycle : sortedList) {
                writer.write(motorcycle.getDate() + "," + motorcycle.getStockLabel() + "," +
                             motorcycle.getBrand() + "," +
                             motorcycle.getEngineNumber() + "," + // Include engine number
                             motorcycle.getPurchaseStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read from CSV file
    private void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Motorcycle motorcycle = new Motorcycle(data[0], data[1], data[2], data[3], data[4]);
                addStock(motorcycle); // Use addStock to maintain BST structure
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}