
package KiLogics;

import Actions.Action;
import Actions.ActionAttack;
import Actions.ActionWorkOffDelay;
import GameLogic.SingleplayerGame;
import Heroes.Hero;
import java.util.ArrayList;
    
public class KiLogicFlint extends KiLogic {
	@Override
	public Action chooseAction(ArrayList<Action> actions, Hero hero, SingleplayerGame singleplayerGame) {
			
		Action resultAction = null;
			
		//es wird solange eine Action ausgefuehrt, wie Aktionspunkte uebrig sind
		//Flint hat als Faehigkeit, seinen letzten Actionmove als Doppelwurf auszufuehren.
		//Seine Prioritaet liegt seinen letzten Zug als Angriff zu spielen, zuvor Delay abbauen, dann verstecken, (einmal die Faehigkeit anwenden) und dann angreifen
		while(singleplayerGame.getCurrentHero().getCurrentActionPoints() > 1) {
			if(hero.getDelayTokens() > 0 && singleplayerGame.getCurrentHero().getCurrentActionPoints() > 1) {
				for(Action action : actions) {
					if(action instanceof ActionWorkOffDelay) {
						resultAction = action;
					}
				}	 
			}else if(hero.isVisible() && hero.getDelayTokens() == 0) {
				for(Action action : actions) {
					//if(action instanceof ActionHide)
					resultAction = action;
				}
				//Hier eventuell ability einfuegen
				}else{
				/*
				 * TODO Angriff. Falls ein Held sichtbar ist, diesen angreifen. Ansonsten primaer Felder angreifen, 
				 * bei denen man selber nicht getroffen wernden kann 
				 */
				
				for(Action action : actions) {
					if(action instanceof ActionAttack) {
						resultAction = action;
					}
				}
				
			}
					/*
			 * Flints letzte Handlung ist immer ein Angriff wegen seiner Spezialfähigkeit. Falls ein Held sichtbar ist, diesen angreifen. Ansonsten primaer Felder angreifen, 
			 * bei denen man selber nicht getroffen wernden kann 
			 */
				
			for(Action action : actions) {
				if(action instanceof ActionAttack) {
					resultAction = action;
					//doubleThrow = true;
				}
			}
		}
		return resultAction;
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