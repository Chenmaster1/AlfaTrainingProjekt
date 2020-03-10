package Actions;

import GameLogic.SingleplayerGame;
import MenuGUI.MyFrame;

public class ActionHide extends Action {

	public ActionHide(int actionPointRequired) {
		super(actionPointRequired, MyFrame.bundle.getString("hideRoll"));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void useAction(SingleplayerGame singleplayerGame) {
		// TODO Auto-generated method stub

	}

}
