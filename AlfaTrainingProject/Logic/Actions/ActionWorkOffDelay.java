package Actions;

import Dice.HideDice;
import GameLogic.SingleplayerGame;
import MenuGUI.MyFrame;

/**
 * Dies ist die Klasse fuer den Spielzug Verzoegerung verringern
 * 
 * @author Kevin
 */
public class ActionWorkOffDelay extends Action {

	/**
	 * Dies ist der Konstruktor fuer den Spielzug Verzoegerung verringern
	 * 
	 * @param actionPointRequired die benoetigte Anzahl an Aktionspunkten fuer den
	 *                            Spielzug Verzoegerung verringern
	 * @author Kevin
	 */
	public ActionWorkOffDelay(int actionPointRequired) {
		super(actionPointRequired, MyFrame.bundle.getString("delayRoll"));
	}

	@Override
	public void useAction(SingleplayerGame singleplayerGame) {

		int diceResult = singleplayerGame.getHideDice().rollDice();

		// Animation des Würfels starten
		singleplayerGame.getGamePanel().getGameSidePanel().getPanelHideDice().setRollResult(diceResult);

		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		switch (diceResult) {
		case HideDice.RESULT_GREEN:
			// Verzoegerungsmarken um 1 verringern
			singleplayerGame.reduceDelayTokens();
			break;
		case HideDice.RESULT_RED:
			// Verzoegerungsmarken um 1 verringern und Zug beenden
			singleplayerGame.reduceDelayTokens();
			singleplayerGame.setCurrentActionPointsToZero();
			break;
		case HideDice.RESULT_NOTHING: // nothing
			break;
		}
	}

	@Override
	public void updateEnabled(SingleplayerGame singlePlayerGame) {
		// workoffdelay funktioniert nur, wenn der held verzoegerungsmarken hat
		if (singlePlayerGame.getCurrentHero().getDelayTokens() > 0)
			setEnabled(true);
		else
			setEnabled(false);
	}
}
