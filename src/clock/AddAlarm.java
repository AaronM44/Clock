package clock;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Observer;
import javax.swing.*;

public class AddAlarm  extends JFrame{

    Model model;

    JSpinner date_spinner = new JSpinner( new SpinnerDateModel());
    SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    JLabel lbl_set = new JLabel("Set Alarm");
    JButton btn_add = new JButton("Add");

    public AddAlarm(Model model) {

        this.model = model;

        setTitle("Add new alarm");
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
        date_spinner.setEditor(new JSpinner.DateEditor(date_spinner, format.toPattern()));
        date_spinner.setFont(new Font("Serif", Font.BOLD, 24));
        date_spinner.setBounds(125, 70, 250, 50);
        pane.add(date_spinner);

        // sixth row
        btn_add.setFont(new Font("Serif", Font.BOLD, 24));
        btn_add.setBounds(125, 130, 100, 50);
        pane.add(btn_add);

        // add listener for the Add button
        btn_add.addActionListener(new AddAlarmActionListener(this, model));

        this.addWindowListener(new AddAlarmWindowAdapter(this, model));

    }
}
