package Heroes;

import java.util.ArrayList;

import Abilities.Ability;
import Abilities.AbilityTolpanLongbeardHide;
import GameLogic.SingleplayerGame;;
/**
 * 
 * @author Kevin
 *
 */
public class HeroTolpanLongbeard extends Hero {

	public HeroTolpanLongbeard(String name) {
		super(name);
		abilities = new ArrayList<Ability>();
		abilities.add(new AbilityTolpanLongbeardHide(1));	
	}

}
