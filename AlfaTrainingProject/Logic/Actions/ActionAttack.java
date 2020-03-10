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
        /*useAction repräsentiert bei der Angriffsaktion die Entscheidung für 
        eine solche Aktion und das Einleiten des Zielvorgangs. 
        Es sollen bis zur Zielauswahl alle
        Aktionen deaktiviert werden. Auf dem Spielfeld soll das Ziel-Overlay 
        eingeblendet werden (dazu MouseListener aktivieren). All das passiert, 
        indem der gameState auf GAMESTATE_AIMING gesetzt wird (siehe setGameState(int)).
        Der eigentliche Treffervorgang, der im Anschluss durch einen Klick auf 
        die gewünschte Position ausgelöst wird, ist nicht als Action repräsentiert
        sondern wird direkt vom SingleplayerGame ausgeführt, mit der 
        fireAt(int fieldPosition) Methode.
         */
        
        singleplayerGame.setGameState(GameState.AIMING);
        singleplayerGame.setAttackMode(AttackMode.NORMAL_ATTACK);
    }

}
