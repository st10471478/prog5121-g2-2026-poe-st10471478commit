package com.student.chatapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 Test Class for Message
 * Tests all message methods with exact test data from assignment
 * 
 * @author Student
 * @version 1.0
 */
public class MessageTest {
    
    private Message message;
    
    @BeforeEach
    public void setUp() {
        // Reset counter before each test for consistent results
        Message.resetCounter();
        message = new Message();
    }
    
    // ==================== MESSAGE LENGTH TESTS (assertEquals) ====================
    
    /**
     * Test: Message should not be more than 250 characters - Success
     * Test Data: "Hi Mike, can you join us for dinner tonight?"
     * Expected: "Message ready to send."
     */
    @Test
    public void testCheckMessageLength_Success() {
        String testMessage = "Hi Mike, can you join us for dinner tonight?";
        String result = message.checkMessageLength(testMessage);
        assertEquals("Message ready to send.", result, 
            "Valid message under 250 chars should return ready message");
    }
    
    /**
     * Test: Message exceeds 250 characters - Failure
     * Expected: "Message exceeds 250 characters by X; please reduce the size."
     */
    @Test
    public void testCheckMessageLength_Failure() {
        // Create a message of 260 characters (exceeds by 10)
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 260; i++) {
            longMessage.append("a");
        }
        
        String result = message.checkMessageLength(longMessage.toString());
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.", result,
            "Message exceeding 250 chars should show excess count");
    }
    
    // ==================== RECIPIENT CELL TESTS (assertEquals) ====================
    
    /**
     * Test: Recipient number correctly formatted - Success
     * Test Data: "+27718693002"
     * Expected: "Cell phone number successfully captured."
     */
    @Test
    public void testCheckRecipientCell_Success() {
        String result = message.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result,
            "Valid international number should return success");
    }
    
    /**
     * Test: Recipient number incorrectly formatted - Failure
     * Test Data: "08575975889"
     * Expected: Error message about international code
     */
    @Test
    public void testCheckRecipientCell_Failure() {
        String result = message.checkRecipientCell("08575975889");
        assertTrue(result.contains("incorrectly formatted"),
            "Number without + code should return error");
    }
    
    // ==================== MESSAGE HASH TESTS (assertEquals) ====================
    
    /**
     * Test: Message hash is correct
     * Test Data from Test Case 1:
     * - Message ID: "0012345678" (first 2 = "00")
     * - Message Number: 0
     * - Message: "Hi Mike, can you join us for dinner tonight?"
     * - Expected: "00:0:HITONIGHT"
     */
    @Test
    public void testCreateMessageHash_TestCase1() {
        String messageId = "0012345678";
        int msgNum = 0;
        String text = "Hi Mike, can you join us for dinner tonight?";
        
        String result = message.createMessageHash(messageId, msgNum, text);
        assertEquals("00:0:HITONIGHT", result,
            "Hash should be first 2 of ID : msgNum : FIRSTWORD+LASTWORD in caps");
    }
    
    /**
     * Test: Message hash with different data
     * Test Data: "Hi Keegan, did you receive the payment?"
     * Expected: First 2 of ID : 1 : HI + PAYMENT
     */
    @Test
    public void testCreateMessageHash_TestCase2() {
        String messageId = "0098765432";
        int msgNum = 1;
        String text = "Hi Keegan, did you receive the payment?";
        
        String result = message.createMessageHash(messageId, msgNum, text);
        assertEquals("00:1:HIPAYMENT", result,
            "Hash should correctly extract first and last words");
    }
    
    // ==================== MESSAGE ID TESTS (assertEquals) ====================
    
    /**
     * Test: Message ID is created and is 10 digits
     * Expected: "Message ID generated: [10-digit ID]"
     */
    @Test
    public void testMessageID_Created() {
        Message msg = new Message();
        String id = msg.getMessageId();
        
        assertNotNull(id, "Message ID should not be null");
        assertEquals(10, id.length(), "Message ID should be exactly 10 characters");
        assertTrue(id.matches("\\d{10}"), "Message ID should contain only digits");
    }
    
    /**
     * Test: checkMessageID method
     */
    @Test
    public void testCheckMessageID() {
        assertTrue(message.checkMessageID("1234567890"), "10 char ID should be valid");
        assertFalse(message.checkMessageID("12345678901"), "11 char ID should be invalid");
    }
    
    // ==================== SEND MESSAGE TESTS (assertEquals) ====================
    
    /**
     * Test: User selected 'Send Message'
     * Expected: "Message successfully sent."
     */
    @Test
    public void testSendMessage_Send() {
        String result = message.sendMessage("send");
        assertEquals("Message successfully sent.", result,
            "Send choice should return success message");
    }
    
    /**
     * Test: User selected 'Disregard Message'
     * Expected: "Press 0 to delete the message."
     */
    @Test
    public void testSendMessage_Disregard() {
        String result = message.sendMessage("disregard");
        assertEquals("Press 0 to delete the message.", result,
            "Disregard choice should return delete message");
    }
    
    /**
     * Test: User selected 'Store Message'
     * Expected: "Message successfully stored."
     */
    @Test
    public void testSendMessage_Store() {
        String result = message.sendMessage("store");
        assertEquals("Message successfully stored.", result,
            "Store choice should return stored message");
    }
    
    // ==================== ADDITIONAL TESTS (assertTrue/assertFalse) ====================
    
    /**
     * Test: Message number auto-increments
     * Expected: First message = 1, Second message = 2
     */
    @Test
    public void testMessageNumber_Increment() {
        Message.resetCounter();
        
        Message msg1 = new Message();
        assertEquals(1, msg1.getNumMessagesSent(), "First message should be number 1");
        
        Message msg2 = new Message();
        assertEquals(2, msg2.getNumMessagesSent(), "Second message should be number 2");
    }
    
    /**
     * Test: Total messages counter
     */
    @Test
    public void testReturnTotalMessages() {
        Message.resetCounter();
        
        new Message();
        new Message();
        new Message();
        
        assertEquals(3, Message.returnTotalMessages(), "Total should count all messages created");
    }
    
    /**
     * Test: Print message returns formatted string
     */
    @Test
    public void testPrintMessage() {
        message.setRecipient("+27718693002");
        message.setMessageText("Hi Mike, can you join us for dinner tonight?");
        message.setMessageHash("00:1:HITONIGHT");
        
        String result = message.printMessage();
        
        assertTrue(result.contains("Message ID:"), "Should contain Message ID");
        assertTrue(result.contains("Message Hash:"), "Should contain Message Hash");
        assertTrue(result.contains("Recipient:"), "Should contain Recipient");
        assertTrue(result.contains("Message:"), "Should contain Message text");
    }
    
    /**
     * Test: Store message creates JSON file
     */
    @Test
    public void testStoreMessage() {
        message.setRecipient("+27718693002");
        message.setMessageText("Test message for storage");
        message.setMessageHash("00:1:TESTMESSAGE");
        
        boolean result = message.storeMessage();
        assertTrue(result, "Store message should return true on success");
    }
}