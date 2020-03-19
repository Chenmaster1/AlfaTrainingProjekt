package GameLogic;

import java.util.ArrayList;

import Actions.Action;
import Actions.ActionAttack;
import Actions.ActionHide;
import Actions.ActionWorkOffDelay;
import Dice.AttackDice;
import Dice.HideDice;
import Heroes.Hero;
import Heroes.HeroEventListener;
import Hideouts.Hideout;
import InGameGUI.GamePanel;
import GameData.GameData;
import enums.AbilityType;
import enums.AttackMode;
import enums.GameState;
import enums.HeroEventType;

import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Abilities.Ability;
import static Dice.AttackDice.*;
import InGameGUI.MapPanel;
import MenuGUI.MainFramePanel;
import MenuGUI.MyFrame;
import SoundThread.SoundController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

public class SingleplayerGame implements HeroEventListener {

	private final JFrame mainFrame;
	private GamePanel gamePanel;
	private GameData gameData;
	private MainFramePanel mainFramePanel;

	private HeroEventListener heroEventListener;
	private MouseListener mapPanelAttackClickListener;
	private MouseListener mapPanelAimClickListener;
	private int chosenAttackField;

	private AttackMode attackMode = AttackMode.NO_ATTACK;
	private GameState gameState;

	private Hero currentHero;
	private int currentHeroIndex;

	private ArrayList<Action> standardActions;
	private ArrayList<ArrayList<Action>> heroActionsLists;

	private ArrayList<Action> playerActions;
	private Action chosenPlayerAction;

	private AttackDice attackDice;
	private HideDice hideDice;

	boolean suddenDeathActive;

	// ------------------booleans fuer Spielkontrolle ueber
	// Karten------------------------
	private boolean mysteriousIdol1 = false;
	private boolean mysteriousIdol2 = false;

	// ------------------booleans fuer Spielkontrolle ueber
	// Karten------------------------
	public SingleplayerGame(JFrame mainFrame, GamePanel gamePanel, GameData map, MainFramePanel mainFramePanel) {
		this.mainFrame = mainFrame;
		this.gamePanel = gamePanel;
		this.gameData = map;
		this.mainFramePanel = mainFramePanel;

		attackDice = new AttackDice();
		hideDice = new HideDice();

		initializeActionLists();

		initializeListeners();
	}

	/**
	 * Diese Methode führt für jeden Helden die Standardaktionen sowie die
	 * Heldenspezifischen Abilities zu einer Liste zusammen und übergibt der GUI die
	 * Liste des Hauptsspielers, damit diese in JButtons umgemünzt werden kann.
	 *
	 * Abilities mit dem AbilityType PASSIVE oder REACTION werden übersprungen, da
	 * sie nicht aktiv einsetzbar sind. Ihre Effekte müssen anderweitig
	 * implementiert werden – als Action existieren sie nur, um der GUI ihren
	 * Erklärungstext geben zu können (wird auf dem HeroPanelLarge angezeigt).
	 */
	private void initializeActionLists() {
		heroActionsLists = new ArrayList<ArrayList<Action>>();
		standardActions = new ArrayList<Action>();
		standardActions.add(new ActionAttack(1));
		standardActions.add(new ActionHide(1));
		standardActions.add(new ActionWorkOffDelay(1));

		ArrayList<Action> heroActionList;

		for (Hero h : gameData.getHeroes()) {
			heroActionList = new ArrayList<>(standardActions);
			for (Ability heroAbility : h.getAbilities()) {
				if (heroAbility.getAbilityType() != AbilityType.PASSIVE
						&& heroAbility.getAbilityType() != AbilityType.REACTION) {
					heroActionList.add(heroAbility);
				}
			}
			heroActionsLists.add(heroActionList);

			if (h.isPlayerControlled()) {
				playerActions = heroActionList;
				gamePanel.getGameSidePanel().getPanelPlayerHero().setActionArrayList(heroActionList);
			}
		}
	}

