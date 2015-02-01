package ru.datastructure.ycache;

/**
 * Auxiliary class for creating linked lists
 *
 * @param <K> Key type
 * @param <V> Value type
 */
class Node<K, V> {
    K key;
    V value;
    Node<K, V> next;
    Node<K, V> prev;

    Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}