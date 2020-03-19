package Heroes;

import Abilities.Ability;
import Abilities.AbilityTolpanLongbeardHide;
import Abilities.AbilityWorokAdditionalAction;
import KiLogics.KiLogicWorok;

import java.util.ArrayList;
import resourceLoaders.ImageName;

/**
 *
 */
public class HeroWorok extends Hero {

    public HeroWorok() {

        super("Worok", "WorokDescription", "WorokArtwork",
                3, 3, 0.3,
                new KiLogicWorok(),
                ImageName.AVATAR_WOROK,
                ImageName.AVATAR_WOROK_DEACTIVATED,
                ImageName.MAPICON_WOROK,
                ImageName.GRAVESTONE_WOROK);

        //passive Fähigkeit, macht selbst nichts, Effekt implementiert in getMaxActionPoints()
        abilities.add(new AbilityWorokAdditionalAction(0));

    }

    @Override
    public int getMaxActionPoints() {
        // 1 zusätzlicher Action Point, solange Worok sichtbar ist
        int maxActionPoints = super.getMaxActionPoints();
        if (isVisible()) {
            maxActionPoints++;
        }
        return maxActionPoints;
    }
}
