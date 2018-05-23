package clock;

import priorityqueue.SortedArrayPriorityQueue;

import java.util.Calendar;
import java.util.Observable;

public class Model extends Observable {
    
    int hour = 0;
    int minute = 0;
    int second = 0;
    
    int oldSecond = 0;

    SortedArrayPriorityQueue<Alarm> alarms = new SortedArrayPriorityQueue<>(5);

    // java util timer  for scheduling alarms
    java.util.Timer alarmTimer;

    public Model() {

        update();
        alarmTimer = new java.util.Timer();
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