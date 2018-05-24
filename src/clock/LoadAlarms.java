package clock;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LoadAlarms extends JFrame{

    JLabel lbl_load = new JLabel("Load alarms from file?");
    JButton btn_yes = new JButton("Yes");
    JButton btn_no = new JButton("No");
    JFileChooser fileChooser = new JFileChooser();
    JTextField txt_file = new JTextField("");
    JButton btn_openFile = new JButton("...");

    public LoadAlarms(Model model) {

        setTitle("Load alarms?");
        setSize(500, 300);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Container pane = getContentPane();
        pane.setLayout(null);


        lbl_load.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_load.setBounds(125, 20, 250, 50);
        pane.add(lbl_load);

        txt_file.setFont(new Font("Serif", Font.BOLD, 24));
        txt_file.setBounds(125, 70, 200, 50);
        txt_file.setEditable(false);
        pane.add(txt_file);

        btn_openFile.setFont(new Font("Serif", Font.BOLD, 24));
        btn_openFile.setBounds(325, 70, 50, 50);
        pane.add(btn_openFile);

        pane.add(fileChooser);

        btn_yes.setFont(new Font("Serif", Font.BOLD, 24));
        btn_yes.setBounds(125, 130, 100, 50);
        pane.add(btn_yes);

        btn_no.setFont(new Font("Serif", Font.BOLD, 24));
        btn_no.setBounds(235, 130, 100, 50);
        pane.add(btn_no);

        // set action commands
        btn_yes.setActionCommand("YES");
        btn_no.setActionCommand("NO");
        btn_openFile.setActionCommand("FILE");

        // button listeners
        btn_yes.addActionListener( new LoadAlarmsActionListener(this, model));
        btn_no.addActionListener( new LoadAlarmsActionListener(this));
        btn_openFile.addActionListener( new LoadAlarmsActionListener(this, model));
    }
}
