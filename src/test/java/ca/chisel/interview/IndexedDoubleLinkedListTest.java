package ca.chisel.interview;

import org.junit.jupiter.api.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IndexedDoubleLinkedListTest {
    private final static int TEST_DATA_SIZE = 100;
    private final static TestDataCollection testData = new TestDataCollection(TEST_DATA_SIZE);
    private final static IndexedDoubleLinkedList<String,String> testLinkedList = new IndexedDoubleLinkedList<>(TEST_DATA_SIZE);
    private final static List<KeyValueObject> listModify = new ArrayList<>();
    private final static List<KeyValueObject> listRemoved = new ArrayList<>();
    private final static int NUMBER_TO_REMOVE = 10;
    @BeforeAll
    static void beforeAll() {
        final SecureRandom random = new SecureRandom();

        listModify.addAll(testData.backingList);
        for (int i = 0;i< NUMBER_TO_REMOVE;i++) {
            final int positionToRemove = random.nextInt(listModify.size());
            final KeyValueObject object = listModify.remove(positionToRemove);
            listRemoved.add(object);
        }
    }

    @Test
    @Order(1)
    public void testLoad() {
        System.out.println("testload");
        for (final KeyValueObject object : testData.backingList) {
            testLinkedList.put(object.getKey(),object.getValue());
        }

        assertEquals(testData.backingList.size(),testData.size());
        testListsEqual(testData.backingList,testLinkedList);
    }


    @Test
    @Order(2)
    public void testRemove() {
        for (final KeyValueObject remove : listRemoved) {
            testLinkedList.remove(remove.getKey());
        }

        testListsEqual(listModify,testLinkedList);

    }

    @Test
    @Order(3)
    public void testSetHead() {
        final KeyValueObject newObject = new KeyValueObject();
        testLinkedList.putHead(newObject.getKey(),newObject.getValue());

        //add at position 0 of test array
        listModify.add(0,newObject);

        testListsEqual(listModify,testLinkedList);

        //what happens if we set the tail to head
        final KeyValueObject tailObject = listModify.get(listModify.size()-1);

        //modify list to move tail
        listModify.remove(tailObject);
        listModify.add(0,tailObject);

        testLinkedList.putHead(tailObject.getKey(),tailObject.getValue());
        testListsEqual(listModify,testLinkedList);

        final KeyValueObject newTail = listModify.get(listModify.size()-1);

        final Node<String,String> tailNode = testLinkedList.getTail();

        assertEquals(newTail.getKey(),tailNode.getKey());
        assertEquals(newTail.getValue(),tailNode.getValue());
    }

    @Test
    @Order(4)
    public void testRemoveHead() {
        final Node<String,String> head = testLinkedList.getHead();
        final Node<String,String> newHead = head.getChild();

        testLinkedList.remove(head.getKey());

        final Node<String,String>  currentHead = testLinkedList.getHead();
        assertEquals(newHead.getKey(),currentHead.getKey());
        assertEquals(newHead.getValue(),currentHead.getValue());
    }

    @Test
    @Order(5)
    public void testClear() {
        testLinkedList.clear();
        assertEquals(0,testLinkedList.size());
        assertNull(testLinkedList.getHead());
        assertNull(testLinkedList.getTail());
    }

    @Test
    @Order(6)
    public void testNoArgsConstructor() {
        final IndexedDoubleLinkedList<String,String> testList2 = new IndexedDoubleLinkedList<>();
        final List<KeyValueObject> objectsArray = new ArrayList<>();

        objectsArray.add(new KeyValueObject());
        objectsArray.add(new KeyValueObject());
        objectsArray.add(new KeyValueObject());

        for (final KeyValueObject object : objectsArray) {
            testList2.put(object.getKey(),object.getValue());
        }

        testListsEqual(objectsArray,testList2);
    }
    private void testListsEqual(final List<KeyValueObject> testList, final IndexedDoubleLinkedList<String,String> linkedList) {
        assertEquals(testList.size(),linkedList.size());
        int i = 0;
        for (final Map.Entry<String,String> entry : linkedList) {
            final KeyValueObject object = testList.get(i++);
            assertEquals(object.getKey(),entry.getKey());
            assertEquals(object.getValue(),entry.getValue());

        }

    }
}