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
	public Action chooseAction(ArrayList<Action> actions, SingleplayerGame singleplayerGame) {
		//New AI
		Action resultAction = null;
		ArrayList<Action> enabledActions = new ArrayList<Action>();
		Hero currentHero = singleplayerGame.getCurrentHero();
		
		//nutzbare Actions bekommen
		for(Action action : actions) {
			if(action.isEnabled())
				enabledActions.add(action);
		}
		
		//falls keine verzoegerungsmarken, aber sichtbar, erst verstecken
		if(currentHero.isVisible() && currentHero.getDelayTokens() == 0) {
			for(Action action : enabledActions)
				if(action instanceof ActionHide)
					return action;
		}
		//falls verzoegerungsmarken vorhanden, erst abbauen falls aufgedeckt, sonst bei letztem aktionspunkt
		if(currentHero.getDelayTokens() > 0) {
			if(currentHero.isVisible()) {
				for(Action action : enabledActions)
					if(action instanceof ActionWorkOffDelay)
						return action;
			}else {
				if(currentHero.getCurrentActionPoints() > 1) {
					for(Action action : enabledActions)
						if(action instanceof ActionAttack)
							return action;
				}else {
					for(Action action : enabledActions)
						if(action instanceof ActionWorkOffDelay)
							return action;
				}
			}
			
		}
		//falls actionpoints <= verzoegerungsmarken, aber actionpoints >1, 
		//dann zufaellig angreifen oder verzoegerung abbauen
//		if(currentHero.getCurrentActionPoints() > 1 
//				&& currentHero.getCurrentActionPoints() <= currentHero.getDelayTokens()) {
//			int random = (int) (Math.random() * 2);
//			for(Action action : enabledActions) {
//				
//				if(random == 0) {
//					if(action instanceof ActionAttack)
//						return action;
//				}else {
//					if(action instanceof ActionWorkOffDelay) 
//						return action;
//				}
//			}				
//		}
		//falls verzoegerungsmarken und actionspoints = 1, dann verzoegerung abbauen
//		if(currentHero.getDelayTokens() > 0 && currentHero.getCurrentActionPoints() == 1)
//			for(Action action : enabledActions)
//				if(action instanceof ActionWorkOffDelay)
//					return action;
		
		for(Action action : enabledActions)
			if(action instanceof ActionAttack)
				resultAction = action;
		
		//ansonsten angreifen

		return resultAction;
		
		
		//Old AI
//		Action resultAction = null;
//		
//		//es wird solange eine Action ausgefuehrt, wie Aktionspunkte uebrig sind
//		//Tolpan hat keine Faehigkeit die während seines Zuges eingesetzt wird
//		//seine Prioritaet liegt beim verzoegerung abbauen, dann verstecken, (einmal die Faehigkeit anwenden) und dann angreifen
//	
//		if(singleplayerGame.getCurrentHero().getDelayTokens() > 0 && singleplayerGame.getCurrentHero().getCurrentActionPoints() == 1) {
//			for(Action action : actions) {
//				if(action instanceof ActionWorkOffDelay) {
//					resultAction = (ActionWorkOffDelay) action;
//				}
//			}	 
//		}else if(singleplayerGame.getCurrentHero().isVisible() && singleplayerGame.getCurrentHero().getDelayTokens() == 0) {
//			for(Action action : actions) {
//			  	if(action instanceof ActionHide)
//			  		resultAction = (ActionHide) action;
//			}
//		//Hier eventuell ability einfuegen
//		}else{
//			
//			
//			for(Action action : actions) {
//				if(action instanceof ActionAttack) {
//					resultAction = (ActionAttack) action;
//				}
//			}
//			
//		}
//		return resultAction;
	}

	/**
	 * Gibt zurück, welches Feld die KI beim aktuellen Zustand des SinglePlayerGame
	 * mit einer AttackAction angreifen würde.
	 * 
	 * @param singleplayerGame DasSinglePlayerGame, für welches eine
	 * @return
	 */
	public int chooseAttackField(SingleplayerGame singleplayerGame) {

		ArrayList<Hideout> availableHideouts = new ArrayList<Hideout>();
		
		for(Hideout hideout : singleplayerGame.getGameData().getHideouts())
			if(hideout.isActive())
				availableHideouts.add(hideout);
		
		
		int ownPosition = -1;
		for (Hideout hideout : availableHideouts) {

			if (singleplayerGame.getGameData().getHideoutHero().containsKey(hideout)) {

				if (singleplayerGame.getGameData().getHideoutHero().get(hideout).equals(singleplayerGame.getCurrentHero())) {
					ownPosition = hideout.getFieldNumber();
				}
			}
		}

		// falls Ein held sichtbar ist, der nicht er selber ist, wird dieser vorrangig
		// angegriffen
		for (Hero hero : singleplayerGame.getGameData().getHeroes()) {
			if(!hero.isDead() && hero.isAttackable()) {
				if (hero.isVisible() && !(hero.equals(singleplayerGame.getCurrentHero()))) {
					HashMap<Hideout, Hero> hideoutHeroMap = singleplayerGame.getGameData().getHideoutHero();
					for (Hideout key : hideoutHeroMap.keySet()) {
						if (hideoutHeroMap.get(key).equals(hero)) {
							return key.getFieldNumber();
						}
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
			int attackField = random.nextInt(availableHideouts.size());
			if(!(attackField == singleplayerGame.getCurrentHeroIndex()))
				return availableHideouts.get(attackField).getFieldNumber();

			
		}
		// reduce sollte bereits in der action, bzw ability aufgerufen werde, sofern die
		// ability einen Zug ersetzt
		// singleplayerGame.reduceCurrentActionPoints();
	}
}
