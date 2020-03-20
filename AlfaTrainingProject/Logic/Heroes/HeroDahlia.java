package Heroes;

import Abilities.AbilityDahliaWhenHitGetNewHideout;
import KiLogics.KiLogicDahlia;
import MenuGUI.MyFrame;
import enums.HeroEventType;
import resourceLoaders.ImageName;


/**
 * Dahlia belongs to the people of Dryaden. This Hero is a forest creature
 *
 * when visible and hit, she auto-retreats to a new hideout (if possible)
 * @author Yovo
 *
 */
public class HeroDahlia extends Hero
{

    
    public HeroDahlia()
    {
        super("Dahlia", "DahliaDescription", "DahliaArtwork",  "Dahlia_gets_hitted.mp3", "Dahlia_dies.mp3", 
                4, 3, 0.3,
                new KiLogicDahlia(),
                ImageName.AVATAR_DAHLIA,
                ImageName.AVATAR_DAHLIA_DEACTIVATED,
                ImageName.MAPICON_DAHLIA,
                ImageName.GRAVESTONE_DAHLIA);

        abilities.add(new AbilityDahliaWhenHitGetNewHideout(0));
    }
   
    @Override
    public void setCurrentHitPoints(int currentHitPoints)
    {

        super.setCurrentHitPoints(currentHitPoints);

        //if currentHitPoints are unequal to initializied 4 Hitpoints
        //TODO what if Dahlia looses hitPoints due to other sources? 
        if (currentHitPoints < getMaxHitPoints() && currentHitPoints > 0)
        {
            notifyAllHeroEventListeners(this, HeroEventType.HIDE_AUTOMATIC);
        }

    }


}

