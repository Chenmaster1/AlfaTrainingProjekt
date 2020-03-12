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
		//Wirklich n�tig das hier zu machen? dann m�sste auch actionattack umgeschrieben werden
		//Falls anders gekl�st wie?
		//enum setzten und in singleplayergame abarbeiten. zb so:	
		singleplayerGame.setGameState(GameState.AIMING);
		singleplayerGame.setAttackMode(AttackMode.ATTACK_TWICE);
		
	}


    @Override
    public void updateEnabled(SingleplayerGame singlePlayerGame)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
