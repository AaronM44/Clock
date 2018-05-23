package clock;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class Alarm extends TimerTask {

    private String ical_alarm;
    private Date raw_alarm;
    private boolean activated;

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

    public String convertToAlarm (Date alarm) {

        String alarmString = String.valueOf(new SimpleDateFormat("yyyyMMdd").format(raw_alarm)) + "T" +
                String.valueOf(new SimpleDateFormat("HHmmss").format(raw_alarm)) + "Z";


        return alarmString;
    }

    public String getIcal_alarm(){ return ical_alarm; }

    public Date getRawAlarm(){ return raw_alarm; }

    public boolean isActivated() { return activated; }
}
