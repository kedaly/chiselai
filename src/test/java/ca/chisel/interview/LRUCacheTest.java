package ca.chisel.interview;

import org.junit.jupiter.api.*;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LRUCacheTest {
    private static final int TEST_CACHE_SIZE = 10;
    private static final int TEST_DATA_SIZE = 100;
    private static final TestDataCollection collection = new TestDataCollection(TEST_DATA_SIZE);
    private static final LRUCache<String,KeyValueObject> testCache = new LRUCache<>(TEST_CACHE_SIZE);

    @BeforeAll
    static void beforeAll() {
        //sanity test on test data
        assertEquals(TEST_DATA_SIZE,collection.backingList.size());
        assertEquals(TEST_DATA_SIZE,collection.backingMap.size());

        //let's put all of the data in the cache
        for (final KeyValueObject object : collection.backingList) {
            testCache.put(object.getKey(),object);
        }

    }

    @Order(1)
    @Test
    void testEvictions() {
        //let's get what's in the cache should be the last n items in the data collection
        for (int i = TEST_DATA_SIZE -1 ; i > TEST_DATA_SIZE-TEST_CACHE_SIZE -1 ; i--) {
            final KeyValueObject testObj = collection.backingList.get(i);
            assertTrue(testCache.cacheContains(testObj.getKey()));
        }

        //let's test that we don't contain any objects we shouldn't
        for (int i = 0; i < TEST_DATA_SIZE-TEST_CACHE_SIZE; i++) {
            final KeyValueObject testObj = collection.backingList.get(i);
            assertFalse(testCache.cacheContains(testObj.getKey()));
        }

        assertEquals(TEST_DATA_SIZE-TEST_CACHE_SIZE,testCache.getEvictions());
    }

    @Order(2)
    @Test
    void testGet() {
        //let's access the cache
        for (int i = TEST_DATA_SIZE -1 ; i > TEST_DATA_SIZE-TEST_CACHE_SIZE -1 ; i--) {
            final KeyValueObject testObj = collection.backingList.get(i);
            final KeyValueObject cacheObject = testCache.get(testObj.getKey());
            assertNotNull(cacheObject);
        }

        //let's check the hits
        assertEquals(TEST_CACHE_SIZE,testCache.getHits());

    }

    @Order(3)
    @Test
    void testLRU() {

        //these should not be in the cache
        for (int i = 0; i < TEST_DATA_SIZE-TEST_CACHE_SIZE-1;i++) {
            final KeyValueObject testObj = collection.backingList.get(i);
            final KeyValueObject cacheObject = testCache.get(testObj.getKey());
            assertNull(cacheObject);
        }
    }

    @Order(4)
    @Test
    void testDelete() {
        final int currentCacheSize = testCache.getCurrentSize();

        //let's get something that is in cache
        final KeyValueObject objectToDelete = collection.backingList.get(collection.backingList.size()-2);

        final KeyValueObject cacheObject = testCache.get(objectToDelete.getKey());

        assertNotNull(cacheObject);
        assertNotNull(objectToDelete);
        assertEquals(objectToDelete.getKey(),cacheObject.getKey());
        assertEquals(objectToDelete.getValue(),cacheObject.getValue());

        testCache.del(objectToDelete.getKey());
        assertEquals(currentCacheSize-1,testCache.getCurrentSize());

        final KeyValueObject object2 = testCache.get(objectToDelete.getKey());

        assertNull(object2);
    }

}