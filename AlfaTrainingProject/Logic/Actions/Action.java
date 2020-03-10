package Actions;

import GameLogic.SingleplayerGame;

/**
 * Diese Klasse ist das Grundgeruest fuer jede Aktion. Hier gehoeren
 * Faehigkeiten, als auch Spielzuege dazu.
 *
 * @author Kevin
 */
public abstract class Action {

    private String name;
    private int actionPointsRequired;
    private boolean enabled;

    /**
     * Dies ist der Konstruktor fuer jede Aktion
     *
     * @param actionPointRequired die benoetigte Anzahl an Aktionspunkten fuer
     * die Aktion
     * @author Kevin
     */
    public Action(int actionPointRequired, String name) {
        this.actionPointsRequired = actionPointRequired;
        this.name = name;
    }

    /**
     * Diese Methode wird von jedem Spielzug und jeder Faehigkeit implementiert
     * und aufgerufen, wenn diese ausgefuehrt werden sollen
     *
     * @param singleplayerGame das aktuelle Spiel
     */
    public abstract void useAction(SingleplayerGame singleplayerGame);

    //-------------------------GETTER-------------------------//
    public boolean isEnabled() {
        return enabled;
    }

    public int getActionPointsRequired() {
        return actionPointsRequired;
    }

    public String getName() {
        return name;
    }

    //-------------------------SETTER-------------------------//
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
