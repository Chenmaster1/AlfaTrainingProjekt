package InGameGUI;

import Actions.Action;
import Actions.ActionAttack;
import Actions.ActionHide;
import Actions.ActionWorkOffDelay;
import Heroes.*;
import Hideouts.Hideout;
import Hideouts.HideoutType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;

import Dice.AttackDice;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * Eine Testklasse für das GamePanel. Zeigt ein GamePanel standalone in einem
 * JFrame an.
 */
public class GamePanelTest extends JFrame {

	public GamePanelTest() {

		// testArray hideouts
		final ArrayList<Hideout> hideoutArray = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			Hideout h = new Hideout(0, HideoutType.FOREST);
			h.setActive(true);
			hideoutArray.add(h);

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
		}

		final MapPanel mp = new MapPanel(hideoutArray);
		final GameSidePanel gsp = new GameSidePanel(otherHeroes, new HeroDahlia());
		GamePanel gp = new GamePanel(mp, gsp);

		setContentPane(gp);

		mp.setMapState(MapPanel.MAPSTATE_AIMING);

                ArrayList<Action> testActionArrayList = new ArrayList<>();
                testActionArrayList.add(new ActionAttack(1));
                testActionArrayList.add(new ActionHide(1));
                testActionArrayList.add(new ActionWorkOffDelay(1));
                
                gsp.getPanelPlayerHero().setActionArrayList(testActionArrayList);
                
		gsp.getPanelAttackDice().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				gsp.getPanelAttackDice().increaseCurrentAnimationFrame();
				gsp.getPanelAttackDice().setRollResult((new Random().nextInt(5)) + 1);			
				//gsp.getPanelAttackDice().setRunning(true);
				//gsp.getThreadAttackDicePanel().start();
			}
		});
                
                gsp.getPanelHideDice().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				gsp.getPanelHideDice().setRollResult((new Random().nextInt(3)) + 1);
			}
		});

		mp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
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

//        getContentPane().setPreferredSize(new Dimension(1080, 1080));
		setLocation(-1920, 0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		pack();

	}

	public static final void main(String args[]) {
		GamePanelTest gpt = new GamePanelTest();

	}
}
