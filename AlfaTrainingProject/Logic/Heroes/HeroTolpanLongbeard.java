package Heroes;

import java.util.ArrayList;

import Abilities.Ability;
import Abilities.AbilityTolpanLongbeardHide;
import KiLogics.KiLogicDahlia;
import KiLogics.KiLogicTolpanLongbeard;
import resourceLoaders.ImageName;

/**
 *
 * @author Kevin
 *
 */
public class HeroTolpanLongbeard extends Hero {

    public HeroTolpanLongbeard() {
        super("Tolpan Longbeard", "TolpanLongbeardDescription", "TolpanLongbeardArtwork", 
                3, 3, 0.3, 
                new KiLogicTolpanLongbeard(), 
                ImageName.AVATAR_TOLPAN,
                ImageName.AVATAR_TOLPAN_DEACTIVATED,
                ImageName.MAPICON_TOLPAN);

        abilities.add(new AbilityTolpanLongbeardHide(1));
    }

}
