package ca.chisel.interview;

import java.util.Map;

/**
 * Wraps a Node int a Map.Entry
 *
 * @param <K> the type parameter
 * @param <V> the type parameter
 */
public class NodeEntryWrapper<K,V> implements Map.Entry<K,V> {
    private final Node<K,V> node;

    /**
     * Instantiates a new Node entry wrapper.
     *
     * @param node the node
     */
    public NodeEntryWrapper(final Node<K, V> node) {
        this.node = node;
    }

    @Override
    public K getKey() {
        return node.getKey();
    }

    @Override
    public V getValue() {
        return node.getValue();
    }

    @Override
    public V setValue(final V v) {
        return node.setValue(v);
    }
}
