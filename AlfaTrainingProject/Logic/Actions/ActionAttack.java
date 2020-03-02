package Actions;

import GameLogic.SingleplayerGame;

/**
 *
 */
public class ActionAttack extends Action {

    public ActionAttack(int actionPointRequired) {
        super(actionPointRequired);
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
        
        singleplayerGame.setGameState(SingleplayerGame.GAMESTATE_AIMING);
        
        
    }

}