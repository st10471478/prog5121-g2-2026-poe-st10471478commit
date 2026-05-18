package com.student.chatapp;

import java.util.Scanner;

/**
 * Main application class for Part 2
 * Handles message sending with menu, loops, and user interaction
 * Links from Part 1 login system
 * 
 * @author Student
 * @version 1.0
 */
public class ChatAppPart2 {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // ============================================
        // CHECK IF COMING FROM PART 1 LOGIN
        // ============================================
        boolean userLoggedIn = ChatAppPart1.isLoggedIn;
        String userFirstName = ChatAppPart1.loggedInFirstName;
        String userLastName = ChatAppPart1.loggedInLastName;
        
        // Welcome message as per assignment requirement
        System.out.println("Welcome to QuickChat");
        
        // If coming from Part 1, show personalized welcome
        if (userLoggedIn) {
            System.out.println();
            System.out.println("Welcome back, " + userFirstName + " " + userLastName + "!");
            System.out.println("You are logged in as: " + ChatAppPart1.loggedInUsername);
        } else {
            // If running Part 2 directly (not from Part 1), require login check
            System.out.println();
            System.out.print("Are you logged in? (yes/no): ");
            String loggedIn = scanner.nextLine();
            
            if (!loggedIn.equalsIgnoreCase("yes")) {
                System.out.println("You must be logged in to send messages.");
                System.out.println("Please run Part 1 (ChatAppPart1) to register and login first.");
                scanner.close();
                return;
            }
        }
        
        // Ask user how many messages they want to enter
        System.out.println();
        System.out.print("How many messages do you wish to enter? ");
        int numMessages = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        // Create array to store messages
        Message[] messages = new Message[numMessages];
        
        // Main application loop - runs until user quits
        boolean running = true;
        
        while (running) {
            // Display numeric menu as per assignment
            System.out.println();
            System.out.println("=== QUICKCHAT MENU ===");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages - Coming Soon");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");
            
            int menuChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            switch(menuChoice) {
                case 1:
                    // Send Messages option
                    sendMessages(scanner, messages, numMessages);
                    break;
                    
                case 2:
                    // Coming Soon feature
                    System.out.println();
                    System.out.println("Coming Soon.");
                    break;
                    
                case 3:
                    // Quit application
                    System.out.println();
                    System.out.println("Thank you for using QuickChat. Goodbye!");
                    running = false;
                    break;
                    
                default:
                    System.out.println();
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
        
        scanner.close();
    }
    
    /**
     * Handles message input using for loop
     * Collects recipient, message text, creates hash, and processes send/store/disregard
     * 
     * @param scanner Scanner for user input
     * @param messages array to store Message objects
     * @param numMessages number of messages to enter
     */
    private static void sendMessages(Scanner scanner, Message[] messages, int numMessages) {
        System.out.println();
        System.out.println("--- SEND MESSAGES ---");
        
        // For loop to enter assigned number of messages
        for (int i = 0; i < numMessages; i++) {
            System.out.println();
            System.out.println("Message " + (i + 1) + " of " + numMessages);
            
            // Create new message object (auto-generates ID and increments counter)
            messages[i] = new Message();
            
            // Get recipient cell number
            System.out.print("Enter Recipient Number (+27XXXXXXXX, max 10 chars): ");
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
            System.out.print("Enter Message (max 250 characters): ");
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
            System.out.println("Message prepared:");
            System.out.println("Message ID: " + messages[i].getMessageId());
            System.out.println("Message Hash: " + hash);
            System.out.println("Message Number: " + messages[i].getNumMessagesSent());
            
            // Send/Disregard/Store menu
            System.out.println();
            System.out.println("Choose action:");
            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message to send later");
            System.out.print("Enter choice: ");
            
            int action = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            String result;
            switch(action) {
                case 1:
                    result = messages[i].sendMessage("send");
                    System.out.println(result);
                    break;
                case 2:
                    result = messages[i].sendMessage("disregard");
                    System.out.println(result);
                    break;
                case 3:
                    result = messages[i].sendMessage("store");
                    System.out.println(result);
                    break;
                default:
                    result = "Invalid choice.";
                    System.out.println(result);
            }
            
            // Display full message details after action
            System.out.println();
            System.out.println("--- Full Message Details ---");
            System.out.println(messages[i].printMessage());
            System.out.println("----------------------------");
        }
        
        // Display total number of messages sent
        System.out.println();
        System.out.println("Total messages processed: " + Message.returnTotalMessages());
    }
}