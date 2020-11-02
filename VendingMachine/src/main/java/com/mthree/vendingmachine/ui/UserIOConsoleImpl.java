package com.mthree.vendingmachine.ui;

import java.util.Scanner;

/**
 *
 * @author kylerudy
 */
public class UserIOConsoleImpl implements UserIO {

    private Scanner console = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public int readInt(String prompt) {
        String stringValue = null;
        int number = 0;
        boolean validInput = false;
        do {
            try {
                stringValue = readString(prompt);
                number = Integer.parseInt(stringValue);
                validInput = true;
            } catch (NumberFormatException e) {
                print(stringValue + " is not valid input, please try again");
            }
        } while (!validInput);
        return number;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int number = 0;
        do {
            number = readInt(prompt);
            if (number < min || number > max) {
                // message is printed because readInt() will not print if input is number but out off range
                print(number + " is not valid input, please try again");
            }
        } while (number < min || number > max);
        return number;
    }

    @Override
    public String readString(String prompt) {
        String userInput = null;
        boolean validInput = false;
        do {
            print(prompt);
            userInput = console.nextLine();
            if (userInput.isBlank()) {
                print("Input can not be blank, please try again");
            } else {
                validInput = true;
            }
        } while (!validInput);

        return userInput;
    }

    @Override
    public String readEnter(String prompt) {
        print(prompt);
        return console.nextLine();
    }

    @Override
    public String readMenuOption(String prompt) {
        String userInput = null;
        boolean validInput = false;
        do {
            print(prompt);
            userInput = console.nextLine();
            if (userInput.equals("0") || userInput.equals("+")) {
                validInput = true;
            } else {
                print("invalid option, please try again");
            }
        } while (!validInput);

        return userInput;
    }

    @Override
    public void printSameLine(String message) {
        System.out.print(message + ", ");
    }
}
