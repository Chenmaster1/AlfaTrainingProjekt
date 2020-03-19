package Abilities;

import Actions.ActionHide;
import GameLogic.SingleplayerGame;
import MenuGUI.MyFrame;
import enums.AbilityType;
import enums.HeroEventType;

/**
 * When Dahlia is hit, she seeks a new hideout (if available) since this is a
 * passive ability, no actionPoints are used or needed
 *
 * @author Yovo
 */
public class AbilityDahliaWhenHitGetNewHideout extends Ability {

	public AbilityDahliaWhenHitGetNewHideout(int actionPointRequired) {
		// comes from heroes.HeroDahlia - abilities.add(new
		// AbilityDahliaWhenHitGetNewHideout(0));
		super(actionPointRequired, AbilityType.REACTION,
				MyFrame.bundle.getString("abilityDahliaWhenHitGetNewHideout_name"),
				MyFrame.bundle.getString("abilityDahliaWhenHitGetNewHideout_description"));
               
	}

	@Override
	public void useAction(SingleplayerGame singleplayerGame) {
		
               //Dahlia autoHides when hit - or rolles a hidedice
               // !! ability is in HeroDahlia
                
	}

	@Override
	public void updateEnabled(SingleplayerGame singlePlayerGame) {
		// Keine aktive Fähigkeit, daher irrelevant
	}

}
