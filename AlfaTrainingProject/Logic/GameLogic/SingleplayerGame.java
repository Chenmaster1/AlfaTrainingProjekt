package GameLogic;

import java.util.ArrayList;

import Actions.Action;
import Dice.AttackDice;
import Dice.HideDice;
import Heroes.Hero;
import InGameGUI.GamePanel;
import Maps.Map;
import enums.AttackMode;
import enums.GameState;
import javax.swing.JFrame;

public class SingleplayerGame {

    private AttackMode attackMode = AttackMode.NO_ATTACK;
    private GameState gameState;
    private Map map;
    private int timer;
    private AttackDice attackDice;
    private HideDice hideDice;
    private ArrayList<Action> standardActions;
    private ArrayList<Action> actions;
    private Hero currentHero;
    private GamePanel panel;
    private JFrame mainFrame;

    //------------------booleans fuer Spielkontrolle ueber Karten------------------------
    private boolean mysteriousIdol1 = false;
    private boolean mysteriousIdol2 = false;
    //------------------booleans fuer Spielkontrolle ueber Karten------------------------
    
    public SingleplayerGame(JFrame mainFrame, GamePanel gamePanel, Map map) {
        this.mainFrame = mainFrame;
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
        //eventuell threadhandler schreiben
    }

    public void createGame() {
    	mainFrame.setContentPane(panel);
    	mainFrame.getIgnoreRepaint();
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
        if (currentHero.getCurrentActionPoints() >= 1) {
            currentHero.setCurrentActionPoints(currentHero.getCurrentActionPoints() - 1);
        }
    }

    /**
     * Reduziert die aktuelle Aktionspunkte sofort auf 0, egal welcher wert
     * vorher gegeben war
     */
    public void setCurrentActionPointsToZero() {

        currentHero.setCurrentActionPoints(0);
    }

    /**
     * Reduziert die Verzoegerungs Tokens um 1 auf maximal 0
     */
    public void reduceDelayTokens() {
        if (currentHero.getDelayTokens() >= 1) {
            currentHero.setDelayTokens(currentHero.getDelayTokens() - 1);
        }
    }

    public void setGameState(GameState gameState) {
        switch (gameState) {
            case AIMING:
                //TODO: Aktionen deaktivieren (oder besser generell bei der
                //Generation 
                //der Aktionsliste den gameState abfragen und sie hier neu 
                //generieren), Zieloverlay einblenden, 
                //MouseListener aktivieren
                generateActionList();
                this.gameState = gameState;
                break;

            case CHOOSING:
                this.gameState = gameState;
                break;
        }
    }

    public void setAttackMode(AttackMode attackMode) {
        this.attackMode = attackMode;
    }
    
    public void increaseCurrentActionPointsBy(int increasment) {
    	currentHero.setCurrentActionPoints(currentHero.getCurrentActionPoints() + increasment);
    }

    public void setMysteriousIdol1(boolean active) {
    	this.mysteriousIdol1 = active;
    }
    
    public void setMysteriousIdol2(boolean active) {
    	this.mysteriousIdol2 = active;
    }
    
    /**
     * TODO: Diese Methode soll die Standardaktionen sowie die
     * Heldenspezifischen Abilities zu einer Liste zusammenführen und diese in
     * der GUI anzeigen bzw updaten lassen. Ob sie aktive Optionen sind, muss
     * sowohl von der Aktion selbst (isEnabled()) als auch vom aktuellen
     * gameState abhängen.
     */
    private void generateActionList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
