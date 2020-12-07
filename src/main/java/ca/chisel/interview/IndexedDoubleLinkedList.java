package ca.chisel.interview;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * An Indexed Doubly linked list implementation
 * Backed by a map for fast access
 *
 * @param <K> the type parameter
 * @param <V> the type parameter
 */
public class IndexedDoubleLinkedList<K,V> implements Iterable<Map.Entry<K,V>> {
    private Node<K,V> head;
    private Node<K,V> tail;
    private final Map<K,Node<K,V>> index;

    /**
     * Instantiates a new Indexed double linked list.
     */
    public IndexedDoubleLinkedList() {
        index = new HashMap<>();
    }

    /**
     * Instantiates a new Indexed double linked list.
     *
     * @param initialSize the initial size
     */
    public IndexedDoubleLinkedList(final int initialSize) {
        index = new HashMap<>(initialSize);
    }


    @NotNull
    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new ListIterator(head);
    }

    /**
     * Put.
     *
     * @param key   the key
     * @param value the value
     */
    public void put(final K key, final V value) {
        final Node<K,V> node = index.get(key);
        if (node != null) {
            node.setValue(value);
        } else {
            final Node<K,V> newNode = new Node<>(key,value);
            index.put(key,newNode);
            if (index.size() == 1) {
                this.head = newNode;
            } else if (index.size() == 2) {
                this.head.setChild(newNode);
            } else {
                this.tail.setChild(newNode);
            }
            this.tail = newNode;
        }
    }

    /**
     * Remove boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean remove(final K key) {
        final Node<K,V> candidate = index.get(key);
        if (candidate != null) {
            index.remove(key);
            final Node<K,V> parent = candidate.getParent();
            final Node<K,V> child = candidate.getChild();

            candidate.unlink();
            if (parent != null) {
                parent.setChild(child);
            }

            if (candidate.equals(head)) {
                head = child;
            } else if (candidate.equals(tail)) {
                tail = parent;
            }
            return true;
        } else {
            return false;
        }

    }

    /**
     * Gets head.
     *
     * @return the head
     */
    public Node<K, V> getHead() {
        return head;
    }

    /**
     * Put head.
     *
     * @param key   the key
     * @param value the value
     */
    public void putHead(final K key, final V value) {
        final Node<K,V> node = index.get(key);
        if (node != null) {
            remove(key);
            node.setChild(head);
            node.setValue(value);
            head = node;
            index.put(node.getKey(),node);
        } else {
            final Node<K,V> newNode = new Node<>(key,value);
            index.put(newNode.getKey(),newNode);
            newNode.setChild(head);
            head = newNode;
        }

        if (index.size() == 2) {
            tail = head.getChild();
        }

    }


    /**
     * Gets tail.
     *
     * @return the tail
     */
    public Node<K, V> getTail() {
        return tail;
    }

    /**
     * Contains key boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean containsKey(final K key) {
        return index.containsKey(key);
    }

    /**
     * Get v.
     *
     * @param key the key
     * @return the v
     */
    public V get(final K key) {
        final Node<K,V> nodeCandidate = index.get(key);

        if (nodeCandidate != null) {
            return nodeCandidate.getValue();
        } else {
            return null;
        }
    }


    /**
     * Clear.
     */
    public void clear() {
        head = null;
        tail = null;
        index.clear();
    }

    /**
     * Size int.
     *
     * @return the int
     */
    public int size() {
        return index.size();
    }

    /**
     * The type List iterator.
     *
     */
    class ListIterator implements Iterator<Map.Entry<K,V>> {
        private Node<K,V> currentNode;
        private boolean first = true;

        /**
         * Instantiates a new List iterator.
         *
         * @param startNode the start node
         */
        public ListIterator(final Node<K, V> startNode) {
            this.currentNode = startNode;
        }

        @Override
        public boolean hasNext() {
            if ((first) && (currentNode != null)) {
                return true;
            } else return currentNode.getChild() != null;
        }

        @Override
        public Map.Entry<K, V> next() {
            if ((first)) {
                first = false;
            } else {
                currentNode = currentNode.getChild();
            }
            return new NodeEntryWrapper<>(currentNode);
        }
    }

}
