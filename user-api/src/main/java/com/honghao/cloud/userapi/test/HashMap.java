package com.honghao.cloud.userapi.test;

import lombok.Data;

/**
 * @author chenhonghao
 * @date 2020-01-16 09:04
 */
@Data
public class HashMap<K,V> implements Map<K,V> {
    private int lenght;
    private double loader;

    private static int defaultLenght = 16;
    private static double defaultLoader = 0.75d;

    private Entry<K,V>[] tables ;

    public HashMap(int lenght,double loader) {
        this.lenght = lenght;
        this.loader = loader;
        tables = new Entry[lenght];
    }

    public HashMap() {
        this(defaultLenght,defaultLoader);
    }
    public HashMap(int lenght){
        this(lenght,defaultLoader);
    }

    @Override
    public void put(K k, V v) {

        int index = hash(k);
        System.out.println("hashCode="+k.hashCode()+ ",index="+index);
        Entry<K,V> entry = tables[index];
        if (entry!=null){
            tables[index] = addEntry(hash(k),k,v,entry);
        }else {
            tables[index] = addEntry(hash(k),k,v,null);
        }

    }

    private Entry<K,V> addEntry(int hashCode, K k, V v, Entry<K, V> next){
        return new Entry<>(hashCode, k, v, next);
    }

    @Override
    public V get(K k) {
        int index = hash(k);
        Entry<K,V> entry ;
        for (entry = tables[index];entry!=null;entry = entry.next){
            if (entry.hashCode==hash(k) && (k.equals(entry.getKey()) || k == entry.getKey())){
                return entry.getValue();
            }
        }
        return null;
    }

    private int hash(K k){
        return Math.abs(k.hashCode() & (lenght-1));
    }

    static class Entry<K,V> implements Map.Entry<K,V>{
        private int hashCode;
        private K k;
        private V v;
        private Entry<K,V> next;

        public Entry(int hashCode, K k, V v, Entry<K, V> next) {
            this.hashCode = hashCode;
            this.k = k;
            this.v = v;
            this.next = next;
        }

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }
    }
}
