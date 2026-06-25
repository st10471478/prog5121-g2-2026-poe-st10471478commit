/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.student.chatapp;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

/**
 * MessageManager handles Part 3 array operations and reports
 * Manages parallel arrays for sent, disregarded, and stored messages
 * 
 * @author Student
 * @version 1.0
 */
public class MessageManager {
    
    // Parallel arrays for message storage - as required by assignment
    private ArrayList<String> sentMessages;
    private ArrayList<String> disregardedMessages;
    private ArrayList<String> storedMessages;
    private ArrayList<String> messageHashes;
    private ArrayList<String> messageIds;
    private ArrayList<String> recipients;
    
    // Counter for totals
    private int totalSent;
    private int totalDisregarded;
    private int totalStored;
    
    /**
     * Constructor - initializes all parallel arrays
     */
    public MessageManager() {
        sentMessages = new ArrayList<>();
        disregardedMessages = new ArrayList<>();
        storedMessages = new ArrayList<>();
        messageHashes = new ArrayList<>();
        messageIds = new ArrayList<>();
        recipients = new ArrayList<>();
        totalSent = 0;
        totalDisregarded = 0;
        totalStored = 0;
    }
    
    /**
     * Adds a sent message to the SentMessages array
     * @param message the message text
     * @param hash the message hash
     * @param id the message ID
     * @param recipient the recipient number
     */
    public void addSentMessage(String message, String hash, String id, String recipient) {
        sentMessages.add(message);
        messageHashes.add(hash);
        messageIds.add(id);
        recipients.add(recipient);
        totalSent++;
    }
    
    /**
     * Adds a disregarded message to the DisregardedMessages array
     * @param message the message text
     */
    public void addDisregardedMessage(String message) {
        disregardedMessages.add(message);
        totalDisregarded++;
    }
    
    /**
     * Adds a stored message to the StoredMessages array
     * @param message the message text
     * @param hash the message hash
     * @param id the message ID
     * @param recipient the recipient number
     */
    public void addStoredMessage(String message, String hash, String id, String recipient) {
        storedMessages.add(message);
        messageHashes.add(hash);
        messageIds.add(id);
        recipients.add(recipient);
        totalStored++;
    }
    
    /**
     * Displays sender and recipient of all stored messages
     * @return formatted string with all stored messages
     */
    public String displayStoredMessages() {
        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }
        
        StringBuilder result = new StringBuilder();
        result.append("=== Stored Messages ===\n");
        
        for (int i = 0; i < storedMessages.size(); i++) {
            result.append("Sender: You\n");
            result.append("Recipient: ").append(recipients.get(i)).append("\n");
            result.append("Message: ").append(storedMessages.get(i)).append("\n");
            result.append("------------------------\n");
        }
        
