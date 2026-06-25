/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.student.chatapp;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Message class handles message creation, validation, and storage
 * for the Chat Application - PROG5121 Part 2
 * 
 * @author Student
 * @version 1.0
 */
public class Message {
    
    // Instance variables
    private String messageId;
    private int numMessagesSent;
    private String recipient;
    private String messageText;
    private String messageHash;
    
    // Static counter for auto-incrementing message numbers
    private static int messageCounter = 0;
    
    /**
     * Default constructor - generates message ID and increments counter
     */
    public Message() {
        this.messageId = generateMessageId();
        this.numMessagesSent = ++messageCounter;
    }
    
    /**
     * Generates a random 10-digit message ID
     * Uses Random class to create unique identifier
     * 
     * @return 10-digit number as string
     */
    private String generateMessageId() {
        Random random = new Random();
        // Generate exactly 10 digits (1000000000 to 9999999999)
        long id = 1000000000L + Math.abs(random.nextLong() % 9000000000L);
        return String.valueOf(id);
    }
    
    /**
     * Checks if message ID is not more than 10 characters
     * 
     * @param id the message ID to check
     * @return true if 10 characters or less, false otherwise
     */
    public boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }
    
    /**
     * Validates recipient cell phone number
     * Must start with + (international code indicator)
     * 
     * Test Data: +27718693002 (valid - starts with +, contains only digits after)
     * Test Data: 08575975889 (invalid - no + code)
     * 
     * @param cell the recipient cell number
     * @return success or error message
     */
    public String checkRecipientCell(String cell) {
        if (cell == null || cell.isEmpty()) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        
        // Must start with + for international country code
        if (!cell.startsWith("+")) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        
        // Must contain digits after +
        if (!cell.matches("^\\+[0-9]+$")) {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
        
        return "Cell phone number successfully captured.";
    }
    
    /**
     * Creates message hash using string manipulation
     * Format: First 2 digits of ID : Message Number : FirstWord + LastWord (UPPERCASE)
     * Example: 00:1:HITONIGHT
     * 
     * @param messageId the message ID
     * @param msgNum the message number
     * @param message the message text
     * @return formatted message hash
     */
    public String createMessageHash(String messageId, int msgNum, String message) {
        if (message == null || message.trim().isEmpty() || messageId == null || messageId.length() < 2) {
            return "INVALID:HASH";
        }
        
        String firstTwo = messageId.substring(0, 2);
        String[] words = message.trim().split("\\s+");
        
        String firstWord = words[0].toUpperCase().replaceAll("[^A-Z]", "");
        String lastWord = words[words.length - 1].toUpperCase().replaceAll("[^A-Z]", "");
        
        return firstTwo + ":" + msgNum + ":" + firstWord + lastWord;
    }
    
    /**
     * Handles user choice to send, disregard, or store message
     * 
     * @param choice the user's choice (send, disregard, store)
     * @return appropriate status message
     */
    public String sendMessage(String choice) {
        switch(choice.toLowerCase()) {
            case "send":
                return "Message successfully sent.";
            case "disregard":
                return "Press 0 to delete the message.";
            case "store":
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }
    
        /**
     * Stores message to JSON file
     * Research: JSON.org (2024) JSON in Java. Available at: https://github.com/stleary/JSON-java
     * 
     * @return true if stored successfully, false otherwise
     */
    public boolean storeMessage() {
        JSONObject json = new JSONObject();
        json.put("messageId", this.messageId);
        json.put("recipient", this.recipient);
        json.put("message", this.messageText);
        json.put("hash", this.messageHash);
        json.put("numSent", this.numMessagesSent);
        
        try {
            String filePath = System.getProperty("user.dir") + "/stored_messages.json";
            FileWriter file = new FileWriter(filePath, true);
            file.write(json.toString() + "\n");
            file.close();
            return true;
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Returns formatted string with all message details
     * Display order: Message ID, Message Hash, Recipient, Message
     * 
     * @return formatted message details
     */
    public String printMessage() {
        return "Message ID: " + messageId + "\n" +
               "Message Hash: " + messageHash + "\n" +
               "Recipient: " + recipient + "\n" +
               "Message: " + messageText;
    }
    
    /**
     * Returns total number of messages created
     * 
     * @return total message count
     */
    public static int returnTotalMessages() {
        return messageCounter;
    }
    
    /**
     * Validates message length does not exceed 250 characters
     * 
     * @param message the message text to check
     * @return success message or error with character count
     */
    public String checkMessageLength(String message) {
        if (message == null) {
            return "Message cannot be null.";
        }
        if (message.length() > 250) {
            int excess = message.length() - 250;
            return "Message exceeds 250 characters by " + excess + "; please reduce the size.";
        }
        return "Message ready to send.";
    }
    
    // ==================== GETTERS AND SETTERS ====================
    
    public String getMessageId() {
        return messageId;
    }
    
    public int getNumMessagesSent() {
        return numMessagesSent;
    }
    
    public String getRecipient() {
        return recipient;
    }
    
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    
    public String getMessageText() {
        return messageText;
    }
    
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    
    public String getMessageHash() {
        return messageHash;
    }
    
    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }
    
    /**
     * Resets the static counter (useful for testing)
     */
    public static void resetCounter() {
        messageCounter = 0;
    }
}