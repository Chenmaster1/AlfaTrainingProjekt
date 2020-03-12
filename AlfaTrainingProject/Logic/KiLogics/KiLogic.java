package KiLogics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import Actions.Action;
import Actions.ActionAttack;
import Actions.ActionHide;
import Actions.ActionWorkOffDelay;
import GameLogic.SingleplayerGame;
import Heroes.Hero;
import Hideouts.Hideout;

/**
 * Diese Klasse dient als Grundgeruest fuer jede implementierte KI. Jeder Held
 * besitzt aufgrund seiner einzigartigkeit eine eigene. 
 * 
 * 
 * @author Kevin
 */
public abstract class KiLogic {

	/**
	 * Gibt die Action zurück, die die KI beim aktuellen Zustand des
	 * SinglePlayerGame ausführen würde. Implementierung sollte beachten:
	 * 
	 * 1. Nur Objekte zurückgeben, die in der actions-Liste referenziert sind (das
	 * SinglePlayerGame generiert diese Liste)
	 * 
	 * 2. Keine direkten Veränderungen an den übergebenen Objekten vornehmen,
	 * sondern nur relevante Daten abfragen (über getter). Insbesondere NICHT die
	 * useAction() der Action aufrufen, sondern nur die Referenz zurückgeben.
	 * 
	 * @param actions          ArrayList aller ausfuehrbaren Aktionen
	 * @param hero             der aktuelle Held
	 * @param singleplayerGame das aktuelle Spiel
	 * @return die gewählte Aktion
	 */
	public Action chooseAction(ArrayList<Action> actions, Hero hero, SingleplayerGame singleplayerGame) {
		Action resultAction = null;
		
		//es wird solange eine Action ausgefuehrt, wie Aktionspunkte uebrig sind
		//Tolpan hat keine Faehigkeit die während seines Zuges eingesetzt wird
		//seine Prioritaet liegt beim verzoegerung abbauen, dann verstecken, (einmal die Faehigkeit anwenden) und dann angreifen
	
		if(hero.getDelayTokens() > 0 && singleplayerGame.getCurrentHero().getCurrentActionPoints() == 1) {
			for(Action action : actions) {
				if(action instanceof ActionWorkOffDelay) {
					resultAction = (ActionWorkOffDelay) action;
				}
			}	 
		}else if(hero.isVisible() && hero.getDelayTokens() == 0) {
			for(Action action : actions) {
			  	if(action instanceof ActionHide)
			  		resultAction = (ActionHide) action;
			}
		//Hier eventuell ability einfuegen
		}else{
			/*
			 * TODO Angriff. Falls ein Held sichtbar ist, diesen angreifen. Ansonsten primaer Felder angreifen, 
			 * bei denen man selber nicht getroffen wernden kann 
			 */
			
			for(Action action : actions) {
				if(action instanceof ActionAttack) {
					resultAction = (ActionAttack) action;
				}
			}
			
		}
		return resultAction;
	}

	/**
	 * Gibt zurück, welches Feld die KI beim aktuellen Zustand des SinglePlayerGame
	 * mit einer AttackAction angreifen würde.
	 * 
	 * @param singleplayerGame DasSinglePlayerGame, für welches eine
	 * @return
	 */
	public int chooseAttackField(SingleplayerGame singleplayerGame) {

		int ownPosition = -1;
		for (Hideout hideout : singleplayerGame.getMap().getHideouts()) {

			if (singleplayerGame.getMap().getHideoutHero().containsKey(hideout)) {

				if (singleplayerGame.getMap().getHideoutHero().get(hideout).equals(singleplayerGame.getCurrentHero())) {
					ownPosition = hideout.getFieldNumber();
				}
			}
		}

		// falls Ein held sichtbar ist, der nicht er selber ist, wird dieser vorrangig
		// angegriffen
		for (Hero hero : singleplayerGame.getMap().getHeroes()) {
			if (hero.isVisible() && !(hero.equals(singleplayerGame.getCurrentHero()))) {
				HashMap<Hideout, Hero> hideoutHeroMap = singleplayerGame.getMap().getHideoutHero();
				for (Hideout key : hideoutHeroMap.keySet()) {
					if (hideoutHeroMap.get(key).equals(hero)) {
						return key.getFieldNumber();
					}
				}
			}
		}

		// es wird solange ein Hideout gewaehlt, bis ein passendes getroffen wird.
		// solange mehr als 6 felder aktiv sind, wird er selber nicht getroffen bei der
		// auswahl
		// und ein entsprechendes Feld wird gewaehlt
		// sind weniger aktiv wird nur geschaut ob das Feld aktiv ist

		while (true) {
			Random random = new Random();
			int attackField = random.nextInt(21);
			int activeFields = 0;

			if (singleplayerGame.getMap().getHideouts().get(attackField).isActive()) {

				for (Hideout hideout : singleplayerGame.getMap().getHideouts()) {
					if (hideout.isActive())
						activeFields++;
				}

				if (activeFields >= 7) {
					int hideoutCount = singleplayerGame.getMap().getHideouts().size();
					if (attackField < (ownPosition + 2) % hideoutCount
							|| attackField > (ownPosition - 2 + hideoutCount) % hideoutCount) {
						return attackField;
					}
				} else {
					return attackField;
				}
			}
		}
		// reduce sollte bereits in der action, bzw ability aufgerufen werde, sofern die
		// ability einen Zug ersetzt
		// singleplayerGame.reduceCurrentActionPoints();
	}
}
