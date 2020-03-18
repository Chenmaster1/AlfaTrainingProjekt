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
        //happening to PanelLogHeroAction 
        singleplayerGame.getGamePanel().getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(MyFrame.bundle.getString("heroAbilityWorok"));
    }


    @Override
    public void updateEnabled(SingleplayerGame singlePlayerGame)
    {
        //Worok hat nur eine passive faehigkeit, deswegen muss diese Methode nicht implementiert werden
    }

}
