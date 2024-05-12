
import java.util.ArrayList;

/**
 * Class creates a MaxHeap using an arrary.
 * @author Dennis Yiaile
 * @param <E> - generic type
 */
public class ArrayHeap<E extends Comparable<E>> 
    extends ArrayBinaryTree<E> implements PriorityQueue<E> {
    
    /** Constructor calls the ArrayBinaryTree. */
    ArrayHeap() {
        super();
    }

    /**
     * Insert an element to the tree.
     * @param element - item that's inserted to the tree
     */
    @Override
    public void insert(E element) {
        int search = containsIdx(element);
        if (search != -1) {
            super.insert(element);
            int child = size() - 1;
            swap(search, child);
            remove(getNode(child));
            int parent = parent(search);
            if (parent != -1 && 
                compareNodes(parent, search) < 0) {
                upHeap(search);
            } else {
                donwHeap(search);
            }
        } else {
            super.insert(element);
            upHeap(size() - 1);
        }
    }

    /**
     * Check if the element can be removed and remove it from the tree.
     * @param element - item to be removed from the tree
     * @return boolean - true if removed and false otherwise
     */
    @Override
    public boolean remove(E element) {
        int removed = containsIdx(element);
        if (removed == -1) {
            return false;
        } else {
            super.remove(element);
            donwHeap(removed);
            return true;
        }
    }

    /**
     * Upheap to make sure that the heap is the appropriate order.
     * @param childInt - index of the child
     */
    private void upHeap(int childInt) {
        int child = childInt;
        int parent = parent(child);
        while (parent != -1 && 
            compareNodes(parent, child) < 0) {
            swap(child, parent);
            child = parent;
            parent = parent(child);
        }
    }

    /**
     * Downheap to make sure that the heap is in the right order.
     * @param removed - index to be removed
     */
    private void donwHeap(int removed) {
        int left = left(removed);
        int right = right(removed);
        while ((left != -1 && 
            compareNodes(removed, left) < 0)
            || (right != -1 && 
            compareNodes(removed, right) < 0)) {
            int donwHeap;
            if (left != -1 && right != -1) {
                if (compareNodes(left, right) < 0) {
                    donwHeap = right;
                } else {
                    donwHeap = left;
                }
            } else if (left == -1) {
                donwHeap = right;
            } else {
                donwHeap = left;
            }
            swap(donwHeap, removed);
            removed = donwHeap;
            left = left(removed);
            right = right(removed);
        }
    }

    /**
     * Compare two nodes to determine the ligest one.
     * @param first - index of the first node 
     * @param second - index of the second node
     * @return 0 if equals, 1 if first is greater otherwise -1
     */
    private int compareNodes(int first, int second) {
        return getNode(first).compareTo(getNode(second));
    }

    /**
     * Returns the node at the top of the heap.
     * @return E - node at the top of the tree
     */
    public E peek() {
        return getRootElement();
    }
    
    /**
     * Remove and return the node at the top of the heap.
     * @return E - node at the top heap.
     */
    public E poll() {
        E top = peek();
        remove(top);
        return top;
    }

    /**
     * Return level by level string representation of a tree.
     * @return String - breadth search nodes of a tree 
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        int node = 0;
        int depth = 1;
        while (node < size()) {
            StringBuilder sameLevel = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                if (node == size()) {
                    break;
                }
                String current = getNode(node).toString();
                sameLevel.append(current).append(" ");
                node++;
            }
            output.append(sameLevel.toString().trim());
            output.append("\n");
            depth = depth * 2;
        }
        return output.toString();
    }

    /**
     * Returns top N elements from the heap in an ArrayList of E.
     * @param n number of top elements to get
     * @return an array of Polling Data containing top n elements
     */
    public ArrayList<E> peekTopN(int n) {
        int capacity = 0;
        if (n <= size()) {
            capacity = n;
        } else {
            capacity = size();
        }
        ArrayList<E> topN = new ArrayList<>(capacity);
        ArrayHeap<E> heap2 = new ArrayHeap<>();
        for (int i = 0; i < size(); i++) {
            heap2.insert(getNode(i));
        }

        for (int i = 0; i < capacity; i++) {
            E currentPeek = heap2.poll();
            topN.add(currentPeek);
        }

        return topN;
    }
}