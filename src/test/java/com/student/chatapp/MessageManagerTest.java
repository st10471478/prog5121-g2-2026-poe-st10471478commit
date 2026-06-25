/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.student.chatapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 Test Class for MessageManager (Part 3)
 * Tests all array operations and report features
 * 
 * @author Student
 * @version 1.0
 */
public class MessageManagerTest {
    
    private MessageManager manager;
    
    @BeforeEach
    public void setUp() {
        manager = new MessageManager();
    }
    
    // ==================== TEST DATA ====================
    
    // Message 1: Sent
    // Recipient: +27834557896
    // Message: "Did you get the cake?"
    // Flag: Sent
    
    // Message 2: Stored
    // Recipient: +27838884567
    // Message: "Where are you? You are late! I have asked you to be on time."
    // Flag: Stored
    
    // Message 3: Disregard
    // Recipient: +27834484567
    // Message: "Yohoooo, I am at your gate."
    // Flag: Disregard
    
    // Message 4: Stored
    // Recipient: +27838884567
    // Message: "It is dinner time!"
    // Flag: Stored
    
    // Message 5: Stored
    // Recipient: +27838884567
    // Message: "Ok, I am leaving without you."
    // Flag: Stored
    
    // ==================== ARRAY POPULATION TESTS ====================
    
    /**
     * Test: SentMessages array correctly populated
     * Expected: Contains "Did you get the cake?"
     */
    @Test
    public void testSentMessagesArrayPopulated() {
        manager.addSentMessage("Did you get the cake?", "00:1:DIDCAKE", "0012345678", "+27834557896");
        
        assertFalse(manager.getSentMessages().isEmpty(), "Sent messages should not be empty");
        assertEquals("Did you get the cake?", manager.getSentMessages().get(0), 
            "Sent message should match test data");
    }
    
    /**
     * Test: StoredMessages array correctly populated
     * Expected: Contains expected test data messages
     */
    @Test
    public void testStoredMessagesArrayPopulated() {
        manager.addStoredMessage("It is dinner time!", "00:4:ITTIME", "0045678901", "+27838884567");
        manager.addStoredMessage("Ok, I am leaving without you.", "00:5:OKYOU", "0056789012", "+27838884567");
        
        assertEquals(2, manager.getStoredMessages().size(), "Should have 2 stored messages");
        assertEquals("It is dinner time!", manager.getStoredMessages().get(0), 
            "First stored message should match");
    }
    
    // ==================== LONGEST MESSAGE TEST ====================
    
