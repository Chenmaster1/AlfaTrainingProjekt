package InGameGUI;

import Heroes.Hero;
import static InGameGUI.MapPanel.MAPSTATE_PLAYER_AIMING;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Das Panel, auf dem die Sidebar mit Helden, Aktionen usw angezeigt wird.
 */
public class GameSidePanel extends JPanel {

	
	private final static double PANELOTHERHEROES_POSITION_RELATIVE_X = 14 / 840.0;
	private final static double PANELOTHERHEROES_POSITION_RELATIVE_Y = 85 / 1080.0;
	private final static double PANELOTHERHEROES_SIZE_RELATIVE_X = 780 / 840.0;
	private final static double PANELOTHERHEROES_SIZE_RELATIVE_Y = 200 / 1080.0;

	private final static double PANELPLAYERHERO_POSITION_RELATIVE_X = 30 / 840.0;
	private final static double PANELPLAYERHERO_POSITION_RELATIVE_Y = 340 / 1080.0;
	private final static double PANELPLAYERHERO_SIZE_RELATIVE_X = 558 / 840.0;
	private final static double PANELPLAYERHERO_SIZE_RELATIVE_Y = 393 / 1080.0;

	private final static double PANELATTACKDICE_POSITION_RELATIVE_X = 20 / 840.0;
	private final static double PANELATTACKDICE_POSITION_RELATIVE_Y = 750 / 1080.0;
	private final static double PANELATTACKDICE_SIZE_RELATIVE_X = 350 / 840.0;
	private final static double PANELATTACKDICE_SIZE_RELATIVE_Y = 250 / 1080.0;

	private final static double PANELHIDEDICE_POSITION_RELATIVE_X = 370 / 840.0;
	private final static double PANELHIDEDICE_POSITION_RELATIVE_Y = 742 / 1080.0;
	private final static double PANELHIDEDICE_SIZE_RELATIVE_X = 437 / 840.0;
	private final static double PANELHIDEDICE_SIZE_RELATIVE_Y = 295 / 1080.0;

	private Image backgroundImage;

	// Array aller Helden
	private ArrayList<Hero> heroes;

	// der Held des Hauptspielers, angezeigt im zentralen HeroPanelLarge
	private Hero playerHero;

	// Die Hero-Panels
	private OtherHeroesPanel panelOtherHeroes;
	private HeroPanelLarge panelPlayerHero;

	// Die Dice-Panels
	private AttackDicePanel panelAttackDice;
	private HideDicePanel panelHideDice;

	// Die Threads für die Würfelanimationen, werden im Konstruktor gestartet
	private Thread threadAttackDicePanel;
	private Thread threadHideDicePanel;

	public GameSidePanel(ArrayList<Hero> heroes, Hero playerHero) {
		super();

		this.heroes = heroes;
		this.playerHero = playerHero;

		backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/Gameboard_Right.png"))
				.getImage();

		setLayout(null);

		panelOtherHeroes = new OtherHeroesPanel(heroes);
		panelPlayerHero = new HeroPanelLarge(playerHero);
		panelAttackDice = new AttackDicePanel();
		panelHideDice = new HideDicePanel();

		threadAttackDicePanel = new Thread(panelAttackDice);
		threadHideDicePanel = new Thread(panelHideDice);

		threadAttackDicePanel.start();
		threadHideDicePanel.start();

		add(panelOtherHeroes);
		add(panelPlayerHero);
		add(panelAttackDice);
		add(panelHideDice);

	}

	/**
	 * Zeichnet das Panel. Die meisten Elemente werden als eigene Components
	 * hinzugefügt und zeichnen sich daher selbst, nur das Hintergrundbild wird hier
	 * extra gezeichnet.
	 *
	 * @param g
	 */
	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		// Hintergrund ganz unten
		g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

		// Bounds setzen für das panelOtherHeroes
		panelOtherHeroes.setBounds(
				(int)(PANELOTHERHEROES_POSITION_RELATIVE_X * getWidth()),
				(int)(PANELOTHERHEROES_POSITION_RELATIVE_Y * getHeight()),
				(int)(PANELOTHERHEROES_SIZE_RELATIVE_X * getWidth()), 
				(int)(PANELOTHERHEROES_SIZE_RELATIVE_Y * getHeight()));
		
		// Bounds setzen für das panelPlayerHero
		panelPlayerHero.setBounds(
				(int)(PANELPLAYERHERO_POSITION_RELATIVE_X * getWidth()),
				(int)(PANELPLAYERHERO_POSITION_RELATIVE_Y * getHeight()),
				(int)(PANELPLAYERHERO_SIZE_RELATIVE_X * getWidth()), 
				(int)(PANELPLAYERHERO_SIZE_RELATIVE_Y * getHeight()));
		
		// Bounds setzen für das panelAttackDice
		panelAttackDice.setBounds(
				(int)(PANELATTACKDICE_POSITION_RELATIVE_X * getWidth()),
				(int)(PANELATTACKDICE_POSITION_RELATIVE_Y * getHeight()),
				(int)(PANELATTACKDICE_SIZE_RELATIVE_X * getWidth()), 
				(int)(PANELATTACKDICE_SIZE_RELATIVE_Y * getHeight()));
		
		// Bounds setzen für das panelHideDice
		panelHideDice.setBounds(
				(int)(PANELHIDEDICE_POSITION_RELATIVE_X * getWidth()),
				(int)(PANELHIDEDICE_POSITION_RELATIVE_Y * getHeight()),
				(int)(PANELHIDEDICE_SIZE_RELATIVE_X * getWidth()), 
				(int)(PANELHIDEDICE_SIZE_RELATIVE_Y * getHeight()));
	}

	public void setHeroes(ArrayList<Hero> heroes) {
		this.heroes = heroes;
	}

	public OtherHeroesPanel getPanelOtherHeroes() {
		return panelOtherHeroes;
	}

	public HeroPanelLarge getPanelPlayerHero() {
		return panelPlayerHero;
	}

	public AttackDicePanel getPanelAttackDice() {
		return panelAttackDice;
	}

	public HideDicePanel getPanelHideDice() {
		return panelHideDice;
	}

	public Thread getThreadAttackDicePanel() {
		return threadAttackDicePanel;
	}

	public Thread getThreadHideDicePanel() {
		return threadHideDicePanel;
	}
}
