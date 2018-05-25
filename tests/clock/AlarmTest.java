package clock;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class AlarmTest {

    // test the method to convert an alarm to the ical string format
    @Test
    public void testConvert() {

        String datetime = "12:00 30/05/2018";
        Date date;
        try {

            date = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(datetime);

            Alarm alarm = new Alarm(date);

            String result = alarm.getIcal_alarm();
            String expResult = "20180530T120000Z";

            assertEquals(result, expResult);
        }
        catch (ParseException e) {

        }
    }
}
