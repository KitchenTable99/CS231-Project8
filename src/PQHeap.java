/**
 * A heap implemented using an internal array
 *
 * @author cbitting
 */

public class PQHeap<T extends Comparable<T>> {
	
	private Object[] data;
	private int size;
	
	// constructors
	public PQHeap() {
		data = new Object[32];
		size = 0;
	}
	
	public PQHeap(int size) {
		data = new Object[size];
		this.size = 0;
	}
	
	// accessors and getters
	/**
	 * @return the size of the array
	 */
	public int size() {
		return size;
	}
	
	/**
	 * @return the data array
	 */
	public Object[] getData() {
		return data;
	}
	
	/**
	 * @return whether or not the heap is empty
	 */
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @param pos to find the parent of
	 * @return the index of the parent
	 */
	private int parent(int pos) {
		return (pos-1)/2;
	}
	
	/**
	 * @param pos to find the left child of
	 * @return the index of the left child
	 */
	private int leftChild(int pos) {
		return (pos*2) + 1;
	}
	
	/**
	 * @param pos to find the right child of
	 * @return the index of the right child
	 */
	private int rightChild(int pos) {
		return (pos*2) + 2;
	}
	
	/**
	 * @param pos to turn into a T object
	 * @return the actual T object at position pos
	 */
	@SuppressWarnings("unchecked" )
	private T getTVal(int pos) {
		return (T) data[pos];
	}
	
	/**
	 * @param targetIndex the index to start the bubble down. This will recursively call itself
	 */
	private void bubbleDown(int targetIndex) {
		// basic set-up
		int leftIndex = this.leftChild(targetIndex);
		int rightIndex = this.rightChild(targetIndex);
		int biggestIndex;
		T biggestChild;
		
		// if there is at least one child within the size constraint
		if (leftIndex <= size) {
			// find the biggest valid child
			T left = this.getTVal(leftIndex);
			// check to see if the right child is both within the size constraint and bigger
			if (rightIndex < size && getTVal(rightIndex).compareTo(left) > 0) {
				// if so set the requisite variables
				biggestIndex = rightIndex;
				biggestChild = getTVal(rightIndex);
			} else {
				biggestIndex = leftIndex;
				biggestChild = left;
			}
			
			// if target smaller than largest child swap and recursively call
			if (getTVal(targetIndex).compareTo(biggestChild) < 0) {
				Object foo = data[targetIndex];
				data[targetIndex] = data[biggestIndex];
				data[biggestIndex] = foo;
				this.bubbleDown(biggestIndex);
			} else {
				return;
			}
		} else {
			return;			// right will always be outside of size if left is
		}
	}
	
	/**
	 * @param targetIndex the index to start the bubble up. Will recursively call itself
	 */
	private void bubbleUp(int targetIndex) {
		// basic set up
		int parentIndex = this.parent(targetIndex);
		T target = getTVal(targetIndex);
		T parent = getTVal(parentIndex);
		// if target position is larger than parent
		if (target.compareTo(parent) > 0) {
			// swap target with parent
			Object foo = data[targetIndex];
			data[targetIndex] = data[parentIndex];
			data[parentIndex] = foo;
			// call bubbleUp on the the parent location
			this.bubbleUp(parentIndex);
		}
		return;
	}
	
	/**
	 * @param item to add to the heap
	 */
	public void add(T item) {
		// expand the array if full
		if (data.length == size) {
			// create larger array
			Object[] foo = new Object[data.length * 2];
			// copy over data
			for (int i = 0; i < data.length; i++) {
				foo[i] = data[i];
			}
			// replace
			data = foo;
		}
		// put the item at the end of the array
		data[size] = (Object) item;
		// bubble up the data
		this.bubbleUp(size++);
	}
	
	/**
	 * @return the item with the highest priority
	 */
	@SuppressWarnings("unchecked")
	public T remove() {
		// switch first and last values and decrement size
		Object toReturn = data[0];
		data[0] = data[--size];
		data[size + 1] = toReturn;
		// re heap
		this.bubbleDown(0);
		// return
		return (T) toReturn;
	}
	
	@Override
	public String toString() {
		String toReturn = "<";
		for (int i = 0; i < data.length; i++) {
			if (data[i] == null) {
				break;
			}
			toReturn += data[i];
			toReturn += ", ";
		}
		toReturn = toReturn.substring(0, toReturn.length() - 2);
		toReturn += ">";
		return toReturn;
	}

}
