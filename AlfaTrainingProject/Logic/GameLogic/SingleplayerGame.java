package GameLogic;

import java.util.ArrayList;

import Actions.Action;
import Dice.AttackDice;
import Dice.HideDice;
import GUI.GamePanel;
import Heroes.Hero;
import Maps.Map;

public class SingleplayerGame {

	private Map map;
	private int timer;
	private AttackDice attackDice;
	private HideDice hideDice;
	private ArrayList<Action> actions;
	private Hero currentHero;
	private int currentActionPoints;
	private GamePanel panel;
	
	public SingleplayerGame(GamePanel gamePanel, Map map) {
		this.panel = gamePanel;
		this.map = map;	
	}
	
	public void startGame() {
		//TODO als pairprogramming
	}
}
