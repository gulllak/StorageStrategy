package com.javarush.task.task33.task3310;

import com.javarush.task.task33.task3310.strategy.StorageStrategy;

import java.io.IOException;

public class Shortener {
    private Long lastId = 0L;
    private StorageStrategy storageStrategy;

    public Shortener(StorageStrategy storageStrategy){
        this.storageStrategy = storageStrategy;
    }

    public synchronized Long getId(String string) throws IOException, ClassNotFoundException {
        if (!storageStrategy.containsValue(string)) {
            lastId++;
            storageStrategy.put(lastId, string);
        }
        return storageStrategy.getKey(string);
    }

    public synchronized String getString(Long id) throws IOException, ClassNotFoundException {

        return storageStrategy.getValue(id);
    }
}
