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
				
				while(true) {
					Random random = new Random();
					int attackField = random.nextInt(21);
					int activeFields = 0;
					
					for(Hideout hideout : singleplayerGame.getMap().getHideouts()) {
						if(hideout.isActive())
							activeFields++;
					}
					
					if(activeFields <= 7) {
						if(attackField < ownPosition + 2 || attackField > ownPosition - 2) {
							if(singleplayerGame.getMap().getHideouts().get(attackField).isActive()) {
								attack(attackField);
								break;
							}
							
						}
						continue;
					}else {
						if(singleplayerGame.getMap().getHideouts().get(attackField).isActive()) {
							attack(attackField);
							break;
						}
					}
					
				}
				
				
			}
			
			singleplayerGame.reduceCurrentActionPoints();
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
