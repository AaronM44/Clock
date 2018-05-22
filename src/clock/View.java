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

    //JFrame frame = new JFrame();
    
    public View(Model model) {

        // main window
        panel = new ClockPanel(model);

        //frame.setContentPane(panel);
        setTitle("Java Clock");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Start of border layout code

        Container pane = getContentPane();
        
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