    /**
     * Test: Display the longest message
     * Test Data: Messages 1-4
     * Expected: Longest message identified correctly
     */
    @Test
    public void testFindLongestMessage() {
        manager.addStoredMessage("Did you get the cake?", "00:1:DIDCAKE", "0012345678", "+27834557896");
        manager.addStoredMessage("Where are you? You are late! I have asked you to be on time.", 
            "00:2:WHERETIME", "0023456789", "+27838884567");
        manager.addStoredMessage("Yohoooo, I am at your gate.", "00:3:YOHOOOGATE", "0034567890", "+27834484567");
        manager.addStoredMessage("It is dinner time!", "00:4:ITTIME", "0045678901", "+27838884567");
        
        String result = manager.findLongestMessage();
        
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."), 
            "Should find the longest message");
        assertTrue(result.contains("58"), "Should show correct length");
    }
    
    // ==================== SEARCH BY MESSAGE ID TEST ====================
    
    /**
     * Test: Search for message ID
     * Test Data: Message 4
     * Expected: "It is dinner time!"
     */
    @Test
    public void testSearchByMessageId_Found() {
        manager.addStoredMessage("It is dinner time!", "00:4:ITTIME", "0045678901", "+27838884567");
        
        String result = manager.searchByMessageId("0045678901");
        
        assertTrue(result.contains("It is dinner time!"), "Should find message by ID");
        assertTrue(result.contains("+27838884567"), "Should show recipient");
    }
    
    /**
     * Test: Search for message ID not found
     * Expected: "Message ID not found"
     */
    @Test
    public void testSearchByMessageId_NotFound() {
        String result = manager.searchByMessageId("9999999999");
        assertEquals("Message ID not found: 9999999999", result, 
            "Should return not found message");
    }
    
    // ==================== SEARCH BY RECIPIENT TEST ====================
    
    /**
     * Test: Search all messages for particular recipient
     * Test Data: +27838884567
     * Expected: Two messages for this recipient
     */
    @Test
    public void testSearchByRecipient_Found() {
        manager.addStoredMessage("Where are you? You are late! I have asked you to be on time.", 
            "00:2:WHERETIME", "0023456789", "+27838884567");
        manager.addStoredMessage("Ok, I am leaving without you.", 
            "00:5:OKYOU", "0056789012", "+27838884567");
        
        String result = manager.searchByRecipient("+27838884567");
        
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."), 
            "Should find first message");
        assertTrue(result.contains("Ok, I am leaving without you."), 
            "Should find second message");
    }
    
    /**
     * Test: Search recipient with no messages
     * Expected: "No messages found"
     */
    @Test
    public void testSearchByRecipient_NotFound() {
        String result = manager.searchByRecipient("+27000000000");
        assertEquals("No messages found for recipient: +27000000000", result, 
            "Should return no messages found");
    }
    
    // ==================== DELETE BY HASH TEST ====================
    
    /**
     * Test: Delete a message using message hash
     * Expected: "successfully deleted"
     */
    @Test
    public void testDeleteByHash_Success() {
        manager.addStoredMessage("Where are you? You are late! I have asked you to be on time.", 
            "00:2:WHERETIME", "0023456789", "+27838884567");
        
        String result = manager.deleteByHash("00:2:WHERETIME");
        
        assertTrue(result.contains("successfully deleted"), "Should confirm deletion");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."), 
            "Should show deleted message");
        assertEquals(0, manager.getStoredMessages().size(), "Array should be empty after delete");
    }
    
    /**
     * Test: Delete by hash not found
     * Expected: "Message hash not found"
     */
    @Test
    public void testDeleteByHash_NotFound() {
        String result = manager.deleteByHash("INVALID:HASH");
        assertEquals("Message hash not found: INVALID:HASH", result, 
            "Should return hash not found");
    }
    
    // ==================== REPORT TEST ====================
    
    /**
     * Test: Display report
     * Expected: Contains all necessary information
     */
    @Test
    public void testDisplayReport() {
        manager.addSentMessage("Did you get the cake?", "00:1:DIDCAKE", "0012345678", "+27834557896");
        manager.addDisregardedMessage("Yohoooo, I am at your gate.");
        manager.addStoredMessage("It is dinner time!", "00:4:ITTIME", "0045678901", "+27838884567");
        
        String result = manager.displayReport();
        
        assertTrue(result.contains("MESSAGE REPORT"), "Should have report title");
        assertTrue(result.contains("Total Sent Messages: 1"), "Should show sent count");
        assertTrue(result.contains("Total Disregarded Messages: 1"), "Should show disregarded count");
        assertTrue(result.contains("Total Stored Messages: 1"), "Should show stored count");
        assertTrue(result.contains("Message ID:"), "Should show message details");
        assertTrue(result.contains("Message Hash:"), "Should show hash");
    }
    
    /**
     * Test: Empty report
     * Expected: "No stored messages"
     */
    @Test
    public void testDisplayReport_Empty() {
        String result = manager.displayReport();
        assertEquals("No stored messages to display in report.", result, 
            "Should return empty report message");
    }
    
    // ==================== JSON FILE READ TEST ====================
    
    /**
     * Test: Load from JSON file
     * Note: This test depends on stored_messages.json existing
     * If file doesn't exist, loadFromJsonFile returns 0
     */
    @Test
    public void testLoadFromJsonFile() {
        int loaded = manager.loadFromJsonFile();
        
        // File may or may not exist, so just check it doesn't crash
        assertTrue(loaded >= 0, "Should return 0 or more messages loaded");
    }
}
