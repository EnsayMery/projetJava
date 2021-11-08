package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnButonPanel extends JPanel {

    private MenuWindow menu;
    private JPanel panel;

    public ReturnButonPanel(MenuWindow menu, JPanel panel){
        this.menu = menu;
        this.panel = panel;
        JButton returnButton = new JButton("Retour");
        returnButton.addActionListener(new ReturnButtonListener());
        this.add(returnButton);
    }
    private class ReturnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            menu.dispose();
            menu.getContentPane().add(menu.getWestPanel(), BorderLayout.WEST);
            menu.getContentPane().add(menu.getEastPanel(), BorderLayout.EAST);
            menu.getContentPane().add(menu.getCenterPanel(), BorderLayout.CENTER);
            panel.setVisible(false);
            menu.setVisible(true);
        }
    }
}

