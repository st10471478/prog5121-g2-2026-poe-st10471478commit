/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.student.chatapp;

import java.util.Scanner;

/**
 * Main application class for Part 3 - FINAL COMPLETE APPLICATION
 * Combines Part 1 (Login), Part 2 (Messages), and Part 3 (Arrays/Reports)
 * 
 * @author Student
 * @version 1.0
 */
public class ChatAppPart3 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MessageManager manager = new MessageManager();
        
        // Load previously stored messages from JSON file
        int loaded = manager.loadFromJsonFile();
        if (loaded > 0) {
            System.out.println("Loaded " + loaded + " previously stored messages.");
        }
        
        System.out.println("========================================");
        System.out.println("    WELCOME TO CHAT APP - FINAL");
        System.out.println("    PART 1 + 2 + 3 COMPLETE");
        System.out.println("========================================");
        System.out.println();
        
        // ============================================
        // PART 1: REGISTRATION AND LOGIN
        // ============================================
        System.out.println("--- STEP 1: REGISTRATION AND LOGIN ---");
        System.out.println();
        
        Login login = new Login();
        
        // Get user personal details
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        login.setFirstName(firstName);
        
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        login.setLastName(lastName);
        
        System.out.println();
        System.out.println("--- Registration ---");
        
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
        System.out.println("--- Login ---");
        
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
            scanner.close();
            return;
        }
        
        System.out.println();
        System.out.println("========================================");
        System.out.println("    LOGIN SUCCESSFUL - PART 1 COMPLETE");
        System.out.println("========================================");
        System.out.println();
        
        // ============================================
        // PART 2: SEND MESSAGES
        // ============================================
        System.out.println("--- STEP 2: SEND MESSAGES ---");
        System.out.println();
        
        System.out.print("How many messages do you want to enter? ");
        int numMessages = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        // Create array to store messages temporarily
        Message[] messages = new Message[numMessages];
        
        // For loop to enter assigned number of messages
        for (int i = 0; i < numMessages; i++) {
            System.out.println();
            System.out.println("Message " + (i + 1) + " of " + numMessages);
            
            // Create new message object (auto-generates ID and increments counter)
            messages[i] = new Message();
            
            // Get recipient cell number
            System.out.print("Enter Recipient Number (+27XXXXXXXX): ");
            String recipient = scanner.nextLine();
            
            // Validate recipient
            String cellCheck = messages[i].checkRecipientCell(recipient);
            System.out.println(cellCheck);
            
            // If invalid, retry this message
            if (!cellCheck.contains("successfully")) {
                System.out.println("Please try again.");
                i--; // Decrement to retry this iteration
                continue;
            }
            messages[i].setRecipient(recipient);
            
            // Get message text
            System.out.print("Enter Message: ");
            String text = scanner.nextLine();
            
            // Validate message length
            String lengthCheck = messages[i].checkMessageLength(text);
            System.out.println(lengthCheck);
            
            // If too long, retry this message
            if (lengthCheck.contains("exceeds")) {
                System.out.println("Please try again.");
                i--; // Decrement to retry this iteration
                continue;
            }
            messages[i].setMessageText(text);
            
            // Create message hash using string manipulation
            String hash = messages[i].createMessageHash(
                messages[i].getMessageId(),
                messages[i].getNumMessagesSent(),
                text
            );
            messages[i].setMessageHash(hash);
            
            // Display message details before action
            System.out.println();
            System.out.println("1. Send  2. Disregard  3. Store");
            System.out.print("Choice: ");
            int action = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            String result;
            switch(action) {
                case 1:
                    result = messages[i].sendMessage("send");
                    System.out.println(result);
                    // ============================================
                    // CRITICAL: Add to SentMessages array
                    // ============================================
                    manager.addSentMessage(text, hash, messages[i].getMessageId(), recipient);
                    break;
                case 2:
                    result = messages[i].sendMessage("disregard");
                    System.out.println(result);
                    // ============================================
                    // CRITICAL: Add to DisregardedMessages array
                    // ============================================
                    manager.addDisregardedMessage(text);
                    break;
                case 3:
                    result = messages[i].sendMessage("store");
                    System.out.println(result);
                    // ============================================
                    // CRITICAL: Store to JSON file AND add to StoredMessages array
                    // ============================================
                    messages[i].storeMessage();  // Write to JSON file
                    manager.addStoredMessage(text, hash, messages[i].getMessageId(), recipient);  // Add to array
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
            
            // Display full message details after action
            System.out.println();
            System.out.println("--- Full Message Details ---");
            System.out.println(messages[i].printMessage());
            System.out.println("----------------------------");
        }
        
        System.out.println();
        System.out.println("Total messages: " + Message.returnTotalMessages());
        
        // ============================================
        // PART 3: STORED MESSAGES MENU
        // ============================================
        boolean running = true;
        
        while (running) {
            System.out.println();
            System.out.println("========================================");
            System.out.println("    PART 3: STORED MESSAGES MENU");
            System.out.println("========================================");
            System.out.println("1) Display sender and recipient of all stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search messages for a particular recipient");
            System.out.println("5) Delete a message using message hash");
            System.out.println("6) Display full report");
            System.out.println("7) Quit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            System.out.println();
            
            switch(choice) {
                case 1:
                    // Display sender and recipient of all stored messages
                    System.out.println(manager.displayStoredMessages());
                    break;
                    
                case 2:
                    // Display the longest stored message
                    System.out.println(manager.findLongestMessage());
                    break;
                    
                case 3:
                    // Search for a message ID
                    System.out.print("Enter Message ID to search: ");
                    String searchId = scanner.nextLine();
                    System.out.println(manager.searchByMessageId(searchId));
                    break;
                    
                case 4:
                    // Search messages for a particular recipient
                    System.out.print("Enter Recipient to search: ");
                    String searchRecipient = scanner.nextLine();
                    System.out.println(manager.searchByRecipient(searchRecipient));
                    break;
                    
                case 5:
                    // Delete a message using message hash
                    System.out.print("Enter Message Hash to delete: ");
                    String deleteHash = scanner.nextLine();
                    System.out.println(manager.deleteByHash(deleteHash));
                    break;
                    
                case 6:
                    // Display full report
                    System.out.println(manager.displayReport());
                    break;
                    
                case 7:
                    // Quit
                    System.out.println("Thank you for using Chat App. Goodbye!");
                    running = false;
                    break;
                    
                default:
                    System.out.println("Invalid option. Please choose 1-7.");
            }
        }
        
        scanner.close();
    }
}