	/**
	 * Initialisiert die ActionListener der Buttons, mit denen der Spieler seine
	 * Aktion auswählt. Diese speichern die Wahl als Attribut im SingleplayerGame
	 * und benutzen dann notify(), um den gameLogicThread wieder zu wecken (siehe
	 * playerTurn()).
	 *
	 * Ein nach dem gleichen Prinzip funktionierender MouseListener wird für die
	 * Zielauswahl initialisiert. Dieser wird bei einer Angriffsaktion des Spielers
	 * temporär beim MapPanel angemeldet (siehe setGameState).
	 * 
	 * Außerdem wird das SingleplayerGame selbst als HeroEventListener bei allen
	 * Helden angemeldet, damit Reaktionen behandelt werden können
	 */
	private void initializeListeners() {
		ArrayList<JButton> playerButtons = gamePanel.getGameSidePanel().getPanelPlayerHero().getButtonArrayList();
		for (int i = 0; i < playerButtons.size(); i++) {
			final Action playerAction = playerActions.get(i);
			playerButtons.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					chosenPlayerAction = playerAction;
					gamePanel.getGameSidePanel().getPanelPlayerHero().setButtonsEnabled(false);
					// System.out.println("notifying");
					synchronized (mainFrame) {
						mainFrame.notifyAll();
					}
					// System.out.println("notified");
				}
			});

		}

		// MapPanel MouseListener zur Wahl der AttackAction (alternativ zum Button)
		mapPanelAttackClickListener = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {
				// Linksklick: AttackAction wählen
				if (me.getButton() == MouseEvent.BUTTON1) {
					// Das anvisierte Feld sofort auf das Feld unterm Cursor setzen
					gamePanel.getMapPanel()
							.setCurrentAimedAtField(gamePanel.getMapPanel().calculateField(me.getPoint()));
					// Auf den ersten Button der Action Liste klicken, weckt auch den GameLogic
					// Thread wieder auf
					gamePanel.getGameSidePanel().getPanelPlayerHero().getButtonArrayList().get(0).doClick();
					// System.out.println("mapPanelAttackClickListener triggered");

				}
			}

		};

		// MapPanel MouseListener für die Zielauswahl bei einer Attacke
		mapPanelAimClickListener = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {
				// Linksklick: Attacke auslösen
				if (me.getButton() == MouseEvent.BUTTON1) {
					chosenAttackField = gamePanel.getMapPanel().getCurrentAimedAtField();
					synchronized (mainFrame) {
						mainFrame.notifyAll();
					}
				} // Rechtsklick: Attacke abbrechen
				else {
					if (me.getButton() == MouseEvent.BUTTON3) {
						chosenAttackField = -1;
						synchronized (mainFrame) {
							mainFrame.notifyAll();
						}
					}
				}
			}

		};

		// SingleplayerGame als HeroEventListener anmelden bei allen Helden
		for (Hero h : gameData.getHeroes()) {
			h.addHeroListener(this);
		}

	}

	public void startGame() {

		showGame();

		Thread gameLogicThread = new Thread() {
			public void run() {

				startGameLogic();

			}
		};
		gameLogicThread.start();
	}

	private void showGame() {

		mainFrame.setContentPane(gamePanel);
		mainFrame.pack();
	}

	private void startGameLogic() {
		// get Hero who does the first turn
		int heroCount = gameData.getHeroes().size();
		Random randomPlayer = new Random();
		setCurrentHeroIndex(randomPlayer.nextInt(heroCount));

		while (true) {
			if (!currentHero.isDead()) {
				currentHero.setAttackable(true);
				// player´s turn
				if (currentHero.isPlayerControlled()) {

					boolean gameOver = playerTurn();

					if (gameOver) {
						break;
					}
				} // ki´s turn
				else {

					boolean gameOver = kiTurn();
					if (gameOver) {
						break;
					}
				}
			}
			// to not exceed playerBase
			setCurrentHeroIndex((currentHeroIndex + 1) % heroCount);

		}

		for (Hero h : gameData.getHeroes()) {
			if (h.isPlayerControlled()) {
				if (h.isDead()) {
					backToMainMenu(false);
				} else {
					backToMainMenu(true);
				}
			}
		}
	}

	private boolean playerTurn() {

//        gamePanel.getGameSidePanel().getPanelPlayerHero().setButtonsEnabled(true);
		currentHero.setCurrentActionPoints(currentHero.getMaxActionPoints());

		// name of Hero to PanelLogHeroAction
		gamePanel.getGameSidePanel().getPanelLogHeroAction()
				.setTextAreaLogHeroAction(currentHero.getName() + " " + MyFrame.bundle.getString("nextTurnIsPlayer"));

		boolean gameOver = false;

		while (currentHero.getCurrentActionPoints() != 0) {
			setGameState(GameState.CHOOSING);

			// Die Button-Blockierung aufheben
			gamePanel.getGameSidePanel().getPanelPlayerHero().setButtonsEnabled(true);

			// Verfügbarkeits-Status der Aktionen updaten
			for (Action a : heroActionsLists.get(currentHeroIndex)) {
				a.updateEnabled(this);
			}

			// Die Buttons je nach Verfügbarkeits-Status der jeweiligen Aktionen enablen
			gamePanel.getGameSidePanel().getPanelPlayerHero().updateButtonsEnabled();

			// MapPanel Listener für die Schnellauswahl der AttackAction einschalten
			gamePanel.getMapPanel().addMouseListener(mapPanelAttackClickListener);

			// Auf Entscheidung des Spielers warten, wird von den Action-Buttons wieder
			// notified
			synchronized (mainFrame) {
				try {
					mainFrame.wait();
//					System.out.println("playerTurn continued");
				} catch (InterruptedException ex) {
					Logger.getLogger(SingleplayerGame.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

			// MapPanel Listener für die Schnellauswahl der AttackAction wieder ausschalten
			gamePanel.getMapPanel().removeMouseListener(mapPanelAttackClickListener);

			// Aktion wurde gewählt. Entsprechend AP reduzieren, Reduktion anzeigen und dann
			// Aktion ausführen
			decreaseCurrentActionPointsBy(chosenPlayerAction.getActionPointsRequired());
			gamePanel.getGameSidePanel().repaint();
//			System.out.println("chosenAction: " + chosenPlayerAction);
			chosenPlayerAction.useAction(this);

			// Auswirkungen der Aktion anzeigen
			gamePanel.repaint();

			// Wenn diese Aktion das Spiel beendet hat (siehe isGameOver), dann aus der
			// Aktionsschleife springen und true zurückgeben
			if (isGameOver()) {
				gameOver = true;
				// happening to PanelLogHeroAction
				gamePanel.getGameSidePanel().getPanelLogHeroAction()
						.setTextAreaLogHeroAction(MyFrame.bundle.getString("gameOverWon"));
				break;
			}

			// Wenn nach der Aktion nur noch so viele offene Verstecke wie Spieler da sind,
			// Sudden Death aktivieren (Unverwundbarkeit ausgeschaltet)
			if (!suddenDeathActive) {
				int activeHideoutsCount = 0;
				int heroesAliveCount = 0;
				for (Hideout hideout : gameData.getHideouts()) {
					if (hideout.isActive()) {
						activeHideoutsCount++;
					}
				}

				for (Hero hero : gameData.getHeroes()) {
					if (!hero.isDead()) {
						heroesAliveCount++;
					}
				}

				if (heroesAliveCount == activeHideoutsCount) {
					activateSuddenDeath();
					// happening to PanelLogHeroAction
					gamePanel.getGameSidePanel().getPanelLogHeroAction()
							.setTextAreaLogHeroAction(MyFrame.bundle.getString("suddenDeathActivation"));
				}
			}
		}
		gamePanel.getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(" ");
		return gameOver;

	}

	private boolean kiTurn() {

		gamePanel.getGameSidePanel().getPanelPlayerHero().setButtonsEnabled(false);
		currentHero.setCurrentActionPoints(currentHero.getMaxActionPoints());
		boolean gameOver = false;

		// name of Hero to PanelLogHeroAction
		gamePanel.getGameSidePanel().getPanelLogHeroAction()
				.setTextAreaLogHeroAction(currentHero.getName() + " " + MyFrame.bundle.getString("nextTurnIs"));

		while (currentHero.getCurrentActionPoints() != 0) {

			setGameState(GameState.CHOOSING);

			// Verfügbarkeits-Status der Aktionen updaten
			for (Action a : heroActionsLists.get(currentHeroIndex)) {
				a.updateEnabled(this);
			}

			// Eine Bedenkzeit der KI simulieren
			try {
				Thread.sleep(500);

			} catch (InterruptedException ex) {
				Logger.getLogger(SingleplayerGame.class.getName()).log(Level.SEVERE, null, ex);
			}

			// KiLogic des Heros nach Entscheidung fragen
			Action currentAction = currentHero.getKiLogic().chooseAction(heroActionsLists.get(currentHeroIndex), this);
			
			// Entsprechend AP reduzieren, Reduktion anzeigen und dann
			// Aktion ausführen
			decreaseCurrentActionPointsBy(currentAction.getActionPointsRequired());
			gamePanel.getGameSidePanel().repaint();
			currentAction.useAction(this);

			// happening to PanelLogHeroAction
			// gamePanel.getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(currentAction.getName());

			// Auswirkungen der Aktion anzeigen
			gamePanel.repaint();

			// Wenn diese Aktion das Spiel beendet hat (siehe isGameOver), dann aus der
			// Schleife springen und true zurückgeben
			if (isGameOver()) {
				gameOver = true;
				// happening to PanelLogHeroAction
				gamePanel.getGameSidePanel().getPanelLogHeroAction()
						.setTextAreaLogHeroAction(MyFrame.bundle.getString("gameOverTwo"));
				break;
			}

			// Wenn nach der Aktion nur noch so viele offene Verstecke wie Spieler da sind,
			// Sudden Death aktivieren (Unverwundbarkeit ausgeschaltet)
			if (!suddenDeathActive) {
				int activeHideoutsCount = 0;
				int heroesAliveCount = 0;
				for (Hideout hideout : gameData.getHideouts()) {
					if (hideout.isActive()) {
						activeHideoutsCount++;
					}
				}

				for (Hero hero : gameData.getHeroes()) {
					if (!hero.isDead()) {
						heroesAliveCount++;
					}
				}

				if (heroesAliveCount == activeHideoutsCount) {
					activateSuddenDeath();
					// happening to PanelLogHeroAction
					gamePanel.getGameSidePanel().getPanelLogHeroAction()
							.setTextAreaLogHeroAction(MyFrame.bundle.getString("suddenDeathActivation"));
				}
			}
		}
		// space between players - happening to PanelLogHeroAction
		gamePanel.getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(" ");
		return gameOver;

	}

	/**
	 * TODO vermutlich sollten die Actions als Control-Objekte ins gleiche Package
	 * wie SingleplayerGame und diese Methode auf package protected stehen
	 *
	 * @param gameState
	 */
	public void setGameState(GameState gameState) {
		switch (gameState) {
		case AIMING:
			if (!currentHero.isPlayerControlled()) {
				// KI-Zielvorgang

				// getting AttackField
				int kiTargetedField = currentHero.getKiLogic().chooseAttackField(this);

				// MAPSTATE_KI_AIMING erzeugt Zielmaske, aber ohne MouseListener
				gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_KI_AIMING);
				gamePanel.getMapPanel().setCurrentAimedAtField(kiTargetedField);

				// Kurz pausieren, damit die Zielauswahl deutlich sichtbar ist.
				try {
					Thread.sleep(200);
				} catch (InterruptedException ex) {
					Logger.getLogger(SingleplayerGame.class.getName()).log(Level.SEVERE, null, ex);
				}

				resolveAttack(kiTargetedField);

			} else {
				// Spielerkontrollierter Zielvorgang

				// MAPSTATE_PLAYER_AIMING erzeugt Zielmaske, mit MouseListener, d.h. sie folgt
				// der Maus
				gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_PLAYER_AIMING);
				gamePanel.getMapPanel().repaint();

				// Der Listener für den Mausklick auf MapPanel wird angemeldet
				gamePanel.getMapPanel().addMouseListener(mapPanelAimClickListener);

				// Thread pausieren, bis aufs MapPanel geklickt wurde (siehe Initialisierung des
				// mapPanelClickListener)
				synchronized (mainFrame) {
					try {
						mainFrame.wait();
					} catch (InterruptedException ex) {
						Logger.getLogger(SingleplayerGame.class.getName()).log(Level.SEVERE, null, ex);
					}
				}

				// Zielfeld wurde angeklickt, mapPanelClickListener wieder entfernen und den
				// Schuss ausloesen.
				gamePanel.getMapPanel().removeMouseListener(mapPanelAimClickListener);

				// Wenn der Spieler mit links geklickt hat, Attacke auslösen
				if (chosenAttackField >= 0) {
					resolveAttack(chosenAttackField);
				}
				// Wenn der Spieler mit rechts geklickt hat, AP zurückerstatten
				else if (chosenAttackField == -1) {
					currentHero.setCurrentActionPoints(
							currentHero.getCurrentActionPoints() + chosenPlayerAction.getActionPointsRequired());
				}

			}

			break;

		case CHOOSING:
			// Zielmaske wieder ausblenden, sonst nichts besonderes
			gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_REGULAR);
			break;

		}
		this.gameState = gameState;
	}

	/**
	 * Der eigentlichen Attacke vorgeschaltete Methode, um auf verschiedene
	 * Angriffsmodi zu testen. Der attackMode wird von der jeweiligen Action bzw.
	 * Ability gesetzt, bevor sie das SingleplayerGame in GameState.AIMING versetzt.
	 */
	private void resolveAttack(int targetedField) {
		switch (attackMode) {
		case ATTACK_TWICE:
			shootAtAttackField(targetedField);

			// Kurz warten, dann zweiter Schuss
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			shootAtAttackField(targetedField);
			break;

		case NORMAL_ATTACK:
			shootAtAttackField(targetedField);
			break;

		case NO_ATTACK:
			break;
		default:
			break;

		}
	}

	/**
	 * Eine einzelne Attacke auf ein anvisiertes Feld. Trifft gemäß der durch den
	 * AttackDice modellierten Verteilung entweder das Feld selbst oder eines in der
	 * Umgebung.
	 *
	 * @param targetedField
	 */
	private void shootAtAttackField(int targetedField) {
		int numberOfHideouts = gameData.getHideouts().size();
		int diceResult = attackDice.rollDice();

		// Zielmaske einfrieren
		gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_KI_AIMING);
		gamePanel.repaint();

		// Animation starten (GameLogicThread wird währenddessen pausiert)
		gamePanel.getGameSidePanel().getPanelAttackDice().setRollResult(diceResult);

		// Kurz pausieren, damit das Ergebnis der Animation vorm Angriff erkennbar ist
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Zielmaske entfernen
		gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_REGULAR);

		// Tatsächlich getroffenes Feld berechnen
		int finalRolledAttackField;
		int mapVisual;
		switch (diceResult) {
		case RESULT_CENTER_HIT:
			finalRolledAttackField = targetedField;
			// happening to PanelLogHeroAction
			mapVisual = gameData.getMapIntToVisualField().get(finalRolledAttackField);
			gamePanel.getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(
					Integer.toString(mapVisual) + " " + MyFrame.bundle.getString("fieldAttacked"));
			break;
		case RESULT_LEFT_CENTER_HIT:
			finalRolledAttackField = (targetedField + numberOfHideouts - 1) % numberOfHideouts;
			// happening to PanelLogHeroAction
			mapVisual = gameData.getMapIntToVisualField().get(finalRolledAttackField);
			gamePanel.getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(
					Integer.toString(mapVisual) + " " + MyFrame.bundle.getString("fieldAttacked"));
			break;
		case RESULT_RIGHT_CENTER_HIT:
			finalRolledAttackField = (targetedField + numberOfHideouts + 1) % numberOfHideouts;
			// happening to PanelLogHeroAction
			mapVisual = gameData.getMapIntToVisualField().get(finalRolledAttackField);
			gamePanel.getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(
					Integer.toString(mapVisual) + " " + MyFrame.bundle.getString("fieldAttacked"));
			break;
		case RESULT_OUTER_LEFT_HIT:
			finalRolledAttackField = (targetedField + numberOfHideouts - 2) % numberOfHideouts;
			// happening to PanelLogHeroAction
			mapVisual = gameData.getMapIntToVisualField().get(finalRolledAttackField);
			gamePanel.getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(
					Integer.toString(mapVisual) + " " + MyFrame.bundle.getString("fieldAttacked"));
			break;
		case RESULT_OUTER_RIGHT_HIT:
			finalRolledAttackField = (targetedField + numberOfHideouts + 2) % numberOfHideouts;
			// happening to PanelLogHeroAction
			mapVisual = gameData.getMapIntToVisualField().get(finalRolledAttackField);
			gamePanel.getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(
					Integer.toString(mapVisual) + " " + MyFrame.bundle.getString("fieldAttacked"));
			break;
		default:
			finalRolledAttackField = -1;
		}

		// hit that field
		if (gameData.getHideoutHero().containsKey(gameData.getHideouts().get(finalRolledAttackField))) {
			// Field is occupied by a hero
			Hero occupyingHero = gameData.getHideoutHero().get(gameData.getHideouts().get(finalRolledAttackField));

			if (occupyingHero != null) {
				// Hero is detected / unveiled
				if (!occupyingHero.isVisible()) {
					gamePanel.getMapPanel().startAnimation(MapPanel.ANIMATIONTYPE_SCAN_OCCUPIED, finalRolledAttackField);
					// happening to PanelLogHeroAction
					gamePanel.getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(
							occupyingHero.getName() + " " + MyFrame.bundle.getString("heroUnveiled"));
					occupyingHero.setVisible(true);
				} // Hero is hit
				else {

					if (occupyingHero.isAttackable()) {
						int newHitPoints = occupyingHero.getCurrentHitPoints() - 1;

						
						// check if hero died / disable field
						if (newHitPoints <=0) {
							gamePanel.getMapPanel().startAnimation(MapPanel.ANIMATIONTYPE_ELIMINATE,
									finalRolledAttackField);
							gameData.getHideouts().get(finalRolledAttackField).setActive(false);
							// happening to PanelLogHeroAction
							gamePanel.getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(
									occupyingHero.getName() + " " + MyFrame.bundle.getString("heroEliminated"));
						} else {
							gamePanel.getMapPanel().startAnimation(MapPanel.ANIMATIONTYPE_FIRE, finalRolledAttackField);
							// happening to PanelLogHeroAction
							gamePanel.getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(
									occupyingHero.getName() + " " + MyFrame.bundle.getString("heroIsHit") + " "
											+ occupyingHero.getCurrentHitPoints());
						}

						if (!suddenDeathActive) {
							occupyingHero.setAttackable(false);
						}
						
						occupyingHero.setCurrentHitPoints(newHitPoints);
					}
					else
					{
						//Held im Feld, aber nicht verwundbar
					}

				}
			}
		} else {
			gamePanel.getMapPanel().startAnimation(MapPanel.ANIMATIONTYPE_SCAN_EMPTY, finalRolledAttackField);
		}
		gamePanel.repaint();

	}

	/**
	 * Startet den Sudden-Death Modus, in dem Helden mehrmals pro Runde getroffen
	 * werden koennen.
	 */
	private void activateSuddenDeath() {
		// Flag setzen
		suddenDeathActive = true;

		// Helden sofort verwundbar machen
		for (Hero h : gameData.getHeroes()) {
			h.setAttackable(true);

		}

		JOptionPane.showMessageDialog(mainFrame, MyFrame.bundle.getString("suddenDeathAnnounced"));
	}

	/**
	 * Gibt true zurück, wenn einer der Helden als einziger übrig ist ODER der
	 * Spielerheld tot ist, sonst false.
	 *
	 * @return
	 */
	private boolean isGameOver() {
		// nach jeder Aktion fragen, ob
		boolean gameOver = false;

		int alive = 0;
		boolean playerHeroDead = false;

		for (Hero hero : gameData.getHeroes()) {
			if (!hero.isDead()) {
				alive++;
			} else {
				if (hero.isPlayerControlled()) {
					playerHeroDead = true;
				}
			}
		}

		if (alive == 1 || playerHeroDead) {
			gameOver = true;
		}

		return gameOver;
	}

	/**
	 *
	 * @param isWon
	 */
	private void backToMainMenu(boolean isWon) {
		String message;
		if (isWon) {
			message = "Du hast gewonnen";
		} else {
			message = "Du hast verloren";
		}
		JOptionPane.showMessageDialog(mainFrame, message);
		mainFrame.setContentPane(mainFramePanel);
                SoundController.setBackgroundMusic("Intro_Main.mp3");
//		mainFrame.repaint();
	}

	// -------------------------GETTER-------------------------//
	public HideDice getHideDice() {
		return hideDice;
	}

	public AttackDice getAttackDice() {
		return attackDice;
	}

	public int getCurrentHeroIndex() {
		return currentHeroIndex;
	}

	public Hero getCurrentHero() {
		return currentHero;
	}

	public GameData getGameData() {
		return gameData;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	// -------------------------SETTER-------------------------//
	/**
	 * Setzt den Helden, der gerade am Zug ist. Updated auch automatisch den
	 * currentHeroIndex.
	 */
	public void setCurrentHero(Hero currentHero) {
		this.currentHero = currentHero;
		this.currentHeroIndex = gameData.getHeroes().indexOf(currentHero);
	}

	/**
	 * Setzt den Index des Helden, der gerade am Zug ist. Updated auch automatisch
	 * den currentHero.
	 *
	 * @param currentHeroIndex
	 */
	public void setCurrentHeroIndex(int currentHeroIndex) {
		this.currentHeroIndex = currentHeroIndex;
		this.currentHero = gameData.getHeroes().get(currentHeroIndex);
	}

	public void reduceCurrentActionPoints() {
		if (currentHero.getCurrentActionPoints() >= 1) {
			currentHero.setCurrentActionPoints(currentHero.getCurrentActionPoints() - 1);
		}
	}

	/**
	 * Reduziert die aktuelle Aktionspunkte sofort auf 0, egal welcher wert vorher
	 * gegeben war
	 */
	public void setCurrentActionPointsToZero() {

		currentHero.setCurrentActionPoints(0);
	}

	/**
	 * Reduziert die Verzoegerungs Tokens um 1 auf maximal 0
	 */
	public void reduceDelayTokens() {
		if (currentHero.getDelayTokens() >= 1) {
			currentHero.setDelayTokens(currentHero.getDelayTokens() - 1);
		}
	}

	public void setAttackMode(AttackMode attackMode) {
		this.attackMode = attackMode;
	}

	public void increaseCurrentActionPointsBy(int increment) {
		currentHero.setCurrentActionPoints(currentHero.getCurrentActionPoints() + increment);
	}

	public void decreaseCurrentActionPointsBy(int decrement) {
		currentHero.setCurrentActionPoints(currentHero.getCurrentActionPoints() - decrement);
	}

	public void setMysteriousIdol1(boolean active) {
		this.mysteriousIdol1 = active;
	}

	public void setMysteriousIdol2(boolean active) {
		this.mysteriousIdol2 = active;
	}

	@Override
	public void heroEventRequest(Hero requestingHero, HeroEventType eventType) {

		// Hier mit einem switch über den HeroEventType den gewünschten Effekt
		// implementieren

		// when HeroDahlia looses a hitpoint she´ll end up @ Hide_Roll
		switch (eventType) {
		case HIDE_AUTOMATIC:

			// freeHide in ActionHide
			if (!suddenDeathActive && requestingHero.getDelayTokens() == 0) {
				ActionHide eventHide = new ActionHide(0);
				eventHide.freeHideHero(requestingHero, this);
				// happening to PanelLogHeroAction
				getGamePanel().getGameSidePanel().getPanelLogHeroAction()
						.setTextAreaLogHeroAction(MyFrame.bundle.getString("heroAbilityDahlia"));

			}

			break;
		case HIDE_ROLL:
			if (!suddenDeathActive && requestingHero.getDelayTokens() == 0) {
				// Aufdeckung anzeigen
				gamePanel.getMapPanel().repaint();

				ActionHide tempActionHide = new ActionHide(0);
				tempActionHide.useAction(requestingHero, this);

			}
			break;
		}

	}

}
