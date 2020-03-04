package Abilities;

import GameLogic.SingleplayerGame;

/**
 * 
 * @author Bastian
 */
public class AbilityDahliaWhenHitGetNewHideout extends Ability
{

    public AbilityDahliaWhenHitGetNewHideout(int getNewHideout)
    {
        super(getNewHideout, AbilityType.REACTION);
    }

    @Override
    public void useAction(SingleplayerGame singleplayerGame)
    {
        //if Dahlia is visible and hit, she seeks a new hideout
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //-----------------------------------

}
