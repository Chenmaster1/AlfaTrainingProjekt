package InGameGUI;

import Actions.Action;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Heroes.Hero;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

public class HeroPanelLarge extends JPanel {

    private Hero displayedHero;
    private Image backgroundImage;
    private Image hitPointImage;
    private Image hitPointUsedImage;
    private Image actionPointImage;
    private Image actionPointUsedImage;
    private Image delayTokenImage;

    private ArrayList<Action> actionArrayList;
    private ArrayList<JButton> buttonArrayList;

    private JPanel actionListPanel;
    private JLabel heroNameLabel;
    private JTextArea heroDescriptionField;

    //TODO: Passende Werte finden bzw. koordinieren
    private static final double AVATAR_POSITION_RELATIVE_X = 0.633;
    private static final double AVATAR_POSITION_RELATIVE_Y = 0.06;
    private static final double AVATAR_SIZE_RELATIVE_X = 0.325;
    private static final double AVATAR_SIZE_RELATIVE_Y = 0.5;

    private static final double ACTIONPOINTICON_POSITION_RELATIVE_X = 0.01;
    private static final double ACTIONPOINTICON_POSITION_RELATIVE_Y = 0.16;

    private static final double HITPOINTICON_POSITION_RELATIVE_X = 0.55;
    private static final double HITPOINTICON_POSITION_RELATIVE_Y = 0.16;

    //POINTICON Variablen gelten sowohl für Action- als auch für Hitpoints
    private static final double POINTICON_SIZE_RELATIVE_X = 0.07;
    private static final double POINTICON_SIZE_RELATIVE_Y = 0.1;
    private static final double POINTICON_DISTANCE_RELATIVE_Y = 0.03;

    private static final double DELAYTOKEN_POSITION_RELATIVE_X = 0.3;
    private static final double DELAYTOKEN_POSITION_RELATIVE_Y = 0.88;
    private static final double DELAYTOKEN_SIZE_RELATIVE_X = 0.04;
    private static final double DELAYTOKEN_SIZE_RELATIVE_Y = 0.1;

    private static final double ACTIONLIST_POSITION_RELATIVE_X = 0.09;
    private static final double ACTIONLIST_POSITION_RELATIVE_Y = 0.36;
    private static final double ACTIONLIST_SIZE_RELATIVE_X = 0.45;
    private static final double ACTIONLIST_SIZE_RELATIVE_Y = 0.5;
    private static final int ACTIONLIST_CELLS = 5;

    private static final double HERONAMELABEL_POSITION_RELATIVE_X = 0.09;
    private static final double HERONAMELABEL_POSITION_RELATIVE_Y = 0.075;
    private static final double HERONAMELABEL_SIZE_RELATIVE_X = 0.45;
    private static final double HERONAMELABEL_SIZE_RELATIVE_Y = 0.04;

    private static final double HERODESCRIPTIONFIELD_POSITION_RELATIVE_X = 0.09;
    private static final double HERODESCRIPTIONFIELD_POSITION_RELATIVE_Y = 0.16;
    private static final double HERODESCRIPTIONFIELD_SIZE_RELATIVE_X = 0.45;
    private static final double HERODESCRIPTIONFIELD_SIZE_RELATIVE_Y = 0.2;

