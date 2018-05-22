package clock;

import priorityqueue.SortedArrayPriorityQueue;

import java.util.Calendar;
import java.util.Observable;
import java.util.GregorianCalendar;
import java.util.PriorityQueue;

public class Model extends Observable {
    
    int hour = 0;
    int minute = 0;
    int second = 0;
    
    int oldSecond = 0;

    SortedArrayPriorityQueue<Alarm> alarms = new SortedArrayPriorityQueue<>(5);
    
    public Model() {

        update();
    }
    
    public void update() {
        Calendar date = Calendar.getInstance();
        hour = date.get(Calendar.HOUR);
        minute = date.get(Calendar.MINUTE);
        oldSecond = second;
        second = date.get(Calendar.SECOND);
        if (oldSecond != second) {
            setChanged();
            notifyObservers();
        }
    }
}