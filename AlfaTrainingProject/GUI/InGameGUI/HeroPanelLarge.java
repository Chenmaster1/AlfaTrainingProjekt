package InGameGUI;

import Abilities.Ability;
import Actions.Action;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Heroes.Hero;
import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
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
 * Panel zur detaillierten Darstellung eines Helden. Enth�lt neben allen
 * Elementen des HeroPanelSmall auch Texte f�r Namen und Ability-Descriptions
 * sowie eine Liste von Buttons f�r die verf�gbaren Actions, mit denen sie
 * w�hrend des Zugs des Spielers ausgef�hrt werden k�nnen.
 *
 * Die Gr��e ist dynamisch ver�nderbar.
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

	private boolean buttonsEnabled;

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

	// POINTICON Variablen gelten sowohl f�r Action- als auch f�r Hitpoints
	private static final double POINTICON_SIZE_RELATIVE_X = 0.07;
	private static final double POINTICON_SIZE_RELATIVE_Y = 0.1;
	private static final double POINTICON_DISTANCE_RELATIVE_Y = 0.03;

	private static final double DELAYTOKEN_POSITION_RELATIVE_X = 0.3;
	private static final double DELAYTOKEN_POSITION_RELATIVE_Y = 0.88;
	private static final double DELAYTOKEN_SIZE_RELATIVE_X = 0.04;
	private static final double DELAYTOKEN_SIZE_RELATIVE_Y = 0.1;

	private static final double ACTIONLIST_POSITION_RELATIVE_X = 0.09;
	private static final double ACTIONLIST_POSITION_RELATIVE_Y = 0.39;
	private static final double ACTIONLIST_SIZE_RELATIVE_X = 0.45;
	private static final double ACTIONLIST_SIZE_RELATIVE_Y = 0.5;
	private static final int ACTIONLIST_CELLS = 5;

	private static final double HERONAMELABEL_POSITION_RELATIVE_X = 0.09;
	private static final double HERONAMELABEL_POSITION_RELATIVE_Y = 0.075;
	private static final double HERONAMELABEL_SIZE_RELATIVE_X = 0.45;
	private static final double HERONAMELABEL_SIZE_RELATIVE_Y = 0.04;

	private static final double HERONAMELABEL_TEXT_SIZE_RELATIVE_Y = 0.04;

	private static final double ABILITYDESCRIPTIONFIELD_POSITION_RELATIVE_X = 0.09;
	private static final double ABILITYDESCRIPTIONFIELD_POSITION_RELATIVE_Y = 0.16;
	private static final double ABILITYDESCRIPTIONFIELD_SIZE_RELATIVE_X = 0.45;
	private static final double ABILITYDESCRIPTIONFIELD_SIZE_RELATIVE_Y = 0.2;

	private static final double ABILITYDESCRIPTIONFIELD_TEXT_SIZE_RELATIVE_Y = 0.045;

	/**
	 * Gro�formatPanel f�r Helden, genutzt f�r den Hauptspieler. Zeigt alle
	 * relevanten Infos an wie HP, AP, Delay, Actionliste, Name, Beschreibung, Bild
	 * etc.
	 *
	 * @param hero Der darzustellende Hero
	 */
	public HeroPanelLarge(Hero hero) {

		displayedHero = hero;

		backgroundImage = ImageLoader.getInstance().getImage(ImageName.HERO_FRONT_EMPTY_TALL);
		hitPointImage = ImageLoader.getInstance().getImage(ImageName.HEART_ACTIVATED);
		hitPointUsedImage = ImageLoader.getInstance().getImage(ImageName.HEART_DEACTIVATED);
		actionPointImage = ImageLoader.getInstance().getImage(ImageName.ACTION_ACTIVATED);
		actionPointUsedImage = ImageLoader.getInstance().getImage(ImageName.ACTION_DEACTIVATED);
		delayTokenImage = ImageLoader.getInstance().getImage(ImageName.DELAY);

		setLayout(null);
		actionArrayList = new ArrayList<>();
		buttonArrayList = new ArrayList<>();

		initializeActionListPanel();

		initializeTextFields();

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// ActionPanel Gr��e anpassen
				updateActionListPanelBounds();

				// Textfelder Gr��e anpassen
				updateTextFieldsBounds();

			}
		});

	}

	/**
	 * Aktiviert bzw deaktiviert die Buttons f�r die Aktionen abh�ngig davon, ob
	 * diese aktuell aktiviert sind oder nicht. Muss aufgerufen werden, wenn sich am
	 * Status der Actions bzw. Abilities etwas �ndert.
	 * 
	 * Solange buttonsEnabled false ist, sind werden alle Buttons deaktiviert
	 * (w�hrend der Z�ge anderer Spieler setzen)
	 */
	public void updateButtonsEnabled() {
		if (buttonsEnabled) {
			for (JButton b : buttonArrayList) {
				b.setEnabled(actionArrayList.get(buttonArrayList.indexOf(b)).isEnabled());
			}
		} else {
			for (JButton b : buttonArrayList) {
				b.setEnabled(false);
			}
		}
	}

	/**
	 * Zeichnet das Panel. Alle Elemente werden relativ zur aktuellen Panelgr��e
	 * angepasst (siehe die entsprechend definierten Konstanten), sodass ein
	 * Vergr��ern bzw. Verkleinern problemlos m�glich ist.
	 *
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		// Hero-Dummypanel als Hintergrund
		g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

		// Overlay f�r den Heldenavatar
		g2d.drawImage(displayedHero.getAvatar(), (int) (AVATAR_POSITION_RELATIVE_X * getWidth()),
				(int) (AVATAR_POSITION_RELATIVE_Y * getHeight()), (int) (AVATAR_SIZE_RELATIVE_X * getWidth()),
				(int) (AVATAR_SIZE_RELATIVE_Y * getHeight()), this);

		// Icongr��e f�r Action- und Hitpoints berechnen
		int iconSize_X = (int) (POINTICON_SIZE_RELATIVE_X * getWidth());
		int iconSize_Y = (int) (POINTICON_SIZE_RELATIVE_Y * getHeight());

		// Overlays f�r Hitpoints
		drawHitPointIcons(g2d, iconSize_X, iconSize_Y);

		// Overlays f�r Actionpoints
		drawActionPointIcons(g2d, iconSize_X, iconSize_Y);

		// Overlays f�r Delaytokens
		drawDelayTokenIcons(g2d);

	}

	/**
	 * Initialisiert das Teil-Panel, welches die JButtons zur Aktionsauswahl
	 * enth�lt.
	 */
	private void initializeActionListPanel() {
		actionListPanel = new JPanel();
		actionListPanel.setLayout(new GridLayout(ACTIONLIST_CELLS, 1));
		actionListPanel.setOpaque(false);
		add(actionListPanel);
	}

	/**
	 * Initialisiert die Textfelder, also sowohl den Heldennamen als auch die Liste
	 * der Abilities, welche aus dem Helden ausgelesen werden.
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
			int position_x = ((int) (DELAYTOKEN_POSITION_RELATIVE_X * getWidth()) - totalSize_X / 2)
					+ (i * delayTokenSize_X);
			int position_y = (int) (DELAYTOKEN_POSITION_RELATIVE_Y * getHeight());

			g2d.drawImage(delayTokenImage, position_x, position_y, delayTokenSize_X, delayTokenSize_Y, this);
		}

	}

	/**
	 * Untermethode zum Zeichnen der Actionpoint-Icons. Die reale Gr��e der Icons
	 * wird vor dem Aufruf aus den relativen Konstanten und der Fenstergr��e
	 * berechnet und �bergeben, weil sie die gleiche ist wie bei den Hitpoint-Icons.
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
				g2d.drawImage(actionPointImage, (int) (getWidth() * ACTIONPOINTICON_POSITION_RELATIVE_X),
						(int) (getHeight() * ACTIONPOINTICON_POSITION_RELATIVE_Y)
								+ (iconSize_Y + (int) (POINTICON_DISTANCE_RELATIVE_Y * getHeight())) * i,
						iconSize_X, iconSize_Y, this);
				currentPoints--;
			} else {
				g2d.drawImage(actionPointUsedImage, (int) (getWidth() * ACTIONPOINTICON_POSITION_RELATIVE_X),
						(int) (getHeight() * ACTIONPOINTICON_POSITION_RELATIVE_Y)
								+ (iconSize_Y + (int) (POINTICON_DISTANCE_RELATIVE_Y * getHeight())) * i,
						iconSize_X, iconSize_Y, this);
			}
		}

	}

	/**
	 * Untermethode zum Zeichnen der Hitpoint-Icons. Die reale Gr��e der Icons wird
	 * vor dem Aufruf aus den relativen Konstanten und der Fenstergr��e berechnet
	 * und �bergeben, weil sie die gleiche ist wie bei den Actionpoint-Icons.
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
				g2d.drawImage(hitPointImage, (int) (getWidth() * HITPOINTICON_POSITION_RELATIVE_X),
						(int) (getHeight() * HITPOINTICON_POSITION_RELATIVE_Y)
								+ (iconSize_Y + (int) (POINTICON_DISTANCE_RELATIVE_Y * getHeight())) * i,
						iconSize_X, iconSize_Y, this);
				currentPoints--;
			} else {
				g2d.drawImage(hitPointUsedImage, (int) (getWidth() * HITPOINTICON_POSITION_RELATIVE_X),
						(int) (getHeight() * HITPOINTICON_POSITION_RELATIVE_Y)
								+ (iconSize_Y + (int) (POINTICON_DISTANCE_RELATIVE_Y * getHeight())) * i,
						iconSize_X, iconSize_Y, this);
			}
		}

	}

	/**
	 * Liest die aktuelle ArrayList der Actions aus und erzeugt eine entsprechende
	 * Liste an Buttons f�r das actionListPanel. Wird immer aufgerufen, wenn eine
	 * neue ArrayList mit Actions zugewiesen wird.
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
	 * Passt die Bounds des actionListPanel an die Gesamtpanel-Gr��e an. Wird in
	 * paintComponent aufgerufen.
	 *
	 * Unabh�ngig von der aktuellen Anzahl der Buttons im actionListPanel liegt der
	 * unterste Button stets am unteren Rand des Gesamtpanel an.
	 */
	private void updateActionListPanelBounds() {

		int size_Y = (int) (ACTIONLIST_SIZE_RELATIVE_Y * getHeight());
		int buttonsize_Y = size_Y / ACTIONLIST_CELLS;
		int numButtons = actionArrayList.size();
		int position_Y = ((int) (ACTIONLIST_POSITION_RELATIVE_Y * getHeight()))
				+ (ACTIONLIST_CELLS - numButtons) * buttonsize_Y;

		actionListPanel.setBounds((int) (ACTIONLIST_POSITION_RELATIVE_X * getWidth()), position_Y,
				(int) (ACTIONLIST_SIZE_RELATIVE_X * getWidth()), size_Y);
	}

	/**
	 * Passt die Bounds der Textfelder (Name und Liste der Abilities) an die
	 * Gesamtpanel-Gr��e an. Wird in paintComponent aufgerufen.
	 */
	private void updateTextFieldsBounds() {
		heroNameLabel.setBounds((int) (HERONAMELABEL_POSITION_RELATIVE_X * getWidth()),
				(int) (HERONAMELABEL_POSITION_RELATIVE_Y * getHeight()),
				(int) (HERONAMELABEL_SIZE_RELATIVE_X * getWidth()),
				(int) (HERONAMELABEL_SIZE_RELATIVE_Y * getHeight()));

		// TODO: Fonts erstellen verbraucht viel CPU, andere L�sung finden

		//Font des Labels anpassen, abh�ngig von der Panelh�he
		Font heroNameLabelFont = heroNameLabel.getFont();
		int newFontSize = (int) (HERONAMELABEL_TEXT_SIZE_RELATIVE_Y * getHeight());
		heroNameLabel.setFont(new Font(heroNameLabelFont.getName(), Font.BOLD, newFontSize));

		abilityDescriptionField.setBounds((int) (ABILITYDESCRIPTIONFIELD_POSITION_RELATIVE_X * getWidth()),
				(int) (ABILITYDESCRIPTIONFIELD_POSITION_RELATIVE_Y * getHeight()),
				(int) (ABILITYDESCRIPTIONFIELD_SIZE_RELATIVE_X * getWidth()),
				(int) (ABILITYDESCRIPTIONFIELD_SIZE_RELATIVE_Y * getHeight()));

		// TODO: Fonts erstellen verbraucht viel CPU, andere L�sung finden

		// Font des Textfeldes anpassen, abh�ngig von der Panelh�he
		Font abilityDescriptionFieldFont = abilityDescriptionField.getFont();
		newFontSize = (int) (ABILITYDESCRIPTIONFIELD_TEXT_SIZE_RELATIVE_Y * getHeight());
		abilityDescriptionField.setFont(new Font(abilityDescriptionFieldFont.getName(), Font.PLAIN, newFontSize));

	}

	public ArrayList<Action> getActionArrayList() {
		return actionArrayList;
	}

	public ArrayList<JButton> getButtonArrayList() {
		return buttonArrayList;
	}

	/**
	 * Setzt eine neue Liste an m�glichen Actions und passt die Buttons entsprechend
	 * an. Nur benutzen, wenn die Optionen permanent ver�ndert werden sollen. Soll
	 * eine Aktion vor�bergehend (de)aktiviert werden, dann an dieser
	 * setEnabled(boolean) benutzen und hier updateButtonsEnabled() aufrufen.
	 *
	 * @param actionArrayList Die neue Aktionsliste
	 */
	public void setActionArrayList(ArrayList<Action> actionArrayList) {
		this.actionArrayList = actionArrayList;
		updateActionListPanelContent();
	}

	public void setButtonsEnabled(boolean buttonsEnabled) {
		this.buttonsEnabled = buttonsEnabled;
		updateButtonsEnabled();
	}

	public void setDisplayedHero(Hero displayedHero) {
		this.displayedHero = displayedHero;

		removeAll();
		initializeActionListPanel();
		initializeTextFields();
		updateActionListPanelBounds();
		updateTextFieldsBounds();
		repaint();
	}

}
