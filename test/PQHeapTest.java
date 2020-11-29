import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PQHeapTest {

	PQHeap<Integer> heap, big;
	
	@BeforeEach
	void init() {
		heap = new PQHeap<Integer>();
		big = new PQHeap<Integer>(256);
	}
	
	@Test
	void constructors() {
		assertEquals(heap.getData().length, 32, "your default constructor does not create an array of size 32");
		assertEquals(big.getData().length, 256, "your constructor does not allow for size changes");
	}
	
	@Test
	void size() {
		assertEquals(heap.size(), 0, "your heap has something in it when it should be empty");
		heap.add(1);
		assertEquals(heap.size(), 1, "your heap size does not update after adding");
		heap.remove();
		assertEquals(heap.size(), 0, "your heap size does not update after removing");
	}
	
	@Test
	void isEmptyTest() {
		assertTrue(heap.isEmpty(), "empty heap presents as non-empty");
		heap.add(1);
		assertFalse(heap.isEmpty(), "non-empty heap presents as empty.");
	}
	
	@Test
	void expansionTest() {
		for (int i = 0; i < 33; i++) {
			heap.add(i);
		}
		assertEquals(heap.getData().length, 64);
	}
	
	@SuppressWarnings("serial")
	@Test
	void removeTest() {
		ArrayList<Integer> removed = new ArrayList<Integer>();
		// add in a particular order to heap
		heap.add(3);
		heap.add(30);
		heap.add(0);
		heap.add(9999999);
		heap.add(-8);
		// add in another order to another heap
		PQHeap<Integer> other = new PQHeap<Integer>();
		other.add(30);
		other.add(-8);
		other.add(9999999);
		other.add(3);
		other.add(0);
		while (!heap.isEmpty()) {
			int foo = heap.remove();
			removed.add(foo);
			assertEquals(foo, other.remove(), "remove method does not remove items in the same order");
		}
		
		
		// check proper removal order
		ArrayList<Integer> properRemoved = new ArrayList<Integer>() {{
			add(9999999);
			add(30);
			add(3);
			add(0);
			add(-8);
		}};
		
		assertEquals(removed, properRemoved, "remove method does not remove items in the proper order");		
	}

}
