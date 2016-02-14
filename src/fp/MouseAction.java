package fp;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseAction implements MouseListener, MouseMotionListener {
    private int x;
    private int y;

    public int return_cursor_x() { return x; }

    public int return_cursor_y() { return y; }

    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public void mouseEntered(MouseEvent arg0) {}
    
    public void mouseExited(MouseEvent arg0) {}
    
    public void mousePressed(MouseEvent arg0) {}
    
    public void mouseReleased(MouseEvent arg0) {}

    public void mouseDragged(MouseEvent e) {
        x=e.getX();
        y=e.getY();
    }

    public void mouseMoved(MouseEvent arg0) {}
}
