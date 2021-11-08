package thread;

import view.MenuWindow;

public class ColorChangingThread extends Thread{
    private MenuWindow menu;
    public ColorChangingThread(MenuWindow menu) {
        this.menu = menu;
    }

    public void run(){
        while (true){
            try {
                Thread.sleep(1000);
                menu.changeColor();

                menu.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
