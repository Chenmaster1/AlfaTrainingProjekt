package InGameGUI;

import Heroes.Hero;
import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;

import static InGameGUI.MapPanel.MAPSTATE_PLAYER_AIMING;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Das Panel, auf dem die Sidebar mit Helden, Aktionen usw angezeigt wird.
 */
public class GameSidePanel extends JPanel {

	private final static double PANELOTHERHEROES_POSITION_RELATIVE_X = 14 / 840.0;
	private final static double PANELOTHERHEROES_POSITION_RELATIVE_Y = 85 / 1080.0;
	private final static double PANELOTHERHEROES_SIZE_RELATIVE_X = 780 / 840.0;
	private final static double PANELOTHERHEROES_SIZE_RELATIVE_Y = 200 / 1080.0;

	// Verschmälerung des otherHeroesPanel pro fehlendem Helden
	private final static double PANELOTHERHEROES_GAPSIZE_RELATIVE_X = 198 / 840.0;

	private final static double PANELPLAYERHERO_POSITION_RELATIVE_X = 30 / 840.0;
	private final static double PANELPLAYERHERO_POSITION_RELATIVE_Y = 340 / 1080.0;
	private final static double PANELPLAYERHERO_SIZE_RELATIVE_X = 558 / 840.0;
	private final static double PANELPLAYERHERO_SIZE_RELATIVE_Y = 393 / 1080.0;
	
	private final static double PANELLOGHEROACTION_POSITION_RELATIVE_X = 600 / 840.0;
	private final static double PANELLOGHEROACTION_POSITION_RELATIVE_Y = 340 / 1080.0;
	private final static double PANELLOGHEROACTION_SIZE_RELATIVE_X = 178 / 840.0;
	private final static double PANELLOGHEROACTION_SIZE_RELATIVE_Y = 393 / 1080.0;

	private final static double PANELATTACKDICE_POSITION_RELATIVE_X = 0 / 840.0;
	private final static double PANELATTACKDICE_POSITION_RELATIVE_Y = 742 / 1080.0;
	private final static double PANELATTACKDICE_SIZE_RELATIVE_X = 403 / 840.0;
	private final static double PANELATTACKDICE_SIZE_RELATIVE_Y = 297 / 1080.0;

	private final static double PANELHIDEDICE_POSITION_RELATIVE_X = 403 / 840.0;
	private final static double PANELHIDEDICE_POSITION_RELATIVE_Y = 742 / 1080.0;
	private final static double PANELHIDEDICE_SIZE_RELATIVE_X = 404 / 840.0;
	private final static double PANELHIDEDICE_SIZE_RELATIVE_Y = 297 / 1080.0;

	private Image backgroundImage;

	// Array aller Helden
	private ArrayList<Hero> heroes;
	private final int numOtherHeroes;

	// der Held des Hauptspielers, angezeigt im zentralen HeroPanelLarge
	private Hero playerHero;

	// Die Hero-Panels
	private OtherHeroesPanel panelOtherHeroes;
	private HeroPanelLarge panelPlayerHero;
	private LogHeroActionPanel panelLogHeroAction;

	// Die Dice-Panels
	private AttackDicePanel panelAttackDice;
	private HideDicePanel panelHideDice;

	// Die Threads für die Würfelanimationen, werden im Konstruktor gestartet
	private Thread threadAttackDicePanel;
	private Thread threadHideDicePanel;

	public GameSidePanel(ArrayList<Hero> allHeroes, Hero playerHero) {
		super();

		this.heroes = allHeroes;
		this.playerHero = playerHero;
		this.numOtherHeroes = heroes.size() - 1;

		backgroundImage = ImageLoader.getInstance().getImage(ImageName.GAMEBOARD_RIGHT);
		setLayout(null);

		panelOtherHeroes = new OtherHeroesPanel(allHeroes);
		panelPlayerHero = new HeroPanelLarge(playerHero);
		panelLogHeroAction = new LogHeroActionPanel();
		panelAttackDice = new AttackDicePanel();
		panelHideDice = new HideDicePanel();

		threadAttackDicePanel = new Thread(panelAttackDice);
		threadHideDicePanel = new Thread(panelHideDice);

		threadAttackDicePanel.start();
		threadHideDicePanel.start();

		add(panelOtherHeroes);
		add(panelPlayerHero);
		add(panelLogHeroAction);
		add(panelAttackDice);
		add(panelHideDice);

		
		// Unterpanels resizen per Listener (wenn dieses Panel in der Größe geändert
		// wird.
		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent arg0) {
				// Bounds setzen für das panelOtherHeroes
				int numEmptySpaces = 4 - numOtherHeroes;
				panelOtherHeroes.setBounds(
						(int) ((PANELOTHERHEROES_POSITION_RELATIVE_X
								+ PANELOTHERHEROES_GAPSIZE_RELATIVE_X * numEmptySpaces / 2) * getWidth()),
						(int) (PANELOTHERHEROES_POSITION_RELATIVE_Y * getHeight()),
						(int) ((PANELOTHERHEROES_SIZE_RELATIVE_X - PANELOTHERHEROES_GAPSIZE_RELATIVE_X * numEmptySpaces)
								* getWidth()),
						(int) (PANELOTHERHEROES_SIZE_RELATIVE_Y * getHeight()));

				// Bounds setzen für das panelPlayerHero
				panelPlayerHero.setBounds((int) (PANELPLAYERHERO_POSITION_RELATIVE_X * getWidth()),
						(int) (PANELPLAYERHERO_POSITION_RELATIVE_Y * getHeight()),
						(int) (PANELPLAYERHERO_SIZE_RELATIVE_X * getWidth()),
						(int) (PANELPLAYERHERO_SIZE_RELATIVE_Y * getHeight()));

				panelLogHeroAction.setBounds((int) (PANELLOGHEROACTION_POSITION_RELATIVE_X * getWidth()),
						(int) (PANELLOGHEROACTION_POSITION_RELATIVE_Y * getHeight()),
						(int) (PANELLOGHEROACTION_SIZE_RELATIVE_X * getWidth()),
						(int) (PANELLOGHEROACTION_SIZE_RELATIVE_Y * getHeight()));

				// Bounds setzen für das panelAttackDice
				panelAttackDice.setBounds((int) (PANELATTACKDICE_POSITION_RELATIVE_X * getWidth()),
						(int) (PANELATTACKDICE_POSITION_RELATIVE_Y * getHeight()),
						(int) (PANELATTACKDICE_SIZE_RELATIVE_X * getWidth()),
						(int) (PANELATTACKDICE_SIZE_RELATIVE_Y * getHeight()));

				// Bounds setzen für das panelHideDice
				panelHideDice.setBounds((int) (PANELHIDEDICE_POSITION_RELATIVE_X * getWidth()),
						(int) (PANELHIDEDICE_POSITION_RELATIVE_Y * getHeight()),
						(int) (PANELHIDEDICE_SIZE_RELATIVE_X * getWidth()),
						(int) (PANELHIDEDICE_SIZE_RELATIVE_Y * getHeight()));
			}
		});

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

	public LogHeroActionPanel getPanelLogHeroAction() {
		return panelLogHeroAction;
	}

	public Thread getThreadHideDicePanel() {
		return threadHideDicePanel;
	}
}
