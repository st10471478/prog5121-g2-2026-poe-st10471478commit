package com.student.chatapp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 Test Class for Login
 * Tests all validation methods with exact test data from assignment
 * 
 * @author Student
 * @version 1.0
 */
public class LoginTest {
    
    private Login login;
    
    @BeforeEach
    public void setUp() {
        login = new Login();
    }
    
    // ==================== USERNAME TESTS ====================
    
    /**
     * Test: Username is correctly formatted
     * Test Data: "kyl_1"
     * Expected: true
     */
    @Test
    public void testCheckUserName_CorrectlyFormatted() {
        assertTrue(login.checkUserName("kyl_1"), 
            "Username with underscore and <=5 chars should return true");
    }
    
    /**
     * Test: Username incorrectly formatted - no underscore
     * Test Data: "kyle!!!!!!!"
     * Expected: false
     */
    @Test
    public void testCheckUserName_IncorrectlyFormatted_NoUnderscore() {
        assertFalse(login.checkUserName("kyle!!!!!!!"), 
            "Username without underscore should return false");
    }
    
    /**
     * Test: Username incorrectly formatted - too long
     * Test Data: "kylong"
     * Expected: false (more than 5 chars)
     */
    @Test
    public void testCheckUserName_IncorrectlyFormatted_TooLong() {
        assertFalse(login.checkUserName("kylong"), 
            "Username longer than 5 characters should return false");
    }
    
    // ==================== PASSWORD TESTS ====================
    
    /**
     * Test: Password meets complexity requirements
     * Test Data: "Ch&&sec@ke99!"
     * Expected: true
     */
    @Test
    public void testCheckPasswordComplexity_MeetsRequirements() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"), 
            "Valid password with 8+ chars, capital, number, special should return true");
    }
    
    /**
     * Test: Password does not meet complexity requirements
     * Test Data: "password"
     * Expected: false (no capital, no number, no special, too short)
     */
    @Test
    public void testCheckPasswordComplexity_DoesNotMeetRequirements() {
        assertFalse(login.checkPasswordComplexity("password"), 
            "Simple password without complexity should return false");
    }
    
    /**
     * Test: Password too short
     * Expected: false
     */
    @Test
    public void testCheckPasswordComplexity_TooShort() {
        assertFalse(login.checkPasswordComplexity("Pass1!"), 
            "Password with less than 8 characters should return false");
    }
    
    /**
     * Test: Password missing special character
     * Expected: false
     */
    @Test
    public void testCheckPasswordComplexity_NoSpecialChar() {
        assertFalse(login.checkPasswordComplexity("Password123"), 
            "Password without special character should return false");
    }
    
    // ==================== CELL PHONE TESTS ====================
    
    /**
     * Test: Cell phone correctly formatted
     * Test Data: "+27838968976"
     * Expected: true
     */
    @Test
    public void testCheckCellPhoneNumber_CorrectlyFormatted() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"), 
            "Valid SA international number should return true");
    }
    
    /**
     * Test: Cell phone incorrectly formatted - no country code
     * Test Data: "08966553"
     * Expected: false
     */
    @Test
    public void testCheckCellPhoneNumber_IncorrectlyFormatted() {
        assertFalse(login.checkCellPhoneNumber("08966553"), 
            "Number without +27 should return false");
    }
    
    /**
     * Test: Cell phone null
     * Expected: false
     */
    @Test
    public void testCheckCellPhoneNumber_Null() {
        assertFalse(login.checkCellPhoneNumber(null), 
            "Null phone number should return false");
    }
    
    /**
     * Test: Cell phone empty
     * Expected: false
     */
    @Test
    public void testCheckCellPhoneNumber_Empty() {
        assertFalse(login.checkCellPhoneNumber(""), 
            "Empty phone number should return false");
    }
    
    /**
     * Test: Cell phone missing + but has numbers
     * Expected: false
     */
    @Test
    public void testCheckCellPhoneNumber_MissingPlus() {
        assertFalse(login.checkCellPhoneNumber("27838968976"), 
            "Number without + should return false");
    }
    
    // ==================== LOGIN TESTS ====================
    
    /**
     * Test: Login successful
     * Expected: true
     */
    @Test
    public void testLoginUser_Success() {
        Login loginWithUser = new Login("John", "Doe");
        loginWithUser.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        assertTrue(loginWithUser.loginUser("kyl_1", "Ch&&sec@ke99!"), 
            "Correct username and password should return true");
    }
    
    /**
     * Test: Login failed
     * Expected: false
     */
    @Test
    public void testLoginUser_Failure() {
        Login loginWithUser = new Login("John", "Doe");
        loginWithUser.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        assertFalse(loginWithUser.loginUser("wrong", "wrong"), 
            "Incorrect username and password should return false");
    }
    
    // ==================== LOGIN STATUS MESSAGE TESTS ====================
    
    /**
     * Test: Return login status - successful
     * Expected: "Welcome <first name>, <last name> it is great to see you again."
     */
    @Test
    public void testReturnLoginStatus_Success() {
        Login loginWithNames = new Login("John", "Doe");
        String expected = "Welcome John Doe, it is great to see you again.";
        String actual = loginWithNames.returnLoginStatus(true);
        assertEquals(expected, actual, 
            "Successful login should return welcome message with full name");
    }
    
    /**
     * Test: Return login status - failed
     * Expected: "Username or password incorrect, please try again."
     */
    @Test
    public void testReturnLoginStatus_Failure() {
        Login loginWithNames = new Login("John", "Doe");
        String expected = "Username or password incorrect, please try again.";
        String actual = loginWithNames.returnLoginStatus(false);
        assertEquals(expected, actual, 
            "Failed login should return error message");
    }
    
    // ==================== REGISTRATION MESSAGE TESTS ====================
    
    /**
     * Test: Register user with all valid inputs
     * Expected: All success messages
     */
    @Test
    public void testRegisterUser_AllValid() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976");
        
        assertTrue(result.contains("Username successfully captured"), 
            "Should contain username success message");
        assertTrue(result.contains("Password successfully captured"), 
            "Should contain password success message");
        assertTrue(result.contains("Cell phone number successfully added"), 
            "Should contain cell phone success message");
    }
    
    /**
     * Test: Register user with all invalid inputs
     * Expected: All error messages
     */
    @Test
    public void testRegisterUser_AllInvalid() {
        String result = login.registerUser("toolong", "weak", "12345");
        
        assertTrue(result.contains("Username is not correctly formatted"), 
            "Should contain username error");
        assertTrue(result.contains("Password is not correctly formatted"), 
            "Should contain password error");
        assertTrue(result.contains("Cell phone number incorrectly formatted"), 
            "Should contain cell phone error");
    }
    
    /**
     * Test: Register user with mixed valid/invalid
     */
    @Test
    public void testRegisterUser_Mixed() {
        String result = login.registerUser("kyl_1", "weakpass", "+27838968976");
        
        assertTrue(result.contains("Username successfully captured"), 
            "Valid username should be captured");
        assertTrue(result.contains("Password is not correctly formatted"), 
            "Invalid password should show error");
        assertTrue(result.contains("Cell phone number successfully added"), 
            "Valid cell should be added");
    }
}