package Abilities;

import GameLogic.SingleplayerGame;
import MenuGUI.MyFrame;
import enums.AbilityType;

/**
 *
 */
public class AbilityWorokAdditionalAction extends Ability{

    public AbilityWorokAdditionalAction(int actionPointRequired) {
        super(actionPointRequired, AbilityType.PASSIVE, 
                MyFrame.bundle.getString("abilityWorokAdditionalAction_name"), 
                MyFrame.bundle.getString("abilityWorokAdditionalAction_description"));
    }

    
    
    @Override
    public void useAction(SingleplayerGame singleplayerGame) {
        // als Action kein Effekt, stattdessen implementiert in getMaxActionPoints()
    }

}
