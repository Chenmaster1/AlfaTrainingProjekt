package KiLogics;

import java.util.ArrayList;
import java.util.Random;

import Actions.*;
import GameLogic.SingleplayerGame;
import Heroes.Hero;
import Hideouts.Hideout;

public class KiLogicTolpanLongbeard extends KiLogic {

	public KiLogicTolpanLongbeard() {
	}

	@Override
	public void chooseAction(ArrayList<Action> actions, Hero hero, SingleplayerGame singleplayerGame) {
		
		//es wird solange eine Action ausgefuehrt, wie Aktionspunkte uebrig sind
		//Tolpan hat keine Faehigkeit die während seines Zuges eingesetzt wird
		//seine Prioritaet liegt beim verstecken, dann verzoegerung abbauen und dann angreifen
		while(singleplayerGame.getCurrentActionPoints() > 0) {
			if(hero.isVisible()) {
				/*for(Action action : actions) {
				 * 	if(action instanceof ActionHide)
				 * 		action.useAction(singleplayerGame);
				 *}
				 */
			}else if(hero.getDelayTokens() > 0) {
				for(Action action : actions) {
					if(action instanceof ActionWorkOffDelay) {
						action.useAction(singleplayerGame);
					}
				}
			}else {
				/*
				 * TODO Angriff. Falls ein Held sichtbar ist, diesen angreifen. Ansonsten primaer Felder angreifen, 
				 * bei denen man selber nicht getroffen wernden kann 
				 */
				int ownPosition = -1;
				for(Hideout hideout : singleplayerGame.getMap().getHideouts()) {
					
					if(singleplayerGame.getMap().getHideoutHero().containsKey(hideout)) {
						
						if(singleplayerGame.getMap().getHideoutHero().get(hideout).equals(hero)) {
							ownPosition = hideout.getFieldNumber();
						}
					}
				}
				
				//es wird solange ein Hideout gewaehlt, bis ein passendes getroffen wird.
				//solange mehr als 6 felder aktiv sind, wird er selber nicht getroffen bei der auswahl 
				//und ein entsprechendes Feld wird gewaehlt
				//sind weniger aktiv wird nur geschaut ob das Feld aktiv ist
				while(true) {
					Random random = new Random();
					int attackField = random.nextInt(21);
					int activeFields = 0;

					if(singleplayerGame.getMap().getHideouts().get(attackField).isActive()) {
						
						for(Hideout hideout : singleplayerGame.getMap().getHideouts()) {
							if(hideout.isActive())
								activeFields++;
						}
						
						if(activeFields <= 7) {
							if(attackField < ownPosition + 2 || attackField > ownPosition - 2) {
								attack(attackField);
								break;
							}
						}else {
							attack(attackField);
							break;
						}
					}															
				}	
			}
			//reduce sollte bereits in der action, bzw ability aufgerufen werde, sofern die ability einen Zug ersetzt
			//singleplayerGame.reduceCurrentActionPoints();
		}
	}

	private void attack(int attackField) {
		//TODO attackaction ausfueheren
		/*
		 * for(Action action : singleplayerGame.getActions()){
		 * 	if(action instanceof ActionAttack)
		 * 		action.useAction(singleplayerGame);
		 * }
		 */
	}
}
