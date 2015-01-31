package ru.datastructure.ycache;

import java.util.HashMap;
import java.util.Map;

import ru.datastructure.ycache.contract.Cache;

public abstract class AbstractYcache<K, V> implements Cache<K, V> {
    private Integer capacity;
    private Map<K, Node<K, V>> searchMap = new HashMap<>();
    private Node<K, V> head;
    private Node<K, V> tail;

    public AbstractYcache() {
        this(100);
    }
    public AbstractYcache(Integer capacity) {
        this.capacity = capacity;
    }

    protected Node<K, V> getHead() {
        return head;
    }

    protected void setHead(Node<K, V> head) {
        this.head = head;
    }

    protected Node<K, V> getTail() {
        return tail;
    }

    protected void setTail(Node<K, V> tail) {
        this.tail = tail;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = searchMap.get(key);
        if (node != null) {
            updateNodePosition(node);
        }
        return node == null ? null : node.value;
    }

    @Override
    public void put(K key, V value) {
        if (searchMap.containsKey(key)) {
            Node<K, V> node = searchMap.get(key);
            node.value = value;
            updateNodePosition(node);
        } else {
            Node<K, V> newNode = new Node<>(key, value);
            replaceFirstNode(newNode);
            searchMap.put(key, newNode);
            if (searchMap.size() > capacity) {
                K keyToRemove = removeLast();
                searchMap.remove(keyToRemove);
            }
        }
    }

    @Override
    public int size() {
        return searchMap.size();
    }

    @Override
    public int getMaxCapacity() {
        return capacity;
    }

    protected void replaceFirstNode(Node<K, V> node) {
        if (getHead() == null) {
            setHead(node);
            setTail(node);
        } else {
            getHead().prev = node;
            node.next = getHead();
            setHead(node);
        }
    }

    protected abstract void updateNodePosition(Node<K, V> node);

    private K removeLast() {
        K key = getTail().key;

        getTail().prev.next = null;
        setTail(getTail().prev);

        return key;
    }
}
