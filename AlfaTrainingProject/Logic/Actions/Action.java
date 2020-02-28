package Actions;

import GameLogic.SingleplayerGame;

public abstract class Action {


	private int actionPointsRequired;
	private boolean enabled;
	
	public Action(int actionPointRequired) {
		this.actionPointsRequired = actionPointRequired;
	}
	
	public abstract void useAction(SingleplayerGame singleplayerGame);

	
	//-------------------------GETTER-------------------------//
	public boolean isEnabled() {
		return enabled;
	}

	public int getActionPointsRequired() {
		return actionPointsRequired;
	}	
	
	//-------------------------SETTER-------------------------//
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
