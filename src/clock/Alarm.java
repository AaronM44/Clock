package clock;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Alarm {

    private String ical_alarm;
    private Date raw_alarm;

    public Alarm(Date alarm) {

        this.raw_alarm = alarm;

        ical_alarm = convertToAlarm(raw_alarm);
    }

    public String convertToAlarm (Date alarm) {

        String alarmString = String.valueOf(new SimpleDateFormat("yyyyMMdd").format(raw_alarm)) + "T" +
                String.valueOf(new SimpleDateFormat("HHmmss").format(raw_alarm)) + "Z";


        return alarmString;
    }

    public String getIcal_alarm(){ return ical_alarm; }

    public Date getRawAlarm(){ return raw_alarm; }
}
