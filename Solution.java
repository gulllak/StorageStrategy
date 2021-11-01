package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        HashMapStorageStrategy hashMapStorageStrategy = new HashMapStorageStrategy();
        OurHashMapStorageStrategy ourHashMapStorageStrategy = new OurHashMapStorageStrategy();
        FileStorageStrategy fileStorageStrategy = new FileStorageStrategy();
        OurHashBiMapStorageStrategy ourHashBiMapStorageStrategy = new OurHashBiMapStorageStrategy();
        HashBiMapStorageStrategy hashBiMapStorageStrategy = new HashBiMapStorageStrategy();
        DualHashBidiMapStorageStrategy dualHashBidiMapStorageStrategy = new DualHashBidiMapStorageStrategy();
        testStrategy(hashMapStorageStrategy, 10000);
        testStrategy(ourHashMapStorageStrategy, 10000);
        testStrategy(fileStorageStrategy, 200);
        testStrategy(ourHashBiMapStorageStrategy, 10000);
        testStrategy(hashBiMapStorageStrategy, 10000);
        testStrategy(dualHashBidiMapStorageStrategy, 10000);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) throws IOException, ClassNotFoundException {
        Set<Long> setIds = new HashSet<>();
        for(String str : strings){
            setIds.add(shortener.getId(str));
        }
        return setIds;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) throws IOException, ClassNotFoundException {
        Set<String> setStrings = new HashSet<>();

        for(Long key : keys){
            setStrings.add(shortener.getString(key));
        }
        return setStrings;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) throws IOException, ClassNotFoundException {
        Helper.printMessage(strategy.getClass().getSimpleName() + ":");

        Set<String> tests = new HashSet<>();

        for(int i = 0; i< elementsNumber; i++){
            tests.add(Helper.generateRandomString());
        }
        Shortener shortener = new Shortener(strategy);
        Date start = new Date();
        Set<Long> keys = getIds(shortener, tests);
        Date stop = new Date();
        long time = stop.getTime() - start.getTime();
        Helper.printMessage("Время отработки метода getIds: " + time);

        Date start1 = new Date();
        Set<String> strings = getStrings(shortener, keys);
        Date stop1 = new Date();
        long time1 = stop1.getTime() - start1.getTime();
        Helper.printMessage("Время отработки метода getStrings: " + time1);

        if(tests.equals(strings)) Helper.printMessage("Тест пройден.");
            else Helper.printMessage("Тест не пройден.");

    }
}
