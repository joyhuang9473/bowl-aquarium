package fp;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class FishState extends JFrame implements ActionListener {
    private JLabel [] jl_fish_image = new JLabel [30];
    private JLabel [] jl_fish_age_text = new JLabel [30];
    private JLabel [] jl_fish_size_text = new JLabel [30];
    private JLabel [] jl_fish_satiety_text = new JLabel [30];

    private JLabel [] jl_fish_value = new JLabel [30];
    private JLabel [] jl_fish_other  = new JLabel [30];
    private JLabel [] jl_fish_other2 = new JLabel [30];

    private JButton [] jb_sell_fish = new JButton[30];

    private JLabel [] jp_add_fish_info = new JLabel[30];

    protected UserData user;
    protected Fish [] fish;

    private JScrollPane jscroll_side = new JScrollPane();
    private JPanel jp_all = new JPanel();
    private JScrollBar scrollbar = new JScrollBar();
    private LineBorder border = new LineBorder(Color.BLACK);

    public FishState(UserData input_user, Fish []input_fish) {
        user = input_user;
        fish = input_fish;

        addFishState();

        jscroll_side.setViewportView(jp_all);
        jscroll_side.setVerticalScrollBar(scrollbar);
        jscroll_side.setSize(100,300);
        jscroll_side.setBounds(0, 100, 150, 300);

        add(jscroll_side);
    }

     public void addFishState() {
        jp_all.removeAll();

        jp_all.setLayout(new GridLayout(user.total_fish_number, 1, 0, 0));

        for ( int i = 0 ; i < user.total_fish_number ; i++ ) {
            jl_fish_image[i] = new JLabel();

            if ( fish[i].fish_kind == 0 ) {
                jl_fish_image[i].setIcon(new ImageIcon("images/commodity/fish1_state.png"));
            } else if ( fish[i].fish_kind == 1 ) {
                jl_fish_image[i].setIcon(new ImageIcon("images/commodity/fish2_state.png"));
            }

            jl_fish_age_text[i] = new JLabel();
            jl_fish_age_text[i].setText("age: " + String.valueOf(fish[i].age));
            jl_fish_satiety_text[i] = new JLabel();

            if ( fish[i].satiety == true ) {
                jl_fish_satiety_text[i].setText("full~");
            } else {
                jl_fish_satiety_text[i].setText("hungry!");
            }

            jl_fish_size_text[i] = new JLabel();
            jl_fish_value[i] = new JLabel();
            
            if ( fish[i].age >= 0 && fish[i].age < 3 ) {
                jl_fish_size_text[i].setText("baby");
                jl_fish_value[i].setText("price: $0");
            } else if ( fish[i].age >= 3 && fish[i].age < 6) {
                jl_fish_size_text[i].setText("teenager");
                jl_fish_value[i].setText("price: $50");
            } else if ( fish[i].age >= 6 ) {
                jl_fish_size_text[i].setText("adult");
                jl_fish_value[i].setText("price: $500");
            }

            jb_sell_fish[i] = new JButton();

            jb_sell_fish[i].setText("Sell");
            jb_sell_fish[i].addActionListener(this);
            
            jl_fish_other[i] = new JLabel();
            jl_fish_other2[i] = new JLabel();
        
            jp_add_fish_info[i] = new JLabel();
            jp_add_fish_info[i].setLayout(new GridLayout(2, 4, 0, 0));
            jp_add_fish_info[i].setBorder(border);

            jp_add_fish_info[i].add(jl_fish_image[i]);
            jp_add_fish_info[i].add(jl_fish_age_text[i]);
            jp_add_fish_info[i].add(jl_fish_satiety_text[i]);
            jp_add_fish_info[i].add(jl_fish_size_text[i]);
            jp_add_fish_info[i].add(jl_fish_other[i]);
            jp_add_fish_info[i].add(jl_fish_other2[i]);
            jp_add_fish_info[i].add(jl_fish_value[i]);
            jp_add_fish_info[i].add(jb_sell_fish[i]);
            jp_add_fish_info[i].setBackground(Color.WHITE);
            
            jp_all.add(jp_add_fish_info[i]);
        }
    }

    public void close() { this.setVisible(false); }
    public void actionPerformed(ActionEvent e) {
        int sell_success = 10;
        int sell_number_fish_kind1 = 0;
        int sell_number_fish_kind2 = 0;
        int sell_number_fish_total = 0;

        for ( int i = 0 ; i < user.total_fish_number ; i++ ) {
            if ( e.getSource() == jb_sell_fish[i] ) {
                sell_success = JOptionPane.showConfirmDialog(null, "sure to sell?");

                if ( sell_success == 0 ) {
                    if ( fish[i].fish_kind == 0) {
                        ++sell_number_fish_kind1;
                    } else if ( fish[i].fish_kind == 1 ) {
                        ++sell_number_fish_kind2;
                    }

                    if ( fish[i].age >= 3 && fish[i].age < 6 ) {
                        fish[i].is_live = false;
                        user.money += 50;
                    } else if ( fish[i].age >= 6 ) {
                        fish[i].is_live = false;
                        user.money += 500;
                    } else if ( fish[i].age < 3 ) {
                        fish[i].is_live = false;
                    }

                    ++sell_number_fish_total;
                    close();
                }
            }

            if ( sell_success == 0 && fish[i+1] != null ) fish[i] = fish[i+1];
        }

        user.total_fish_number -= sell_number_fish_total;
        user.kind1_fish_number -= sell_number_fish_kind1;
        user.kind2_fish_number -= sell_number_fish_kind2;
    }
}
