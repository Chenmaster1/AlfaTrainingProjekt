package KiLogics;

import Actions.Action;
import Actions.ActionAttack;
import Actions.ActionHide;
import Actions.ActionWorkOffDelay;
import GameLogic.SingleplayerGame;
import Heroes.Hero;
import java.util.ArrayList;


/**
 * Dahlia KiLogic 
 * Dahlia automaticaly hides once she is hit(passive ability). 
 * While Dahlia has at least 2 HP left, she´s a bit aggressive
 *
 * @author Yovo
 */
public class KiLogicDahlia extends KiLogic
{

    @Override
    public Action chooseAction(ArrayList<Action> actions, Hero hero, SingleplayerGame singleplayerGame)
    {
        Action returnAction = null, attackSmth = null, removeDelay = null, seekHideout = null;

        // number of visible Heroes
        int numHeroesVisible = 0;
        for (Hero h : singleplayerGame.getMap().getHeroes())
        {
            if (h.isVisible())
            {
                numHeroesVisible++;
            }
        }

        // assigning actions Dahlias ability hideWhenHit is passive
        for (Action a : actions)
        {
            if (a instanceof ActionAttack)
            {
                attackSmth = a;
            }
            else if (a instanceof ActionHide)
            {
                seekHideout = a;
            }
            else if (a instanceof ActionWorkOffDelay)
            {
                removeDelay = a;
            }
        }

        // if no hero or enemy is visible - attack hideouts far away
        if (numHeroesVisible == 0)
        {
            returnAction = attackSmth;
        }

        // if only enemys are visible - attack enemy  
        if (numHeroesVisible != 0 && !hero.isVisible())
        {
            returnAction = attackSmth;
        }

        // 2+HP Dahlia - if Dahlia is visible and has at least 2 hitpoints
        if (numHeroesVisible != 0 && hero.isVisible() && hero.getCurrentHitPoints() >= 2)
        {
            returnAction = attackSmth;
        }

        // 1HP Dahlia - if Dahlia is visible and has 1 hitpoints
        if (numHeroesVisible != 0 && hero.isVisible() && hero.getCurrentHitPoints() == 1)
        {
            if (hero.getDelayTokens() > 0 && singleplayerGame.getCurrentActionPoints() > 1)
            {
                returnAction = removeDelay;
            }
            if (hero.getDelayTokens() == 0 && singleplayerGame.getCurrentActionPoints() > 1)
            {
                returnAction = seekHideout;
            }

            if (hero.getDelayTokens() == 0 && singleplayerGame.getCurrentActionPoints() == 1)
            {
                returnAction = attackSmth;
            }

        }

        return returnAction;
    }

    //-----------------------------------

}

