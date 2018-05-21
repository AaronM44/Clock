package clock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;
import javax.swing.*;

public class ViewAlarm  extends JFrame{

    Model model;

    JLabel lbl_time = new JLabel("Time");
    JLabel lbl_date = new JLabel("Date");
    JButton btn_alarm1 = new JButton("");
    JButton btn_alarm2 = new JButton("");
    JButton btn_alarm3 = new JButton("");
    JButton btn_alarm4 = new JButton("");
    JButton btn_alarm5 = new JButton("");

    public ViewAlarm(Model model) {

        this.model = model;

        setTitle("View alarms");
        setSize(500, 400);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container pane = getContentPane();
        pane.setLayout(null);

        // first row
        lbl_time.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_time.setBounds(20, 20, 150, 50);
        pane.add(lbl_time);

        lbl_date.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_date.setBounds(150, 20, 150, 50);
        pane.add(lbl_date);

        // beyond
        btn_alarm1.setFont(new Font("Serif", Font.BOLD, 24));
        btn_alarm1.setBounds(20, 70, 250, 50);
        pane.add(btn_alarm1);
        btn_alarm1.setVisible(false);

        btn_alarm2.setFont(new Font("Serif", Font.BOLD, 24));
        btn_alarm2.setBounds(20, 120, 250, 50);
        pane.add(btn_alarm2);
        btn_alarm2.setVisible(false);

        btn_alarm3.setFont(new Font("Serif", Font.BOLD, 24));
        btn_alarm3.setBounds(20, 170, 250, 50);
        pane.add(btn_alarm3);
        btn_alarm3.setVisible(false);

        btn_alarm4.setFont(new Font("Serif", Font.BOLD, 24));
        btn_alarm4.setBounds(20, 220, 250, 50);
        pane.add(btn_alarm4);
        btn_alarm4.setVisible(false);

        btn_alarm5.setFont(new Font("Serif", Font.BOLD, 24));
        btn_alarm5.setBounds(20, 270, 250, 50);
        pane.add(btn_alarm5);
        btn_alarm5.setVisible(false);

        // button listeners
        btn_alarm1.addActionListener( new ViewAlarmActionListener(this));
        btn_alarm2.addActionListener( new ViewAlarmActionListener(this));
        btn_alarm3.addActionListener( new ViewAlarmActionListener(this));
        btn_alarm4.addActionListener( new ViewAlarmActionListener(this));
        btn_alarm5.addActionListener( new ViewAlarmActionListener(this));
    }
}
