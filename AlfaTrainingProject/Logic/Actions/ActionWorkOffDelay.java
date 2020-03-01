package Actions;

import GameLogic.SingleplayerGame;

/**
 * 
 * @author Kevin
 *
 */
public class ActionWorkOffDelay extends Action {

	public ActionWorkOffDelay(int actionPointRequired) {
		super(actionPointRequired);
	}

	@Override
	public void useAction(SingleplayerGame singleplayerGame) {
		//TODO eventuell alle zahlen von 1 bis 6 abdecken. es sind aber nur 3 verschiedene n�tig
		int diceResult = singleplayerGame.getHideDice().rollDice();
		
		switch(diceResult) {
			case 0: // green
				//Verzoegerungsmarken und Aktionspunkte um 1 verringern
				singleplayerGame.reduceCurrentActionPoints();
				singleplayerGame.reduceDelayTokens();	
				break;
			case 1: // red
				// was passiert bei rot
				singleplayerGame.reduceDelayTokens();
				singleplayerGame.setCurrentActionPointsToZero();
				break;
			case 2: // nothing
				//nichts passiert
				return;
		}
	}

}
