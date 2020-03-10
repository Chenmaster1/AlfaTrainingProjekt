package Actions;

import GameLogic.SingleplayerGame;
import MenuGUI.MyFrame;
import enums.AttackMode;
import enums.GameState;

/**
 * @Holger 
 * benoetigt einen Boolean fuer einen Doppelwurf
 */
public class ActionAttack extends Action {
    
public boolean doubleThrow = false;

    public ActionAttack(int actionPointRequired) {
        super(actionPointRequired, MyFrame.bundle.getString("attackRoll"));
    }

    @Override
    public void useAction(SingleplayerGame singleplayerGame) {
        /*useAction repr�sentiert bei der Angriffsaktion die Entscheidung f�r 
        eine solche Aktion und das Einleiten des Zielvorgangs. 
        Es sollen bis zur Zielauswahl alle
        Aktionen deaktiviert werden. Auf dem Spielfeld soll das Ziel-Overlay 
        eingeblendet werden (dazu MouseListener aktivieren). All das passiert, 
        indem der gameState auf GAMESTATE_AIMING gesetzt wird (siehe setGameState(int)).
        Der eigentliche Treffervorgang, der im Anschluss durch einen Klick auf 
        die gew�nschte Position ausgel�st wird, ist nicht als Action repr�sentiert
        sondern wird direkt vom SingleplayerGame ausgef�hrt, mit der 
        fireAt(int fieldPosition) Methode.
         */
        
        singleplayerGame.setGameState(GameState.AIMING);
        singleplayerGame.setAttackMode(AttackMode.NORMAL_ATTACK);
    }

}
