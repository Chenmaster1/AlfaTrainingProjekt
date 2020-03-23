package KiLogics;

import java.util.ArrayList;

import Actions.Action;
import Actions.ActionAttack;
import Actions.ActionHide;
import Actions.ActionWorkOffDelay;
import GameLogic.SingleplayerGame;
import Heroes.Hero;

public class KiLogicWorok extends KiLogic {
	// Woroks sichtbarkeit wird ueber setVisible geregelt und damit auch seine maximalen actionpoints in der runde
	//damit kann worok die standard KI benuten. Ohne @Override wird die Muttermethode benutzt
	
//	@Override
//	public Action chooseAction(ArrayList<Action> actions, SingleplayerGame singleplayerGame) {
//		Action attackAction = null, delayAction = null, hideAction = null, chosenAction = null;
//
//		// Aktionsobjekte bereithalten.
//		// an
//		for (Action a : actions) {
//			if (a instanceof ActionAttack) {
//				attackAction = a;
//			} else if (a instanceof ActionHide) {
//				hideAction = a;
//			} else if (a instanceof ActionWorkOffDelay) {
//				delayAction = a;
//			}
//		}
//
//		// Anzahl sichtbarer Helden bestimmen
//		int numHeroesVisible = 0;
//		for (Hero h : singleplayerGame.getMap().getHeroes()) {
//			if (h.isVisible()) {
//				numHeroesVisible++;
//			}
//		}
//
//		// Wenn Worok nicht sichtbar ist
//		if (!singleplayerGame.getCurrentHero().isVisible()) {
//			// Wenn Worok nur noch 1 AP hat und mindestens ein
//			// DelayToken, delay abbauen
//			if ((singleplayerGame.getCurrentHero().getCurrentActionPoints() == 1) && singleplayerGame.getCurrentHero().getDelayTokens() > 0) {
//				chosenAction = delayAction;
//			}
//			// Sonst angreifen
//			else {
//				chosenAction = attackAction;
//			}
//
//		}
//		// Wenn Worok sichtbar ist
//		else {
//			// Wenn Worok als einziger sichtbar ist, verstecken bzw. delay abbauen
//			if (numHeroesVisible == 1) {
//				if (singleplayerGame.getCurrentHero().getDelayTokens() > 0) {
//					chosenAction = delayAction;
//				} else {
//					chosenAction = hideAction;
//				}
//			}
//			// Wenn außer Worok noch andere sichtbar sind
//			else {
//				// Wenn es die letzte Aktion ist, verstecken bzw. delay abbauen
//				if (singleplayerGame.getCurrentHero().getCurrentActionPoints() == 1) {
//					if (singleplayerGame.getCurrentHero().getDelayTokens() > 0) {
//						chosenAction = delayAction;
//					} else {
//						chosenAction = hideAction;
//					}
//				}
//				// Wenn noch >1 AP übrig ist, angreifen
//				else {
//					chosenAction = attackAction;
//				}
//			}
//
//		}
//
//		return chosenAction;
//	}

}
