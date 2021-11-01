package com.javarush.task.task33.task3310.strategy;

import java.io.IOException;

public class FileStorageStrategy implements StorageStrategy{
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final long DEFAULT_BUCKET_SIZE_LIMIT = 10000;
    FileBucket[] table;
    int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    long maxBucketSize;

    public FileStorageStrategy() throws IOException {
        init();
    }

    public void init() throws IOException {
        table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
        for(int i = 0; i < table.length; i++){
            table[i] = new FileBucket();
        }
    }

    public int hash(Long k){
        return k.hashCode();
    }

    public int indexFor(int hash, int length){
        return hash & (length - 1);
    }

    public Entry getEntry(Long key) throws IOException, ClassNotFoundException {
        if(size == 0){
            return null;
        }
        int hash = hash(key);
        int index = indexFor(hash,table.length);
        for(Entry entry = table[index].getEntry(); entry != null; entry = entry.next){
            if(key.equals(entry.key)){
                return entry;
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(Long key) throws IOException, ClassNotFoundException {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) throws IOException, ClassNotFoundException {
        for(FileBucket backet : table){
            for(Entry entry = backet.getEntry(); entry != null; entry = entry.next){
                if(entry.value.equals(value)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void put(Long key, String value) throws IOException, ClassNotFoundException {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for(Entry e = table[index].getEntry(); e != null; e = e.next){
            if(key.equals(e.key)){
                e.value = value;
                return;
            }
        }
        addEntry(hash, key, value, index);
    }

    @Override
    public Long getKey(String value) throws IOException, ClassNotFoundException {
        for(FileBucket file : table){
            for(Entry entry = file.getEntry(); entry != null; entry = entry.next){
                if(entry.getValue().equals(value)){
                    return entry.getKey();
                }
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) throws IOException, ClassNotFoundException {
        Entry entry = getEntry(key);
        if(entry != null){
            return entry.getValue();
        }
        return null;
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) throws IOException, ClassNotFoundException {
        for (FileBucket fileBucket : table) {
            if (fileBucket.getFileSize() > getBucketSizeLimit()) {
                resize(2 * table.length);
            }
        }
        createEntry(hash, key, value, bucketIndex);
    }

    public void resize(int newCapacity) throws IOException, ClassNotFoundException {
        FileBucket[] newTable = new FileBucket[newCapacity];
        for(int i = 0; i< newTable.length; i++){
            newTable[i] = new FileBucket();
        }
        transfer(newTable);
        for (FileBucket fileBucket : table) {
            fileBucket.remove();
        }
        table = newTable;
    }

    public void transfer(FileBucket[] newTable) throws IOException, ClassNotFoundException {
        int newCapacity = newTable.length;
        maxBucketSize = 0;
        for(FileBucket file : table){
            Entry entry = file.getEntry();
            while (entry != null){
                Entry next = entry.next;
                int indexInNewTable = indexFor(hash(entry.getKey()), newCapacity);
                entry.next = newTable[indexInNewTable].getEntry();
                newTable[indexInNewTable].putEntry(entry);
                entry = next;
            }
        }
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) throws IOException, ClassNotFoundException {
        Entry entry = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, entry));
        size++;
    }


    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }
}
