package fp;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameLogicPanel extends JPanel {    
    private Bowl bowl = new Bowl();

    protected int numberOfFish_total = 0;

    private int numberOfFish_kind1 = 0;
    private int numberOfFish_kind2 = 0;
    private int numberOfFish_Max = 30;

    protected Fish [] fish = new Fish[numberOfFish_Max];

    protected Image bowl_image;

    private int panelSize_width;
    protected int panelSize_height;
    //hungry image
    protected ImageIcon hungry_imageIcon = new ImageIcon("images/feed/feeding.png");
    protected Image hungry_image = hungry_imageIcon.getImage();
    private int hungry_image_position_x;
    private int hungry_image_position_y;
    private int hungry_image_width = 30;
    private int hungry_image_height = 30;
    //feed image
    protected ImageIcon feed_imageIcon = new ImageIcon("images/feed/feed.png");
    protected Image feed_image = feed_imageIcon.getImage();
    protected int delay = 125;
    private Timer timer = new Timer(delay, new TimerListener());
    protected UserData user;

    private boolean is_feeding_Mode;
    protected int numberOfFeed_total = 0;
    private int numberOfFeed_Max = 10;
    protected Feeding [] feed = new Feeding[numberOfFeed_Max];

    private FeedingListener feedListener = new FeedingListener();
    
    public GameLogicPanel(UserData input_user) {
        bowl_image = bowl.get_bowel_image();
        user = input_user;
        numberOfFish_total = user.getTotalFish();
        numberOfFish_kind1 = user.getKind1FishNumber();
        numberOfFish_kind2 = user.getKind2FishNumber();

        origin_set_Fish();
        origin_set_Feed();
    }

    public void origin_set_Fish() {
        for ( int i = 0 ; i < numberOfFish_kind1 ; i++ ) {
            fish[i] = new Fish();
            fish[i].setLive();
            fish[i].setFish_Kind(0);

            for ( int j = 0 ; j < 2 ; j++ ) {
                if ( j == 0 ) fish[i].age = user.fish_data[i][j];
                if ( j == 1 ) {
                    if ( user.fish_data[i][j] == 0 ) {
                        fish[i].satiety = false;
                    } else {
                        fish[i].satiety = true;
                    }
                }
            }

            fish[i].setFish_size(fish[i].age, 0);
        }

        for ( int i = numberOfFish_kind1 ; i < numberOfFish_total ; i++) {
            fish[i] = new Fish();
            fish[i].setLive();
            fish[i].setFish_Kind(1);
            fish[i].setFish_size(fish[i].age, 1);
            
            for ( int j=0 ; j < 2 ; j++ ) {
                if ( j == 0 ) fish[i].age = user.fish_data[i][j];
                if ( j == 1 ) {
                    if ( user.fish_data[i][j] == 0 ) {
                        fish[i].satiety=false;
                    } else {
                        fish[i].satiety=true;
                    }
                }

                fish[i].setFish_size(fish[i].age,1);
            }
        }

        timer.start();
    }

    public void origin_set_Feed() {
        for ( int i=0 ; i < numberOfFeed_Max ; i++ ) {
            feed[i] = new Feeding(this);
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //bowl
        if ( bowl_image != null ) {
            g.drawImage(bowl_image,0,0,getWidth(),getHeight(),this);
        } else {
            System.out.print("wrong");
        }
        //fish image & feed image
        for ( int i = 0 ; i < numberOfFish_total ; i++ ) {    
            if( fish[i].is_live == true ) {
                g.drawImage(fish[i].image, fish[i].position_x, fish[i].position_y, fish[i].fishSize_width, fish[i].fishSize_height, this);

                if( fish[i].satiety == false ) {
                    if ( fish[i].is_turnRight ) {
                        hungry_image_position_x = fish[i].position_x + 40;
                        hungry_image_position_y = fish[i].position_y - 20;

                        g.drawImage(hungry_image, hungry_image_position_x, hungry_image_position_y, hungry_image_width, hungry_image_height, this);    
                    } else if ( !fish[i].is_turnRight ) {
                        hungry_image_position_x = fish[i].position_x - 20;
                        hungry_image_position_y = fish[i].position_y - 20;

                        g.drawImage(hungry_image, hungry_image_position_x, hungry_image_position_y, hungry_image_width, hungry_image_height, this);    
                    }
                }
            }
        }
        //feed draw
        for ( int j = 0 ; j < numberOfFeed_total ; j++ ) {
            if( feed[j].is_live == true ) {
                System.out.println("feed2");
                g.drawImage(feed[j].feed_image, feed[j].position_x, feed[j].position_y, feed[j].width, feed[j].height, this);
            }
        }//end feed
    }

    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            panelSize_width = getWidth();
            panelSize_height = getHeight();

            for ( int i = 0 ; i < numberOfFish_total ; i++ ) {
                if ( fish[i].position_x > (panelSize_width - fish[i].fishSize_width) ) {
                    fish[i].is_turnRight = false;
                } else if ( fish[i].position_x < 0 ) {
                    fish[i].is_turnRight = true;
                }
                if( fish[i].is_turnRight ) {
                    fish[i].position_x += fish[i].speed_x;
                    repaint();
                } else if ( !fish[i].is_turnRight ) {
                    fish[i].position_x -= fish[i].speed_x;
                    repaint();
                }
            }

            repaint();
        }
    }

    class FeedingListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            add_feed(e.getX(),e.getY());
        }

        public void mouseEntered(MouseEvent arg0) {}

        public void mouseExited(MouseEvent arg0) {}

        public void mousePressed(MouseEvent arg0) {}

        public void mouseReleased(MouseEvent arg0) {}
    }
    
    public void add_fish(int add_number,int kind_index) {
        numberOfFish_total = user.getTotalFish();
        numberOfFish_kind1 = user.getKind1FishNumber();
        numberOfFish_kind2 = user.getKind2FishNumber();

        for ( int i = numberOfFish_total ; i < (numberOfFish_total+add_number) ; i++ ) {
            System.out.println(i);
            fish[i] = new Fish();

            System.out.println("fish age: " + fish[i].age);

            for( int j = 0 ; j < 2 ; j++ ) {
                if ( j == 0 ) {
                    user.fish_data[i][0] = fish[i].age;
                } else if ( j == 1 ) {
                    if ( fish[i].satiety ) {
                        user.fish_data[i][1] = 1;
                    } else {
                        user.fish_data[i][1] = 0;
                    }
                }
            }

            fish[i].setLive();
            fish[i].setFish_Kind(kind_index);
            fish[i].setFish_size(fish[i].age, kind_index);

            if ( kind_index == 0 ) {
                ++user.kind1_fish_number;
            } else if ( kind_index == 1 ) {
                ++user.kind2_fish_number;
            }
        }

        numberOfFish_total += add_number;
        user.total_fish_number = numberOfFish_total;

        repaint();
    }

    public void add_feed(int input_position_x, int input_position_y) {
        if ( numberOfFeed_total < 10 ) {
            feed[numberOfFeed_total].is_live = true;
            feed[numberOfFeed_total].position_x = input_position_x;
            feed[numberOfFeed_total].position_y = input_position_y;
            ++numberOfFeed_total;
        }

        repaint();
    }

    public void addFeedingListener() {
        this.addMouseListener(feedListener);
    }

    public void removeFeedingListener() {
        this.removeMouseListener(feedListener);
    }

    public void change_background(int kind_index) {
        bowl_image=bowl.change_return_BackgroundImage(kind_index);

        repaint();
        System.out.print("change success");
    }

    public void is_feeding(boolean input_is_feeding_Mode) {
        is_feeding_Mode = input_is_feeding_Mode;

        if( is_feeding_Mode ) {
            addFeedingListener();
        } else {
            removeFeedingListener();
        }
    }
}
