package Heroes;

import Abilities.Ability;
import Abilities.AbilityTolpanLongbeardHide;
import java.util.ArrayList;

/**
 *
 */
public class HeroWorok extends Hero{

    public HeroWorok(String name) {
		
		super(name);
                
                //no active Abilities
		abilities = new ArrayList<>();
                
                
	}
    
    
    @Override
    public int getMaxActionPoints() {
        // Returns 1 additional Action Point if Hero is visible when their turn begins
        int maxActionPoints = super.getMaxActionPoints();
        if(isVisible())
        {
            maxActionPoints++;
        }
        return maxActionPoints;
    }
}
