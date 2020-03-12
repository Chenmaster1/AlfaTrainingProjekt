package GameLogic;

import java.util.ArrayList;

import Actions.Action;
import Actions.ActionAttack;
import Actions.ActionHide;
import Actions.ActionWorkOffDelay;
import Dice.AttackDice;
import Dice.HideDice;
import Heroes.Hero;
import InGameGUI.GamePanel;
import Maps.Map;
import enums.AttackMode;
import enums.GameState;
import java.util.Random;
import javax.swing.JFrame;


public class SingleplayerGame
{

    private AttackMode attackMode = AttackMode.NO_ATTACK;
    private GameState gameState;
    private Map map;
    private int timer;
    private AttackDice attackDice;
    private HideDice hideDice;
    private ArrayList<Action> standardActions;
    private ArrayList<ArrayList<Action>> heroActionsLists;
    private Hero currentHero;
    private int currentHeroIndex;
    private GamePanel gamePanel;
    private JFrame mainFrame;

    //------------------booleans fuer Spielkontrolle ueber Karten------------------------
    private boolean mysteriousIdol1 = false;
    private boolean mysteriousIdol2 = false;

    //------------------booleans fuer Spielkontrolle ueber Karten------------------------

    public SingleplayerGame(JFrame mainFrame, GamePanel gamePanel, Map map)
    {
        this.mainFrame = mainFrame;
        this.gamePanel = gamePanel;
        this.map = map;

        heroActionsLists = new ArrayList<ArrayList<Action>>();
        standardActions = new ArrayList<Action>();
        standardActions.add(new ActionAttack(1));
        standardActions.add(new ActionHide(1));
        standardActions.add(new ActionWorkOffDelay(1));

        ArrayList<Action> heroActionList;

        for (Hero h : map.getHeroes())
        {
            heroActionList = new ArrayList<>(standardActions);
            heroActionList.addAll(h.getAbilities());
            heroActionsLists.add(heroActionList);
        }
    }


    public void startGame()
    {

        showGame();

        // get Hero who does the first turn
        int heroCount = map.getHeroes().size();
        Random randomPlayer = new Random();
        setCurrentHeroIndex(randomPlayer.nextInt(heroCount));

        // beginn turn 
        while (true)
        {
            //player´s turn
            if (currentHero.isPlayerControlled())
            {
                playerTurn();
            }

            //ki´s turn
            else
            {
                kiTurn();
            }
            // to not exceed playerBase
            setCurrentHeroIndex((currentHeroIndex + 1) % heroCount);

        }

        // zug auslagern
        //randomPlayer.
        //TODO als pairprogramming
        //abilites einen flag geben. wird ein held betroffen, wird der flag seiner ability überprüft. 
        //führt entprechend abilies dann aus, wann die flag es zulässt
        //Fuer die Auswahl eines Feldes, wird beim bewegen der Maus der winkel zum Mittelpunkt berechnet und der Tower entsprechend mitbewegt
        //beim klick wird eingerastet und angegriffen (eventuell verzoegerung mit einarbeiten)
        //vllt doch erstmal ohne db? nur helden und safegame? wären inbegriffen. hardcoden?
        //eventuell threadhandler schreiben
    }


    public void showGame()
    {

        mainFrame.setContentPane(gamePanel);
        mainFrame.pack();
    }


    //-------------------------GETTER-------------------------//
    public HideDice getHideDice()
    {
        return hideDice;
    }


    public AttackDice getAttackDice()
    {
        return attackDice;
    }


    public int getCurrentHeroIndex()
    {
        return currentHeroIndex;
    }


    public Hero getCurrentHero()
    {
        return currentHero;
    }


    public Map getMap()
    {
        return map;
    }

//
//    public ArrayList<Action> getActions()
//    {
//        return heroActionsList;
//    }

    //-------------------------SETTER-------------------------//
    /**
     * Reduziert die aktuellen Aktionspunkte um 1, auf maximal 0
     */
    public void setCurrentHero(Hero currentHero)
    {
        this.currentHero = currentHero;
        this.currentHeroIndex = map.getHeroes().indexOf(currentHero);
    }


    public void setCurrentHeroIndex(int currentHeroIndex)
    {
        this.currentHero = map.getHeroes().get(currentHeroIndex);
        this.currentHeroIndex = currentHeroIndex;
    }


    public void reduceCurrentActionPoints()
    {
        if (map.getHeroes().get(currentHeroIndex).getCurrentActionPoints() >= 1)
        {
            map.getHeroes().get(currentHeroIndex).setCurrentActionPoints(map.getHeroes().get(currentHeroIndex).getCurrentActionPoints() - 1);
        }
    }


    /**
     * Reduziert die aktuelle Aktionspunkte sofort auf 0, egal welcher wert
     * vorher gegeben war
     */
    public void setCurrentActionPointsToZero()
    {

        map.getHeroes().get(currentHeroIndex).setCurrentActionPoints(0);
    }


    /**
     * Reduziert die Verzoegerungs Tokens um 1 auf maximal 0
     */
    public void reduceDelayTokens()
    {
        if (map.getHeroes().get(currentHeroIndex).getDelayTokens() >= 1)
        {
            map.getHeroes().get(currentHeroIndex).setDelayTokens(map.getHeroes().get(currentHeroIndex).getDelayTokens() - 1);
        }
    }


    public void setGameState(GameState gameState)
    {
        switch (gameState)
        {
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


    public void setAttackMode(AttackMode attackMode)
    {
        this.attackMode = attackMode;
    }


    public void increaseCurrentActionPointsBy(int increasment)
    {
        map.getHeroes().get(currentHeroIndex).setCurrentActionPoints(map.getHeroes().get(currentHeroIndex).getCurrentActionPoints() + increasment);
    }


    public void setMysteriousIdol1(boolean active)
    {
        this.mysteriousIdol1 = active;
    }


    public void setMysteriousIdol2(boolean active)
    {
        this.mysteriousIdol2 = active;
    }


    /**
     * TODO: Diese Methode soll die Standardaktionen sowie die
     * Heldenspezifischen Abilities zu einer Liste zusammenführen und diese in
     * der GUI anzeigen bzw updaten lassen. Ob sie aktive Optionen sind, muss
     * sowohl von der Aktion selbst (isEnabled()) als auch vom aktuellen
     * gameState abhängen.
     */
    private void generateActionList()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    private void playerTurn()
    {

    }


    private void kiTurn()
    {

        currentHero.setCurrentActionPoints(currentHero.getMaxActionPoints());

        while (currentHero.getCurrentActionPoints() != 0)
        {
                
            for ( Action a : heroActionsLists.get(currentHeroIndex))
            {
                a.updateEnabled(this);
                        

            }

             // heroActionsLists




            //while !=0 update die Aktionsliste ob dis/enabled
            // aktionsliste an ki übergeben
            //auswahl kütt zurück
            //aktion ausführen
            // AP verringern sich entsprechend
        }

    }


}

