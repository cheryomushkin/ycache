package ru.datastructure.ycache;

import java.util.HashMap;
import java.util.Map;

import ru.datastructure.ycache.contract.Cache;

/**
 * This class provides base cache functionality and allows derived classes
 * to implement different cache invalidation strategies
 *
 * This class always removes the last element from internal linked list when cache reaches the max capacity.
 * Derived classes must ensure that the last element in the linked list is the one which should be deleted.
 *
 * @param <K> Cache key type
 * @param <V> Cache value type
 */
public abstract class AbstractYcache<K, V> implements Cache<K, V> {
    private Integer capacity;
    /**
     * Using map for quick access to underlying data
     */
    private Map<K, Node<K, V>> searchMap = new HashMap<>();
    /**
     * Using doubly linked list to allow sub classes implement their own cache invalidation strategies
     */
    private Node<K, V> head;
    private Node<K, V> tail;

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
        if (capacity <= 0) {
            return;
        }
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

    /**
     * Updates order of elements in the internal linked list (if necessary)
     * according to the cache strategy.
     * @param node
     */
    protected abstract void updateNodePosition(Node<K, V> node);

    private K removeLast() {
        K key = getTail().key;

        getTail().prev.next = null;
        setTail(getTail().prev);

        return key;
    }
}
