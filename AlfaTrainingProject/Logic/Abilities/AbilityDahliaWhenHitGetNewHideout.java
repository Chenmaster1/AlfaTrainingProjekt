package Abilities;

import Actions.ActionHide;
import GameLogic.SingleplayerGame;
import MenuGUI.MyFrame;
import enums.AbilityType;

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
		
		//keine aktive Aktion, die w‰hrend des Zuges w‰hlbar ist, daher leer.
		// Stattdessen das SingleplayerGame als HeroListener anmelden und dort in der
		// requestedHeroEvent Methode Anfragen je nach HeroEventType behandeln.
//wirklich currenthero? das ist doch der der schieﬂt
	//	singleplayerGame.heroEventRequest(singleplayerGame.getCurrentHero(), HeroEventType.HIDE_ROLL);
	//	          System.out.println("dahlia ability sagt use action");
		// once Dahlia is hit - freeHide
//        ActionHide freeHide = new ActionHide(0);
//        freeHide.useAction(singleplayerGame);
	}

	@Override
	public void updateEnabled(SingleplayerGame singlePlayerGame) {
		// Keine aktive F‰higkeit, daher irrelevant
	}

}
