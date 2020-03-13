package GameLogic;

import java.util.ArrayList;

import Actions.Action;
import Actions.ActionAttack;
import Actions.ActionHide;
import Actions.ActionWorkOffDelay;
import Dice.AttackDice;
import Dice.HideDice;
import Heroes.Hero;
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

	public void startGame() {

		showGame();

		Thread gameLogicThread = new Thread() {
			public void run() {

				startGameLogic();

			}
		};
		gameLogicThread.start();
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

					playerTurn();
					if (checkAllHeroesAlive())
						return;
				} // ki´s turn
				else {

					kiTurn();
					if (checkAllHeroesAlive())
						return;
				}
			}
			// to not exceed playerBase
			setCurrentHeroIndex((currentHeroIndex + 1) % heroCount);

		}

	}

	private boolean checkAllHeroesAlive() {
		// nach jedem turn fragen
		int alive = 0;
		for (Hero hero : gameData.getHeroes()) {
			if (hero.isDead() && hero.isPlayerControlled()) {
				backToMainMenu(hero, false);
				return true;
			}
			if (!hero.isDead()) {
				alive++;
			}
		}

		if (alive == 1) {
			for (Hero hero : gameData.getHeroes()) {
				if (!hero.isDead()) {
					backToMainMenu(hero, true);
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 
	 * @param hero
	 * @param isWon
	 */
	private void backToMainMenu(Hero hero, boolean isWon) {
		String message;
		if (isWon)
			message = "Du hast gewonnen";
		else
			message = "Du hast verloren";
		JOptionPane.showMessageDialog(mainFrame, message);
		mainFrame.setContentPane(mainFramePanel);
		mainFrame.repaint();
	}

	/**
	 * Diese Methode führt die Standardaktionen sowie die Heldenspezifischen
	 * Abilities zu einer Liste zusammen und übergibt der GUI die Liste des
	 * Hauptsspielers.
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

	private void initializeButtonListeners() {
		ArrayList<JButton> playerButtons = gamePanel.getGameSidePanel().getPanelPlayerHero().getButtonArrayList();
		for (int i = 0; i < playerButtons.size(); i++) {
			final Action playerAction = playerActions.get(i);
			playerButtons.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					chosenPlayerAction = playerAction;
					gamePanel.getGameSidePanel().getPanelPlayerHero().setButtonsEnabled(false);
					//System.out.println("notifying");
					synchronized (mainFrame) {
						mainFrame.notifyAll();
					}
					//System.out.println("notified");
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

	private void showGame() {

		mainFrame.setContentPane(gamePanel);
		mainFrame.pack();
	}

	private void playerTurn() {

		gamePanel.getGameSidePanel().getPanelPlayerHero().setButtonsEnabled(true);
		currentHero.setCurrentActionPoints(currentHero.getMaxActionPoints());

		while (currentHero.getCurrentActionPoints() != 0) {
			setGameState(GameState.CHOOSING);
			gamePanel.getGameSidePanel().getPanelPlayerHero().setButtonsEnabled(true);
			for (Action a : heroActionsLists.get(currentHeroIndex)) {
				a.updateEnabled(this);

			}
			gamePanel.getGameSidePanel().getPanelPlayerHero().updateButtonsEnabled();

			//System.out.println("playerTurn Waiting");
			synchronized (mainFrame) {
				try {
					mainFrame.wait();
					//System.out.println("playerTurn continued");
				} catch (InterruptedException ex) {
					Logger.getLogger(SingleplayerGame.class.getName()).log(Level.SEVERE, null, ex);
				}
			}

			decreaseCurrentActionPointsBy(chosenPlayerAction.getActionPointsRequired());
			chosenPlayerAction.useAction(this);
			

			gamePanel.repaint();
		}

	}

	private void kiTurn() {

		gamePanel.getGameSidePanel().getPanelPlayerHero().setButtonsEnabled(false);
		currentHero.setCurrentActionPoints(currentHero.getMaxActionPoints());

		while (currentHero.getCurrentActionPoints() != 0) {

			setGameState(GameState.CHOOSING);
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				Logger.getLogger(SingleplayerGame.class.getName()).log(Level.SEVERE, null, ex);
			}
			for (Action a : heroActionsLists.get(currentHeroIndex)) {
				a.updateEnabled(this);
			}

			Action currentAction = currentHero.getKiLogic().chooseAction(heroActionsLists.get(currentHeroIndex), this);

			decreaseCurrentActionPointsBy(currentAction.getActionPointsRequired());
			currentAction.useAction(this);
			

			gamePanel.repaint();
		}

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

	public void setGameState(GameState gameState) {
		switch (gameState) {
		case AIMING:
			if (!currentHero.isPlayerControlled()) {
				// getting AttackField
				int currentAttackField = currentHero.getKiLogic().chooseAttackField(this);

				gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_KI_AIMING);
				gamePanel.getMapPanel().setCurrentAimedAtField(currentAttackField);
				try {
					Thread.sleep(200);
				} catch (InterruptedException ex) {
					Logger.getLogger(SingleplayerGame.class.getName()).log(Level.SEVERE, null, ex);
				}
				shootAtAttackField(currentAttackField);

			} // playerTurn
			else {
				gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_PLAYER_AIMING);
				gamePanel.getMapPanel().repaint();
				gamePanel.getMapPanel().addMouseListener(mapPanelClickListener);

				synchronized (mainFrame) {
					try {
						mainFrame.wait();
						//System.out.println("AIMING continued");
					} catch (InterruptedException ex) {
						Logger.getLogger(SingleplayerGame.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
				gamePanel.getMapPanel().removeMouseListener(mapPanelClickListener);
				shootAtAttackField(chosenAttackField);
			}

			break;

		case CHOOSING:
			gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_REGULAR);
			break;

		}
		this.gameState = gameState;
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

	private void shootAtAttackField(int currentAttackField) {
		int numberOfHideouts = gameData.getHideouts().size();
		int diceResult = attackDice.rollDice();
		
		//Zielmaske einfrieren
		gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_KI_AIMING);
		
		//Animation starten
		gamePanel.getGameSidePanel().getPanelAttackDice().setRollResult(diceResult);
		//Auf Animation warten
		
		synchronized(gamePanel.getGameSidePanel().getPanelAttackDice())
		{
			try {
				gamePanel.getGameSidePanel().getPanelAttackDice().wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Zielmaske entfernen
		gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_REGULAR);
		
		
		
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
					if(occupyingHero.isAttackable())
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

	private void usePlayerAction(Action chosenAction) {
		chosenAction.useAction(this);

	}

}
