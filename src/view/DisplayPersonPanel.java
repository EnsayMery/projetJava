package view;

import javax.swing.*;
import java.awt.*;

public class DisplayPersonPanel extends JPanel {

    public DisplayPersonPanel(MenuWindow menu) {

        this.setLayout(new BorderLayout());
        AllPeoplePanel allPeoplePanel = new AllPeoplePanel("display");

        /* ------ Ajout panel ------ */

        this.add(allPeoplePanel, BorderLayout.CENTER);
        this.add(new ReturnButonPanel(menu, this), BorderLayout.SOUTH);

    }
}
