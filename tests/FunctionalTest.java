package com.javarush.task.task33.task3310.tests;

import com.javarush.task.task33.task3310.Shortener;
import com.javarush.task.task33.task3310.strategy.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class FunctionalTest {
    public void testStorage(Shortener shortener) throws IOException, ClassNotFoundException {
        String a = "sdgsefafaf";
        String b = "jfjffsydhdj";
        String c = "sdgsefafaf";

        Long test1 = shortener.getId(a);
        Long test2 = shortener.getId(b);
        Long test3 = shortener.getId(c);

        Assert.assertNotEquals(test2, test1);
        Assert.assertNotEquals(test2, test3);
        Assert.assertEquals(test1,test3);

        Assert.assertEquals(a, shortener.getString(test1));
        Assert.assertEquals(b, shortener.getString(test2));
        Assert.assertEquals(c, shortener.getString(test3));
    }
    @Test
    public void testHashMapStorageStrategy() throws IOException, ClassNotFoundException {
        Shortener shortener = new Shortener(new HashMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testOurHashMapStorageStrategy() throws IOException, ClassNotFoundException {
        Shortener shortener = new Shortener(new OurHashMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testFileStorageStrategy() throws IOException, ClassNotFoundException {
        Shortener shortener = new Shortener(new FileStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testHashBiMapStorageStrategy() throws IOException, ClassNotFoundException {
        Shortener shortener = new Shortener(new HashBiMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testDualHashBidiMapStorageStrategy() throws IOException, ClassNotFoundException {
        Shortener shortener = new Shortener(new DualHashBidiMapStorageStrategy());
        testStorage(shortener);
    }
    @Test
    public void testOurHashBiMapStorageStrategy() throws IOException, ClassNotFoundException {
        Shortener shortener = new Shortener(new OurHashBiMapStorageStrategy());
        testStorage(shortener);
    }
}
