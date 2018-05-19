package clock;

import java.awt.*;
import java.util.Observer;
import javax.swing.*;

public class AddAlarm  extends JFrame{

    public AddAlarm() {

        setTitle("Add new alarm");
        setSize(500, 400);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container pane = getContentPane();
        pane.setLayout(null);

        // first row
        JLabel lbl_day = new JLabel("Day");
        lbl_day.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_day.setBounds(20, 20, 100, 50);
        pane.add(lbl_day);

        JLabel lbl_month = new JLabel("Month");
        lbl_month.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_month.setBounds(150, 20, 100, 50);
        pane.add(lbl_month);

        JLabel lbl_year = new JLabel("Year");
        lbl_year.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_year.setBounds(280, 20, 100, 50);
        pane.add(lbl_year);

        // second row
        JTextField txt_day = new JTextField();
        txt_day.setFont(new Font("Serif", Font.BOLD, 24));
        txt_day.setBounds(20, 70, 100, 50);
        pane.add(txt_day);

        JTextField txt_month = new JTextField();
        txt_month.setFont(new Font("Serif", Font.BOLD, 24));
        txt_month.setBounds(150, 70, 100, 50);
        pane.add(txt_month);

        JTextField txt_year = new JTextField();
        txt_year.setFont(new Font("Serif", Font.BOLD, 24));
        txt_year.setBounds(280, 70, 100, 50);
        pane.add(txt_year);

        // third row
        JLabel lbl_hours = new JLabel("Hour");
        lbl_hours.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_hours.setBounds(20, 120, 100, 50);
        pane.add(lbl_hours);

        JLabel lbl_minutes = new JLabel("Minutes");
        lbl_minutes.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_minutes.setBounds(150, 120, 100, 50);
        pane.add(lbl_minutes);

        // fourth row
        JTextField txt_hours = new JTextField();
        txt_hours.setFont(new Font("Serif", Font.BOLD, 24));
        txt_hours.setBounds(20, 170, 100, 50);
        pane.add(txt_hours);

        JTextField txt_minutes = new JTextField();
        txt_minutes.setFont(new Font("Serif", Font.BOLD, 24));
        txt_minutes.setBounds(150, 170, 100, 50);
        pane.add(txt_minutes);

        // fifth row
        JLabel lbl_blank = new JLabel("");
        lbl_blank.setBounds(20, 220, 100, 50);
        pane.add(lbl_blank);

        // sixth row
        JButton btn_add = new JButton("Add");
        btn_add.setFont(new Font("Serif", Font.BOLD, 24));
        btn_add.setBounds(20, 270, 100, 50);
        pane.add(btn_add);

    }
}