        return result.toString().trim();
    }
    
    /**
     * Finds and returns the longest stored message
     * @return the longest message or "No stored messages" if empty
     */
    public String findLongestMessage() {
        if (storedMessages.isEmpty()) {
            return "No stored messages found.";
        }
        
        String longest = storedMessages.get(0);
        int maxLength = longest.length();
        int index = 0;
        
        for (int i = 1; i < storedMessages.size(); i++) {
            if (storedMessages.get(i).length() > maxLength) {
                longest = storedMessages.get(i);
                maxLength = longest.length();
                index = i;
            }
        }
        
        return "Longest Message: " + longest + 
               "\nLength: " + maxLength + " characters" +
               "\nRecipient: " + recipients.get(index);
    }
    
    /**
     * Searches for a message by ID and returns recipient and message
     * @param id the message ID to search for
     * @return recipient and message, or "Not found"
     */
    public String searchByMessageId(String id) {
        for (int i = 0; i < messageIds.size(); i++) {
            if (messageIds.get(i).equals(id)) {
                return "Message ID: " + id + 
                       "\nRecipient: " + recipients.get(i) + 
                       "\nMessage: " + storedMessages.get(i);
            }
        }
        return "Message ID not found: " + id;
    }
    
    /**
     * Searches for all messages sent to a particular recipient
     * @param recipient the recipient number to search for
     * @return all messages for that recipient
     */
    public String searchByRecipient(String recipient) {
        StringBuilder result = new StringBuilder();
        boolean found = false;
        
        for (int i = 0; i < recipients.size(); i++) {
            if (recipients.get(i).equals(recipient)) {
                if (!found) {
                    result.append("Messages for ").append(recipient).append(":\n");
                    found = true;
                }
                result.append("- ").append(storedMessages.get(i)).append("\n");
            }
        }
        
        if (!found) {
            return "No messages found for recipient: " + recipient;
        }
        
        return result.toString().trim();
    }
    
    /**
     * Deletes a message using its hash
     * @param hash the message hash to delete
     * @return success or failure message
     */
    public String deleteByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                String deletedMessage = storedMessages.get(i);
                storedMessages.remove(i);
                messageHashes.remove(i);
                messageIds.remove(i);
                recipients.remove(i);
                totalStored--;
                return "Message: \"" + deletedMessage + "\" successfully deleted.";
            }
        }
        return "Message hash not found: " + hash;
    }
    
    /**
     * Displays a full report of all stored messages
     * @return formatted report
     */
    public String displayReport() {
        if (storedMessages.isEmpty() && sentMessages.isEmpty() && disregardedMessages.isEmpty()) {
            return "No messages to display in report.";
        }
        
        StringBuilder report = new StringBuilder();
        report.append("========================================\n");
        report.append("         MESSAGE REPORT\n");
        report.append("========================================\n\n");
        report.append("Total Sent Messages: ").append(totalSent).append("\n");
        report.append("Total Disregarded Messages: ").append(totalDisregarded).append("\n");
        report.append("Total Stored Messages: ").append(totalStored).append("\n\n");
        report.append("--- Full Details ---\n\n");
        
        // Show all stored messages in report
        for (int i = 0; i < storedMessages.size(); i++) {
            report.append("Message ID: ").append(messageIds.get(i)).append("\n");
            report.append("Message Hash: ").append(messageHashes.get(i)).append("\n");
            report.append("Recipient: ").append(recipients.get(i)).append("\n");
            report.append("Message: ").append(storedMessages.get(i)).append("\n");
            report.append("----------------------------------------\n");
        }
        
        return report.toString().trim();
    }
    
    /**
     * Reads stored messages from JSON file into StoredMessages array
     * Research: JSON.org (2024) JSON in Java.
     * Available at: https://github.com/stleary/JSON-java
     * 
     * @return number of messages loaded, or 0 if file not found
     */
    public int loadFromJsonFile() {
        String filePath = System.getProperty("user.dir") + "/stored_messages.json";
        File file = new File(filePath);
        
        if (!file.exists()) {
            return 0;
        }
        
        int count = 0;
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            
            // File may contain multiple JSON objects, one per line
            String[] lines = content.split("\n");
            
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                
                JSONObject json = new JSONObject(line);
                
                String id = json.optString("messageId", "UNKNOWN");
                String recipient = json.optString("recipient", "UNKNOWN");
                String message = json.optString("message", "UNKNOWN");
                String hash = json.optString("hash", "UNKNOWN");
                
                storedMessages.add(message);
                messageIds.add(id);
                recipients.add(recipient);
                messageHashes.add(hash);
                totalStored++;
                
                count++;
            }
            
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
            return 0;
        }
        
        return count;
    }
    
    // ==================== GETTERS FOR TESTS ====================
    
    public ArrayList<String> getSentMessages() {
        return sentMessages;
    }
    
    public ArrayList<String> getDisregardedMessages() {
        return disregardedMessages;
    }
    
    public ArrayList<String> getStoredMessages() {
        return storedMessages;
    }
    
    public ArrayList<String> getMessageHashes() {
        return messageHashes;
    }
    
    public ArrayList<String> getMessageIds() {
        return messageIds;
    }
    
    public ArrayList<String> getRecipients() {
        return recipients;
    }
    
    public int getTotalSent() {
        return totalSent;
    }
    
    public int getTotalDisregarded() {
        return totalDisregarded;
    }
    
    public int getTotalStored() {
        return totalStored;
    }
}
