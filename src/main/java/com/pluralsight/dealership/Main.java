package com.pluralsight.dealership;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Car Dealership Database System ===");
        System.out.println("Select interface:");
        System.out.println("1 - Customer Interface");
        System.out.println("2 - Admin Interface");
        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> {
                UserInterface ui = new UserInterface();
                ui.display();
            }
            case "2" -> {
                AdminUserInterface adminUI = new AdminUserInterface();
                adminUI.display();
            }
            default -> {
                System.out.println("Invalid choice. Starting customer interface by default.");
                UserInterface ui = new UserInterface();
                ui.display();
            }
        }

        scanner.close();
    }
}