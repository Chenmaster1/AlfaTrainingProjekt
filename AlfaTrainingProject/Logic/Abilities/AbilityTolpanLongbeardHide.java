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
// TODO       for (Action action : singleplayerGame.getActions()) {
//            if (action instanceof ActionHide) {
//                action.useAction(singleplayerGame);
//            }
//        }
    }


    @Override
    public void updateEnabled(SingleplayerGame singlePlayerGame)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
