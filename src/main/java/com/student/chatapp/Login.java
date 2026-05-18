/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.student.chatapp;

/**
 * Login class handles user registration and authentication
 * for the Chat Application - PROG5121 Part 1
 * 
 * @author Student
 * @version 1.0
 */
public class Login {
    
    // Instance variables to store user details
    private String username;
    private String password;
    private String cellPhone;
    private String firstName;
    private String lastName;
    
    /**
     * Default constructor
     */
    public Login() {
    }
    
    /**
     * Constructor with names
     * @param firstName user's first name
     * @param lastName user's last name
     */
    public Login(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Checks if username contains underscore and is no more than 5 characters
     * @param username the username to check
     * @return true if valid, false otherwise
     */
    public boolean checkUserName(String username) {
        // Must contain underscore AND be 5 characters or less
        return username != null && username.contains("_") && username.length() <= 5;
    }
    
    /**
     * Checks password complexity requirements:
     * - At least 8 characters
     * - Contains capital letter
     * - Contains number
     * - Contains special character
     * @param password the password to check
     * @return true if meets complexity, false otherwise
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasCapital = true;
            }
            if (Character.isDigit(c)) {
                hasNumber = true;
            }
            if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        
        return hasCapital && hasNumber && hasSpecial;
    }
    
    /**
     * Validates South African cell phone number with international code
     * Matches assignment test data: +27838968976
     * 
     * Test Data: +27838968976 (valid - starts with +, contains only digits after)
     * Test Data: 08966553 (invalid - no country code, no +)
     * 
     * Regex pattern adapted from: Regexlib (2024) South African phone number validation.
     * Available at: https://regexlib.com/Search.aspx?k=phone (Accessed: 11 April 2026).
     * 
     * @param cellPhone the cell phone number to validate
     * @return true if contains + and country code
     */
    public boolean checkCellPhoneNumber(String cellPhone) {
        if (cellPhone == null || cellPhone.isEmpty()) {
            return false;
        }
        
        // Must start with + (international code)
        if (!cellPhone.startsWith("+")) {
            return false;
        }
        
        // Must contain only digits after the +
        // Test data: +27838968976 (valid)
        // Invalid: 08966553 (no +)
        return cellPhone.matches("^\\+[0-9]+$");
    }
    
    /**
     * Registers user with validation for all fields
     * @param username desired username
     * @param password desired password
     * @param cellPhone cell phone number
     * @return formatted message with validation results
     */
    public String registerUser(String username, String password, String cellPhone) {
        boolean validUsername = checkUserName(username);
        boolean validPassword = checkPasswordComplexity(password);
        boolean validCell = checkCellPhoneNumber(cellPhone);
        
        StringBuilder message = new StringBuilder();
        
        // Username validation message
        if (!validUsername) {
            message.append("Username is not correctly formatted; please ensure that your username ")
                   .append("contains an underscore and is no more than five characters in length.");
        } else {
            message.append("Username successfully captured.");
            this.username = username;
        }
        message.append("\n");
        
        // Password validation message
        if (!validPassword) {
            message.append("Password is not correctly formatted; please ensure that the password ")
                   .append("contains at least eight characters, a capital letter, a number, and a special character.");
        } else {
            message.append("Password successfully captured.");
            this.password = password;
        }
        message.append("\n");
        
        // Cell phone validation message
        if (!validCell) {
            message.append("Cell phone number incorrectly formatted or does not contain an international code; ")
                   .append("please correct the number and try again.");
        } else {
            message.append("Cell phone number successfully added.");
            this.cellPhone = cellPhone;
        }
        
        return message.toString().trim();
    }
    
    /**
     * Verifies login credentials match registered details
     * @param enteredUsername username entered at login
     * @param enteredPassword password entered at login
     * @return true if credentials match, false otherwise
     */
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername != null && enteredPassword != null &&
               enteredUsername.equals(this.username) && 
               enteredPassword.equals(this.password);
    }
    
    /**
     * Returns appropriate login status message
     * @param loginSuccess whether login was successful
     * @return welcome message or error message
     */
    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
    
    // Getter methods
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getCellPhone() {
        return cellPhone;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    // Setter methods
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }
}