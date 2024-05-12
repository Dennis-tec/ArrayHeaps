/**
 * class design a binary tree from usin an array.
 * @author Dennis Yiaile
 * @param <E> - array can store generic types
 */
public class ArrayBinaryTree<E extends Comparable<E>> 
    implements BinaryTree<E> {
    // initialize parameters.
    /** Default array capacity. */
    private static final int CAPACITY = 100;
    /* array to contain elements of a tree. */
    private E[] data;
    /* Track index of the last elements. */
    private int j;

    // Constructors
    /**
     * Create an empty an array with initialized size of 16.
     */
    @SuppressWarnings("unchecked")
    ArrayBinaryTree() {
        this.data = (E[]) new Comparable[CAPACITY];
    }

    /**
     * Get the root element of the tree.
     * @return E - the root element as a generic type
     */
    public E getRootElement() {
        if (j == 0) {
            return null;
        }
        return data[0];
    }

    /**
     * Return the node at an inputed index.
     * @param pos - index of the element to get
     * @return Node at an inputed position
     */
    public E getNode(int pos) {
        if (pos >= j) {
            return null;
        }
        return data[pos];
    }

    /**
     * Get the size of the tree.
     * @return int - number of the elements as an integer
     */
    public int size() {
        return j;
    }

    /**
     * Check if the tree is empty.
     * @return boolean - true if empty else false
     */
    public boolean isEmpty() {
        return j ==  0;
    }

    /**
     * Insert an element to the tree.
     * @param element - item that's inserted to the tree
     */
    public void insert(E element) {
        data[j++] = element;
    }

    /**
     * Check if the element can be removed and remove it from the tree.
     * @param element - item to be removed from the tree
     * @return boolean - true if removed and false otherwise
     */
    public boolean remove(E element) {
        int delete = containsIdx(element);
        if (delete == -1) {
            return false;
        } else {
            data[delete] = null;
            swap(delete, j - 1);
            j--;
            return true;
        }
    }

    /**
     * Return the element of a tree as a string.
     * @return String - element of the tree as a string
     */
    public String toStringBreadthFirst() {
        StringBuilder tree = new StringBuilder();
        for (int i = 0; i < j; i++) {
            tree.append(data[i].toString());
            tree.append(" ");
        }
        return tree.toString().trim();
    }

    /**
     * Returns the index of the parent of child stored at i.
     * @param i - index of a child
     * @return int - index of the parent of the child at i
     */
    public int parent(int i) {
        if (i > 0) {
            if (i % 2 == 0) {
                return (i - 2) / 2;
            }
            return (i - 1) / 2;
        }
        return -1;
    }

    /**
     * Returns index of left child of parent stored at i.
     * @param i - parent stored at i
     * @return int - left child of the parent at i
     */
    public int left(int i) {
        int left = 2 * i + 1;
        if (left < j) {
            return left;
        }
        return -1;
    }
    
    /**
     * Returns index of right child of parent stored at i.
     * @param i - parent stored at i
     * @return int - right child of the parent at i 
     */
    public int right(int i) {
        int right = 2 * i + 2;
        if (right < j) {
            return right;
        }
        return -1;
    }

    /**
     * Swaps the two nodes stored at indices i and j.
     * @param i - index of a node 
     * @param k - index of another node
     */
    public void swap(int i, int k) {
        E temp = data[i];
        data[i] = data[k];
        data[k] = temp;
    }

    /**
     * Determines if the tree contains a specific element.
     * @param element - the element to search
     * @return a boolean if the item was found
     */
    public boolean contains(E element) {
        if (containsIdx(element) == -1) {
            return false;
        }
        return true;
    }

    /**
     * Returns the index of the node containing element. 
     * If the element does not exist in the tree return -1.
     * @param element - node to search 
     * @return int - index of the element found
     */
    public int containsIdx(E element) {
        int contains = -1;
        for (int i = 0; i < j; i++) {
            if (data[i].compareTo(element) == 0) {
                contains = i;
            }   
        }
        return contains;
    }


    /**
     * Create a string representation of the elements in the tree.
     * the string is based on an in-order traversal.
     * @return A string representation of the elements in the tree
     */
    public String toStringInOrder() {
        StringBuilder inOrderBuilder = new StringBuilder();
        toStringInOrder(0, inOrderBuilder);
        return "In: " + inOrderBuilder.toString().trim();
    }

    /**
     * Recursive method that builds a string of elements
     * in a tree based on an in-order traversal 
     * @param index - index of the current nodes
     * @param builder - StringBuilder to build a string
     */
    private void toStringInOrder(int index, StringBuilder builder) {
        if (index != -1) {
            toStringInOrder(left(index), builder);
            builder.append(data[index].toString()).append(" ");
            toStringInOrder(right(index), builder);
        }
    }

    /**
     * Create a string representation of the elements in the tree.
     * the string is based on an pre-order traversal.
     * @return A string representation of the elements in the tree
     */
    public String toStringPreOrder() {
        StringBuilder preOrderBuilder = new StringBuilder();
        toStringPreOrder(0, preOrderBuilder);
        return "Pre: " + preOrderBuilder.toString().trim();
    }

    /**
     * Recursive method that builds a string of elements
     * in a tree based on a pre-order traversal 
     * @param index - index of the current nodes
     * @param builder - StringBuilder to build the string
     */
    private void toStringPreOrder(int index, StringBuilder builder) {
        if (index != -1) {
            builder.append(data[index].toString()).append(" ");
            toStringPreOrder(left(index), builder);
            toStringPreOrder(right(index), builder);
        }
    }


    /**
     * Create a string representation of the elements in the tree.
     * the string is based on an post-order traversal.
     * @return A string representation of the elements in the tree
     */ 
    public String toStringPostOrder() {
        StringBuilder postOrderBuilder = new StringBuilder();
        toStringPostOrder(0, postOrderBuilder);
        return "Post: " + postOrderBuilder.toString().trim();
    }

    /**
     * Recursive method that builds a string of elements
     * in a tree based on an post-order traversal 
     * @param index - index of the current nodes
     * @param builder - StringBuilder to build a string
     */
    private void toStringPostOrder(int index, StringBuilder builder) {
        if (index != -1) {
            toStringPostOrder(left(index), builder);
            toStringPostOrder(right(index), builder);
            builder.append(data[index].toString()).append(" ");
        }
    }

    /**
     * Return a string representation of the tree for all the traversal methods.
     */
    @Override
    public String toString() {
        String pre = toStringPreOrder();
        String in = toStringInOrder();
        String post = toStringPostOrder();

        return "Tree:" + "\n" + pre  + "\n" +
            in  + "\n" + post;
    }

}