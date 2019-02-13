package HashMap;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyHashMap<K, V> {
    private MyEntry<K, V>[] buckets;
    private int size;
    private static final int INITIAL_CAPACITY = 16;

    public MyHashMap() {
        this(INITIAL_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public MyHashMap(int capacity) {
        buckets = new MyEntry[capacity];
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(K key) {
        MyEntry currentEntry = buckets[getHash(key)];

        while(currentEntry != null) {
            if (currentEntry.key.equals(key)) {
                return true;
            } else {
                currentEntry = currentEntry.next;
            }
        }

        return false;
    }


    public boolean containsValue(V value) {
        for (MyEntry entry : entrySet()) {
            if (entry.value == value) {
                return true;
            }
        }
        return false;
    }

    public V get(K key) {
        return buckets[getHash(key)].value;
    }

    public MyEntry put(K key, V value) {

        MyEntry<K, V> newEntry = new MyEntry<>(key, value);

        int bucket = getHash(key);
        MyEntry<K, V> currentEntry = buckets[bucket];

        if (currentEntry == null) {
            buckets[bucket] = newEntry;
            size++;
        } else {

            while (currentEntry.next != null) {
                if (currentEntry.key.equals(key)) {
                    currentEntry.value = value;
                    return currentEntry;
                } else {
                    currentEntry = currentEntry.next;
                }
            }

            if (currentEntry.key.equals(key)) {
                currentEntry.value = value;
            } else {
                currentEntry.next = newEntry;
                size++;
            }
        }
        return currentEntry;
    }

    public K remove(K key) {
        MyEntry<K, V> currentEntry = buckets[getHash(key)];

        while (currentEntry != null) {
            if (currentEntry.key.equals(key)) {
                currentEntry = currentEntry.next;
                size--;
                return key;
            } else {
                currentEntry = currentEntry.next;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public void putAll(MyHashMap<K, V> m) {
        for (MyEntry<K, V> entry : m.entrySet()) {
            this.put(entry.key, entry.value);
        }
    }

    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = null;
        }
    }

    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();

        for (MyEntry<K, V> entry : buckets) {
            while(entry != null) {
                keySet.add(entry.key);
                entry = entry.next;
            }
        }
        return keySet;
    }

    public Set<V> valueSet() {
        Set<V> valueSet = new HashSet<>();
        for (MyEntry<K, V> entry : buckets) {
            while(entry.next != null) {
                valueSet.add(entry.value);
                entry = entry.next;
            }
        }
        return valueSet;
    }

    public Set<MyEntry> entrySet() {

        Set<MyEntry> entrySet = new HashSet<>();
        for (MyEntry<K, V> entry : buckets) {
            while (entry != null) {
                entrySet.add(entry);
                entry = entry.next;
            }
        }
        return entrySet;
    }

    public int getHash(K key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    private class MyEntry<K, V> {
        private K key;
        private V value;
        private MyEntry<K, V> next;

        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public String toString() {
            return String.format("%s, %s", key, value);
        }
    }
}
