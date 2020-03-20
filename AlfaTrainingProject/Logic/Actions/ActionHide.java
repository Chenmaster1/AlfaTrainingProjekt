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

	/**
	 * Simuliert den Versteckwurf. Ergebnis ist abhängig vom Wurf und der Art des
	 * Geländes, auf dem der Spieler steht.
	 * 
	 * @param hero
	 * @param singleplayerGame
	 */
	private void hideHero(Hero hero, SingleplayerGame singleplayerGame) {

		HashMap<Hideout, Hero> hideoutHeroMap = singleplayerGame.getGameData().getHideoutHero();

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

		switch (rollResult) {
		case HideDice.RESULT_RED:
			int additionalDelayTokens;
			boolean hidingSuccessful;
			switch (oldHideoutType) {
			case WETLANDS:
				additionalDelayTokens = 2;
				hidingSuccessful = true;
				// happening to PanelLogHeroAction
				singleplayerGame.getGamePanel().getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(
						MyFrame.bundle.getString("recieveToken") + " " + Integer.toString(additionalDelayTokens));
				break;
			case FOREST:
				additionalDelayTokens = 1;
				hidingSuccessful = true;
				// happening to PanelLogHeroAction
				singleplayerGame.getGamePanel().getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(
						MyFrame.bundle.getString("recieveToken") + " " + Integer.toString(additionalDelayTokens));
				break;
			case DESERT:
				additionalDelayTokens = 0;
				hidingSuccessful = false;
				// Nur den Fehlschlag im Log verzeichnen
				singleplayerGame.getGamePanel().getGameSidePanel().getPanelLogHeroAction()
						.setTextAreaLogHeroAction(MyFrame.bundle.getString("hideRollFailed"));
				break;
			default:
				additionalDelayTokens = -1;
				hidingSuccessful = false;
				break;
			}
			hero.addDelayTokens(additionalDelayTokens);
			if (hidingSuccessful) {
				freeHideHero(hero, singleplayerGame);
			}

			break;
		case HideDice.RESULT_GREEN:

			freeHideHero(hero, singleplayerGame);

			break;
		case HideDice.RESULT_NOTHING:
			// Nur den Fehlschlag im Log verzeichnen
			singleplayerGame.getGamePanel().getGameSidePanel().getPanelLogHeroAction()
					.setTextAreaLogHeroAction(MyFrame.bundle.getString("hideRollFailed"));
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
	 * Sucht dem gegebenen Hero ein neues Versteck und bewegt ihn dort hin, setzt
	 * ihn unsichtbar, startet die Animation zur Zerstörung seines bisherigen Feldes
	 */
	public void freeHideHero(Hero hero, SingleplayerGame singleplayerGame) {
		ArrayList<Hideout> availableHideouts = new ArrayList<Hideout>();
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

		Hideout oldHideout = null;
		for (Hideout hideout : hideoutHeroMap.keySet()) {
			if (hideoutHeroMap.get(hideout).equals(hero)) {
				oldHideout = hideout;
				break;
			}
		}

//		 happening to PanelLogHeroAction
		singleplayerGame.getGamePanel().getGameSidePanel().getPanelLogHeroAction()
				.setTextAreaLogHeroAction(MyFrame.bundle.getString("getNewHideout"));

		hero.setVisible(false);
		int oldHideoutNumber = singleplayerGame.getGameData().getHideouts().indexOf(oldHideout);
		singleplayerGame.getGamePanel().getMapPanel().startAnimation(MapPanel.ANIMATIONTYPE_ELIMINATE,
				oldHideoutNumber);
		oldHideout.setActive(false);

		int newHideoutNumber = (int) (Math.random() * availableHideouts.size());
		Hideout newHideout = availableHideouts.get(newHideoutNumber);
		hideoutHeroMap.remove(oldHideout);
		hideoutHeroMap.put(newHideout, hero);

	}

}
