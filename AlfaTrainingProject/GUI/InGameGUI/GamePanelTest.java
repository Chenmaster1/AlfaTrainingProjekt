package InGameGUI;

import Actions.Action;
import Actions.ActionAttack;
import Actions.ActionHide;
import Actions.ActionWorkOffDelay;
import Arenacards.Arenacards;
import Heroes.*;
import Hideouts.Hideout;
import Hideouts.HideoutType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JFrame;

import Dice.AttackDice;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * Eine Testklasse für das GamePanel. Zeigt ein GamePanel mitsamt aller Elemente
 * standalone in einem JFrame an.
 */
public class GamePanelTest extends JFrame {

    public GamePanelTest() {

        // testArray hideouts
        final ArrayList<Hideout> hideoutArray = new ArrayList<>();
        Hideout hideout = null;
        for (int i = 0; i < 20; i++) {

            if ((i >= 12 && i <= 15) || i >= 18 || i <= 2) {
                hideout = new Hideout(i, HideoutType.FOREST);
            } else {
                if (i >= 3 && i <= 9) {
                    hideout = new Hideout(i, HideoutType.DESERT);
                } else {
                    hideout = new Hideout(i, HideoutType.WETLANDS);
                }
            }

            hideout.setActive(true);
            hideoutArray.add(hideout);

        }

        // testArray Gegnerhelden
        ArrayList<Hero> otherHeroes = new ArrayList<>();
        otherHeroes.add(new HeroWorok());
        otherHeroes.add(new HeroDahlia());
        otherHeroes.add(new HeroTolpanLongbeard());
        otherHeroes.add(new HeroWorok());
        for (Hero h : otherHeroes) {
            h.setDelayTokens(new Random().nextInt(4));
            h.setCurrentActionPoints(new Random().nextInt(h.getMaxActionPoints()));
            h.setCurrentHitPoints(new Random().nextInt(h.getMaxHitPoints()));
//			h.setVisible(new Random().nextBoolean());
            h.setVisible(true);
        }

        Hero mainHero = new HeroDahlia();
        mainHero.setVisible(false);
        mainHero.setDelayTokens(4);

        //test Hideout-Hero Map
        HashMap<Hideout, Hero> testHideoutHero = new HashMap<>();
        testHideoutHero.put(hideoutArray.get(4), otherHeroes.get(0));
        testHideoutHero.put(hideoutArray.get(8), otherHeroes.get(1));
        testHideoutHero.put(hideoutArray.get(12), otherHeroes.get(2));
        testHideoutHero.put(hideoutArray.get(13), otherHeroes.get(3));
        testHideoutHero.put(hideoutArray.get(17), mainHero);

        final MapPanel mp = new MapPanel(hideoutArray, testHideoutHero, mainHero);
        mp.setMapState(MapPanel.MAPSTATE_PLAYER_AIMING);

        final GameSidePanel gsp = new GameSidePanel(otherHeroes, mainHero);

        GamePanel gp = new GamePanel(mp, gsp, this);

        ArrayList<Action> testActionArrayList = new ArrayList<>();
        Action action1 = new ActionAttack(1);
        Action action2 = new ActionHide(1);
        Action action3 = new ActionWorkOffDelay(1);
        action1.setEnabled(true);
        action2.setEnabled(true);
        action3.setEnabled(true);
        testActionArrayList.add(action1);
        testActionArrayList.add(action2);
        testActionArrayList.add(action3);

        gsp.getPanelPlayerHero().setActionArrayList(testActionArrayList);

        // Das folgende Panel befindet sich standardmäßig NICHT im gamesidepanel,
        // ist hier nur als visueller Test der einzelkomponente
//        ArenaCardPanel acp = new ArenaCardPanel(new Arenacards(9));
//		acp.setBounds(0, 0, 744, 1040);
//        acp.setBounds(870, 10, 186, 260);
//        mp.add(acp);


        gsp.getPanelAttackDice().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {

                gsp.getPanelAttackDice().setRollResult((new Random().nextInt(5)) + 1);
                // gsp.getPanelAttackDice().setRunning(true);
                // gsp.getThreadAttackDicePanel().start();
            }
        });

        gsp.getPanelHideDice().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                gsp.getPanelHideDice().setRollResult((new Random().nextInt(3)) + 1);
            }
        });

        mp.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
//                if (state)
//                {
//                    mp.setMapState(MapPanel.MAPSTATE_REGULAR);
//                    mp.repaint();
//                }
//                else
//                {
//                    mp.setMapState(MapPanel.MAPSTATE_AIMING);
//                    mp.repaint();
//                }
//                state = !state;
                hideoutArray.get(mp.getCurrentAimedAtField())
                        .setActive(!hideoutArray.get(mp.getCurrentAimedAtField()).isActive());
                repaint();
            }
        });

        setContentPane(gp);

        setUndecorated(true);
        setLocation(0, 0);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();

    }

    public static final void main(String args[]) {
        GamePanelTest gpt = new GamePanelTest();

    }
}
