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
import GameData.GameData;
import enums.AbilityType;
import enums.AttackMode;
import enums.GameState;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Abilities.Ability;
import static Dice.AttackDice.*;
import InGameGUI.MapPanel;
import MenuGUI.MainFramePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

public class SingleplayerGame {

    private JFrame mainFrame;
    private GamePanel gamePanel;
    private GameData gameData;
    private MainFramePanel mainFramePanel;
    
    private AttackMode attackMode = AttackMode.NO_ATTACK;
    private GameState gameState;

    private Hero currentHero;
    private int currentHeroIndex;

    private ArrayList<Action> standardActions;
    private ArrayList<ArrayList<Action>> heroActionsLists;
    
    private ArrayList<Action> playerActions;

    private AttackDice attackDice;
    private HideDice hideDice;

    // ------------------booleans fuer Spielkontrolle ueber
    // Karten------------------------
    private boolean mysteriousIdol1 = false;
    private boolean mysteriousIdol2 = false;

    // ------------------booleans fuer Spielkontrolle ueber
    // Karten------------------------
    public SingleplayerGame(JFrame mainFrame, GamePanel gamePanel, GameData map, MainFramePanel mainFramePanel) {
        this.mainFrame = mainFrame;
        this.gamePanel = gamePanel;
        this.gameData = map;
        this.mainFramePanel = mainFramePanel;

        attackDice = new AttackDice();
        hideDice = new HideDice();

        initializeActionLists();
        
        initializeButtonListeners();
    }

    public void startGame() {

        showGame();

        Thread gameLogicThread = new Thread() {
            public void run() {
                startGameLogic();
            }
        };
        gameLogicThread.start();
    }

    private void startGameLogic() {
        // get Hero who does the first turn
        int heroCount = gameData.getHeroes().size();
        Random randomPlayer = new Random();
        setCurrentHeroIndex(randomPlayer.nextInt(heroCount));
        
        lblWhileLoop:
        while (true) {
            // player´s turn
            if (currentHero.isPlayerControlled()) {
            	playerTurn();
            } // ki´s turn
            else {
            	if(!currentHero.isDead())
            		kiTurn();
            }
            // to not exceed playerBase
            setCurrentHeroIndex((currentHeroIndex + 1) % heroCount);
            
            int alive = 0;
            for(Hero hero : gameData.getHeroes()) 
            	if(!hero.isDead())
            		alive++;
            
            if(alive == 1)
            	for(Hero hero : gameData.getHeroes())
            		if(!hero.isDead()) {
            			JOptionPane.showMessageDialog(mainFrame,hero.getName() + " hat gewonnen");
            			mainFrame.setContentPane(mainFramePanel);
            			mainFrame.repaint();
            			break lblWhileLoop; 
            		}
            
            			
        }

        // zug auslagern
        // randomPlayer.
        // TODO als pairprogramming
        // abilites einen flag geben. wird ein held betroffen, wird der flag seiner
        // ability überprüft.
        // führt entprechend abilies dann aus, wann die flag es zulässt
        // Fuer die Auswahl eines Feldes, wird beim bewegen der Maus der winkel zum
        // Mittelpunkt berechnet und der Tower entsprechend mitbewegt
        // beim klick wird eingerastet und angegriffen (eventuell verzoegerung mit
        // einarbeiten)
        // vllt doch erstmal ohne db? nur helden und safegame? wären inbegriffen.
        // hardcoden?
        // eventuell threadhandler schreiben
    }

    /**
     * Diese Methode führt die Standardaktionen sowie die Heldenspezifischen
     * Abilities zu einer Liste zusammen und übergibt der GUI die Liste des
     * Hauptsspielers.
     */
    private void initializeActionLists() {
        heroActionsLists = new ArrayList<ArrayList<Action>>();
        standardActions = new ArrayList<Action>();
        standardActions.add(new ActionAttack(1));
        standardActions.add(new ActionHide(1));
        standardActions.add(new ActionWorkOffDelay(1));

        ArrayList<Action> heroActionList;

        for (Hero h : gameData.getHeroes()) {
            heroActionList = new ArrayList<>(standardActions);
            for (Ability heroAbility : h.getAbilities()) {
                if (heroAbility.getAbilityType() != AbilityType.PASSIVE) {
                    heroActionList.add(heroAbility);
                }
            }
            heroActionsLists.add(heroActionList);

            if (h.isPlayerControlled()) {
                playerActions = heroActionList;
                gamePanel.getGameSidePanel().getPanelPlayerHero().setActionArrayList(heroActionList);
            }
        }
    }

    private void showGame() {

        mainFrame.setContentPane(gamePanel);
        mainFrame.pack();
    }

    private void playerTurn() {
        setGameState(GameState.CHOOSING);
        gamePanel.getGameSidePanel().getPanelPlayerHero().setButtonsEnabled(true);
    }

