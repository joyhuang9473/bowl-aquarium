package fp;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import fp.Fish.TimerListener;

public class Feeding {
    protected ImageIcon feed_imageIcon = new ImageIcon("images/feed/feed.png");
    protected Image feed_image = feed_imageIcon.getImage();
    protected int position_x;
    protected int position_y;

    protected int width = 10;
    protected int height = 10;

    protected boolean is_live;
    protected double feed_speed_y = 1;

    private int gamepanel_width;
    private int gamepanel_height;

    private int delay = 125;
    protected Timer timer = new Timer(delay, new TimerListener());
    private GameLogicPanel logicPanel;
    private Fish [] fish;

    private int min_distance_between_feed = 10000;
    private int min_distance_fish_index;
    
    public Feeding(GameLogicPanel input_logicPanel) {
        logicPanel = input_logicPanel;
        fish = logicPanel.fish;

        gamepanel_width = logicPanel.getWidth();

        is_live = false;
        position_x = 0;
        position_y = 0;
        timer.start();
    }
    
    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gamepanel_height = logicPanel.panelSize_height;

            if ( is_live == true ) {
                position_y += feed_speed_y;

                for ( int w = 0 ; w < logicPanel.numberOfFish_total ; w++ ) {
                    if ( fish[w].satiety == false ) {
                        System.out.println("in fish satiety");

                        if ( Math.abs(position_x-fish[w].position_x) < min_distance_between_feed ) {
                            min_distance_between_feed = Math.abs(position_x - fish[w].position_x);
                            min_distance_fish_index = w;
                            System.out.println("index:" + w);
                        }
                    }
                }

                if( fish[min_distance_fish_index].position_x > position_x ) {
                    System.out.println("access");
                    fish[min_distance_fish_index].is_turnRight = false;

                    if ( fish[min_distance_fish_index].position_y > position_y ) {
                        fish[min_distance_fish_index].position_y -= fish[min_distance_fish_index].speed_y;
                    } else if ( fish[min_distance_fish_index].position_y < position_y ) {
                        fish[min_distance_fish_index].position_y += fish[min_distance_fish_index].speed_y;
                    }
                } else if ( fish[min_distance_fish_index].position_x < position_x ) {
                    fish[min_distance_fish_index].is_turnRight = true;
    
                    if ( fish[min_distance_fish_index].position_y > position_y) {
                        fish[min_distance_fish_index].position_y -= fish[min_distance_fish_index].speed_y;
                    } else if ( fish[min_distance_fish_index].position_y < position_y) {
                        fish[min_distance_fish_index].position_y += fish[min_distance_fish_index].speed_y;
                    }
                }

                if ( Math.abs(position_x-fish[min_distance_fish_index].position_x) <= 5 && Math.abs(position_y-fish[min_distance_fish_index].position_y) <= 5 ) {
                    is_live = false;
                    --logicPanel.numberOfFeed_total;
                    fish[min_distance_fish_index].satiety = true;
                    logicPanel.user.fish_data[min_distance_fish_index][1] = 1;
                    min_distance_between_feed = 10000;
                    fish[min_distance_fish_index].speed_x = 2;
                    fish[min_distance_fish_index].speed_y = 2;
                } else {
                    System.out.println("feed live");
                    fish[min_distance_fish_index].speed_x = 3;
                    fish[min_distance_fish_index].speed_y = 3;
                }
            }

            if ( position_y > gamepanel_height ) {
                is_live = false;
                --logicPanel.numberOfFeed_total;
                System.out.println(gamepanel_height);
            }
        }
    }
}
