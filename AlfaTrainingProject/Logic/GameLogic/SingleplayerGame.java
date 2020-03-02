package GameLogic;

import java.util.ArrayList;

import Actions.Action;
import Dice.AttackDice;
import Dice.HideDice;
import GUI.GamePanel;
import Heroes.Hero;
import Maps.Map;

public class SingleplayerGame {

    public final static int GAMESTATE_AIMING = 1, GAMESTATE_CHOOSING = 0;
    private int gameState;
    private Map map;
    private int timer;
    private AttackDice attackDice;
    private HideDice hideDice;
    private ArrayList<Action> standardActions;
    private ArrayList<Action> actions;
    private Hero currentHero;
    private int currentActionPoints;
    private GamePanel panel;

    public SingleplayerGame(GamePanel gamePanel, Map map) {
        this.panel = gamePanel;
        this.map = map;
    }

    public void startGame() {
        //TODO als pairprogramming

        //abilites einen flag geben. wird ein held betroffen, wird der flag seiner ability überprüft. 
        //führt entprechend abilies dann aus, wann die flag es zulässt
    	
    	//Fuer die Auswahl eines Feldes, wird beim bewegen der Maus der winkel zum Mittelpunkt berechnet und der Tower entsprechend mitbewegt
    	//beim klick wird eingerastet und angegriffen (eventuell verzoegerung mit einarbeiten)
    	
    	//vllt doch erstmal ohne db? nur helden und safegame? wären inbegriffen. hardcoden?
    }

    //-------------------------GETTER-------------------------//
    public HideDice getHideDice() {
        return hideDice;
    }

    public AttackDice getAttackDice() {
        return attackDice;
    }

    public Hero getCurrentHero() {
        return currentHero;
    }

    public int getCurrentActionPoints() {
        return currentActionPoints;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }
    //-------------------------SETTER-------------------------//

    /**
     * Reduziert die aktuellen Aktionspunkte um 1, auf maximal 0
     */
    public void reduceCurrentActionPoints() {
        if (currentActionPoints >= 1) {
            currentActionPoints -= 1;
        }
    }

    /**
     * Reduziert die aktuelle Aktionspunkte sofort auf 0, egal welcher wert
     * vorher gegeben war
     */
    public void setCurrentActionPointsToZero() {
        if (currentActionPoints >= 1) {
            currentActionPoints = 0;
        }
    }

    /**
     * Reduziert die Verzoegerungs Tokens um 1 auf maximal 0
     */
    public void reduceDelayTokens() {
        if (currentHero.getDelayTokens() >= 1) {
            currentHero.setDelayTokens(currentHero.getDelayTokens() - 1);
        }
    }

    public void setGameState(int gameState) {
        switch(gameState)
        {
            case GAMESTATE_AIMING:
                //TODO: Aktionen deaktivieren (oder besser generell bei der
                //Generation 
                //der Aktionsliste den gameState abfragen und sie hier neu 
                //generieren), Zieloverlay einblenden, 
                //MouseListener aktivieren
                generateActionList();
                this.gameState = gameState;
                break;
                
            case GAMESTATE_CHOOSING:
                this.gameState = gameState;
                break;
        }
    }

    /**
     * TODO: Diese Methode soll die Standardaktionen sowie die Heldenspezifischen
     * Abilities zu einer Liste zusammenführen und diese in der GUI anzeigen
     * bzw updaten lassen. Ob sie aktive Optionen sind, muss sowohl von der Aktion 
     * selbst (isEnabled()) als auch vom aktuellen gameState abhängen.
     */
    private void generateActionList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
