package fp;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

public class ReadFile {
    private JFileChooser fileChooser = new JFileChooser();
    private ImageIcon image;
    private Image image_file;
    public ReadFile(){}
    
    public Image return_image_file(){ return image_file; }
    
    public void read_Image_data() {
        if ( fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ) {
            java.io.File file = fileChooser.getSelectedFile();

            String fname = file.getPath();

            image = new ImageIcon(fname);
            image_file = image.getImage();    
        } else {
            image = new ImageIcon("images/background/background.jpg");
            image_file = image.getImage();    
        }
    }
}
