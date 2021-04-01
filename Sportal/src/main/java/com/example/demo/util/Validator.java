package com.example.demo.util;

import com.example.demo.exeptions.BadRequestException;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

public class Validator {


    public static boolean validateString(String text) {
        return text != null && !text.isEmpty();
    }

    public static boolean validUsername(String username) {
        String regexUsername = "(\\S){5,}";
        if (validateString(username) && username.matches(regexUsername)) {
            return true;
        }
        return false;
    }

    public static boolean validPassword(String password){
        String regPassword = "(\\S){5,}";
        if (password.matches(regPassword)){
            return true;
        }
        return false;

    }

    public static boolean validEMail(String email) {
        String regEmail = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}".toLowerCase();
        if (email.matches(regEmail)){
            return true;
        }
        return false;
    }

    public static boolean checkForPositiveNum(int a) {
        if (a < 0) {
            System.out.println("Please enter correct number");
            return false;
        }
        return a > 0;
    }



}

