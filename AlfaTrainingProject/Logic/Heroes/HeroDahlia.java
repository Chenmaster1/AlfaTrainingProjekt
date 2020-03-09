package Heroes;

import Abilities.Ability;
import Abilities.AbilityDahliaWhenHitGetNewHideout;
import KiLogics.KiLogicDahlia;

import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * Dahlia belongs to the people of Dryaden. This Hero is a forest creature
 *
 * @author Yovo
 *
 */
public class HeroDahlia extends Hero {
    //-----------------------------------

    public HeroDahlia() {
        super("Dahlia", "DahliaDescription", "DahliaArtwork", 0, 0, 0, new KiLogicDahlia(), "Hero_Card/Avatar_Dahlia.jpg");

        abilities = new ArrayList<Ability>();
        abilities.add(new AbilityDahliaWhenHitGetNewHideout(1));
    }
    //-----------------------------------

}
