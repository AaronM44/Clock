package clock;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observer;
import java.util.Observable;

public class View extends JFrame implements Observer{
    
    ClockPanel panel;

    JButton btn_cur_time = new JButton("Current Time");
    JButton btn_view = new JButton("View Alarms");
    JButton btn_next= new JButton("Next Alarm");
    JButton btn_add = new JButton("Add Alarm");
    
    public View(Model model) {

        // main window
        panel = new ClockPanel(model);

        //frame.setContentPane(panel);
        setTitle("Java Clock");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // Start of border layout code

        Container pane = getContentPane();
         
        panel.setPreferredSize(new Dimension(300, 200));
        pane.add(panel, BorderLayout.CENTER);

        pane.add(btn_cur_time, BorderLayout.PAGE_START);
        pane.add(btn_view, BorderLayout.LINE_START);
        pane.add(btn_next, BorderLayout.PAGE_END);
        pane.add(btn_add, BorderLayout.LINE_END);

        btn_cur_time.setText(String.valueOf(model.hour));

        btn_add.setActionCommand("ADD");
        btn_view.setActionCommand("VIEW");
        
        // End of borderlayout code

        // listeners
        btn_add.addActionListener(new ViewActionListener(this, model));
        btn_view.addActionListener(new ViewActionListener(this, model));


        addWindowListener(new ViewWindowAdapter(this, model));

        pack();
        setVisible(true);
    }


    public void update(Observable o, Object arg) {
        panel.repaint();
    }
}
