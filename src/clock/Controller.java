package clock;

import priorityqueue.*;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.io.*;
import java.nio.file.NoSuchFileException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

/**
 * Provides the functionality for the clock so that this can be separated from the View and Model
 */
public class Controller {
    
    ActionListener listener;
    javax.swing.Timer timer;
    
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
// action listeners
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

            // open add alarm window
            AddAlarm addAlarm = new AddAlarm(model);
        }
        else if (actionEvent.getActionCommand() == "VIEW") {

            // open view alarm window
            ViewAlarm viewAlarm = new ViewAlarm(model);
        }
    }
}

// window listeners
class ViewWindowAdapter extends WindowAdapter {

    View view;
    Model model;

    public ViewWindowAdapter(View view, Model model) {

        this.view = view;
        this.model = model;
    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

        // timer for updating current time
        Timer clockTimer = new Timer(999, updateCurrentTime);
        clockTimer.start();

        // error reporting
        System.out.println("View model: " + model.toString());
        System.out.println("View alarms: " + model.alarms.toString());

        // update next alarm
        try {
            view.btn_next.setText("Next alarm: " + String.valueOf(new SimpleDateFormat("HH:mm dd/MM/yyyy").format(model.alarms.head().getRawAlarm())));
        }
        catch (QueueUnderflowException e) {

        }
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

        // open the load alarms dialog on window first open
        LoadAlarms loadAlarms = new LoadAlarms(model);
    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {

        // prompt user to save alarms on window close
        SaveAlarms saveAlarms = new SaveAlarms(model);
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

            // remove timer task from schedule and remove head of queue if popup has been displayed
            try {
                if (model.alarms.head().isActivated()) {

                    model.alarms.head().cancel();
                    model.alarms.remove();
                }
            }
            catch (QueueUnderflowException e) {

            }
        }
    };
}

// ****** ADD ALARM LISTENERS
// action listeners
class AddAlarmActionListener implements ActionListener {

    AddAlarm view;
    Model model;

    public AddAlarmActionListener(AddAlarm view, Model model) {

        this.view = view;
        this.model = model;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        model.addAlarm((Date) view.date_spinner.getValue());

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

        // for error checking
        System.out.println("Add Alarm model: " + model.toString());
        System.out.println("Add Alarm alarms: " + model.alarms.toString());
    }
}

// ****** VIEW ALARMS LISTENERS
// action listeners
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

        // create an array list of the alarms queue so that I can access specific elements
        ArrayList<Object> copyAlarms = view.model.alarms.returnArrayList();

        // get number of alarms in the queue
        int numAlarms = view.model.alarms.count();

        // y coordinate for the first button
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

            // update position for the next button
            baseHeight += 50;

            System.out.println("button created");

            button.setVisible(true);
        }

        // stops buttons lagging
        view.repaint();
    }
}

// ****** EDIT ALARM LISTENERS
// action listeners
class EditAlarmActionListener implements ActionListener {

    EditAlarm view;

    public EditAlarmActionListener(EditAlarm view) {

        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        // save alarm
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
                    alarmOfItem.cancel();
                    view.model.alarmTimer.schedule(alarm, alarm.getRawAlarm());
                }

                // add alarms to the new queue and timer
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

