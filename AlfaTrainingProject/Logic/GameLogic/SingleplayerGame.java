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
import Maps.Map;
import enums.AbilityType;
import enums.AttackMode;
import enums.GameState;
import java.util.Random;
import javax.swing.JFrame;

import Abilities.Ability;

public class SingleplayerGame {

	private JFrame mainFrame;
	private GamePanel gamePanel;
	private Map map;
	
	private AttackMode attackMode = AttackMode.NO_ATTACK;
	private GameState gameState;
	
	private Hero currentHero;
	private int currentHeroIndex;
	
	private ArrayList<Action> standardActions;
	private ArrayList<ArrayList<Action>> heroActionsLists;
	
	private AttackDice attackDice;
	private HideDice hideDice;
	

	// ------------------booleans fuer Spielkontrolle ueber
	// Karten------------------------
	private boolean mysteriousIdol1 = false;
	private boolean mysteriousIdol2 = false;

	// ------------------booleans fuer Spielkontrolle ueber
	// Karten------------------------

	public SingleplayerGame(JFrame mainFrame, GamePanel gamePanel, Map map) {
		this.mainFrame = mainFrame;
		this.gamePanel = gamePanel;
		this.map = map;
		
		attackDice = new AttackDice();
		hideDice = new HideDice();

		initializeActionLists();
	}

	public void startGame() {

		showGame();

		// get Hero who does the first turn
		int heroCount = map.getHeroes().size();
		Random randomPlayer = new Random();
		setCurrentHeroIndex(randomPlayer.nextInt(heroCount));

		          System.out.println("huhustartGame");
		while (true) {
			// player´s turn
			if (currentHero.isPlayerControlled()) {
				playerTurn();
			}

			// ki´s turn
			else {
				kiTurn();
			}
			// to not exceed playerBase
			setCurrentHeroIndex((currentHeroIndex + 1) % heroCount);

		}

		// zug auslagern
		// randomPlayer.
		// TODO als pairprogramming
		// abilites einen flag geben. wird ein held betroffen, wird der flag seiner
		// ability überprüft.
		// führt entprechend abilies dann aus, wann die flag es zulässt
		// Fuer die Auswahl eines Feldes, wird beim bewegen der Maus der winkel zum
		// Mittelpunkt berechnet und der Tower entsprechend mitbewegt
		// beim klick wird eingerastet und angegriffen (eventuell verzoegerung mit
		// einarbeiten)
		// vllt doch erstmal ohne db? nur helden und safegame? wären inbegriffen.
		// hardcoden?
		// eventuell threadhandler schreiben
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

		for (Hero h : map.getHeroes()) {
			heroActionList = new ArrayList<>(standardActions);
			for (Ability heroAbility : h.getAbilities()) {
				if (heroAbility.getAbilityType() != AbilityType.PASSIVE) {
					heroActionList.add(heroAbility);
				}
			}
			heroActionsLists.add(heroActionList);

			if (h.isPlayerControlled()) {
				gamePanel.getGameSidePanel().getPanelPlayerHero().setActionArrayList(heroActionList);
			}
		}
	}

	private void showGame() {

		mainFrame.setContentPane(gamePanel);
		mainFrame.pack();
	}

	private void playerTurn() {

	}

	private void kiTurn() {
            System.out.println("huhukiturn");
		currentHero.setCurrentActionPoints(currentHero.getMaxActionPoints());

		while (currentHero.getCurrentActionPoints() != 0) {

			for (Action a : heroActionsLists.get(currentHeroIndex)) {
				a.updateEnabled(this);
			}

			Action currentAction = currentHero.getKiLogic().chooseAction(heroActionsLists.get(currentHeroIndex), this);
			System.out.println(currentAction);
			currentAction.useAction(this);
			decreaseCurrentActionPointsBy(currentAction.getActionPointsRequired());

			// aktionsliste an ki übergeben
			// auswahl kütt zurück
			// aktion ausführen
			// AP verringern sich entsprechend
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

	public Map getMap() {
		return map;
	}

	// -------------------------SETTER-------------------------//
	/**
	 * Setzt den Helden, der gerade am Zug ist. Updated auch automatisch den currentHeroIndex.
	 */
	public void setCurrentHero(Hero currentHero) {
		this.currentHero = currentHero;
		this.currentHeroIndex = map.getHeroes().indexOf(currentHero);
	}

	/**
	 * Setzt den Index des Helden, der gerade am Zug ist. Updated auch automatisch den currentHero.
	 * @param currentHeroIndex
	 */
	public void setCurrentHeroIndex(int currentHeroIndex) {
		this.currentHeroIndex = currentHeroIndex;
		this.currentHero = map.getHeroes().get(currentHeroIndex);
	}

	public void reduceCurrentActionPoints() {
		if (map.getHeroes().get(currentHeroIndex).getCurrentActionPoints() >= 1) {
			map.getHeroes().get(currentHeroIndex)
					.setCurrentActionPoints(map.getHeroes().get(currentHeroIndex).getCurrentActionPoints() - 1);
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
			// TODO: Aktionen deaktivieren (oder besser generell bei der
			// Generation
			// der Aktionsliste den gameState abfragen und sie hier neu
			// generieren), Zieloverlay einblenden,
			// MouseListener aktivieren
			
			break;

		case CHOOSING:
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
}
