package GameLogic;

import Heroes.Hero;
import Heroes.HeroBalthur;
import Heroes.HeroDahlia;
import Heroes.HeroFlint;
import Heroes.HeroTolpanLongbeard;
import Heroes.HeroWorok;
import Hideouts.Hideout;
import Hideouts.HideoutType;
import InGameGUI.GamePanel;
import InGameGUI.MapPanel;
import MenuGUI.MainFramePanel;
import InGameGUI.GameSidePanel;
import GameData.GameData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JFrame;

/**
 * Eine Klasse zur Erzeugung eines SingleplayerGame.
 */
public class SingleplayerGameCreator {

	/**
	 * Erzeugt ein SingleplayerGame basierend auf der Standardkarte und gibt es
	 * zurück.
	 * 
	 * @param heroes              Die Liste der teilnehmenden Helden inklusive dem
	 *                            Spielerheld
	 * @param mainHero            Der Held des Spielers
	 * @param closedHideoutNumber Anzahl der Hideouts, die vom Anfang an geschlossen
	 *                            sein sollen
	 * @param mainFrame           Das JFrame, in dem das SingleplayerGame
	 *                            dargestellt werden soll
	 * 
	 * @return
	 */
	public static SingleplayerGame createSingleplayerGame(ArrayList<Hero> heroes, Hero mainHero,
			int closedHideoutNumber, JFrame mainFrame, MainFramePanel mainFramePanel) {
		// MODEL-OBJEKTE

		//Zufallsgenerator
		Random randomGenerator = new Random();
		int fieldNumber;

		// Helden sind übergeben
		// Hideouts initialisieren
		ArrayList<Hideout> hideouts = new ArrayList<>();
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
			hideouts.add(hideout);
		}

		// Angegebene Anzahl an Hideouts zufällig schließen
		for (int i = 0; i < closedHideoutNumber; i++) {
			do {
				fieldNumber = randomGenerator.nextInt(hideouts.size());
			} while (!hideouts.get(fieldNumber).isActive());
			
			hideouts.get(fieldNumber).setActive(false);
			
		}

		// erste Verstecke zufällig initialisieren
		HashMap<Hideout, Hero> hideoutHero = new HashMap<>();
		for (Hero h : heroes) {
			do {
				fieldNumber = randomGenerator.nextInt(hideouts.size());
			} while ( (!hideouts.get(fieldNumber).isActive()) 
					||	(hideoutHero.containsKey(hideouts.get(fieldNumber))));

			hideoutHero.put(hideouts.get(fieldNumber), h);
		}

		// das Mapobjekt zusammensetzen
		GameData standardMap = new GameData(hideoutHero, hideouts, heroes);

		// --------------------
		// VIEW-Objekte
		MapPanel mp = new MapPanel(hideouts, hideoutHero, mainHero);

		GameSidePanel gsp = new GameSidePanel(heroes, mainHero);

		GamePanel gamePanel = new GamePanel(mp, gsp, mainFrame, mainFramePanel);

		// ------------
		// SingleplayerGame als CONTROL-Objekt zusammensetzen
		SingleplayerGame resultGame = new SingleplayerGame(mainFrame, gamePanel, standardMap, mainFramePanel);
		return resultGame;

	}

	/**
	 * Erzeugt ein Test - SingleplayerGame basierend auf der Standardkarte und gibt
	 * es zurück. Es enthält 5 Helden (inklusive Hauptspieler).
	 *
	 * @param mainFrame Das JFrame, in dem dieses SingleplayerGame sich darstellen
	 *                  soll.
	 * @return
	 */
	public static SingleplayerGame createTestSingleplayerGame(JFrame mainFrame, MainFramePanel mainFramePanel) {

		// Helden initialisieren
		ArrayList<Hero> heroes = new ArrayList<>();
		heroes.add(new HeroBalthur());
		heroes.add(new HeroDahlia());
		heroes.add(new HeroTolpanLongbeard());
		heroes.add(new HeroWorok());
		heroes.add(new HeroFlint());

		heroes.get(4).setPlayerControlled(true);
		Hero mainHero = heroes.get(4);

		SingleplayerGame resultGame = createSingleplayerGame(heroes, mainHero, 2, mainFrame, mainFramePanel);
		return resultGame;
	}

}
