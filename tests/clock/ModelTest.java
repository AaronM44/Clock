package clock;

import org.junit.Test;
import priorityqueue.QueueUnderflowException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Holds all the data for our program
 */
public class ModelTest {

    Model model = new Model();

    // test adding an alarm to a model
    @Test
    public void addAlarm() {

        String datetime = "12:00 30/05/2018";
        Date date;
        try {

            date = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(datetime);

            model.addAlarm(date);

            String result = model.alarms.head().getIcal_alarm();
            String expResult = "20180530T120000Z";

            assertEquals(result, expResult);
        }
        catch (ParseException | QueueUnderflowException e) {

        }
    }

    // test adding an alarm in the past to a model
    @Test
    public void addAlarm2() {

        String datetime = "12:00 05/05/2018";
        Date date;
        try {

            date = new SimpleDateFormat("HH:mm dd/MM/yyyy").parse(datetime);

            model.addAlarm(date);

            String result = model.alarms.toString();
            String expResult = "[]";

            assertEquals(result, expResult);
        }
        catch (ParseException e) {

        }
    }
}