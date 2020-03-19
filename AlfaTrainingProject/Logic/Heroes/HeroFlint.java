package Heroes;

import Abilities.AbilityFlintAttackTwice;
import Abilities.AbilityTolpanLongbeardHide;
import KiLogics.KiLogic;
import KiLogics.KiLogicFlint;
import resourceLoaders.ImageName;

public class HeroFlint extends Hero {

    public HeroFlint() {
        super("Flint", "FlintDescription", "FlintArtwork",  "Flint_gets_hitted.mp3", "Flint_dies.mp3", 
                3, 3, 1.5,
                new KiLogicFlint(),
                ImageName.AVATAR_FLINT,
                ImageName.AVATAR_FLINT_DEACTIVATED,
                ImageName.MAPICON_FLINT,
                ImageName.GRAVESTONE_FLINT);

        abilities.add(new AbilityFlintAttackTwice(1));
    }

}
