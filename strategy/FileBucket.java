package com.javarush.task.task33.task3310.strategy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    private Path path;

    public FileBucket() throws IOException {
        this.path = Files.createTempFile(Integer.toHexString(hashCode()), null);
        path.toFile().deleteOnExit();

        Files.deleteIfExists(path);
        Files.createFile(path);
    }

    public long getFileSize() throws IOException {
        return Files.size(path);
    }
    public void putEntry(Entry entry) throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path));
        outputStream.writeObject(entry);
    }
    public Entry getEntry() throws IOException, ClassNotFoundException {
        if(getFileSize()>0) {
            ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path));
            return (Entry) inputStream.readObject();
        }
        return null;
    }

    public void remove() throws IOException {
        Files.delete(path);
    }
}
