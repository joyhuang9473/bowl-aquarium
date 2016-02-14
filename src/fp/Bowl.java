package fp;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.*;

public class Bowl  {
    private ImageIcon background1 = new ImageIcon("images/background/background.jpg");
    private ImageIcon background2 = new ImageIcon("images/background/background2.jpg");

    private Image bowl_image = background1.getImage();    

    public Image get_bowel_image() { return bowl_image; }

    public Image change_return_BackgroundImage(int kindIndex) {
        if ( kindIndex == 0 ) {
            bowl_image = background1.getImage();    
        } else if ( kindIndex == 1 ) {
            bowl_image = background2.getImage();    
        }

        return bowl_image;
    }
}
