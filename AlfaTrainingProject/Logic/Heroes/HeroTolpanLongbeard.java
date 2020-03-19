package Heroes;

import java.util.ArrayList;

import Abilities.Ability;
import Abilities.AbilityTolpanLongbeardHide;
import KiLogics.KiLogicDahlia;
import KiLogics.KiLogicTolpanLongbeard;
import enums.HeroEventType;
import resourceLoaders.ImageName;

/**
 *
 * @author Kevin
 *
 */
public class HeroTolpanLongbeard extends Hero {

    
	public HeroTolpanLongbeard() {
		super("Tolpan Longbeard", "TolpanLongbeardDescription", "TolpanLongbeardArtwork",  "Tolpan_gets_hitted.mp3", "Tolpan_dies.mp3", 
                        3, 3, 0.3,
				new KiLogicTolpanLongbeard(), 
				ImageName.AVATAR_TOLPAN, 
				ImageName.AVATAR_TOLPAN_DEACTIVATED,
				ImageName.MAPICON_TOLPAN,
                ImageName.GRAVESTONE_TOLPAN);

		abilities.add(new AbilityTolpanLongbeardHide(0));
	}

	public void setVisible(boolean isVisible) {
		boolean previouslyVisible = isVisible();
		super.setVisible(isVisible);

		//Buddeln-Fähigkeit
		if (!previouslyVisible && isVisible) {
			notifyAllHeroEventListeners(this, HeroEventType.HIDE_ROLL);
		}

	}
}
