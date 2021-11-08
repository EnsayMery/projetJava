package thread;

import view.ImagePanel;

public class MovementThread extends Thread{
    private ImagePanel imagePanel;
    public MovementThread(ImagePanel imagePanel) {
        this.imagePanel = imagePanel;
    }

    public void run(){
        while (true){
            try {
                Thread.sleep(500);
                imagePanel.move();

                imagePanel.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
