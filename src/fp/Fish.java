package fp;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Fish {
    protected boolean is_live = false;
    protected int age;
    protected boolean satiety;
    
    protected int speed_y = 2;
    protected int speed_x = 2;
    
    protected int position_x;
    protected int position_y;

    //fish size
    protected int fishSize_width;
    protected int fishSize_height;
    //fish kind1
    private int fishSize_kind1_small_width = 40;
    protected int fishSize_kind1_small_height = 35;
    private int fishSize_kind1_medium_width = 60;
    protected int fishSize_kind1_medium_height = 55;
    private int fishSize_kind1_big_width = 80;
    protected int fishSize_kind1_big_height = 75;
    //fish kind2
    private int fishSize_kind2_small_width = 50;
    protected int fishSize_kind2_small_height = 20;
    private int fishSize_kind2_medium_width = 70;
    protected int fishSize_kind2_medium_height = 40;
    private int fishSize_kind2_big_width = 90;
    protected int fishSize_kind2_big_height = 60;

    protected ImageIcon [] fish_imageIcon = new ImageIcon[16];

    protected Image image;
    
    protected int fish_kind;
    
    protected boolean is_turnRight;
    private boolean record_direction; //true:right ; false:left
    
    //image
    private int image_index = 0; //image index
    int delay = 125;
    private Timer timer = new Timer(delay, new TimerListener());
    
    //fish age
    private int delay_minute = 60000;
    private Timer timer_fish_age = new Timer(delay_minute, new Timer_age_Listener());
    private int time_min = 0;
    
    public Fish() {
        age = 0;
        satiety = false;
        position_x = (int) (Math.random()*1000%600);
        position_y = (int) (Math.random()*1000%400);

        is_turnRight = ( Math.random()*1000%2 == 0 ) ? true : false;

        record_direction = is_turnRight;

        timer.start();
        timer_fish_age.start();    
    }
    
    public void setLive() { is_live = ( !is_live ) ? true : false; }

    public void setFish_Kind(int kind_index) {
        if ( kind_index == 0 ) {
            for( int i = 0 ; i < 16 ; i++ ) {
                fish_imageIcon[i] = new ImageIcon("images/fish/fish1/FISH" + (i+1) + ".png");
            }

            fish_kind = 0;    
        } else if ( kind_index == 1 ) {
            for( int i = 0 ; i < 16 ; i++ ) {
                fish_imageIcon[i] = new ImageIcon("images/fish/fish2/FISH" + (i+1) + ".png");
            }

            fish_kind = 1;
        }
    }
    
    public void setFish_size(int age, int kind_index) {
        if ( kind_index == 0 ) {
            if ( age >= 0 && age < 3 ) {
                fishSize_width = fishSize_kind1_small_width;
                fishSize_height = fishSize_kind1_small_height;
            } else if ( age >= 3 && age < 6 ) {
                fishSize_width = fishSize_kind1_medium_width;
                fishSize_height = fishSize_kind1_medium_height;
            } else if ( age >= 6 ) {
                fishSize_width = fishSize_kind1_big_width;
                fishSize_height = fishSize_kind1_big_height;
            }
        } else if ( kind_index == 1 ) {
            if ( age >= 0 && age < 3 ) {
                fishSize_width = fishSize_kind2_small_width;
                fishSize_height = fishSize_kind2_small_height;
            } else if ( age >= 3 && age < 6 ) {
                fishSize_width = fishSize_kind2_medium_width;
                fishSize_height = fishSize_kind2_medium_height;
            } else if ( age>=6 ) {
                fishSize_width = fishSize_kind2_big_width;
                fishSize_height = fishSize_kind2_big_height;
            }
        }
    }

    public Image getFishImage() { return image; }

    class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if( !is_turnRight ) {
                if ( record_direction != is_turnRight ) {
                    image_index = 0;
                    record_direction = is_turnRight;
                }

                switch( image_index ) {    
                    case 0:
                        image = fish_imageIcon[0].getImage();
                        ++image_index;
                        break;
                    case 1:
                        image = fish_imageIcon[1].getImage();
                        ++image_index;
                        break;
                    case 2:
                        image = fish_imageIcon[2].getImage();
                        ++image_index;
                        break;
                    case 3:
                        image = fish_imageIcon[3].getImage();
                        ++image_index;
                        break;
                    case 4:
                        image = fish_imageIcon[4].getImage();
                        ++image_index;
                        break;
                    case 5:
                        image = fish_imageIcon[5].getImage();
                        ++image_index;
                        break;
                    case 6:
                        image = fish_imageIcon[6].getImage();
                        ++image_index;
                        break;
                    case 7:
                        image = fish_imageIcon[7].getImage();
                        image_index = 0;
                        break;
                }
            } else if ( is_turnRight ) {
                if ( record_direction != is_turnRight ) {
                    image_index = 8;
                    record_direction = is_turnRight;
                }

                switch( image_index ) {
                    case 8:
                        image = fish_imageIcon[8].getImage();
                        ++image_index;
                        break;
                    case 9:
                        image = fish_imageIcon[9].getImage();
                        ++image_index;
                        break;
                    case 10:
                        image = fish_imageIcon[10].getImage();
                        ++image_index;
                        break;
                    case 11:
                        image = fish_imageIcon[11].getImage();
                        ++image_index;
                        break;
                    case 12:
                        image = fish_imageIcon[12].getImage();
                        ++image_index;
                        break;
                    case 13:
                        image = fish_imageIcon[13].getImage();
                        ++image_index;
                        break;
                    case 14:
                        image = fish_imageIcon[14].getImage();
                        ++image_index;
                        break;
                    case 15:
                        image = fish_imageIcon[15].getImage();
                        image_index = 8;
                        break;
                }
            }
        }
    }

    class Timer_age_Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ++time_min;

            if ( time_min == 10 ) {
                age += 1;
                time_min = 0;
            }
        }
    }
}
