package clock;

import priorityqueue.PriorityItem;
import priorityqueue.QueueOverflowException;
import priorityqueue.QueueUnderflowException;
import priorityqueue.SortedArrayPriorityQueue;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

// ****** VIEW LISTENERS
class ViewActionListener implements ActionListener {

    View view;
    Model model;

    public ViewActionListener(View view, Model model) {

        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getActionCommand() == "ADD") {

            AddAlarm addAlarm = new AddAlarm(model);
        }
        else if (actionEvent.getActionCommand() == "VIEW") {

            ViewAlarm viewAlarm = new ViewAlarm(model);
        }
    }
}

class ViewWindowAdapter extends WindowAdapter {

    View view;
    Model model;

    public ViewWindowAdapter(View view, Model model) {

        this.view = view;
        this.model = model;
    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

        System.out.println("View model: " + model.toString());
        System.out.println("View alarms: " + model.alarms.toString());
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

        LoadAlarms loadAlarms = new LoadAlarms();
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {

        SaveAlarms saveAlarms = new SaveAlarms();
    }
}

// ****** ADD ALARM LISTENERS
class AddAlarmActionListener implements ActionListener {

    AddAlarm view;
    Model model;

    public AddAlarmActionListener(AddAlarm view, Model model) {

        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // create new Alarm object
        Alarm alarm = new Alarm((Date) view.date_spinner.getValue());

        long datetime = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmm").format(view.date_spinner.getValue()));

        // add alarm to the queue
        try {

            model.alarms.add(alarm, datetime);
        }
        catch (QueueOverflowException error) {

        }

        JOptionPane.showMessageDialog(view, "Alarm saved");

    }
}

// window listener for Add Alarm window
class AddAlarmWindowAdapter extends WindowAdapter {

    AddAlarm view;
    Model model;

    public AddAlarmWindowAdapter(AddAlarm view, Model model) {

        this.view = view;
        this.model = model;
    }

    @Override
    public void windowActivated(WindowEvent we) {

        System.out.println("Add Alarm model: " + model.toString());
        System.out.println("Add Alarm alarms: " + model.alarms.toString());
    }
}

// ****** VIEW ALARMS LISTENERS
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

// window listener events
class ViewAlarmWindowAdapter extends WindowAdapter {

    ViewAlarm view;

    public ViewAlarmWindowAdapter(ViewAlarm view) {

        this.view = view;
    }

    @Override
    public void windowActivated(WindowEvent we) {

        // error checking
        System.out.println("View Alarm model: " + view.model.toString());
        System.out.println("View Alarm alarms: " + view.model.alarms.toString());

        ArrayList<Object> copyAlarms = view.model.alarms.returnArrayList();

        // get number of alarms in the queue
        int numAlarms = view.model.alarms.count();

        int baseHeight = 20;

        // create a button for each alarm in the queue
        // buttons are slow to load, no idea why, guessing there's a better way to do this somehow
        for (int i = 0; i < numAlarms; i++) {

            PriorityItem<Alarm> alarm = (PriorityItem<Alarm>) copyAlarms.get(i);

            JButton button = new JButton(String.valueOf(new SimpleDateFormat("HH:mm dd/MM/yyyy").format(alarm.getItem().getRawAlarm())));
            button.setFont(new Font("Serif", Font.BOLD, 24));
            button.setBounds(20, baseHeight + 50, 250, 50);
            view.pane.add(button);

            button.addActionListener(new ViewAlarmActionListener(view, alarm.getItem()));

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

        if (e.getActionCommand() == "SAVE") {

            System.out.println(String.valueOf(new SimpleDateFormat("HH:mm dd/MM/yyyy").format(view.alarm.getRawAlarm())));
            System.out.println(String.valueOf(new SimpleDateFormat("HH:mm dd/MM/yyyy").format(view.date_spinner.getValue())));

        }
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

        System.out.println("Edit Alarm model: " + view.model.toString());
        System.out.println("Edit Alarm alarms: " + view.model.alarms.toString());

        view.date_spinner.setValue(alarm.getRawAlarm());

    }

}

