package fp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import fp.GameLogicPanel.TimerListener;

public class Menu extends JPanel implements ActionListener {
    private JLabel jl_menu = new JLabel();

    protected JButton jb_start = new JButton("Login");
    protected JButton jb_create_new_user = new JButton("New User");
    protected JButton jb_quit = new JButton("Quit");

    protected ActionEvent e_menu ;

    public Menu() {
        jl_menu.setLayout(null);
        jl_menu.setIcon(new ImageIcon("images/ui/menu_background.jpg"));

        jb_start.setBounds(350, 310, 100, 50);
        jb_create_new_user.setBounds(350, 370, 100, 50);
        jb_quit.setBounds(350, 430, 100, 50);
        jl_menu.add(jb_start);
        jl_menu.add(jb_create_new_user);
        jl_menu.add(jb_quit);

        add(jl_menu,BorderLayout.CENTER);

        this.validate();
    }

    public void actionPerformed(ActionEvent arg0) {}
}
