package KiLogics;

import java.util.ArrayList;
import Actions.*;
import GameLogic.SingleplayerGame;
import Heroes.Hero;

public class KiLogicTolpanLongbeard extends KiLogic {

	public KiLogicTolpanLongbeard() {
	}

	@Override
	public void chooseAction(ArrayList<Action> actions, Hero hero, SingleplayerGame singleplayerGame) {
		
		while(singleplayerGame.getCurrentActionPoints() > 0) {
			if(hero.isVisible()) {
				/*for(Action action : actions) {
				 * 	if(action instanceof ActionHide)
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
				 * TODO Angriff. Falls ein Held sichtbar ist, diesen angreifen
				 */
			}
			
			singleplayerGame.reduceCurrentActionPoints();
		}
		
		
	}

}
