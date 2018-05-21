package clock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;
import javax.swing.*;

public class EditAlarm  extends JFrame{

    Model model;
    Alarm alarm;

    SpinnerDateModel date_model = new SpinnerDateModel();
    JSpinner date_spinner = new JSpinner(date_model);

    JLabel lbl_set = new JLabel("Edit Alarm");
    JButton btn_save = new JButton("Save");
    JButton btn_delete = new JButton("Delete");

    public EditAlarm(Model model, Alarm alarm) {

        this.model = model;
        this.alarm = alarm;

        setTitle("Edit alarm");
        setSize(500, 300);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container pane = getContentPane();
        pane.setLayout(null);

        // first row
        lbl_set.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_set.setBounds(125, 20, 250, 50);
        pane.add(lbl_set);

        // second row
        date_spinner.setFont(new Font("Serif", Font.BOLD, 24));
        date_spinner.setBounds(125, 70, 250, 50);
        pane.add(date_spinner);

        // sixth row
        btn_save.setFont(new Font("Serif", Font.BOLD, 24));
        btn_save.setBounds(125, 130, 100, 50);
        pane.add(btn_save);

        btn_delete.setFont(new Font("Serif", Font.BOLD, 24));
        btn_delete.setBounds(235, 130, 100, 50);
        pane.add(btn_delete);

        // listeners
        btn_save.addActionListener( new EditAlarmActionListener(this));
        btn_delete.addActionListener( new EditAlarmActionListener(this));

    }
}