        // delete alarm
        else if (ae.getActionCommand() == "DELETE") {

            // again with the dodgy queue editing. If I remember will make this a method in the queue or something
            // create an arraylist containing a copy of the queue. This is so that we can access items at specific
            // indexes.
            ArrayList<Object> copyAlarms = view.model.alarms.returnArrayList();

            System.out.println("copyAlarms: " + copyAlarms);

            // create a new queue. The idea being that we will perform the edit in the arraylist then add
            // all the items from the arraylist to this new queue and replace the old queue with the new
            // updated one.
            SortedArrayPriorityQueue<Alarm> newQueue = new SortedArrayPriorityQueue<>(5);

            int numAlarms = view.model.alarms.count();

            System.out.println("count: " + numAlarms);


            // iterate through the items in the queue to look for the item that is being deleted
            for (int i = 0; i < numAlarms; i++) {

                // annoying java casting as the items are stored in an arraylist of generic Objects
                PriorityItem item = PriorityItem.class.cast(copyAlarms.get(i));
                Alarm alarmOfItem = Alarm.class.cast(item.getItem());

                // if we find the item we need to edit, delete it
                if (view.alarm == alarmOfItem) {

                    alarmOfItem.cancel();
                    copyAlarms.remove(i);
                    numAlarms -= 1;
                }
            }

            System.out.println("num alarms: " + numAlarms);
            System.out.println(copyAlarms);

            // iterate through the items in the arraylist and add them to the new queue
            for (int i = 0; i < numAlarms; i++) {

                PriorityItem item = PriorityItem.class.cast(copyAlarms.get(i));
                Alarm alarmOfItem = Alarm.class.cast(item.getItem());

                // add alarms to the new queue
                try {
                    newQueue.add(Alarm.class.cast(alarmOfItem), item.getPriority());

                    System.out.println(newQueue);
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

        // for error checking
        System.out.println("Edit Alarm model: " + view.model.toString());
        System.out.println("Edit Alarm alarms: " + view.model.alarms.toString());

        // set spinner value
        view.date_spinner.setValue(alarm.getRawAlarm());

    }

}

// ****** SAVE ALARMS LISTENERS
// action listeners
class SaveAlarmsActionListener implements ActionListener {

    SaveAlarms view;
    Model model;

    public SaveAlarmsActionListener(SaveAlarms view, Model model) {

        this.view = view;
        this.model = model;
    }

    public SaveAlarmsActionListener(SaveAlarms view) {

        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        String filepath = view.txt_file.getText();

        // yes button
        if (ae.getActionCommand() == "YES") {

            try {

                // create new writer object
                PrintWriter writer = new PrintWriter(filepath, "UTF-8");

                // create copy of queue as arraylist
                ArrayList<Object> copyAlarms = model.alarms.returnArrayList();

                int numAlarms = model.alarms.count();

                // loop over the queue and write alarms to file in iCal format
                for (int i = 0; i < numAlarms; i++) {

                    PriorityItem item = PriorityItem.class.cast(copyAlarms.get(i));
                    Alarm alarmOfItem = Alarm.class.cast(item.getItem());

                    writer.println("BEGIN:VCALENDAR");
                    writer.println("VERSION:2.0");
                    writer.println("PRODID:-//hacksw/handcal//NONSGML v1.0//EN");
                    writer.println("BEGIN:VEVENT");
                    writer.println("UID:@uid1@example.com");
                    writer.println("DTSTAMP:" + alarmOfItem.getIcal_alarm());
                    writer.println("DTSTART:" + alarmOfItem.getIcal_alarm());
                    writer.println("DTEND:" + alarmOfItem.getIcal_alarm());
                    writer.println("END:VEVENT");
                    writer.println("END:VCALENDAR");
                }

                writer.close();
            }
            catch (FileNotFoundException | UnsupportedEncodingException e) {

            }

            System.exit(0);
        }

        // no button
        else if (ae.getActionCommand() == "NO") {

            System.exit(0);
        }

        // file choose functionality
        else if (ae.getActionCommand() == "FILE") {

            // set text field to selected file
            view.fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = view.fileChooser.showOpenDialog(view);

            if (result == JFileChooser.APPROVE_OPTION) {

                File selectedFile = view.fileChooser.getSelectedFile();
                view.txt_file.setText(String.valueOf(selectedFile));
            }
        }
    }
}

// ****** LOAD ALARMS LISTENERS
// action listeners
class LoadAlarmsActionListener implements ActionListener {

    LoadAlarms view;
    Model model;

    public LoadAlarmsActionListener(LoadAlarms view, Model model) {

        this.view = view;
        this.model = model;
    }

    public LoadAlarmsActionListener(LoadAlarms view) {

        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        String filepath = view.txt_file.getText();
        ArrayList<String> icalAlarms = new ArrayList<>();

        // read file and separate out the alarms in their ical DTSTART format
        if (ae.getActionCommand() == "YES") {

            try {
                // read file
                BufferedReader file = new BufferedReader(new FileReader(filepath));
                String line;
                String[] lineSplit;

                // loop through file and split each line into an array
                while ((line = file.readLine()) != null) {

                    lineSplit = line.split(":");
                    java.util.List<String> lineArray = new ArrayList<String>(Arrays.asList(lineSplit));

                    // if the line starts with DTSTART put the datetime string into an array
                    if (lineArray.get(0).equals("DTSTART")) {

                        icalAlarms.add(lineArray.get(1));
                    }
                }

                // error checking
                System.out.println(icalAlarms);

                file.close();
            }
            catch (IOException e) {

            }

            // loop through alarms and add them to the queue
            for (String icalAlarm : icalAlarms) {

                //split string into characters and other formatting
                String[] icalChars;
                icalChars = icalAlarm.split("");
                ArrayList<String> icalSplit = new ArrayList<String>(Arrays.asList(icalChars));

                String year = icalSplit.get(0) + icalSplit.get(1) + icalSplit.get(2) + icalSplit.get(3);
                String month = icalSplit.get(4) + icalSplit.get(5);
                String day = icalSplit.get(6) + icalSplit.get(7);
                String hour = icalSplit.get(9) + icalSplit.get(10);
                String minutes = icalSplit.get(11) + icalSplit.get(12);

                String datetimeString = (hour + ":" + minutes + " " + day + "/" + month + "/" + year);
                DateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");

                try {

                    Date alarm = format.parse(datetimeString);
                    model.addAlarm(alarm);
                }
                catch (ParseException e) {

                }
            }

            view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
        }

        // no button
        else if (ae.getActionCommand() == "NO") {

            view.dispatchEvent(new WindowEvent(view, WindowEvent.WINDOW_CLOSING));
        }

        // file choose functionality
        else if (ae.getActionCommand() == "FILE") {

            // set text field to selected file
            view.fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = view.fileChooser.showOpenDialog(view);

            if (result == JFileChooser.APPROVE_OPTION) {

                File selectedFile = view.fileChooser.getSelectedFile();
                view.txt_file.setText(String.valueOf(selectedFile));
            }
        }
    }
}

