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
public class AbilityDahliaWhenHitGetNewHideout extends Ability
{

    public AbilityDahliaWhenHitGetNewHideout(int actionPointRequired)
    {
        // comes from heroes.HeroDahlia - abilities.add(new AbilityDahliaWhenHitGetNewHideout(0));
        super(actionPointRequired, AbilityType.REACTION, 
                MyFrame.bundle.getString("abilityDahliaWhenHitGetNewHideout_name"), 
                MyFrame.bundle.getString("abilityDahliaWhenHitGetNewHideout_description"));
    }


    @Override
    public void useAction(SingleplayerGame singleplayerGame)
    {
        // once Dahlia is hit - freeHide
        ActionHide freeHide = new ActionHide(0);
        freeHide.useAction(singleplayerGame);
    }


    @Override
    public void updateEnabled(SingleplayerGame singlePlayerGame)
    {
        //Tolpan hat nur eine passive faehigkeit, deswegen muss diese Methode nicht implementiert werden
    }
    
}
