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
		//Wirklich n�tig das hier zu machen? dann m�sste auch actionattack umgeschrieben werden
		//Falls anders gekl�st wie?
		//enum setzten und in singleplayergame abarbeiten. zb so:	
		
		singleplayerGame.setAttackMode(AttackMode.ATTACK_TWICE);
		singleplayerGame.setGameState(GameState.AIMING);
                //happening to PanelLogHeroAction 
                singleplayerGame.getGamePanel().getGameSidePanel().getPanelLogHeroAction().setTextAreaLogHeroAction(MyFrame.bundle.getString("heroAbilityFlint"));
		
		
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
