package Heroes;

import java.util.ArrayList;

import Abilities.Ability;
import Abilities.AbilityTolpanLongbeardHide;
/**
 * 
 * @author Kevin
 *
 */
public class HeroTolpanLongbeard extends Hero {

	public HeroTolpanLongbeard(String name) {
		//der rest wird über die Datenbank im konstruktor von Hero gefuellt.
		super(name);
		abilities = new ArrayList<Ability>();
		abilities.add(new AbilityTolpanLongbeardHide(1));	
	}

}