    public HeroPanelLarge(Hero hero) {

        displayedHero = hero;

        //TODO: Auf gemeinsame Image-Objekte zugreifen, damit nicht jedes Panel seine eigenen instanziiert. Siehe auch HeroPanelSmall
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Hero_Front_Empty_tall.jpg"))
                .getImage();
        hitPointImage = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Heart_Activated.png"))
                .getImage();
        hitPointUsedImage = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Heart_Deactivated.png"))
                .getImage();
        actionPointImage = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Action_Activated.png"))
                .getImage();
        actionPointUsedImage = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Action_Deactivated.png"))
                .getImage();
        delayTokenImage = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Delay.png"))
                .getImage();

        setLayout(null);
        actionArrayList = new ArrayList<>();
        buttonArrayList = new ArrayList<>();

        initializeActionListPanel();

        initializeTextFields();
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        // Hero-Dummypanel als Hintergrund
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // Overlay für den Heldenavatar
        g2d.drawImage(displayedHero.getAvatar().getImage(),
                (int) (AVATAR_POSITION_RELATIVE_X * getWidth()),
                (int) (AVATAR_POSITION_RELATIVE_Y * getHeight()),
                (int) (AVATAR_SIZE_RELATIVE_X * getWidth()),
                (int) (AVATAR_SIZE_RELATIVE_Y * getHeight()), this);

        //Icongröße für Action- und Hitpoints berechnen
        int iconSize_X = (int) (POINTICON_SIZE_RELATIVE_X * getWidth());
        int iconSize_Y = (int) (POINTICON_SIZE_RELATIVE_Y * getHeight());

        // Overlays für Hitpoints
        drawHitPointIcons(g2d, iconSize_X, iconSize_Y);

        // Overlays für Actionpoints
        drawActionPointIcons(g2d, iconSize_X, iconSize_Y);

        // Overlays für Delaytokens
        drawDelayTokenIcons(g2d);

        //ActionPanel Größe anpassen
        updateActionListPanelBounds();
        
        //Textfelder Größe anpassen
        updateTextFieldsBounds();
    }

    private void drawDelayTokenIcons(Graphics2D g2d) {
        int delayTokenSize_X = (int) (DELAYTOKEN_SIZE_RELATIVE_X * getWidth());
        int delayTokenSize_Y = (int) (DELAYTOKEN_SIZE_RELATIVE_Y * getHeight());

        int numDelayTokens = displayedHero.getDelayTokens();
        int totalSize_X = numDelayTokens * delayTokenSize_X;

        for (int i = 0; i < numDelayTokens; i++) {
            int position_x = ((int) (DELAYTOKEN_POSITION_RELATIVE_X * getWidth()) - totalSize_X / 2) + (i * delayTokenSize_X);
            int position_y = (int) (DELAYTOKEN_POSITION_RELATIVE_Y * getHeight());

            g2d.drawImage(delayTokenImage,
                    position_x,
                    position_y,
                    delayTokenSize_X,
                    delayTokenSize_Y,
                    this);
        }

    }

    private void drawActionPointIcons(Graphics2D g2d, int iconSize_X, int iconSize_Y) {
        int maxPoints = displayedHero.getMaxActionPoints();
        int currentPoints = displayedHero.getCurrentActionPoints();

        for (int i = 0; i < maxPoints; i++) {
            if (currentPoints > 0) {
                g2d.drawImage(actionPointImage,
                        (int) (getWidth() * ACTIONPOINTICON_POSITION_RELATIVE_X),
                        (int) (getHeight() * ACTIONPOINTICON_POSITION_RELATIVE_Y) + (iconSize_Y + (int) (POINTICON_DISTANCE_RELATIVE_Y * getHeight())) * i,
                        iconSize_X, iconSize_Y,
                        this);
                currentPoints--;
            } else {
                g2d.drawImage(actionPointUsedImage,
                        (int) (getWidth() * ACTIONPOINTICON_POSITION_RELATIVE_X),
                        (int) (getHeight() * ACTIONPOINTICON_POSITION_RELATIVE_Y) + (iconSize_Y + (int) (POINTICON_DISTANCE_RELATIVE_Y * getHeight())) * i,
                        iconSize_X, iconSize_Y,
                        this);
            }
        }

    }

