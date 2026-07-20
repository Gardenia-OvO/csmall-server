package cn.hqu.csmall.product;

import java.util.HashMap;

public class SyncHashMap<K,V> extends HashMap<K,V> {

    @Override
    public synchronized V put(K key, V value) {
        return super.put(key, value);
    }

    @Override
    public synchronized V get(Object key) {
        return super.get(key);
    }
}
