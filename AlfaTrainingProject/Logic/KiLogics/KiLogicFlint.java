
package KiLogics;

import Actions.Action;
import Actions.ActionAttack;
import Actions.ActionHide;
import Actions.ActionWorkOffDelay;
import GameLogic.SingleplayerGame;
import Heroes.Hero;
import java.util.ArrayList;

import Abilities.AbilityFlintAttackTwice;
    
public class KiLogicFlint extends KiLogic {
	
	//KI angepasst. Falls nur ein Actionpoint uebrig ist, wird auf jedenfall mit der ability angegriffen, 
	//selbst wenn verzoegerungsmarken aktiv sind
	@Override
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
		if(currentHero.isVisible() && currentHero.getDelayTokens() ==0) {
			for(Action action : enabledActions)
				if(action instanceof ActionHide)
					resultAction = action;
		}
		//falls actionpoints > verzoegerungsmarken, dann angreifen
		else if(currentHero.getCurrentActionPoints() > currentHero.getDelayTokens()) {
			for(Action action : enabledActions)
				if(currentHero.getCurrentActionPoints() == 1) {
					if(action instanceof AbilityFlintAttackTwice)
						return action;
				}else if(action instanceof ActionAttack)
					resultAction = action;
		}
		//falls actionpoints <= verzoegerungsmarken, aber actionpoints >1, 
		//dann zufaellig angreifen oder verzoegerung abbauen
		else if(currentHero.getCurrentActionPoints() > 1 
				&& currentHero.getCurrentActionPoints() <= currentHero.getDelayTokens()) {
			int random = (int) (Math.random() * 2);
			for(Action action : enabledActions) {
				
				if(random == 0) {
					if(action instanceof ActionAttack)
						resultAction = action;
				}else {
					if(action instanceof ActionWorkOffDelay) 
						resultAction = action;
				}
			}				
		}
		//
		else {
			for(Action action : enabledActions)
				if(action instanceof AbilityFlintAttackTwice)
					resultAction = action;
		}
		//ansonsten angreifen

		return resultAction;
			
		//Old AI
//		Action resultAction = null;
//			
//		//es wird solange eine Action ausgefuehrt, wie Aktionspunkte uebrig sind
//		//Flint hat als Faehigkeit, seinen letzten Actionmove als Doppelwurf auszufuehren.
//		//Seine Prioritaet liegt seinen letzten Zug als Angriff zu spielen, zuvor Delay abbauen, dann verstecken, (einmal die Faehigkeit anwenden) und dann angreifen
//		
//			if(singleplayerGame.getCurrentHero().getDelayTokens() > 0 && singleplayerGame.getCurrentHero().getCurrentActionPoints() > 1) {
//				for(Action action : actions) {
//					if(action instanceof ActionWorkOffDelay) {
//						resultAction = action;
//					}
//				}	 
//			}else if(singleplayerGame.getCurrentHero().isVisible() && singleplayerGame.getCurrentHero().getDelayTokens() == 0) {
//				for(Action action : actions) {
//					//if(action instanceof ActionHide)
//					resultAction = action;
//				}
//				//Hier eventuell ability einfuegen
//				}else{
//				
//				
//				for(Action action : actions) {
//					if(action instanceof ActionAttack) {
//						resultAction = action;
//					}
//				}
//				
//			}
//					/*
//			 * Flints letzte Handlung ist immer ein Angriff wegen seiner Spezialfähigkeit. Falls ein Held sichtbar ist, diesen angreifen. Ansonsten primaer Felder angreifen, 
//			 * bei denen man selber nicht getroffen wernden kann 
//			 */
//				
//			for(Action action : actions) {
//				if(action instanceof ActionAttack) {
//					resultAction = action;
//					//doubleThrow = true;
//				}
//			}
//		
//		return resultAction;
	} 
}