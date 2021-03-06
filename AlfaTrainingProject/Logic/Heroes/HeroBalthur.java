package Heroes;

import KiLogics.KiLogic;
import KiLogics.KiLogicBalthur;
import resourceLoaders.ImageName;

public class HeroBalthur extends Hero{

    
	public HeroBalthur() {
		super("Balthur", "BalthurDescription", "BalthurArtwork", "Balthur_gets_hitted.mp3", "Balthur_dies.mp3", 
                        5, 3, (double) 2.5, 
                        new KiLogicBalthur(), 
                        ImageName.AVATAR_BALTHUR,
                        ImageName.AVATAR_BALTHUR_DEACTIVATED,
                        ImageName.MAPICON_BALTHUR,
                        ImageName.GRAVESTONE_BALTHUR);
		
	}


}
