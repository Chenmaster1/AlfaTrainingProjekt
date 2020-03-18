package Abilities;

import Actions.Action;
import Actions.ActionHide;
import GameLogic.SingleplayerGame;
import MenuGUI.MyFrame;
import enums.AbilityType;

/**
 *
 * @author Kevin
 */
public class AbilityTolpanLongbeardHide extends Ability {

    public AbilityTolpanLongbeardHide(int actionPointRequired) {
        super(actionPointRequired, AbilityType.REACTION,
                MyFrame.bundle.getString("abilityTolpanLongbeardHide_name"),
                MyFrame.bundle.getString("abilityTolpanLongbeardHide_description"));
    }

    @Override
    public void useAction(SingleplayerGame singleplayerGame) {
    	//Fähigkeit ist passiv
        //happening to PanelLogHeroAction 
        singleplayerGame.getGamePanel().getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(MyFrame.bundle.getString("heroAbilityTolpan"));
    }


    @Override
    public void updateEnabled(SingleplayerGame singlePlayerGame)
    {
        //Tolpan hat nur eine passive faehigkeit, deswegen muss diese Methode nicht implementiert werden
    }

}
