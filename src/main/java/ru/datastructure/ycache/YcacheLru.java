package ru.datastructure.ycache;

public class YcacheLru<K, V> extends AbstractYcache<K, V> {

    public YcacheLru() {
        super();
    }
    public YcacheLru(Integer capacity) {
        super(capacity);
    }

    @Override
    protected void updateNodePosition(Node<K, V> node) {
        ejectNode(node);
        replaceFirstNode(node);
    }

    private void ejectNode(Node<K, V> node) {
        if (node.next != null && node.prev != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        } else if (node.next == null && node.prev != null) {
            node.prev.next = null;
            setTail(node.prev);
        }

        node.next = null;
        node.prev = null;
    }
}
