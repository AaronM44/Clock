package priorityqueue;

/**
 * Queue is full.
 *
 * Cannot accept a new item.
 */
public class QueueOverflowException extends Exception {

    public QueueOverflowException() {
        super("Queue is full");
    }
}
