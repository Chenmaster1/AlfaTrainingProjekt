package KiLogics;

import Actions.Action;
import Actions.ActionAttack;
import Actions.ActionHide;
import Actions.ActionWorkOffDelay;
import GameLogic.SingleplayerGame;
import Heroes.Hero;
import java.util.ArrayList;


/**
 * Dahlia KiLogic Dahlia automaticaly hides once she is hit. While Dahlia has at
 * least 2 HP left, she´s a bit aggressive
 *
 * @author Yovo
 */
public class KiLogicDahlia extends KiLogic
{

    @Override
    public Action chooseAction(ArrayList<Action> actions, Hero hero, SingleplayerGame singleplayerGame)
    {
        Action returnAction = null, attackHideout = null, attackEnemy = null, removeDelay = null, seekHideout = null;

        // number of visible enemys
        int numHeroesVisible = 0;
    //TO-DO : check if enemys are visible
      
    
        // if no hero or enemy is visible - attack hideouts far away
        if(numHeroesVisible == 0 && singleplayerGame.getCurrentActionPoints() >0)
        {
            
    //TO-DO : get own hideout position and select attackHideout far away
                returnAction =  attackHideout;
   
        }
        
        
        // if only enemy is visible - attack enemy  
         if (numHeroesVisible !=0 && !hero.isVisible() && singleplayerGame.getCurrentActionPoints() >0)
        {
    //TO-DO : If more than 1 enemy - choose which enemy to attack
            returnAction = attackEnemy;
        }
        
     
         // 2+HP Dahlia - if Dahlia is visible and has at least 2 hitpoints
          if (numHeroesVisible !=0 && hero.isVisible() && singleplayerGame.getCurrentActionPoints() >0 && hero.getCurrentHitPoints()>=2)
          {
              if (numHeroesVisible ==1)
              {
                returnAction =  attackHideout;
              }
              if (numHeroesVisible >=2)
              {
    //TO-DO : If more than 1 enemy - choose which enemy to attack              
                returnAction =  attackEnemy;
              }
            
          }
          
          // 1HP Dahlia - if Dahlia is visible and has 1 hitpoints
          if (numHeroesVisible !=0 && hero.isVisible() && singleplayerGame.getCurrentActionPoints() >0 && hero.getCurrentHitPoints()==1)
          {
              if (hero.getDelayTokens()>0 && singleplayerGame.getCurrentActionPoints() >1)
              {
                returnAction =  removeDelay;
              }
              if (hero.getDelayTokens()>0 && singleplayerGame.getCurrentActionPoints() >1)
              {
                returnAction =  seekHideout;
              }
              
              if (hero.getDelayTokens()>0 && singleplayerGame.getCurrentActionPoints() ==1)
              {
    //TO-DO : If more than 1 enemy - choose which enemy to attack              
                returnAction =  attackEnemy;  
              }
            
          }
         
         

        return returnAction;
    }

    //-----------------------------------

}

