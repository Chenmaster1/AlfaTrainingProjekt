package InGameGUI;

import Abilities.Ability;
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

/**
 * Panel zur detaillierten Darstellung eines Helden. Enthält neben allen
 * Elementen des HeroPanelSmall auch Texte für Namen und Ability-Descriptions
 * sowie eine Liste von Buttons für die verfügbaren Actions, mit denen sie
 * während des Zugs des Spielers ausgeführt werden können.
 *
 * Die Größe ist dynamisch veränderbar.
 * 
 * @author Alfa
 */
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
    private JTextArea abilityDescriptionField;

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

    /**
     * GroßformatPanel für Helden, genutzt für den Hauptspieler. Zeigt alle
     * relevanten Infos an wie HP, AP, Delay, Actionliste, Name, Beschreibung,
     * Bild etc.
     *
     * @param hero Der darzustellende Hero
     */
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

    /**
     * Aktiviert bzw deaktiviert die Buttons für die Aktionen abhängig davon, ob
     * diese aktuell aktiviert sind oder nicht. Muss aufgerufen werden, wenn
     * sich am Status der Actions bzw. Abilities etwas ändert.
     *
     * TODO: Evtl. zusätzliche Flag einbauen, die alles deaktiviert (wenn z.B.
     * andere Spieler am Zug sind)
     */
    public void updateButtonsEnabled() {
        for (JButton b : buttonArrayList) {
            b.setEnabled(actionArrayList.get(buttonArrayList.indexOf(b)).isEnabled());

        }
    }

    /**
     * Zeichnet das Panel. Alle Elemente werden relativ zur aktuellen Panelgröße
     * angepasst (siehe die entsprechend definierten Konstanten), sodass ein
     * Vergrößern bzw. Verkleinern problemlos möglich ist.
     *
     * @param g
     */
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

    /**
     * Initialisiert das Teil-Panel, welches die JButtons zur Aktionsauswahl
     * enthält.
     */
    private void initializeActionListPanel() {
        actionListPanel = new JPanel();
        actionListPanel.setLayout(new GridLayout(ACTIONLIST_CELLS, 1));
        actionListPanel.setOpaque(false);
        add(actionListPanel);
    }

    /**
     * Initialisiert die Textfelder, also sowohl den Heldennamen als auch die
     * Liste der Abilities, welche aus dem Helden ausgelesen werden.
     */
    private void initializeTextFields() {
        heroNameLabel = new JLabel(displayedHero.getName());
        heroNameLabel.setForeground(Color.yellow);
        add(heroNameLabel);

        StringBuilder sbAbilities = new StringBuilder();
        for (Ability ab : displayedHero.getAbilities()) {
            sbAbilities.append(ab.getName());
            sbAbilities.append(": ");
            sbAbilities.append(ab.getDescription());
            sbAbilities.append("\n");
        }

        abilityDescriptionField = new JTextArea(sbAbilities.toString());
        abilityDescriptionField.setEditable(false);
        abilityDescriptionField.setOpaque(false);
        abilityDescriptionField.setLineWrap(true);
        abilityDescriptionField.setWrapStyleWord(true);
        add(abilityDescriptionField);
    }

    /**
     * Untermethode zum Zeichnen der Delay-Tokens.
     *
     * @param g2d
     */
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

    /**
     * Untermethode zum Zeichnen der Actionpoint-Icons. Die reale Größe der
     * Icons wird vor dem Aufruf aus den relativen Konstanten und der
     * Fenstergröße berechnet und übergeben, weil sie die gleiche ist wie bei
     * den Hitpoint-Icons.
     *
     * @param g2d
     * @param iconSize_X
     * @param iconSize_Y
     */
    private void drawActionPointIcons(Graphics2D g2d, int iconSize_X, int iconSize_Y) {
        int maxPoints = displayedHero.getMaxActionPoints();
        int currentPoints = displayedHero.getCurrentActionPoints();

        for (int i = 0; i < maxPoints; i++) {
            if (currentPoints > 0) {
                g2d.drawImage(actionPointImage,
                        (int) (getWidth() * ACTIONPOINTICON_POSITION_RELATIVE_X),
                        (int) (getHeight() * ACTIONPOINTICON_POSITION_RELATIVE_Y)
                        + (iconSize_Y + (int) (POINTICON_DISTANCE_RELATIVE_Y * getHeight())) * i,
                        iconSize_X, iconSize_Y,
                        this);
                currentPoints--;
            } else {
                g2d.drawImage(actionPointUsedImage,
                        (int) (getWidth() * ACTIONPOINTICON_POSITION_RELATIVE_X),
                        (int) (getHeight() * ACTIONPOINTICON_POSITION_RELATIVE_Y)
                        + (iconSize_Y + (int) (POINTICON_DISTANCE_RELATIVE_Y * getHeight())) * i,
                        iconSize_X, iconSize_Y,
                        this);
            }
        }

    }

    /**
     * Untermethode zum Zeichnen der Hitpoint-Icons. Die reale Größe der Icons
     * wird vor dem Aufruf aus den relativen Konstanten und der Fenstergröße
     * berechnet und übergeben, weil sie die gleiche ist wie bei den
     * Actionpoint-Icons.
     *
     * @param g2d
     * @param iconSize_X
     * @param iconSize_Y
     */
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

    /**
     * Liest die aktuelle ArrayList der Actions aus und erzeugt eine
     * entsprechende Liste an Buttons für das actionListPanel. Wird immer
     * aufgerufen, wenn eine neue ArrayList mit Actions zugewiesen wird.
     */
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

    /**
     * Passt die Bounds des actionListPanel an die Gesamtpanel-Größe an. Wird in
     * paintComponent aufgerufen.
     *
     * Unabhängig von der aktuellen Anzahl der Buttons im actionListPanel liegt
     * der unterste Button stets am unteren Rand des Gesamtpanel an.
     */
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

    /**
     * Passt die Bounds der Textfelder (Name und Liste der Abilities) an die
     * Gesamtpanel-Größe an. Wird in paintComponent aufgerufen.
     */
    private void updateTextFieldsBounds() {
        heroNameLabel.setBounds((int) (HERONAMELABEL_POSITION_RELATIVE_X * getWidth()),
                (int) (HERONAMELABEL_POSITION_RELATIVE_Y * getHeight()),
                (int) (HERONAMELABEL_SIZE_RELATIVE_X * getWidth()),
                (int) (HERONAMELABEL_SIZE_RELATIVE_Y * getHeight()));

        abilityDescriptionField.setBounds((int) (HERODESCRIPTIONFIELD_POSITION_RELATIVE_X * getWidth()),
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

    /**
     * Setzt eine neue Liste an möglichen Actions und passt die Buttons
     * entsprechend an. Nur benutzen, wenn die Optionen permanent verändert
     * werden sollen. Soll eine Aktion vorübergehend (de)aktiviert werden, dann
     * an dieser setEnabled(boolean) benutzen und hier updateButtonsEnabled()
     * aufrufen.
     *
     * @param actionArrayList Die neue Aktionsliste
     */
    public void setActionArrayList(ArrayList<Action> actionArrayList) {
        this.actionArrayList = actionArrayList;
        updateActionListPanelContent();
    }

}
