package priorityqueue;

/**
 * Implementation of a Linked List
 */
public class LinkedList<T> {

    /**
     * The first node in the list
     *
     */
    public Node firstNode;

    /**
     * The current size of the list
     *
     */
    public int size;

    /**
     * Create a new empty linked list
     *
     */
    public LinkedList() {

        firstNode = null;
        size = 0;
    }

    /**
     * Return the size of the list
     *
     * @return
     */
    public int getSize() { return size; }

    /**
     * Check if the list is empty
     *
     * @return
     */
    public boolean isEmpty() {

        return(firstNode == null);
    }

    /**
     * Add an item to the list
     *
     * @param item
     */
    public void add(T item) {

        Node newNode = new Node(item);

        newNode.next = firstNode;

        firstNode = newNode;

        size++;
    }

    /**
     * Remove an item from the list
     *
     * @throws QueueUnderflowException
     */
    public void remove() throws QueueUnderflowException{

        if(isEmpty()) {

            throw new QueueUnderflowException();
        }
        else {

            firstNode = firstNode.next;
            size--;
        }
    }

    /**
     * Method for printing the list
     *
     * @return
     */
    @Override
    public String toString() {

        Node node = firstNode;
        String string = "";

        while (node != null) {

            string += (node.toString());

            if (node.next != null) {

                string += ", ";
            }

            node = node.next;
        }

        return string;
    }
}
