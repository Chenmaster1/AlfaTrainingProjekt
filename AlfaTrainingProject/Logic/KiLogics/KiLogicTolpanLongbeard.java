package KiLogics;

import java.util.ArrayList;
import java.util.Random;

import Actions.*;
import GameLogic.SingleplayerGame;
import Heroes.Hero;
import Hideouts.Hideout;

/**
 * Dies ist die KiLogic fuer den Helden TolpanLongbeard
 * @author Kevin
 */
public class KiLogicTolpanLongbeard extends KiLogic {

	@Override
	public Action chooseAction(ArrayList<Action> actions, Hero hero, SingleplayerGame singleplayerGame) {
		
		Action resultAction = null;
		
		//es wird solange eine Action ausgefuehrt, wie Aktionspunkte uebrig sind
		//Tolpan hat keine Faehigkeit die während seines Zuges eingesetzt wird
		//seine Prioritaet liegt beim verzoegerung abbauen, dann verstecken, (einmal die Faehigkeit anwenden) und dann angreifen
		while(singleplayerGame.getCurrentActionPoints() > 0) {
			if(hero.getDelayTokens() > 0) {
				for(Action action : actions) {
					if(action instanceof ActionWorkOffDelay) {
						resultAction = action;
					}
				}	 
			}else if(hero.isVisible()) {
				for(Action action : actions) {
				  	//if(action instanceof ActionHide)
					resultAction = action;
				}
			//Hier eventuell ability einfuegen (zur einfachheit nur einmal ausgeführt, also mit bool arbeiten?)
			}else {
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
