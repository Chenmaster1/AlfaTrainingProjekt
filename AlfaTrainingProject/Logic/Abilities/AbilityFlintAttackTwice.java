package Abilities;

import GameLogic.SingleplayerGame;
import MenuGUI.MyFrame;

public class AbilityFlintAttackTwice  extends Ability{

	public AbilityFlintAttackTwice(int actionPointRequired){
		super(actionPointRequired, AbilityType.TURN,  
                MyFrame.bundle.getString("abilityFlintAttackTwice_name"), 
                MyFrame.bundle.getString("abilityFlintAttackTwice_description"));
	}

	@Override
	public void useAction(SingleplayerGame singleplayerGame) {
		// TODO Magie hier. wird anstatt eines zuges ausgefuehrt, also zweimal angreifen
		
	}

}
