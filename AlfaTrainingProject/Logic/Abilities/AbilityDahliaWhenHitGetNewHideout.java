package Abilities;

import Actions.ActionHide;
import GameLogic.SingleplayerGame;


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
        super(actionPointRequired, AbilityType.REACTION);
    }


    @Override
    public void useAction(SingleplayerGame singleplayerGame)
    {
        // once Dahlia is hit - freeHide
        ActionHide freeHide = new ActionHide(0);
        freeHide.useAction(singleplayerGame);
    }
    
}
