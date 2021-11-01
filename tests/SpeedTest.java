package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Helper;
import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.HashBiMapStorageStrategy;
import com.javarush.task.task33.task3310.strategy.HashMapStorageStrategy;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SpeedTest {
    public long getTimeToGetIds(Shortener shortener, Set<String> strings, Set<Long> ids) throws IOException, ClassNotFoundException {
        Date start = new Date();
        for (String str : strings){
            ids.add(shortener.getId(str));
        }
        Date stop = new Date();
        return stop.getTime() - start.getTime();
    }
    public long getTimeToGetStrings(Shortener shortener, Set<Long> ids, Set<String> strings) throws IOException, ClassNotFoundException {
        Date start = new Date();
        for(Long id : ids){
            strings.add(shortener.getString(id));
        }
        Date stop = new Date();
        return stop.getTime() - start.getTime();
    }

    @Test
    public void testHashMapStorage() throws IOException, ClassNotFoundException {
        Shortener shortener1 = new Shortener(new HashMapStorageStrategy());
        Shortener shortener2 = new Shortener(new HashBiMapStorageStrategy());

        Set<String> origStrings = new HashSet<>();
        Set<Long> ids = new HashSet<>();

        for(int i = 0; i<10000; i++){
            origStrings.add(Helper.generateRandomString());
        }

        long timeGetIdHashMap = getTimeToGetIds(shortener1, origStrings, ids);
        long timeGetIdHashBiMap = getTimeToGetIds(shortener2, origStrings, ids);
        Assert.assertTrue(timeGetIdHashMap > timeGetIdHashBiMap);

        long timeGetStringsHashMap = getTimeToGetStrings(shortener1, ids, new HashSet<>());
        long timeGetStringsHashBiMap = getTimeToGetStrings(shortener2, ids, new HashSet<>());
        Assert.assertEquals(timeGetStringsHashMap,timeGetStringsHashBiMap,30);
    }
}
