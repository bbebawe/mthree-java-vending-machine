/*
 * @Copyright Beshoy Bebawe 2020.
 */
package com.mthree.vendingmachine.ui;

/**
 *
 * @author beshoy
 */
public interface UserIO {

    /**
     * prints message to console
     *
     * @param msg String - message
     */
    void print(String message);

    /**
     * prints message without new line
     * @param message message to print
     */
    void printSameLine(String message);
    /**
     * prompts message to user and reads integer
     *
     * @param prompt String message
     * @return Integer - number from user input
     */
    int readInt(String prompt);

    /**
     * prompts message to user and reads Integer and read number between min and
     * max value
     *
     * @param prompt String message
     * @param min Integer - min number allowed
     * @param max Integer - max number allowed
     * @return Integer - number from user input between min and max values
     */
    int readInt(String prompt, int min, int max);

    /**
     * prompts message to user and reads String
     *
     * @param prompt String - message
     * @return String - user input
     */
    String readString(String prompt);

    /**
     * prompts user to hit enter to continue
     *
     * @param prompt prompt
     * @return String - blank input
     */
    String readEnter(String prompt);
    
    /** 
     * prompts user for next operation option
     * @param prompt prompt message
     * @return + or 0 as next operation
     */
    String readMenuOption(String prompt);

}
