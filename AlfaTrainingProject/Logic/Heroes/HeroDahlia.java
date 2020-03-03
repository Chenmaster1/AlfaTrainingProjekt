package Heroes;

import Abilities.Ability;
import Abilities.AbilityDahliaWhenHitGetNewHideout;
import KiLogics.KiLogicDahlia;

import java.util.ArrayList;


/**
 * 
 * @author Bastian
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
