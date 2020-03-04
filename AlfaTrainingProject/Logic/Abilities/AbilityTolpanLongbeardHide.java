package Abilities;

import Actions.Action;
import Actions.ActionHide;
import GameLogic.SingleplayerGame;

/**
 * 
 * @author Kevin
 */
public class AbilityTolpanLongbeardHide extends Ability{

	public AbilityTolpanLongbeardHide(int actionPointRequired) {
		super(actionPointRequired, AbilityType.REACTION);
	}

	@Override
	public void useAction(SingleplayerGame singleplayerGame) {
		for(Action action : singleplayerGame.getActions()) {
			if(action instanceof ActionHide)
				action.useAction(singleplayerGame);
		}
	}

}
