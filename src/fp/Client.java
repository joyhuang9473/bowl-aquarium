package fp;

import javax.swing.JFrame;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client {
    public static FrameWork frame = new FrameWork();
    
    public Client() {}
    
    public static void main(String[] args) {
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Bowl");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
