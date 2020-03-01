package GameLogic;

import java.util.ArrayList;

import Actions.Action;
import Actions.ActionWorkOffDelay;
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
		
		//abilites einen flag geben. wird ein held betroffen, wird der flag der ability überprüft. 
		//führt entprechend abilies dann aus, wann die flag es zulässt
		
	}
	
	//-------------------------GETTER-------------------------//
	public HideDice getHideDice() {
		return hideDice;
	}
	
	public AttackDice getAttackDice() {
		return attackDice;
	}
	
	public Hero getCurrentHero() {
		return currentHero;
	}
	
	public int getCurrentActionPoints() {
		return currentActionPoints;
	}
	//-------------------------SETTER-------------------------//
	
	public void reduceCurrentActionPoints() {
		if(currentActionPoints >= 1)
			currentActionPoints -= 1;
	}
	
	public void setCurrentActionPointsToZero() {
		if(currentActionPoints >= 1) 
			currentActionPoints = 0;				
	}
	
	public void reduceDelayTokens() {
		if(currentHero.getDelayTokens() >= 1) 
			currentHero.setDelayTokens(currentHero.getDelayTokens() - 1);			
	}

}
