package clock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

    Container pane = getContentPane();

    public ViewAlarm(Model model) {

        this.model = model;

        setTitle("View alarms");
        setSize(500, 400);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        pane.setLayout(null);

        // first row
        lbl_time.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_time.setBounds(20, 20, 150, 50);
        pane.add(lbl_time);

        lbl_date.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_date.setBounds(150, 20, 150, 50);
        pane.add(lbl_date);

        // window listener
        this.addWindowListener(new ViewAlarmWindowAdapter(this));
    }
}
