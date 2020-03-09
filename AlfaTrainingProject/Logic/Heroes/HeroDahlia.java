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
    //-----------------------------------

    public HeroDahlia(String name)
    {
        super(name);
        
        ki = new KiLogicDahlia();
        
        abilities = new ArrayList<Ability>();
        abilities.add(new AbilityDahliaWhenHitGetNewHideout(1));
    }
    //-----------------------------------

}
