package clock;

import javax.swing.*;
import java.awt.*;

public class LoadAlarms extends JFrame{

    public LoadAlarms() {

        setTitle("Load alarms?");
        setSize(500, 400);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container pane = getContentPane();
        pane.setLayout(null);


        JLabel lbl_load = new JLabel("Load alarms from file?");
        lbl_load.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_load.setBounds(125, 20, 250, 50);
        pane.add(lbl_load);

        JButton btn_yes = new JButton("Yes");
        btn_yes.setFont(new Font("Serif", Font.BOLD, 24));
        btn_yes.setBounds(125, 70, 100, 50);
        pane.add(btn_yes);

        JButton btn_no = new JButton("No");
        btn_no.setFont(new Font("Serif", Font.BOLD, 24));
        btn_no.setBounds(235, 70, 100, 50);
        pane.add(btn_no);


    }
}