    private void kiTurn() {
        setGameState(GameState.CHOOSING);
        currentHero.setCurrentActionPoints(currentHero.getMaxActionPoints());

        while (currentHero.getCurrentActionPoints() != 0) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SingleplayerGame.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (Action a : heroActionsLists.get(currentHeroIndex)) {
                //a.updateEnabled(this);
            }

            Action currentAction = currentHero.getKiLogic().chooseAction(heroActionsLists.get(currentHeroIndex), this);

            currentAction.useAction(this);
            decreaseCurrentActionPointsBy(currentAction.getActionPointsRequired());

            // is currentHero ki / player?
            // aktionsliste an ki übergeben
            // auswahl kütt zurück
            // aktion ausführen
            // AP verringern sich entsprechend
            gamePanel.repaint();
        }

    }

    // -------------------------GETTER-------------------------//
    public HideDice getHideDice() {
        return hideDice;
    }

    public AttackDice getAttackDice() {
        return attackDice;
    }

    public int getCurrentHeroIndex() {
        return currentHeroIndex;
    }

    public Hero getCurrentHero() {
        return currentHero;
    }

    public GameData getGameData() {
        return gameData;
    }

    // -------------------------SETTER-------------------------//
    /**
     * Setzt den Helden, der gerade am Zug ist. Updated auch automatisch den
     * currentHeroIndex.
     */
    public void setCurrentHero(Hero currentHero) {
        this.currentHero = currentHero;
        this.currentHeroIndex = gameData.getHeroes().indexOf(currentHero);
    }

    /**
     * Setzt den Index des Helden, der gerade am Zug ist. Updated auch
     * automatisch den currentHero.
     *
     * @param currentHeroIndex
     */
    public void setCurrentHeroIndex(int currentHeroIndex) {
        this.currentHeroIndex = currentHeroIndex;
        this.currentHero = gameData.getHeroes().get(currentHeroIndex);
    }

    public void reduceCurrentActionPoints() {
        if (gameData.getHeroes().get(currentHeroIndex).getCurrentActionPoints() >= 1) {
            gameData.getHeroes().get(currentHeroIndex)
                    .setCurrentActionPoints(gameData.getHeroes().get(currentHeroIndex).getCurrentActionPoints() - 1);
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
                if (!currentHero.isPlayerControlled()) {
                    // getting AttackField
                    int currentAttackField = currentHero.getKiLogic().chooseAttackField(this);

                    gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_KI_AIMING);
                    gamePanel.getMapPanel().setCurrentAimedAtField(currentAttackField);

                    shootAtAttackField(currentAttackField);

                } //playerTurn
                else {
                  gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_PLAYER_AIMING);
                }

                break;

            case CHOOSING:
                gamePanel.getMapPanel().setMapState(MapPanel.MAPSTATE_REGULAR);
                break;

        }
        this.gameState = gameState;
    }

    public void setAttackMode(AttackMode attackMode) {
        this.attackMode = attackMode;
    }

    public void increaseCurrentActionPointsBy(int increment) {
        currentHero.setCurrentActionPoints(currentHero.getCurrentActionPoints() + increment);
    }

    public void decreaseCurrentActionPointsBy(int decrement) {
        currentHero.setCurrentActionPoints(currentHero.getCurrentActionPoints() - decrement);
    }

    public void setMysteriousIdol1(boolean active) {
        this.mysteriousIdol1 = active;
    }

    public void setMysteriousIdol2(boolean active) {
        this.mysteriousIdol2 = active;
    }

    private void shootAtAttackField(int currentAttackField) {
        int numberOfHideouts = gameData.getHideouts().size();
        int diceResult = attackDice.rollDice();
        int finalRolledAttackField;
        switch (diceResult) {
            case RESULT_CENTER_HIT:
                finalRolledAttackField = currentAttackField;
                break;
            case RESULT_LEFT_CENTER_HIT:
                finalRolledAttackField = (currentAttackField + numberOfHideouts - 1) % numberOfHideouts;
                break;
            case RESULT_RIGHT_CENTER_HIT:
                finalRolledAttackField = (currentAttackField + numberOfHideouts + 1) % numberOfHideouts;
                break;
            case RESULT_OUTER_LEFT_HIT:
                finalRolledAttackField = (currentAttackField + numberOfHideouts - 2) % numberOfHideouts;
                break;
            case RESULT_OUTER_RIGHT_HIT:
                finalRolledAttackField = (currentAttackField + numberOfHideouts + 2) % numberOfHideouts;
                break;
            default:
                finalRolledAttackField = -1;
        }

        // hit that field
        if (gameData.getHideoutHero().containsKey(gameData.getHideouts().get(finalRolledAttackField))) {
            //Field is occupied by a hero
            Hero occupyingHero = gameData.getHideoutHero().get(gameData.getHideouts().get(finalRolledAttackField));

            if (occupyingHero != null) {
                // Hero is detected / unveiled
                if (!occupyingHero.isVisible()) {
                    occupyingHero.setVisible(true);
                } // Hero is hit
                else {
                    //TODO invulnerability
                    occupyingHero.setCurrentHitPoints(occupyingHero.getCurrentHitPoints() - 1);

                    //check if hero died / disable field
                    if (occupyingHero.isDead()) {
                        gameData.getHideouts().get(finalRolledAttackField).setActive(false);
                    }

                }
            }
        }
        gamePanel.repaint();
    }

    private void usePlayerAction(Action chosenAction) {
        chosenAction.useAction(this);
        
    } 
    
    private void initializeButtonListeners() {
        ArrayList<JButton> playerButtons = gamePanel.getGameSidePanel().getPanelPlayerHero().getButtonArrayList();
        for (int i = 0; i < playerButtons.size(); i++)
        {
            Action tester = playerActions.get(i);
            playerButtons.get(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    usePlayerAction(tester);
                }
            });
            
        }
        
        
    }

}
