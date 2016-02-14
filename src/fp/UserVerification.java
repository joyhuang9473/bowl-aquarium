package fp;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.io.ObjectOutputStream;

public class UserVerification extends JPanel {
    // panel component
    protected JButton jb_enter = new JButton("Enter");
    protected JButton jb_return = new JButton("Return");
    protected JLabel jl_user_name = new JLabel("Username: ");
    protected JLabel jl_user_password = new JLabel("Password: ");
    protected int lendth_username = 8;
    protected int lendth_userpassword = 8;
    protected JTextField jtf_name = new JTextField(lendth_username);
    protected JTextField jtf_password = new JTextField(lendth_userpassword);
    
    private JLabel jl_area = new JLabel();
    
    public UserVerification(int userType) { setLayout(userType); }
    
    public void setLayout(int userType) {
        //set panel layout
        jl_area.setLayout(null);
        //@Advise
        if ( userType == 0 ) {
            jl_area.setIcon(new ImageIcon("images/ui/user_login_bg.jpg"));
        } else if ( userType == 1 ) {
            jl_area.setIcon(new ImageIcon("images/ui/new_user_bg.jpg"));
        }

        jl_user_name.setBounds(240, 200, 100, 25);
        jtf_name.setBounds(340, 200, 150, 25);
        jl_user_password.setBounds(240, 225, 100, 25);
        jtf_password.setBounds(340, 225, 150, 25);
        jb_enter.setBounds(270, 300, 100, 25);
        jb_return.setBounds(370, 300, 100, 25);
        
        jl_area.add(jl_user_name);
        jl_area.add(jtf_name);
        jl_area.add(jl_user_password);
        jl_area.add(jtf_password);
        jl_area.add(jb_enter);
        jl_area.add(jb_return);
        add(jl_area, BorderLayout.CENTER);
    }

}
