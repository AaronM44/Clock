package clock;

import priorityqueue.QueueOverflowException;
import priorityqueue.QueueUnderflowException;
import priorityqueue.SortedArrayPriorityQueue;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public ViewAlarmActionListener(ViewAlarm view) {

        this.view = view;
    }

    public ViewAlarmActionListener(ViewAlarm view, Alarm alarm) {

        this.view = view;
        this.alarm = alarm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        EditAlarm editAlarm = new EditAlarm(view.model, alarm);
    }
}

class ViewAlarmWindowAdapter extends WindowAdapter {

    ViewAlarm view;

    public ViewAlarmWindowAdapter(ViewAlarm view) {

        this.view = view;
    }

    @Override
    public void windowActivated(WindowEvent we) {

        // create a copy of the alarm queue
        SortedArrayPriorityQueue<Alarm> copyAlarms = view.model.alarms;

        // get number of alarms in the queue
        int numAlarms = copyAlarms.count();

        int baseHeight = 20;

        // loop through all alarms creating a button and listener for them all
        // can't access priority queue items specifically so instead we create a copy
        // access the head then pop it, access the next one and so on...
        // loads the buttons really slow, not sure, probably just a really bad way of doing things
        for (int i = 0; i < numAlarms; i++) {

            try {
                JButton button = new JButton(String.valueOf(new SimpleDateFormat("HH:mm dd/MM/yyyy").format(copyAlarms.head().getRawAlarm())));
                button.setFont(new Font("Serif", Font.BOLD, 24));
                button.setBounds(20, baseHeight + 50, 250, 50);
                view.pane.add(button);

                button.addActionListener(new ViewAlarmActionListener(view, copyAlarms.head()));
            }
            catch (QueueUnderflowException e) {

            }

            try {
                copyAlarms.remove();
            }
            catch (QueueUnderflowException e) {

            }

            baseHeight += 50;
        }
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

// edit alarm window adapter
class EditAlarmWindowAdapter extends WindowAdapter {

    EditAlarm view;
    Alarm alarm;

    public EditAlarmWindowAdapter(EditAlarm view, Alarm alarm) {

        this.view = view;
        this.alarm = alarm;
    }

    @Override
    public void windowOpened(WindowEvent we) {

        view.date_spinner.setValue(alarm.getRawAlarm());

    }

}

