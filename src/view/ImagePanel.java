package view;

import thread.MovementThread;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private ImageIcon icon;
    private int xPos;
    private int yPos;
    public ImagePanel(String path) {
        icon = new ImageIcon(path);
        int h = icon.getIconHeight();
        int w = icon.getIconWidth();
        setPreferredSize(new Dimension(w,h));

        xPos = 0;
        yPos = 50;

        MovementThread movementThread = new MovementThread(this);
        movementThread.start();
    }

    public void move(){
       if(yPos < 500)
            yPos += 50;
       else
           yPos = 50;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        icon.paintIcon(this,g,xPos,yPos);
    }
}
