package com.student.chatapp;

import java.util.Scanner;

/**
 * Main application class for Part 1
 * Handles user registration and login flow
 * Links to Part 2 message sending after successful login
 * 
 * @author Student
 * @version 1.0
 */
public class ChatAppPart1 {
    
    // Static variables to share login state with Part 2
    public static boolean isLoggedIn = false;
    public static String loggedInFirstName = "";
    public static String loggedInLastName = "";
    public static String loggedInUsername = "";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();
        
        System.out.println("========================================");
        System.out.println("    WELCOME TO CHAT APP - PART 1");
        System.out.println("    REGISTRATION AND LOGIN");
        System.out.println("========================================");
        System.out.println();
        
        // Get user personal details
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        login.setFirstName(firstName);
        
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        login.setLastName(lastName);
        
        System.out.println();
        System.out.println("--- REGISTRATION ---");
        
        // Registration phase - loop until valid
        boolean registered = false;
        while (!registered) {
            System.out.print("Enter Username (max 5 chars with underscore, e.g., kyl_1): ");
            String username = scanner.nextLine();
            
            System.out.print("Enter Password (8+ chars, capital, number, special): ");
            String password = scanner.nextLine();
            
            System.out.print("Enter Cell Phone (+27XXXXXXXXX, e.g., +27838968976): ");
            String cellPhone = scanner.nextLine();
            
            System.out.println();
            String result = login.registerUser(username, password, cellPhone);
            System.out.println(result);
            System.out.println();
            
            // Check if all validations passed
            if (login.getUsername() != null && login.getPassword() != null && login.getCellPhone() != null) {
                registered = true;
                System.out.println("Registration successful! You can now login.");
            } else {
                System.out.println("Registration failed. Please try again with correct format.");
                System.out.println();
            }
        }
        
        System.out.println();
        System.out.println("--- LOGIN ---");
        
        // Login phase - loop until successful or max attempts
        boolean loginSuccess = false;
        int attempts = 0;
        final int MAX_ATTEMPTS = 3;
        
        while (!loginSuccess && attempts < MAX_ATTEMPTS) {
            System.out.print("Enter Username: ");
            String loginUser = scanner.nextLine();
            
            System.out.print("Enter Password: ");
            String loginPass = scanner.nextLine();
            
            loginSuccess = login.loginUser(loginUser, loginPass);
            String statusMessage = login.returnLoginStatus(loginSuccess);
            System.out.println(statusMessage);
            
            if (!loginSuccess) {
                attempts++;
                int remaining = MAX_ATTEMPTS - attempts;
                if (remaining > 0) {
                    System.out.println("Attempts remaining: " + remaining);
                }
            }
        }
        
        if (!loginSuccess) {
            System.out.println("Maximum login attempts reached. Account locked.");
            System.out.println("Please restart the application.");
        } else {
            // Save login state for Part 2
            isLoggedIn = true;
            loggedInFirstName = login.getFirstName();
            loggedInLastName = login.getLastName();
            loggedInUsername = login.getUsername();
            
            System.out.println();
            System.out.println("========================================");
            System.out.println("    LOGIN SUCCESSFUL - PART 1 COMPLETE");
            System.out.println("========================================");
            System.out.println();
            System.out.println("Welcome, " + loggedInFirstName + " " + loggedInLastName + "!");
            System.out.println("You can now proceed to Part 2: Sending Messages.");
            System.out.println();
            
            // ============================================
            // LINK TO PART 2 - ASK USER TO CONTINUE
            // ============================================
            System.out.print("Would you like to continue to Part 2 (Message Sending)? (yes/no): ");
            String continueToPart2 = scanner.nextLine();
            
            if (continueToPart2.equalsIgnoreCase("yes")) {
                System.out.println();
                System.out.println("Transitioning to Part 2...");
                System.out.println("========================================");
                System.out.println();
                
                // Call Part 2 main method - THIS IS THE LINK!
                ChatAppPart2.main(new String[]{});
                
            } else {
                System.out.println();
                System.out.println("Thank you for using Chat App. Goodbye!");
            }
        }
        
        scanner.close();
    }
}