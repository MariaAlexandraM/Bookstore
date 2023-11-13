package repository.book;

import java.util.*;

public class Cache<T> { // clasa generica; aici nu cream obiecte, atribuim sau incarcam
    private List<T> storage;

    public void save(List<T> entities) { // salveaza in cache
        this.storage = entities;
    }

    public List<T> load() { // scoate din cache
        System.out.println("Loaded from cache");
        return storage;
    }

    public void invalidateCache() { // invalidam cache daca se schimba datele din bd
        storage = null;
    }

    public boolean hasResult() {
        return (storage != null);
    }

}
