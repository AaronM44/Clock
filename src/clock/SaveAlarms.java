package clock;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * Window that provides the option for a user to save the alarms in the queue to an iCal file
 */
public class SaveAlarms extends JFrame{

    JLabel lbl_save = new JLabel("Save alarms to file?");
    JButton btn_yes = new JButton("Yes");
    JButton btn_no = new JButton("No");
    JFileChooser fileChooser = new JFileChooser();
    JTextField txt_file = new JTextField("");
    JButton btn_openFile = new JButton("...");

    public SaveAlarms(Model model) {

        setTitle("Save alarms?");
        setSize(500, 300);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container pane = getContentPane();
        pane.setLayout(null);


        lbl_save.setFont(new Font("Serif", Font.BOLD, 24));
        lbl_save.setBounds(125, 20, 250, 50);
        pane.add(lbl_save);

        txt_file.setFont(new Font("Serif", Font.BOLD, 24));
        txt_file.setBounds(125, 70, 200, 50);
        txt_file.setEditable(false);
        pane.add(txt_file);

        btn_openFile.setFont(new Font("Serif", Font.BOLD, 24));
        btn_openFile.setBounds(325, 70, 50, 50);
        pane.add(btn_openFile);

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(null,"ics");
        fileChooser.setFileFilter(filter);
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
        btn_yes.addActionListener( new SaveAlarmsActionListener(this, model));
        btn_no.addActionListener( new SaveAlarmsActionListener(this));
        btn_openFile.addActionListener( new SaveAlarmsActionListener(this, model));
    }
}
