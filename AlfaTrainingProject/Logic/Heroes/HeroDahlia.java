package Heroes;

import Abilities.Ability;
import Abilities.AbilityDahliaWhenHitGetNewHideout;
import KiLogics.KiLogicDahlia;

import java.util.ArrayList;


/**
 * Dahlia belongs to the people of Dryaden.
 * This Hero is a forest creature
 * 
 * @author Yovo
 *
 */
public class HeroDahlia extends Hero
{
    //setter hitpoints when hit 
      // action hide im abiletie ausführen
    //-----------------------------------

    public HeroDahlia(String name)
    {
        super(name);
        super.setMaxHitPoints(4);
        
        
        ki = new KiLogicDahlia();
        
        abilities = new ArrayList<Ability>();
        // 0 action points required since it is a passive ability
        abilities.add(new AbilityDahliaWhenHitGetNewHideout(0));  
        
    }
    //-----------------------------------

}
