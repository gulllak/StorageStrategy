package com.javarush.task.task33.task3310;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Helper {
    public static String generateRandomString(){
        SecureRandom rand = new SecureRandom();
        return new BigInteger(130, rand).toString(36);
    }

    public static void printMessage(String message){
        System.out.println(message);
    }

}
