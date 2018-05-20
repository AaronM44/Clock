package clock;

import javax.swing.*;
import java.awt.*;

public class SaveAlarms extends JFrame{

    public SaveAlarms() {

        setTitle("Save alarms?");
        setSize(500, 400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container pane = getContentPane();
        pane.setLayout(null);


        JLabel lbl_save = new JLabel("Save alarms to file?");
        lbl_save.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_save.setBounds(125, 20, 250, 50);
        pane.add(lbl_save);

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
