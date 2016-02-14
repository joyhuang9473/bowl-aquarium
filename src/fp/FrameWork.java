package fp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class FrameWork extends JFrame {
    private GamePanel gamePanel;
    
    private Menu menu = new Menu();
    private SetupFrame setup = new SetupFrame();

    private UserData userData = new UserData();
    private UserVerification userVerification;

    private Socket client;
    private ObjectOutputStream toServer;
    private ObjectInputStream fromServer;
    
    private boolean is_feeding_Mode = false;

    public FrameWork() {
        add(menu);
        addActionListener_Menu_buttons();
        addActionListener_SetupFrame_buttons();

        try {
            client = new Socket("localhost", 8000);
        } catch (IOException ex) {
            System.out.println("client socket error");
        }
    }

    public void set_Menu() {
        add(menu);
        menu.repaint();
        this.validate();
    }

    public void set_UserVerification() {
        userVerification = new UserVerification(userData.getUserType());
        add(userVerification);
        userVerification.repaint();
        addActionListener_UserVerification_buttons();
        this.validate();
    }

    public void set_GamePanel() {
        gamePanel = new GamePanel(userData, this, setup);
        gamePanel.add_User_Information(userData);
        add(gamePanel);
        addActionListener_GamePanel_buttons();
        this.validate();
    }

    public void addActionListener_Menu_buttons() {
        menu.jb_start.addActionListener(new MenuActionListener());
        menu.jb_create_new_user.addActionListener(new MenuActionListener());
        menu.jb_quit.addActionListener(new MenuActionListener());
    }

    public void addActionListener_UserVerification_buttons() {
        userVerification.jb_enter.addActionListener(new UserVerificationActionListener());
        userVerification.jb_return.addActionListener(new UserVerificationActionListener());
    }

    private void addActionListener_GamePanel_buttons() {
        gamePanel.jb_describe.addActionListener(new GamePanelActionListener());
        gamePanel.jb_fish_feeding.addActionListener(new GamePanelActionListener());
        gamePanel.jb_fish_shop.addActionListener(new GamePanelActionListener());
        gamePanel.jb_fish_state.addActionListener(new GamePanelActionListener());
        gamePanel.jb_setUp.addActionListener(new GamePanelActionListener());
    }
    
    private void addActionListener_SetupFrame_buttons() {
        setup.jb_setBackground.addActionListener(new SetupFrameActionListener());
        setup.jb_exit_save.addActionListener(new SetupFrameActionListener());
    }

    class MenuActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if ( e.getSource() == menu.jb_start ) {
                remove(menu);
                try {
                    userData.setUserType(0);
                    set_UserVerification();
                } catch (Exception e1) {
                    System.out.println("set_UserVerification() is wrong");
                }
            } else if ( e.getSource() == menu.jb_create_new_user ) {
                remove(menu);
                try {
                    userData.setUserType(1);
                    set_UserVerification();
                } catch (Exception e1) {
                    System.out.println("set_UserVerification() is wrong");
                }
            } else if ( e.getSource() == menu.jb_quit ) {
                System.exit(0);
            }
        }
    }

    class UserVerificationActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if ( e.getSource() == userVerification.jb_enter ) {
                userData.setUserName(userVerification.jtf_name.getText());
                userData.setUserPassword(userVerification.jtf_password.getText());

                updateUserData(connectDatabase());
                set_GamePanel();
                remove(userVerification);
            } else if ( e.getSource() == userVerification.jb_return ) {
                set_Menu();
                remove(userVerification);
            }
        }
    }

    class GamePanelActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if ( e.getSource() == gamePanel.jb_fish_state ) {
                gamePanel.set_FishState();
            } else if ( e.getSource() == gamePanel.jb_fish_feeding ) {
                is_feeding_Mode = !is_feeding_Mode;
                gamePanel.setFeedingButton(is_feeding_Mode);
                gamePanel.logicPanel.is_feeding(is_feeding_Mode);
            } else if ( e.getSource() == gamePanel.jb_fish_shop ) {
                gamePanel.set_ShopFrame();
            } else if ( e.getSource() == gamePanel.jb_setUp ) {
                gamePanel.set_SetupFrame();
            } else if ( e.getSource() == gamePanel.jb_describe ) {
                Description description = new Description();
                description.setVisible(true);
                description.setSize(80,80);
                description.setLocationRelativeTo(null);
            }
        }
    }
    
    class SetupFrameActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if ( e.getSource() == setup.jb_setBackground ) {
                ReadFile read_file = new ReadFile();
                read_file.read_Image_data();
                gamePanel.logicPanel.bowl_image = read_file.return_image_file();
                gamePanel.repaint();
            } else if ( e.getSource() == setup.jb_exit_save ) {
                userData.setUserType(2);
                updateUserData(connectDatabase());

                JOptionPane.showMessageDialog(null, "Update Database. Finish!");
                System.exit(0);
            }
        }
    }
    
    public UserData connectDatabase() {
        UserData db_userData = null;

        try {
            toServer = new ObjectOutputStream(client.getOutputStream());
            toServer.writeObject(userData);
            toServer.flush();
            fromServer = new ObjectInputStream(client.getInputStream());
            db_userData = (UserData)fromServer.readObject();
        } catch (IOException ex) {
            System.out.println("Client: cannot send userdata to server");
        } catch (ClassNotFoundException ex) {
            System.out.println("Client: cannot get userdata from server");
        }

        return db_userData;    
    }

    public void updateUserData(UserData db_userData) {
        userData.setMoney(db_userData.getMoney());
        userData.setTotalFish(db_userData.getTotalFish());
        userData.setKind1FishNumber(db_userData.getKind1FishNumber());
        userData.setKind2FishNumber(db_userData.getKind2FishNumber());
    }
}
