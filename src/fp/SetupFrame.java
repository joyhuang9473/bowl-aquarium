package fp;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SetupFrame extends JFrame {
    private JPanel jp_setup = new JPanel();

    public JButton jb_setBackground = new JButton("Set custom Background");
    public JButton jb_exit_save = new JButton("Saved and Exit");

    public SetupFrame() {
        jp_setup.setLayout(new GridLayout(4, 1, 10, 10));
        jp_setup.add(jb_setBackground);
        jp_setup.add(jb_exit_save);

        add(jp_setup);
    }
}
