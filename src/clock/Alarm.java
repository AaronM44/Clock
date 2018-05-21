package clock;

public class Alarm {

    private String alarm;

    private int year;
    private int month;
    private int day;
    private int hours;
    private int minutes;
    private int seconds;

    public Alarm(int yyyy, int mm, int dd, int hh, int MM, int ss) {

        year = yyyy;
        month = mm;
        day = dd;
        hours = hh;
        minutes = MM;
        seconds = ss;

        alarm = convertToAlarm(year, month, day, hours, minutes, seconds);
    }

    public String convertToAlarm (int yyyy, int mm, int dd, int hh, int MM, int ss) {

        String alarmString = String.valueOf(yyyy) + String.valueOf(mm) + String.valueOf(dd)
                + "T" + String.valueOf(hh) + String.valueOf(MM) + String.valueOf(ss) + "Z";

        return alarmString;
    }

    public String getAlarm(){ return alarm; };
}
