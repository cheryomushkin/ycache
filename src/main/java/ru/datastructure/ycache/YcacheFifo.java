package ru.datastructure.ycache;

public class YcacheFifo<K, V> extends AbstractYcache<K, V> {

    public YcacheFifo(Integer capacity) {
        super(capacity);
    }

    @Override
    protected void updateNodePosition(Node<K, V> node) {
        // Do nothing here - abstract class provides FIFO strategy by default
    }
}
