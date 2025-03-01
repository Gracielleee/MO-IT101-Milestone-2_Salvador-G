package src;


import java.util.Scanner;

public class main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        MotorcycleInventory inventory = new MotorcycleInventory();
        inventory.displayAll();

        while (true) {
            try {
                System.out.println("");
                System.out.println("What would you like to do?");
                System.out.println("1. Insert Stock");
                System.out.println("2. Delete Stock");
                System.out.println("3. Sort");
                System.out.println("4. Search");
                System.out.println("5. Exit");
                System.out.print(": ");

                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        Actions.add(inventory);
                        break;

                    case 2:
                        Actions.delete(inventory);
                        break;

                    case 3:
                        Actions.sort(inventory);
                        break;

                    case 4:
                        Actions.search(inventory);
                        break;

                    case 5:
                        System.out.println("\nExiting\n . \n . \n . \nThanks for your hard work! :)");
                        sc.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                sc.nextLine(); // Clear the invalid input
            }
        }
    }
}
