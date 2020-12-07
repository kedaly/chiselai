package ca.chisel.interview;


import java.util.Map;
import java.util.Set;

/**
 * An Cache Interface for Java
 *
 * @param <K> the key of the value
 * @param <V> the value to be cached
 */
public interface Cache<K,V> {

    /**
     * Put a value into the cache
     *
     * @param key   the key
     * @param value the value
     */
    void put(K key, V value);

    /**
     * Get a value from the cache
     *
     * @param key the key
     * @return the v
     */
    V get(K key);

    /**
     * Delete a value from the cache
     *
     * @param key the key
     */
    void del(K key);

    /**
     * Reset the cache and empty all values.
     */
    void reset();

    /**
     * Checks to see if the cache contains a value.
     * Does this without reading the value.
     *
     * @param key the key
     * @return the boolean
     */
    boolean cacheContains(K key);

    /**
     * Get the backing entry set of the cache.
     *
     * @return the map
     */
    Set<Map.Entry<K,V>> entrySet();

    /**
     * Gets the maximum size of the cache
     *
     * @return the max size
     */
    int getMaxSize();

    /**
     * Gets current size of the items in the cahce
     *
     * @return the current size
     */
    int getCurrentSize();
}
