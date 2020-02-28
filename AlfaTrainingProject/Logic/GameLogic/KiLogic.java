package GameLogic;

import java.util.ArrayList;

import Actions.Action;
import Heroes.Hero;

public abstract class KiLogic {

	public abstract void chooseAction(ArrayList<Action> actions, Hero hero, SingleplayerGame singleplayerGame);
	
}
