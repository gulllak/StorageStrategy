package com.javarush.task.task33.task3310.strategy;

public class OurHashMapStorageStrategy implements StorageStrategy{
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    int size;
    int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    float loadFactor = DEFAULT_LOAD_FACTOR;

    public int hash(Long k){
        return k.hashCode();
    }

    public int indexFor(int hash, int length){
        return hash & (length - 1);
    }

    public Entry getEntry(Long key){
        if(size == 0){
            return null;
        }
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        for(Entry entry = table[index]; entry != null; entry = entry.next){
            if(key.equals(entry.key)){
                return entry;
            }
        }
        return null;
    }

    @Override
    public void put(Long key, String value) {
        int hash = hash(key);
        int index = indexFor(hash,table.length);
        for(Entry entry = table[index]; entry != null; entry = entry.next){
            if(key.equals(entry.key)){
                entry.value = value;
                return;
            }
        }
        addEntry(hash,key,value,index);
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex){
        if ((size >= threshold)) {
            resize(2 * table.length);
            hash = hash(key);
            bucketIndex = indexFor(hash, table.length);
        }
        createEntry(hash, key, value, bucketIndex);
    }


    public void resize(int newCapacity){
        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }

    public void transfer(Entry[] newTable){
        int newCapacity = newTable.length;
        for(Entry e : table){
            while(e != null){
                Entry next = e.next;
                int indexInNewTable = indexFor(hash(e.getKey()), newCapacity);
                e.next = newTable[indexInNewTable];
                newTable[indexInNewTable] = e;
                e = next;
            }
        }
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex){
        Entry entry = table[bucketIndex];
        table[bucketIndex] = new Entry(hash,key, value, entry);
        size++;
    }


    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        for(Entry backet : table){
            for(Entry node = backet; node != null; node = node.next){
                if(node.value.equals(value)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Long getKey(String value) {
        for(Entry backet : table){
            for(Entry node = backet; node != null; node = node.next){
                if(node.value.equals(value)){
                    return node.getKey();
                }
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {
        Entry entry = getEntry(key);
        if(entry != null) {
            return entry.getValue();
        }
        return null;
    }
}
