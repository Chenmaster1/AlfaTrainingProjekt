package Heroes;

import Abilities.Ability;
import Abilities.AbilityDahliaWhenHitGetNewHideout;
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
        abilities = new ArrayList<Ability>();
        abilities.add(new AbilityDahliaWhenHitGetNewHideout(1));
    }
    //-----------------------------------

}
