package KiLogics;

import java.util.ArrayList;

import Actions.Action;
import GameLogic.SingleplayerGame;
import Heroes.Hero;

/**
 * Diese Klasse dient als Grundgeruest fuer jede implementierte KI.
 * Jeder Held besitzt aufgrund seiner einzigartigkeit einee eigene
 * @author Kevin
 */
public abstract class KiLogic {

	/**
	 * Diese Methode wird von jeder KI implementiert und darueber aufgerufen
	 * @param actions alle ausfuehrbaren Aktionen ohne Faehigkeiten
	 * @param hero der aktuelle Held
	 * @param singleplayerGame das aktuelle Spiel
	 */
	public abstract void chooseAction(ArrayList<Action> actions, Hero hero, SingleplayerGame singleplayerGame);
	
}
