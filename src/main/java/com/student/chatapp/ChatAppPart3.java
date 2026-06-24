/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.student.chatapp;

import java.util.Scanner;

/**
 * Main application class for Part 3
 * Final application combining Part 1, 2, and 3
 * Handles arrays, reports, and JSON file reading
 * 
 * @author Student
 * @version 1.0
 */
public class ChatAppPart3 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MessageManager manager = new MessageManager();
        
        // Load previously stored messages from JSON
        int loaded = manager.loadFromJsonFile();
        if (loaded > 0) {
            System.out.println("Loaded " + loaded + " messages from storage.");
        }
        
        System.out.println("========================================");
        System.out.println("    WELCOME TO CHAT APP - FINAL");
        System.out.println("    PART 1 + 2 + 3 COMPLETE");
        System.out.println("========================================");
        System.out.println();
        
        // Run Part 1 - Registration and Login
        System.out.println("--- STEP 1: REGISTRATION AND LOGIN ---");
        System.out.println();
        
        // Simulate Part 1 login (or call it directly)
        boolean loggedIn = runPart1Login(scanner);
        
        if (!loggedIn) {
            System.out.println("Login failed. Exiting application.");
            scanner.close();
            return;
        }
        
        // Run Part 2 - Send Messages
        System.out.println();
        System.out.println("--- STEP 2: SEND MESSAGES ---");
        System.out.println();
        runPart2Messages(scanner, manager);
        
        // Part 3 - Stored Messages Menu
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
                    System.out.println(manager.displayStoredMessages());
                    break;
                    
                case 2:
                    System.out.println(manager.findLongestMessage());
                    break;
                    
                case 3:
                    System.out.print("Enter Message ID to search: ");
                    String searchId = scanner.nextLine();
                    System.out.println(manager.searchByMessageId(searchId));
                    break;
                    
                case 4:
                    System.out.print("Enter Recipient to search: ");
                    String searchRecipient = scanner.nextLine();
                    System.out.println(manager.searchByRecipient(searchRecipient));
                    break;
                    
                case 5:
                    System.out.print("Enter Message Hash to delete: ");
                    String deleteHash = scanner.nextLine();
                    System.out.println(manager.deleteByHash(deleteHash));
                    break;
                    
                case 6:
                    System.out.println(manager.displayReport());
                    break;
                    
                case 7:
                    System.out.println("Thank you for using Chat App. Goodbye!");
                    running = false;
                    break;
                    
                default:
                    System.out.println("Invalid option. Please choose 1-7.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Simulates Part 1 login process
     * @param scanner Scanner for input
     * @return true if login successful
     */
    private static boolean runPart1Login(Scanner scanner) {
        Login login = new Login();
        
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        login.setFirstName(firstName);
        
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        login.setLastName(lastName);
        
        System.out.println();
        System.out.println("--- Registration ---");
        
        boolean registered = false;
        while (!registered) {
            System.out.print("Username (max 5 chars with _): ");
            String username = scanner.nextLine();
            
            System.out.print("Password (8+ chars, cap, num, special): ");
            String password = scanner.nextLine();
            
            System.out.print("Cell Phone (+27XXXXXXXXX): ");
            String cellPhone = scanner.nextLine();
            
            String result = login.registerUser(username, password, cellPhone);
            System.out.println("\n" + result);
            
            if (login.getUsername() != null && login.getPassword() != null && login.getCellPhone() != null) {
                registered = true;
                System.out.println("Registration successful!");
            } else {
                System.out.println("Please try again.\n");
            }
        }
        
        System.out.println();
        System.out.println("--- Login ---");
        
        boolean loginSuccess = false;
        int attempts = 0;
        
        while (!loginSuccess && attempts < 3) {
            System.out.print("Username: ");
            String user = scanner.nextLine();
            
            System.out.print("Password: ");
            String pass = scanner.nextLine();
            
            loginSuccess = login.loginUser(user, pass);
            System.out.println(login.returnLoginStatus(loginSuccess));
            
            if (!loginSuccess) {
                attempts++;
            }
        }
        
        return loginSuccess;
    }
    
    /**
     * Simulates Part 2 message sending
     * @param scanner Scanner for input
     * @param manager MessageManager to store messages
     */
    private static void runPart2Messages(Scanner scanner, MessageManager manager) {
        System.out.print("How many messages do you want to enter? ");
        int numMessages = scanner.nextInt();
        scanner.nextLine();
        
        for (int i = 0; i < numMessages; i++) {
            System.out.println();
            System.out.println("Message " + (i + 1) + " of " + numMessages);
            
            Message msg = new Message();
            
            System.out.print("Recipient (+27XXXXXXXX): ");
            String recipient = scanner.nextLine();
            String cellCheck = msg.checkRecipientCell(recipient);
            System.out.println(cellCheck);
            
            if (!cellCheck.contains("successfully")) {
                i--;
                continue;
            }
            msg.setRecipient(recipient);
            
            System.out.print("Message text: ");
            String text = scanner.nextLine();
            String lengthCheck = msg.checkMessageLength(text);
            System.out.println(lengthCheck);
            
            if (lengthCheck.contains("exceeds")) {
                i--;
                continue;
            }
            msg.setMessageText(text);
            
            String hash = msg.createMessageHash(msg.getMessageId(), msg.getNumMessagesSent(), text);
            msg.setMessageHash(hash);
            
            System.out.println();
            System.out.println("1. Send  2. Disregard  3. Store");
            System.out.print("Choice: ");
            int action = scanner.nextInt();
            scanner.nextLine();
            
            switch(action) {
                case 1:
                    System.out.println(msg.sendMessage("send"));
                    manager.addSentMessage(text, hash, msg.getMessageId(), recipient);
                    break;
                case 2:
                    System.out.println(msg.sendMessage("disregard"));
                    manager.addDisregardedMessage(text);
                    break;
                case 3:
                    System.out.println(msg.sendMessage("store"));
                    msg.storeMessage();
                    manager.addStoredMessage(text, hash, msg.getMessageId(), recipient);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
            
            System.out.println("\n" + msg.printMessage());
        }
        
        System.out.println();
        System.out.println("Total messages: " + Message.returnTotalMessages());
    }
}