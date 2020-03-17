package Heroes;

import Abilities.AbilityDahliaWhenHitGetNewHideout;
import KiLogics.KiLogicDahlia;
import resourceLoaders.ImageName;

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
                new KiLogicDahlia(),
                ImageName.AVATAR_DAHLIA,
                ImageName.AVATAR_DAHLIA_DEACTIVATED,
                ImageName.MAPICON_DAHLIA);

        abilities.add(new AbilityDahliaWhenHitGetNewHideout(0));
    }
    //-----------------------------------

    
}
