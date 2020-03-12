package Heroes;

import java.util.ArrayList;

import Abilities.Ability;
import Abilities.AbilityTolpanLongbeardHide;
import KiLogics.KiLogicDahlia;
import KiLogics.KiLogicTolpanLongbeard;

/**
 *
 * @author Kevin
 *
 */
public class HeroTolpanLongbeard extends Hero {

    public HeroTolpanLongbeard() {
        super("Tolpan Longbeard", "TolpanLongbeardDescription", "TolpanLongbeardArtwork", 
                3, 3, 0.3, 
                new KiLogicTolpanLongbeard(), "Hero_Card/Avatar_Talpan.jpg", "Gameboard/Spiel_Avatar_Talpan.png");

        abilities.add(new AbilityTolpanLongbeardHide(1));
    }

}
