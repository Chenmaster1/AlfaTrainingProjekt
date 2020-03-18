package Actions;

import java.util.ArrayList;
import java.util.HashMap;

import Dice.HideDice;
import static Dice.HideDice.RESULT_GREEN;
import GameLogic.SingleplayerGame;
import MenuGUI.MyFrame;
import Heroes.Hero;
import Hideouts.Hideout;
import Hideouts.HideoutType;
import InGameGUI.MapPanel;
import GameData.GameData;

public class ActionHide extends Action {

	public ActionHide(int actionPointRequired) {
		super(actionPointRequired, MyFrame.bundle.getString("hideRoll"));
	}

	@Override
	public void useAction(SingleplayerGame singleplayerGame) {
		hideHero(singleplayerGame.getCurrentHero(), singleplayerGame);
	}

	/**
	 * falls sich nicht der aktuelle Held versteckt, wird diese Methode aufgerufen
	 * 
	 * @param hero
	 * @param singleplayerGame
	 */
	public void useAction(Hero hero, SingleplayerGame singleplayerGame) {
		hideHero(hero, singleplayerGame);
	}

	private void hideHero(Hero hero, SingleplayerGame singleplayerGame) {
		ArrayList<Hideout> availableHideouts = new ArrayList<Hideout>();
		ArrayList<Hero> aliveHeroes = new ArrayList<Hero>();
		HashMap<Hideout, Hero> hideoutHeroMap = singleplayerGame.getGameData().getHideoutHero();

		// availableHideouts wird mit allen noch verfügbaren Hideouts befüllt
		for (Hideout hideout : singleplayerGame.getGameData().getHideouts()) {
			boolean isUsed = false;
			for (Hideout usedHideout : hideoutHeroMap.keySet()) {
				if (hideout == usedHideout) {
					isUsed = true;
					break;
				}

			}

			if ((!isUsed) && hideout.isActive()) {
				availableHideouts.add(hideout);
			}
		}

		// aliveHeroes mit allen lebenden Helden füllen
		for (Hero heroFromList : singleplayerGame.getGameData().getHeroes()) {
			if (!heroFromList.isDead())
				aliveHeroes.add(heroFromList);
		}

		// HideDice benutzen
		int rollResult = singleplayerGame.getHideDice().rollDice();

		// Animation des Würfels starten
		singleplayerGame.getGamePanel().getGameSidePanel().getPanelHideDice().setRollResult(rollResult);


		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Geländetyp bestimmen
		Hideout oldHideout = null;
		for (Hideout hideout : hideoutHeroMap.keySet()) {
			if (hideoutHeroMap.get(hideout).equals(hero)) {
				oldHideout = hideout;
				break;
			}
		}
		HideoutType oldHideoutType = oldHideout.getHideoutType();

		// KEIN break im RESULT_RED case, weil Verstecken auch ausgeführt werden soll
		switch (rollResult) {
		case HideDice.RESULT_RED:
			int additionalDelayTokens;
			switch (oldHideoutType) {
			case DESERT:
				additionalDelayTokens = 2;
				break;
			case WETLANDS:
				additionalDelayTokens = 1;
				break;
			case FOREST:
				additionalDelayTokens = 0;
				break;
			default:
				additionalDelayTokens = -1;
				break;
			}
			hero.addDelayTokens(additionalDelayTokens);
		case HideDice.RESULT_GREEN:
			// Neu verstecken
			hero.setVisible(false);

			int newHideoutNumber = (int) (Math.random() * availableHideouts.size());
			Hideout newHideout = availableHideouts.get(newHideoutNumber);
			hideoutHeroMap.remove(oldHideout);
			hideoutHeroMap.put(newHideout, hero);

			int oldHideoutNumber = singleplayerGame.getGameData().getHideouts().indexOf(oldHideout);
			singleplayerGame.getGamePanel().getMapPanel().startAnimation(MapPanel.ANIMATIONTYPE_ELIMINATE,
					oldHideoutNumber);
			
			oldHideout.setActive(false);
			
			break;
		case HideDice.RESULT_NOTHING:
			break;
		}

	}

	@Override
	public void updateEnabled(SingleplayerGame singlePlayerGame) {

		int activeHideoutsCount = 0;
		int heroesAliveCount = 0;
		for (Hideout hideout : singlePlayerGame.getGameData().getHideouts()) {
			if (hideout.isActive())
				activeHideoutsCount++;
		}

		for (Hero hero : singlePlayerGame.getGameData().getHeroes()) {
			if (!hero.isDead())
				heroesAliveCount++;
		}

		if (!(activeHideoutsCount <= heroesAliveCount)) {
//			 verstecken geht nur, wenn keine verzoegerungsmarken aktiv sind und der Hero sichtbar ist
			if (singlePlayerGame.getCurrentHero().getDelayTokens() == 0
					&& singlePlayerGame.getCurrentHero().isVisible())
				setEnabled(true);
			else
				setEnabled(false);
		} else {
			setEnabled(false);
		}

	}
        
        
        
        
        
        
        
        /**
	 * get a free hide
	 * Dahlia
	 */
        public void freeHideHero(Hero hero, SingleplayerGame singleplayerGame) {
		ArrayList<Hideout> availableHideouts = new ArrayList<Hideout>();
		ArrayList<Hero> aliveHeroes = new ArrayList<Hero>();
		HashMap<Hideout, Hero> hideoutHeroMap = singleplayerGame.getGameData().getHideoutHero();

		// availableHideouts wird mit allen noch verfügbaren Hideouts befüllt
		for (Hideout hideout : singleplayerGame.getGameData().getHideouts()) {
			boolean isUsed = false;
			for (Hideout usedHideout : hideoutHeroMap.keySet()) {
				if (hideout == usedHideout) {
					isUsed = true;
					break;
				}

			}

			if ((!isUsed) && hideout.isActive()) {
				availableHideouts.add(hideout);
			}
		}

		// aliveHeroes mit allen lebenden Helden füllen
		for (Hero heroFromList : singleplayerGame.getGameData().getHeroes()) {
			if (!heroFromList.isDead())
				aliveHeroes.add(heroFromList);
		}

		// HideDice benutzen
		int rollResult = RESULT_GREEN;

		Hideout oldHideout = null;
		for (Hideout hideout : hideoutHeroMap.keySet()) {
			if (hideoutHeroMap.get(hideout).equals(hero)) {
				oldHideout = hideout;
				break;
			}
		}
		
			hero.setVisible(false);
			oldHideout.setActive(false);

			int newHideoutNumber = (int) (Math.random() * availableHideouts.size());
			Hideout newHideout = availableHideouts.get(newHideoutNumber);
			hideoutHeroMap.remove(oldHideout);
			hideoutHeroMap.put(newHideout, hero);
			
		}

	}

	


