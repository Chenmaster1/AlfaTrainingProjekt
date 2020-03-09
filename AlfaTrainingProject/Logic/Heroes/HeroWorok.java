package Heroes;

import Abilities.Ability;
import Abilities.AbilityTolpanLongbeardHide;
import KiLogics.KiLogicWorok;

import java.util.ArrayList;

/**
 *
 */
public class HeroWorok extends Hero {

	public HeroWorok() {

		super("Worok", "WorokDescription", "WorokArtwork", 3, 3, 0.3, new KiLogicWorok(), "Hero_Card/Avatar_Worok.jpg");

		// Keine aktiven Abilities

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