    private void drawHitPointIcons(Graphics2D g2d, int iconSize_X, int iconSize_Y) {
        int maxPoints = displayedHero.getMaxHitPoints();
        int currentPoints = displayedHero.getCurrentHitPoints();

        for (int i = 0; i < maxPoints; i++) {
            if (currentPoints > 0) {
                g2d.drawImage(hitPointImage,
                        (int) (getWidth() * HITPOINTICON_POSITION_RELATIVE_X),
                        (int) (getHeight() * HITPOINTICON_POSITION_RELATIVE_Y) + (iconSize_Y + (int) (POINTICON_DISTANCE_RELATIVE_Y * getHeight())) * i,
                        iconSize_X, iconSize_Y,
                        this);
                currentPoints--;
            } else {
                g2d.drawImage(hitPointUsedImage,
                        (int) (getWidth() * HITPOINTICON_POSITION_RELATIVE_X),
                        (int) (getHeight() * HITPOINTICON_POSITION_RELATIVE_Y) + (iconSize_Y + (int) (POINTICON_DISTANCE_RELATIVE_Y * getHeight())) * i,
                        iconSize_X, iconSize_Y,
                        this);
            }
        }

    }

    private void initializeActionListPanel() {
        actionListPanel = new JPanel();
        actionListPanel.setLayout(new GridLayout(ACTIONLIST_CELLS, 1));
        actionListPanel.setOpaque(false);
        add(actionListPanel);
    }

    private void initializeTextFields() {
        heroNameLabel = new JLabel(displayedHero.getName());
        heroNameLabel.setForeground(Color.yellow);
        add(heroNameLabel);

        heroDescriptionField = new JTextArea(displayedHero.getDescription());
        heroDescriptionField.setEditable(false);
//        heroDescriptionField.setOpaque(false);
        heroDescriptionField.setLineWrap(true);
        add(heroDescriptionField);
    }

    private void updateActionListPanelContent() {
        actionListPanel.removeAll();
        buttonArrayList.clear();
        JButton actionButton;
        for (Action a : actionArrayList) {
            actionButton = new JButton(a.getName());
            actionButton.setFocusable(false);
            buttonArrayList.add(actionButton);
            actionListPanel.add(actionButton);
        }
        updateButtonsEnabled();
        repaint();
    }

    public void updateButtonsEnabled() {
        for (JButton b : buttonArrayList) {
            b.setEnabled(actionArrayList.get(buttonArrayList.indexOf(b)).isEnabled());

        }
    }

    private void updateActionListPanelBounds() {

        int size_Y = (int) (ACTIONLIST_SIZE_RELATIVE_Y * getHeight());
        int buttonsize_Y = size_Y / ACTIONLIST_CELLS;
        int numButtons = actionArrayList.size();
        int position_Y = ((int) (ACTIONLIST_POSITION_RELATIVE_Y * getHeight())) + (ACTIONLIST_CELLS - numButtons) * buttonsize_Y;

        actionListPanel.setBounds((int) (ACTIONLIST_POSITION_RELATIVE_X * getWidth()),
                position_Y,
                (int) (ACTIONLIST_SIZE_RELATIVE_X * getWidth()),
                size_Y);
    }

    private void updateTextFieldsBounds() {
        heroNameLabel.setBounds((int) (HERONAMELABEL_POSITION_RELATIVE_X * getWidth()),
                (int) (HERONAMELABEL_POSITION_RELATIVE_Y * getHeight()),
                (int) (HERONAMELABEL_SIZE_RELATIVE_X * getWidth()),
                (int) (HERONAMELABEL_SIZE_RELATIVE_Y * getHeight()));
        
        heroDescriptionField.setBounds((int) (HERODESCRIPTIONFIELD_POSITION_RELATIVE_X * getWidth()),
                (int) (HERODESCRIPTIONFIELD_POSITION_RELATIVE_Y * getHeight()),
                (int) (HERODESCRIPTIONFIELD_SIZE_RELATIVE_X * getWidth()),
                (int) (HERODESCRIPTIONFIELD_SIZE_RELATIVE_Y * getHeight()));
    }

    public ArrayList<Action> getActionArrayList() {
        return actionArrayList;
    }

    public ArrayList<JButton> getButtonArrayList() {
        return buttonArrayList;
    }

    public void setActionArrayList(ArrayList<Action> actionArrayList) {
        this.actionArrayList = actionArrayList;
        updateActionListPanelContent();
    }

}
