package Actions;

import java.util.ArrayList;
import java.util.HashMap;

import GameLogic.SingleplayerGame;
import MenuGUI.MyFrame;
import Heroes.Hero;
import Hideouts.Hideout;
import Maps.Map;

public class ActionHide extends Action {

	public ActionHide(int actionPointRequired) {
		super(actionPointRequired, MyFrame.bundle.getString("hideRoll"));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useAction(SingleplayerGame singleplayerGame) {
		hideHero(singleplayerGame.getCurrentHero(), singleplayerGame);
	}
	
	/**
	 * falls sich nicht der aktuelle Held versteckt, wird diese Methode aufgerufen
	 * @param hero
	 * @param singleplayerGame
	 */
	public void useAction(Hero hero, SingleplayerGame singleplayerGame) {
		hideHero(hero, singleplayerGame);
	}

	private void hideHero(Hero hero, SingleplayerGame singleplayerGame) {
		ArrayList<Hideout> availableHideouts = new ArrayList<Hideout>();
		
		HashMap<Hideout, Hero> hideoutHeroMap = singleplayerGame.getMap().getHideoutHero();
		
		for(Hideout hideout : singleplayerGame.getMap().getHideouts()) {
			boolean used = false;
			for(Hideout usedHideout : hideoutHeroMap.keySet()) {
				if(hideout.getFieldNumber() == usedHideout.getFieldNumber()) {
					used = true;
					break;
				}
					
			}
			
			if((!used) && hideout.isActive()) {
				availableHideouts.add(hideout);
			}
		}
		
		int newHideout = (int) (Math.random() * availableHideouts.size());
		
		for(Hideout hideout : hideoutHeroMap.keySet()) {
			if(hideoutHeroMap.get(hideout).equals(hero)) {
				hideoutHeroMap.remove(hideout);
				hideoutHeroMap.put(singleplayerGame.getMap().getHideouts().get(newHideout), hero);
				hero.setVisible(false);
			}
		}
	}


    @Override
    public void updateEnabled(SingleplayerGame singlePlayerGame)
    {
    	//verstecken geht nur, wenn keine verzoegerungsmarken aktiv sind und der Held sichtbar ist
        if(singlePlayerGame.getCurrentHero().getDelayTokens() == 0 && singlePlayerGame.getCurrentHero().isVisible())
        	setEnabled(true);
        else
        	setEnabled(false);
    }
	
}
