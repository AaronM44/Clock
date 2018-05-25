package clock;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * Class representing an alarm
 *
 * Extends TimerTask so that the alarm can be scheduled on a java.util.Timer
 */
public class Alarm extends TimerTask {

    private String ical_alarm;
    private Date raw_alarm;
    private boolean activated;

    /**
     * @param alarm a Date object holding the date and time of when the alarm should go off
     */
    public Alarm(Date alarm) {

        raw_alarm = alarm;
        activated = false;
        ical_alarm = convertToAlarm(raw_alarm);
    }

    @Override
    public void run() {

        JOptionPane.showMessageDialog(null, String.valueOf( new SimpleDateFormat("HH:mm dd/MM/yyyy").format(raw_alarm)));
        activated = true;
    }

    /**
     * @param alarm a Date object holding the date and time the alarm should go off
     * @return string in the format required by iCal in the DTSTAMP, DTSTART or DTEND fields
     */
    public String convertToAlarm (Date alarm) {

        String alarmString = String.valueOf(new SimpleDateFormat("yyyyMMdd").format(raw_alarm)) + "T" +
                String.valueOf(new SimpleDateFormat("HHmmss").format(raw_alarm)) + "Z";

        return alarmString;
    }

    public String getIcal_alarm(){ return ical_alarm; }

    public Date getRawAlarm(){ return raw_alarm; }

    /**
     * @return checks if the alarm has gone off
     */
    public boolean isActivated() { return activated; }
}
