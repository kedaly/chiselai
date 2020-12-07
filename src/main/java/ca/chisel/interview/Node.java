package ca.chisel.interview;

import java.util.Objects;

/**
 * Represents a parent / child relationship.
 *
 * @param <K> the type parameter
 * @param <V> the type parameter
 */
public class Node<K,V>  {
    private final K key;
    private V value;
    private Node<K,V> parent;
    private Node<K,V> child;

    /**
     * Instantiates a new Node.
     *
     * @param key   the key
     * @param value the value
     */
    public Node(final K key, final V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public K getKey() {
        return key;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public V getValue() {
        return value;
    }

    /**
     * Sets value.
     *
     * @param value the value
     * @return the value
     */
    public V setValue(final V value) {
        return this.value = value;
    }

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Node<?, ?> node = (Node<?, ?>) o;
        return key.equals(node.key);
    }

    /**
     * Gets parent.
     *
     * @return the parent
     */
    public Node<K, V> getParent() {
        return parent;
    }

    /**
     * Gets child.
     *
     * @return the child
     */
    public Node<K, V> getChild() {
        return child;
    }

    /**
     * Sets child.
     *
     * @param child the child
     */
    public void setChild(final Node<K, V> child) {
        if (child != null) {
            child.parent = this;
        }
        this.child = child;
    }

    /**
     * Unlink.
     */
    public void unlink() {
        this.child = null;
        this.parent = null;
    }
    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
