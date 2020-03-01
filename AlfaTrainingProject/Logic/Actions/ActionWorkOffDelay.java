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
				//TODO was passiert bei gr�n
				break;
			case 1: // red
				//TODO was passiert bei rot
				break;
			case 3: // nothing
				//was passiert bei niete
				break;
		}
	}

}
