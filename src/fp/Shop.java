package fp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import fp.GameLogicPanel.TimerListener;

public class Shop extends JFrame implements ActionListener {
    private JLabel header = new JLabel();
    private JLabel money_text = new JLabel("money:");
    private JLabel money_value = new JLabel("");

    private JScrollPane jscroll_side = new JScrollPane();
    private JPanel jp_side_buttons = new JPanel();

    private JButton jb_commodity_display_left = new JButton();
    private JButton jb_commodity_display_right = new JButton();

    private JPanel jp_commodity_all = new JPanel();

    private JButton jb_turn_left = new JButton("left");
    private JButton jb_turn_right = new JButton("right");
    private JButton jb_purchase = new JButton("purchase");
    private JButton jb_return_game = new JButton("return");

    private JPanel jp_footer = new JPanel();

    private JPanel jp_commodity_and_footer = new JPanel();

    private JSplitPane split_bar_and_commodity = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jscroll_side, jp_commodity_and_footer);
    //commodity    
    private JButton jb_buy_fish = new JButton("Fish");
    private JButton jb_buy_background = new JButton("Wallpaper");
    private JButton jb_buy_other = new JButton("other");
    private JButton jb_buy_other2 = new JButton("other1");
    private JButton jb_buy_other3 = new JButton("other2");
    private JButton jb_buy_other4 = new JButton("other3");
    
    private JPanel jp_total = new JPanel();
    
    private FlowLayout flowLayout = new FlowLayout(FlowLayout.TRAILING);
    
    private Timer timer = new Timer(125,new TimerListener());
    
    private CommodityList commodity = new CommodityList();

    protected int left_image_index = 0;
    protected int right_image_index = 1;
    
    private boolean left_is_selected = false;
    private boolean right_is_selected = false;
    
    private Border border_red = BorderFactory.createLineBorder(Color.RED);
    private Border border_null = null;
    //fish:0;background:1
    private int request_commodity;
    private int request_fish_number;
    private boolean request_is_finished = false;
    
    private ActionEvent display_event;
    private GamePanel game_Panel;
    private UserData user;
    
    public Shop(GamePanel gamePanel, UserData input_user) {    
        game_Panel = gamePanel;
        user = input_user;
        setPanel();
        timer.start();
        addActionListener_button();
        addTurnDirectionListener();
    }

    public void setPanel() {
        //header
        header.setSize(600, 80);
        header.setBounds(0, 0, 600, 80);
        header.setLayout(flowLayout);
        header.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        header.add(money_text);

        money_value.setText(String.valueOf(user.money));

        header.add(money_value);
        header.setIcon(new ImageIcon("images/ui/header_shop.jpg"));
        //side
        jp_side_buttons.setLayout(new GridLayout(6, 1, 10, 10));
        jp_side_buttons.add(jb_buy_fish);
        jp_side_buttons.add(jb_buy_background);
        jp_side_buttons.add(jb_buy_other);
        jp_side_buttons.add(jb_buy_other2);
        jp_side_buttons.add(jb_buy_other3);
        jp_side_buttons.add(jb_buy_other4);
        jscroll_side.setViewportView(jp_side_buttons);
        JScrollBar scrollbar = new JScrollBar();
        jscroll_side.setVerticalScrollBar(scrollbar);
        jscroll_side.setSize(100, 300);
        jscroll_side.setBounds(0, 100, 150, 300);
        //commodity
        jp_commodity_all.setLayout(new GridLayout(1, 2, 10, 10));
        jp_commodity_all.setBorder(new TitledBorder("Commodity"));
        jp_commodity_all.add(jb_commodity_display_left);
        jp_commodity_all.add(jb_commodity_display_right);
        jp_commodity_all.setBounds(150, 100, 450, 350);
        //jp_footer
        jp_footer.add(jb_turn_left);
        jp_footer.add(jb_turn_right);
        jp_footer.add(jb_purchase);
        jp_footer.add(jb_return_game);
        //jp_commodity_and_footer
        jp_commodity_and_footer.setLayout(new BorderLayout(10, 10));
        jp_commodity_and_footer.add(jp_commodity_all,BorderLayout.CENTER);
        jp_commodity_and_footer.add(jp_footer,BorderLayout.SOUTH);
        //JPanel total
        jp_total.setLayout(new BorderLayout(10, 10));
        jp_total.add(header,BorderLayout.NORTH);
        jp_total.add(split_bar_and_commodity,BorderLayout.CENTER);
        //JFrame
        add(jp_total);
    }

    public void set_commodity_display() {
        //width=243;height=222
        if ( display_event.getSource() == jb_buy_fish ) {
            set_commodity_display_fish();
        } else if( display_event.getSource() == jb_buy_background ) {
            set_commodity_display_background();
        }
    }

    public void set_commodity_display_fish() {
        //width=243;height=222
        if( left_image_index < 0 ) {
            left_image_index = commodity.numberOfImage_Fish - 1;
        } else if( left_image_index == commodity.numberOfImage_Fish ) {
            left_image_index=0;
        }

        if( right_image_index < 0 ) {
            right_image_index = commodity.numberOfImage_Fish - 1;
        } else if( right_image_index == commodity.numberOfImage_Fish) {
            right_image_index = 0;
        }
        //fish
        jb_commodity_display_left.setIcon(commodity.Set_returnImageIcon(0, left_image_index));
        jb_commodity_display_right.setIcon(commodity.Set_returnImageIcon(0, right_image_index));

        validate();
    }

    public void set_commodity_display_background() {
        if( left_image_index < 0 ) {
            left_image_index = commodity.numberOfImage_Bowl - 1;
        } else if ( left_image_index == commodity.numberOfImage_Bowl ) {
            left_image_index = 0;
        }

        if( right_image_index < 0 ) {
            right_image_index = commodity.numberOfImage_Bowl - 1;
        } else if( right_image_index == commodity.numberOfImage_Bowl ) {
            right_image_index = 0;
        }

        jb_commodity_display_left.setIcon(commodity.Set_returnImageIcon(1, left_image_index));
        jb_commodity_display_right.setIcon(commodity.Set_returnImageIcon(1, right_image_index));

        validate();
    }

    public void set_commodity_selected_and_border(ActionEvent e) {
        if( e.getSource() == jb_commodity_display_left ) {
            left_is_selected = true;
            right_is_selected = false;
            jb_commodity_display_left.setBorder(border_red);
            jb_commodity_display_right.setBorder(border_null);
            System.out.println("ok");
        } else if ( e.getSource() == jb_commodity_display_right ) {
            left_is_selected = false;
            right_is_selected = true;
            jb_commodity_display_left.setBorder(border_null);
            jb_commodity_display_right.setBorder(border_red);
        }
    }

    public void set_buy_request() {
        if( display_event.getSource() == jb_buy_fish && (left_is_selected || right_is_selected) ) {
            System.out.println("buy");

            request_commodity = 0;
            request_fish_number = Integer.parseInt((JOptionPane.showInputDialog(null, "Input the number of fishes you want to buy :", "Purchase", JOptionPane.QUESTION_MESSAGE)));

            if ( user.money >= 100*request_fish_number ) {
                user.money -= 100*request_fish_number;

                if( request_fish_number > 0 && request_fish_number <= 30 && left_is_selected ) {
                    System.out.println(request_fish_number);
                    game_Panel.buy_fish(request_fish_number, left_image_index);
                } else if ( request_fish_number > 0 && request_fish_number <= 30 && right_is_selected ) {
                    game_Panel.buy_fish(request_fish_number, right_image_index);
                } else {
                    JOptionPane.showMessageDialog(null, "the number of fishes should be greater than 0 or less than 30");
                }
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough money!");
            }
        } else if( display_event.getSource() == jb_buy_background && (left_is_selected || right_is_selected) ) {
            request_commodity = 1;

            int choose;

            if ( user.money >= 100 ) {
                choose=JOptionPane.showConfirmDialog(null, "Do you want to buy this background ?");

                if( choose == 0 && left_is_selected ) {
                    game_Panel.buy_background(left_image_index);
                    user.money -= 100;
                } else if( choose == 0 && right_is_selected ) {
                    game_Panel.buy_background(right_image_index);
                    user.money -= 100;
                } else {
                    request_is_finished = false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "You don't have enough money!");
            }
        }
    }

    public void addActionListener_button() {
        jb_buy_fish.addActionListener(this);
        jb_buy_background.addActionListener(this);
        jb_purchase.addActionListener(this);
        jb_return_game.addActionListener(this);
        jb_turn_left.addActionListener(this);
        jb_turn_right.addActionListener(this);
        jb_commodity_display_left.addActionListener(this);
        jb_commodity_display_right.addActionListener(this);
    }

    public void addTurnDirectionListener() {
        jb_turn_left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ++left_image_index;
                ++right_image_index;
                set_commodity_display();
            }
        });

        jb_turn_right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                --left_image_index;
                --right_image_index;
                set_commodity_display();
            }
        });
    }

    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            money_value.setText(String.valueOf(user.money));
            repaint();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if ( e.getSource() == jb_turn_left ) {
            // blank
        } else if ( e.getSource() == jb_turn_right ) {
            // blank
        } else if ( e.getSource() == jb_purchase ) {
            set_buy_request();
        } else if ( e.getSource() == jb_return_game) {
            this.setVisible(false);
        } else if ( e.getSource() == jb_buy_fish) {
            display_event=e;
            set_commodity_display_fish();
        } else if ( e.getSource() == jb_buy_background ) {
            display_event=e;
            set_commodity_display_background();
        } else if ( e.getSource() == jb_commodity_display_left ) {
            set_commodity_selected_and_border(e);
            System.out.println("jl");
        } else if ( e.getSource() == jb_commodity_display_right ) {
            set_commodity_selected_and_border(e);
        }
    }

    public void update_frame() { this.validate(); }
}
