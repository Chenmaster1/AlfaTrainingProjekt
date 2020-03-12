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

	//Tolpan benoetigt fuer das standard spiel keine eigene KI. Ohne @Override wird die Muttermethode genommen
//	@Override
//	public Action chooseAction(ArrayList<Action> actions, SingleplayerGame singleplayerGame) {
//		
//		Action resultAction = null;
//		
//		//es wird solange eine Action ausgefuehrt, wie Aktionspunkte uebrig sind
//		//Tolpan hat keine Faehigkeit die während seines Zuges eingesetzt wird
//		//seine Prioritaet liegt beim verzoegerung abbauen, dann verstecken, (einmal die Faehigkeit anwenden) und dann angreifen
//		
//			if(singleplayerGame.getCurrentHero().getDelayTokens() > 0 && singleplayerGame.getCurrentHero().getCurrentActionPoints() == 1) {
//				for(Action action : actions) {
//					if(action instanceof ActionWorkOffDelay) {
//						resultAction = (ActionWorkOffDelay) action;
//					}
//				}	 
//			}else if(singleplayerGame.getCurrentHero().isVisible() && singleplayerGame.getCurrentHero().getDelayTokens() == 0) {
//				for(Action action : actions) {
//				  	if(action instanceof ActionHide)
//				  		resultAction = (ActionHide) action;
//				}
//			//Hier eventuell ability einfuegen
//			}else{
//				/*
//				 * TODO Angriff. Falls ein Held sichtbar ist, diesen angreifen. Ansonsten primaer Felder angreifen, 
//				 * bei denen man selber nicht getroffen wernden kann 
//				 */
//				
//				for(Action action : actions) {
//					if(action instanceof ActionAttack) {
//						resultAction = (ActionAttack) action;
//					}
//				}
//				
//			}
//
//		return resultAction;
//	}
}
