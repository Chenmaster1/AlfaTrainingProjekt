package Heroes;

import Abilities.AbilityDahliaWhenHitGetNewHideout;
import KiLogics.KiLogicDahlia;


/**
 * Dahlia belongs to the people of Dryaden. This Hero is a forest creature
 *
 * @author Yovo
 *
 */
public class HeroDahlia extends Hero {
    //-----------------------------------

    public HeroDahlia() {
        super("Dahlia", "DahliaDescription", "DahliaArtwork",
                4, 3, 0.3,
                new KiLogicDahlia(), "Hero_Card/Avatar_Dahlia.jpg");

        abilities.add(new AbilityDahliaWhenHitGetNewHideout(0));
    }
    //-----------------------------------

}
