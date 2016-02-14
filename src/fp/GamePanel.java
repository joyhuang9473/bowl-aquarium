package fp;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import fp.Shop.TimerListener;

public class GamePanel extends JPanel {
    protected GameLogicPanel logicPanel;

    private JLabel game_mark = new JLabel();
    private JLabel user_name = new JLabel("User:");
    private JLabel user_name_value = new JLabel();
    private JLabel user_money = new JLabel("Money:");
    private JLabel user_money_value = new JLabel();

    protected JButton jb_fish_state = new JButton("State");
    protected JButton jb_fish_feeding = new JButton("Feeding");
    protected JButton jb_fish_shop = new JButton("Shop");
    protected JButton jb_setUp = new JButton("SetUp");
    protected JButton jb_describe = new JButton("Describe");

    private Shop shop;
    private Timer timer = new Timer(125, new TimerListener());

    private int time_second;

    protected UserData user;
    protected SetupFrame setup;
    protected FishState fishState;

    public GamePanel(UserData input_user, FrameWork input_Frame, SetupFrame input_setup) {
        logicPanel = new GameLogicPanel(input_user);
        shop = new Shop(this,input_user);
        user = input_user;
        setup = input_setup;

        setLayout(null);
        setTitleLabel();
        setOptionLabel();

        add_Title_Option();

        logicPanel.setBounds(0, 60, 800, 440);
        add(logicPanel);
        timer.start();

        this.setBackground(Color.WHITE);
    }

    public void setTitleLabel() {
        game_mark.setBounds(0, 10, 400, 50);
        game_mark.setForeground(Color.BLUE);
        user_name.setBounds(400, 10, 50, 50);
        user_name_value.setBounds(450, 10, 50, 50);
        user_money.setBounds(600, 10, 50, 50);
        user_money_value.setBounds(650, 10, 50, 50);
    }

    public void setOptionLabel() {
        //(500,530)
        jb_fish_state.setBounds(250, 500, 100, 50);
        jb_fish_feeding.setBounds(350, 500, 100, 50);
        jb_fish_shop.setBounds(450, 500, 100, 50);
        jb_setUp.setBounds(550, 500, 100, 50);
        jb_describe.setBounds(650, 500, 100, 50);
    }

    public void set_ShopFrame() {
        shop.setVisible(true);
        shop.setSize(600, 400);
        shop.setLocationRelativeTo(null);
    }

    public void set_SetupFrame() {
        setup.setVisible(true);
        setup.setSize(300, 300);
        setup.setLocationRelativeTo(null);
    }

    public void set_FishState() {
        fishState = new FishState(user, logicPanel.fish);
        fishState.setVisible(true);
        fishState.setSize(720,500);
    }
    
    public void add_Title_Option() {
        add(game_mark);
        add(user_name);
        add(user_name_value);
        add(user_money);
        add(user_money_value);
        add(jb_fish_state);
        add(jb_fish_feeding);
        add(jb_fish_shop);
        add(jb_setUp);
        add(jb_describe);
    }

    public void add_User_Information(UserData user) {
        user_name_value.setText(user.username);
        user_money_value.setText(String.valueOf(user.money));
        validate();
    }

    public void buy_fish(int add_number,int kind_index) {
        logicPanel.add_fish(add_number, kind_index);
        shop.setVisible(false);
    }
    public void buy_background(int kind_index) {
        logicPanel.change_background(kind_index);
        shop.setVisible(false);
    }

    public void setFeedingButton(boolean is_feeding_Mode) {
        if ( is_feeding_Mode ) {
            jb_fish_feeding.setText("Feeding");
        } else {
            jb_fish_feeding.setText("Stop feeding");
        }
    }

    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            user_money_value.setText(String.valueOf(user.money));
            repaint();
        }
    }
}
