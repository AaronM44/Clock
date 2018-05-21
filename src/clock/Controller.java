package clock;

import priorityqueue.QueueOverflowException;
import priorityqueue.QueueUnderflowException;

import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class Controller {
    
    ActionListener listener;
    Timer timer;
    
    Model model;
    View view;
    
    public Controller(Model m, View v) {
        model = m;
        view = v;
        
        listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.update();

            }
        };
        
        timer = new Timer(100, listener);
        timer.start();
    }
}

// handle the events for the Add Alarm window
class AddAlarmActionListener implements ActionListener {

    AddAlarm view;

    public AddAlarmActionListener(AddAlarm view) {

        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // create new Alarm object
        Alarm alarm = new Alarm((Date) view.date_spinner.getValue());

        long datetime = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmm").format(view.date_spinner.getValue()));

        // add alarm to the queue
        try {

            view.model.alarms.add(alarm, datetime);
        }
        catch (QueueOverflowException error) {

        }

        JOptionPane.showMessageDialog(view, view.model.alarms.toString());

        // show alarm
//        try {
//            JOptionPane.showMessageDialog(view, view.model.alarms.toString());
//
//        }
//        catch (QueueUnderflowException error) {
//
//        }
    }
}

// handle the events for the View Alarms window
class ViewAlarmActionListener implements ActionListener {

    ViewAlarm view;
    Alarm alarm;

    public ViewAlarmActionListener(ViewAlarm view, Alarm alarm) {

        this.view = view;
        this.alarm = alarm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        EditAlarm editAlarm = new EditAlarm(view.model, alarm);
    }
}

// handle the events for the Edit Alarm window
class EditAlarmActionListener implements ActionListener {

    EditAlarm view;

    public EditAlarmActionListener(EditAlarm view) {

        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }
}

