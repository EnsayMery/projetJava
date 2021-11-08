package view;

import thread.ColorChangingThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuWindow extends JFrame {
    Container container;
    private JLabel welcomeMsg,byMsg;
    private MenuWindow menuWindow;
    private JPanel westPanel, centerPanel,eastPanel;


    public MenuWindow(){
        super("Menu");
        menuWindow = this;
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        container = getContentPane();
        container.setLayout(new BorderLayout());
        this.addWindowListener(new ClosingListener());

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        westPanel = new JPanel(new BorderLayout());
        centerPanel = new JPanel(new FlowLayout());
        eastPanel = new JPanel(new BorderLayout());

        centerPanel.add(new ImagePanel("src/images/Application_de_diététique-removebg-preview.png"));
        //Message d'accueil

        welcomeMsg = new JLabel("<html> <p style='text-align: center;'> Bienvenue </p> <p style='text-align: center;'> sur l'application de diététique  </p> </html>");
        welcomeMsg.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeMsg.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        welcomeMsg.setFont(new Font("Sérif",Font.BOLD,20));

        byMsg = new JLabel("<html> <p style='text-align: center;'> Programmé par </p> <p style='text-align: center;'> Didier Isaline  Ensay Mery </p> </html>");
        byMsg.setHorizontalAlignment(SwingConstants.CENTER);
        byMsg.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        byMsg.setFont(new Font("Sérif",Font.BOLD,20));

        westPanel.add(welcomeMsg,BorderLayout.CENTER);
        eastPanel.add(byMsg,BorderLayout.CENTER);

        container.add(westPanel,BorderLayout.WEST);
        container.add(centerPanel,BorderLayout.CENTER);
        container.add(eastPanel,BorderLayout.EAST);

        //Menu personne
        JMenu personMenu = new JMenu("Personne");
        menuBar.add(personMenu);

        JMenuItem registrationPerson = new JMenuItem("Ajouter");
        personMenu.add(registrationPerson);

        JMenuItem listPerson = new JMenuItem("Listing");
        personMenu.add(listPerson);

        JMenuItem updatePerson = new JMenuItem("Modifier");
        personMenu.add(updatePerson);

        JMenuItem deletePerson = new JMenuItem("Supprimer");
        personMenu.add(deletePerson);

        //Menu plat
        JMenu dishMenu = new JMenu("Plat");
        menuBar.add(dishMenu);

        JMenuItem dishListing = new JMenuItem("Listing plat");
        dishMenu.add(dishListing);

        JMenuItem recipe = new JMenuItem("Recette");
        dishMenu.add(recipe);

        //Menu planning
        JMenu mealPlanningMenu = new JMenu("Planning menu");
        menuBar.add(mealPlanningMenu);

        JMenuItem addMealPlanning = new JMenuItem("Ajouter planning repas");
        mealPlanningMenu.add(addMealPlanning);

        JMenuItem displayMealPlanning = new JMenuItem("Afficher planning repas");
        mealPlanningMenu.add(displayMealPlanning);

        registrationPerson.addActionListener(new RegistrationPersonListener());
        listPerson.addActionListener(new ListPersonListener());
        updatePerson.addActionListener(new UpdatePersonListener());
        deletePerson.addActionListener(new DeletePersonListener());
        dishListing.addActionListener(new DishListingListener());
        recipe.addActionListener(new RecipeListener());
        addMealPlanning.addActionListener(new AddMealPlanningListener());
        displayMealPlanning.addActionListener(new DisplayMealPlanningListener());

        ColorChangingThread colorChangingThread = new ColorChangingThread(this);
        colorChangingThread.start();
        setVisible(true);

    }

    public void changeColor(){
        if(welcomeMsg.getForeground() == Color.BLACK){
            welcomeMsg.setForeground(new Color(171,185,72));
            byMsg.setForeground(new Color(171,185,72));}
        else {
            welcomeMsg.setForeground(Color.BLACK);
            byMsg.setForeground(Color.BLACK);
        }

    }

    public JPanel getEastPanel() {
        return eastPanel;
    }

    public JPanel getWestPanel() {
        return westPanel;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }

    public Container getContainer() {
        return container;
    }

    private class ClosingListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    private class RegistrationPersonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new AddOrModifyPersonPanel(menuWindow));
            container.revalidate();
        }
    }

    private class ListPersonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new DisplayPersonPanel(menuWindow));
            container.revalidate();
        }
    }

    private class DeletePersonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new DeletePersonPanel(menuWindow));
            container.revalidate();
        }
    }

    private class UpdatePersonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new UpdatePersonPanel(menuWindow));
            container.revalidate();
        }
    }
    private class DishListingListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new DishListingPanel(menuWindow));
            container.revalidate();
        }
    }

    private class RecipeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new RecipePanel(menuWindow));
            container.revalidate();
        }
    }
    private class AddMealPlanningListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new MealPlanningFormPanel(menuWindow));
            container.revalidate();
        }
    }

    private class DisplayMealPlanningListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            container.removeAll();
            container.add(new DisplayMealPlanning(menuWindow));
            container.revalidate();
        }
    }

}
