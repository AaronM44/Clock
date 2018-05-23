package clock;

import priorityqueue.*;

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

        Timer t = new Timer(3000, updateCurrentTime);
        t.start();

        // error reporting
        System.out.println("View model: " + model.toString());
        System.out.println("View alarms: " + model.alarms.toString());

        // update next alarm
        try {
            view.btn_next.setText(String.valueOf(new SimpleDateFormat("HH:mm dd/MM/yyyy").format(model.alarms.head().getRawAlarm())));
        }
        catch (QueueUnderflowException e) {

        }
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

        LoadAlarms loadAlarms = new LoadAlarms();
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {

        SaveAlarms saveAlarms = new SaveAlarms();
    }

    ActionListener updateCurrentTime = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            // format current time
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            Date cur_datetime = new Date(System.currentTimeMillis());
            String dateFormatted = formatter.format(cur_datetime);

            // set current time
            view.btn_cur_time.setText(dateFormatted);

            // next alarm
            Alarm nextAlarm;



            try {
                nextAlarm = model.alarms.head();

                System.out.println(cur_datetime.compareTo(nextAlarm.getRawAlarm()));

                System.out.println(dateFormatted);
                System.out.println(formatter.format(nextAlarm.getRawAlarm()));

                if (cur_datetime.compareTo(nextAlarm.getRawAlarm()) == 1) {

                    JOptionPane.showMessageDialog(view, "alarm");

                    model.alarms.remove();

                }
            }
            catch (QueueUnderflowException e) {

            }


        }
    };
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

        // confirmation notification
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
        // close view alarms window
        // doing this because the window activated event is not working as I thought it would
        view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
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

        // create a button and listener for each alarm in the queue
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
    public void actionPerformed(ActionEvent ae) {

        if (ae.getActionCommand() == "SAVE") {

            // error reporting
            System.out.println(String.valueOf(new SimpleDateFormat("HH:mm dd/MM/yyyy").format(view.alarm.getRawAlarm())));
            System.out.println(String.valueOf(new SimpleDateFormat("HH:mm dd/MM/yyyy").format(view.date_spinner.getValue())));

            // create new Alarm object
            Alarm alarm = new Alarm((Date) view.date_spinner.getValue());

            long datetime = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmm").format(view.date_spinner.getValue()));

            // probably the worst way to edit items in a queue...
            // create an arraylist containing a copy of the queue. This is so that we can access items at specific
            // indexes.
            ArrayList<Object> copyAlarms = view.model.alarms.returnArrayList();

            // create a new queue. The idea being that we will perform the edit in the arraylist then add
            // all the items from the arraylist to this new queue and replace the old queue with the new
            // updated one.
            // it works for now.
            SortedArrayPriorityQueue<Alarm> newQueue = new SortedArrayPriorityQueue<>(5);

            int numAlarms = view.model.alarms.count();

            // iterate through the items in the queue to look for the item that is being changed
            for (int i = 0; i < numAlarms; i++) {

                // annoying java casting as the items are stored in an arraylist of generic Objects
                PriorityItem item = PriorityItem.class.cast(copyAlarms.get(i));
                Alarm alarmOfItem = Alarm.class.cast(item.getItem());

                // if we find the item we need to edit, replace it with the new alarm
                if (view.alarm == alarmOfItem) {
                    copyAlarms.set(i, new PriorityItem<Alarm>(alarm, datetime));
                }

                // add alarms to the new queue
                try {
                    newQueue.add(Alarm.class.cast(PriorityItem.class.cast(copyAlarms.get(i)).getItem()), (PriorityItem.class.cast(copyAlarms.get(i)).getPriority()));

                }
                catch (QueueOverflowException error) {

                }
            }

            // replace the old queue with the new modified one
            view.model.alarms = newQueue;

            // confirmation notification
            JOptionPane.showMessageDialog(view, "Alarm saved");
        }
        else if (ae.getActionCommand() == "DELETE") {

            // again with the dodgy queue editing. If I remember will make this a method in the queue or something
            // create an arraylist containing a copy of the queue. This is so that we can access items at specific
            // indexes.
            ArrayList<Object> copyAlarms = view.model.alarms.returnArrayList();

            // create a new queue. The idea being that we will perform the edit in the arraylist then add
            // all the items from the arraylist to this new queue and replace the old queue with the new
            // updated one.
            SortedArrayPriorityQueue<Alarm> newQueue = new SortedArrayPriorityQueue<>(5);

            int numAlarms = view.model.alarms.count();

            // iterate through the items in the queue to look for the item that is being deleted
            for (int i = 0; i < numAlarms; i++) {

                // annoying java casting as the items are stored in an arraylist of generic Objects
                PriorityItem item = PriorityItem.class.cast(copyAlarms.get(i));
                Alarm alarmOfItem = Alarm.class.cast(item.getItem());

                // if we find the item we need to edit, delete it
                if (view.alarm == alarmOfItem) {
                    copyAlarms.remove(i);
                }
            }

            // iterate through the items in the arraylist and add them to the new queue
            for (int i = 0; i < numAlarms - 1; i++) {

                // annoying java casting as the items are stored in an arraylist of generic Objects
                PriorityItem item = PriorityItem.class.cast(copyAlarms.get(i));
                Alarm alarmOfItem = Alarm.class.cast(item.getItem());

                // add alarms to the new queue
                try {
                    newQueue.add(Alarm.class.cast(PriorityItem.class.cast(copyAlarms.get(i)).getItem()), (PriorityItem.class.cast(copyAlarms.get(i)).getPriority()));

                }
                catch (QueueOverflowException error) {

                }
            }

            // replace the old queue with the new modified one
            view.model.alarms = newQueue;

            // confirmation notification
            JOptionPane.showMessageDialog(view, "Alarm deleted");

            // close edit alarm window
            view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
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

