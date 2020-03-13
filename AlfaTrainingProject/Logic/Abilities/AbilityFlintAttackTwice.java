package Abilities;


import GameLogic.SingleplayerGame;
import MenuGUI.MyFrame;
import enums.AbilityType;
import enums.AttackMode;
import enums.GameState;

public class AbilityFlintAttackTwice  extends Ability{

	public AbilityFlintAttackTwice(int actionPointRequired){
		super(actionPointRequired, AbilityType.TURN,  
                MyFrame.bundle.getString("abilityFlintAttackTwice_name"), 
                MyFrame.bundle.getString("abilityFlintAttackTwice_description"));
	}

	@Override
	public void useAction(SingleplayerGame singleplayerGame) {
		// TODO Magie hier. wird anstatt eines zuges ausgefuehrt, also zweimal angreifen
		//Wirklich nötig das hier zu machen? dann müsste auch actionattack umgeschrieben werden
		//Falls anders geklöst wie?
		//enum setzten und in singleplayergame abarbeiten. zb so:	
		
		singleplayerGame.setAttackMode(AttackMode.ATTACK_TWICE);
		singleplayerGame.setGameState(GameState.AIMING);
		
		
	}


    @Override
    public void updateEnabled(SingleplayerGame singlePlayerGame)
    {
        if(singlePlayerGame.getCurrentHero().getCurrentActionPoints() == 1)
        	setEnabled(true);
        else
        	setEnabled(false);
    }

}
