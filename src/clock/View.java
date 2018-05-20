package clock;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observer;
import java.util.Observable;

public class View implements Observer {
    
    ClockPanel panel;
    
    public View(Model model) {

        // main window
        JFrame frame = new JFrame();

        panel = new ClockPanel(model);
        //frame.setContentPane(panel);
        frame.setTitle("Java Clock");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Start of border layout code

        Container pane = frame.getContentPane();
        
        JButton button = new JButton("Current Time");
        pane.add(button, BorderLayout.PAGE_START);
         
        panel.setPreferredSize(new Dimension(300, 200));
        pane.add(panel, BorderLayout.CENTER);
         
        JButton btn_view = new JButton("View Alarms");
        pane.add(btn_view, BorderLayout.LINE_START);
         
        button = new JButton("Next Alarm");
        pane.add(button, BorderLayout.PAGE_END);

        JButton btn_add = new JButton("Add Alarm");
        pane.add(btn_add, BorderLayout.LINE_END);
        
        // End of borderlayout code


        // event listener for adding a new alarm
        btn_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                AddAlarm newAlarm = new AddAlarm();
            }
        });

        // event listener for viewing alarms set
        btn_view.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                ViewAlarm viewAlarms = new ViewAlarm();
            }
        });

        // event listener for when user attempts to close application
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                SaveAlarms saveAlarms = new SaveAlarms();
            }
        });
        
        frame.pack();
        frame.setVisible(true);
    }

    
    public void update(Observable o, Object arg) {
        panel.repaint();
    }
}
