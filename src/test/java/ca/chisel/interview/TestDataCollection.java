package ca.chisel.interview;

import java.util.*;

public class TestDataCollection implements Map<String,KeyValueObject> {
    final Map<String,KeyValueObject> backingMap;
    final List<KeyValueObject> backingList;
    public TestDataCollection(final int size) {
        backingMap = new HashMap<>(size);
        backingList = new ArrayList<>(size);
        //let's populate the backing map until we have the total size
        while (backingMap.size() < size) {
            final KeyValueObject object = new KeyValueObject();
            backingMap.put(object.getKey(),object);
            backingList.add(object);
        }

    }

    @Override
    public int size() {
        return backingMap.size();
    }

    @Override
    public boolean isEmpty() {
        return backingMap.isEmpty();
    }

    @Override
    public boolean containsKey(final Object o) {
        return backingMap.containsKey(o);
    }

    @Override
    public boolean containsValue(final Object o) {
        return backingMap.containsValue(o);
    }

    @Override
    public KeyValueObject get(final Object o) {
        return backingMap.get(o);
    }

    @Override
    public KeyValueObject put(final String s, final KeyValueObject keyValueObject) {
        return backingMap.put(s,keyValueObject);
    }

    @Override
    public KeyValueObject remove(final Object o) {
        return backingMap.remove(o);
    }




    @Override
    public void putAll(final Map<? extends String, ? extends KeyValueObject> map) {
        backingMap.putAll(map);
    }

    @Override
    public void clear() {
        backingMap.clear();
        backingList.clear();
    }

    @Override
    public Set<String> keySet() {
        return backingMap.keySet();
    }

    @Override
    public Collection<KeyValueObject> values() {
        return backingMap.values();
    }

    @Override
    public Set<Entry<String, KeyValueObject>> entrySet() {
        return backingMap.entrySet();
    }

    public List<KeyValueObject> getBackingList() {
        return backingList;
    }
}
