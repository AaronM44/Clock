package clock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Alert extends JFrame{

    public Alert() {

        setTitle("Alert");
        setSize(500, 400);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container pane = getContentPane();
        pane.setLayout(null);

        JLabel lbl_alarm = new JLabel("11:00 20/05/2018");
        lbl_alarm.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_alarm.setBounds(125, 20, 250, 50);
        pane.add(lbl_alarm);

        JButton btn_dismiss = new JButton("Dismiss");
        btn_dismiss.setFont(new Font("Serif", Font.BOLD, 24));
        btn_dismiss.setBounds(135, 70, 100, 50);
        pane.add(btn_dismiss);

    }
}
