package clock;

import java.awt.*;
import java.util.Observer;
import javax.swing.*;

public class ViewAlarm  extends JFrame{

    public ViewAlarm() {

        setTitle("View alarms");
        setSize(500, 400);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container pane = getContentPane();
        pane.setLayout(null);

        // first row
        JLabel lbl_time = new JLabel("Time");
        lbl_time.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_time.setBounds(20, 20, 150, 50);
        pane.add(lbl_time);

        JLabel lbl_date = new JLabel("Date");
        lbl_date.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_date.setBounds(150, 20, 150, 50);
        pane.add(lbl_date);


    }
}
