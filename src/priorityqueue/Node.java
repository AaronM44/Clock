package priorityqueue;


/**
 * A class representing a node in a linked list.
 *
 * @param <T>
 */
public class Node<T> {

    private final T item;
    public Node next;

    public Node(T item) {

        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public Node getNext() { return next; }

    @Override
    public String toString() { return item.toString(); }
}
