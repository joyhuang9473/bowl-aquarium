package fp;

import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class CommodityList {
    protected int numberOfImage_Fish = 2;
    protected int numberOfImage_Bowl = 2;

    protected ImageIcon [] fish_image = new ImageIcon[numberOfImage_Fish];
    protected ImageIcon [] bowl_image = new ImageIcon[numberOfImage_Bowl];

    public CommodityList() {
        for ( int i = 0 ; i < numberOfImage_Fish ; i++ ) {
            fish_image[i] = new ImageIcon("images/commodity/fish" + (i+1) + ".png");
            bowl_image[i] = new ImageIcon("images/commodity/background" + (i+1) + ".png");
        }
    }

    public ImageIcon Set_returnImageIcon(int name, int index) {
        ImageIcon returnImage = null;
        
        if ( name == 0 ) {
            returnImage = fish_image[index];
        } else if ( name == 1 ) {
            returnImage = bowl_image[index];
        }

        return returnImage;
    }
}
