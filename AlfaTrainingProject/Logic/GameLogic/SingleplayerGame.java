package GameLogic;

import java.util.ArrayList;

import Actions.Action;
import Actions.ActionAttack;
import Actions.ActionHide;
import Actions.ActionWorkOffDelay;
import Dice.AttackDice;
import Dice.HideDice;
import Heroes.Hero;
import Hideouts.Hideout;
import InGameGUI.GamePanel;
import GameData.GameData;
import enums.AbilityType;
import enums.AttackMode;
import enums.GameState;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Abilities.Ability;
import static Dice.AttackDice.*;
import InGameGUI.MapPanel;
import MenuGUI.MainFramePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

public class SingleplayerGame {

	private final JFrame mainFrame;
	private GamePanel gamePanel;
	private GameData gameData;
	private MainFramePanel mainFramePanel;

	private MouseListener mapPanelClickListener;
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

		initializeButtonListeners();
	}

	/**
	 * Diese Methode führt für jeden Helden die Standardaktionen sowie die
	 * Heldenspezifischen Abilities zu einer Liste zusammen und übergibt der GUI die
	 * Liste des Hauptsspielers, damit diese in JButtons umgemünzt werden kann.
	 * 
	 * Abilities mit dem AbilityType PASSIVE werden übersprungen, da sie nicht aktiv
	 * einsetzbar sind. Ihre Effekte müssen anderweitig implementiert werden – als
	 * Action existieren sie nur, um der GUI ihren Erklärungstext geben zu können
	 * (wird auf dem HeroPanelLarge angezeigt).
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
				if (heroAbility.getAbilityType() != AbilityType.PASSIVE) {
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
	 */
	private void initializeButtonListeners() {
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

		// MapPanel MouseClick Listener
		mapPanelClickListener = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {
				chosenAttackField = gamePanel.getMapPanel().getCurrentAimedAtField();
				synchronized (mainFrame) {
					mainFrame.notifyAll();
				}
			}

		};

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
				currentHero.setIsAttackable(true);
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

		gamePanel.getGameSidePanel().getPanelPlayerHero().setButtonsEnabled(true);
		currentHero.setCurrentActionPoints(currentHero.getMaxActionPoints());
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

			// Auf Entscheidung des Spielers warten, wird von den Action-Buttons wieder
			// notified
			synchronized (mainFrame) {
				try {
					mainFrame.wait();
					// System.out.println("playerTurn continued");
				} catch (InterruptedException ex) {
					Logger.getLogger(SingleplayerGame.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

			// Aktion wurde gewählt. Entsprechend AP reduzieren, Reduktion anzeigen und dann
			// Aktion ausführen
			decreaseCurrentActionPointsBy(chosenPlayerAction.getActionPointsRequired());
			gamePanel.getGameSidePanel().repaint();
			chosenPlayerAction.useAction(this);

			// Auswirkungen der Aktion anzeigen
			gamePanel.repaint();

			// Wenn diese Aktion das Spiel beendet hat (siehe isGameOver), dann aus der
			// Aktionsschleife springen und true zurückgeben
			if (isGameOver()) {
				gameOver = true;
				break;
			}

			// Wenn nach der Aktion nur noch so viele offene Verstecke wie Spieler da sind,
			// Sudden Death aktivieren (Unverwundbarkeit ausgeschaltet)
			if (!suddenDeathActive) {
				int activeHideoutsCount = 0;
				int heroesAliveCount = 0;
				for (Hideout hideout : gameData.getHideouts()) {
					if (hideout.isActive())
						activeHideoutsCount++;
				}

				for (Hero hero : gameData.getHeroes()) {
					if (!hero.isDead())
						heroesAliveCount++;
				}

				if (heroesAliveCount == activeHideoutsCount) {
					suddenDeathActive = true;
				}
			}
		}

		return gameOver;

	}

	private boolean kiTurn() {

		gamePanel.getGameSidePanel().getPanelPlayerHero().setButtonsEnabled(false);
		currentHero.setCurrentActionPoints(currentHero.getMaxActionPoints());
		boolean gameOver = false;

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

			// Auswirkungen der Aktion anzeigen
			gamePanel.repaint();

			// Wenn diese Aktion das Spiel beendet hat (siehe isGameOver), dann aus der
			// Schleife springen und true zurückgeben
			if (isGameOver()) {
				gameOver = true;
				break;
			}

			// Wenn nach der Aktion nur noch so viele offene Verstecke wie Spieler da sind,
			// Sudden Death aktivieren (Unverwundbarkeit ausgeschaltet)
			if (!suddenDeathActive) {
				int activeHideoutsCount = 0;
				int heroesAliveCount = 0;
				for (Hideout hideout : gameData.getHideouts()) {
					if (hideout.isActive())
						activeHideoutsCount++;
				}

				for (Hero hero : gameData.getHeroes()) {
					if (!hero.isDead())
						heroesAliveCount++;
				}

				if (heroesAliveCount == activeHideoutsCount) {
					suddenDeathActive = true;
				}
			}
		}

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
				// AI-Zielvorgang

				// getting AttackField
				int currentAttackField = currentHero.getKiLogic().chooseAttackField(this);

				// MAPSTATE_KI_AIMING erzeugt Zielmaske, aber ohne MouseListener
				gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_KI_AIMING);
				gamePanel.getMapPanel().setCurrentAimedAtField(currentAttackField);

				// Kurz pausieren, damit die Zielauswahl deutlich sichtbar ist.
				try {
					Thread.sleep(200);
				} catch (InterruptedException ex) {
					Logger.getLogger(SingleplayerGame.class.getName()).log(Level.SEVERE, null, ex);
				}

				shootAtAttackField(currentAttackField);

			} else {
				// Spielerkontrollierter Zielvorgang

				// MAPSTATE_PLAYER_AIMING erzeugt Zielmaske, mit MouseListener, d.h. sie folgt
				// der Maus
				gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_PLAYER_AIMING);
				gamePanel.getMapPanel().repaint();

				// Der Listener für den Mausklick auf MapPanel wird angemeldet
				gamePanel.getMapPanel().addMouseListener(mapPanelClickListener);

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
				gamePanel.getMapPanel().removeMouseListener(mapPanelClickListener);
				shootAtAttackField(chosenAttackField);
			}

			break;

		case CHOOSING:
			// Zielmaske wieder ausblenden, sonst nichts besonderes
			gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_REGULAR);
			break;

		}
		this.gameState = gameState;
	}

	private void shootAtAttackField(int currentAttackField) {
		int numberOfHideouts = gameData.getHideouts().size();
		int diceResult = attackDice.rollDice();

		// Zielmaske einfrieren
		gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_KI_AIMING);

		// Animation starten
		gamePanel.getGameSidePanel().getPanelAttackDice().setRollResult(diceResult);

		// Auf Animation warten
		synchronized (gamePanel.getGameSidePanel().getPanelAttackDice()) {
			try {
				gamePanel.getGameSidePanel().getPanelAttackDice().wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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
		switch (diceResult) {
		case RESULT_CENTER_HIT:
			finalRolledAttackField = currentAttackField;
			break;
		case RESULT_LEFT_CENTER_HIT:
			finalRolledAttackField = (currentAttackField + numberOfHideouts - 1) % numberOfHideouts;
			break;
		case RESULT_RIGHT_CENTER_HIT:
			finalRolledAttackField = (currentAttackField + numberOfHideouts + 1) % numberOfHideouts;
			break;
		case RESULT_OUTER_LEFT_HIT:
			finalRolledAttackField = (currentAttackField + numberOfHideouts - 2) % numberOfHideouts;
			break;
		case RESULT_OUTER_RIGHT_HIT:
			finalRolledAttackField = (currentAttackField + numberOfHideouts + 2) % numberOfHideouts;
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
					occupyingHero.setVisible(true);
				} // Hero is hit
				else {
					if (occupyingHero.isAttackable() || suddenDeathActive)
						occupyingHero.heroGotHit();
					// check if hero died / disable field
					if (occupyingHero.isDead()) {
						gameData.getHideouts().get(finalRolledAttackField).setActive(false);
					}

				}
			}
		}
		gamePanel.repaint();
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
	 * @param hero
	 * @param isWon
	 */
	private void backToMainMenu(boolean isWon) {
		String message;
		if (isWon)
			message = "Du hast gewonnen";
		else
			message = "Du hast verloren";
		JOptionPane.showMessageDialog(mainFrame, message);
		mainFrame.setContentPane(mainFramePanel);
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

}
