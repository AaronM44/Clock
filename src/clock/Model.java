package clock;

import priorityqueue.QueueOverflowException;
import priorityqueue.SortedArrayPriorityQueue;

import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public void addAlarm(Date datetime) {

        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        DateFormat priorityFormat = new SimpleDateFormat("yyyyMMddHHmm");

        // create new Alarm object
        Alarm alarm = new Alarm(datetime);

        // add alarms to the queue only if they have not passed
        if (!datetime.before(new Date())) {

            // convert date to long for priority
            long priority = Long.parseLong(priorityFormat.format(datetime));

            // add alarm to the queue and schedule it with the timer
            try {

                alarms.add(alarm, priority);
                alarmTimer.schedule(alarm, alarm.getRawAlarm());

                JOptionPane.showMessageDialog(null, "Alarm saved");
            }
            catch (QueueOverflowException error) {

                JOptionPane.showMessageDialog(null, "Too many alarms already");
            }
        }
        else {

            JOptionPane.showMessageDialog(null, dateFormat.format(datetime) + " has passed");
        }
    }
}