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
     * @param mainFrame Das JFrame, in dem das SingleplayerGame dargestellt werden soll
     * @param heroes    Die Liste der teilnehmenden Helden inklusive dem Spielerheld
     * @param mainHero  Der Held des Spielers
     * @return 
     */
    public static SingleplayerGame createSingleplayerGame(JFrame mainFrame, ArrayList<Hero> heroes, Hero mainHero) {
        //MODEL-OBJEKTE
        
        //Helden sind übergeben
        //Hideouts initialisieren
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

        //erste Verstecke zufällig initialisieren
        HashMap<Hideout, Hero> hideoutHero = new HashMap<>();
        int fieldNumber;
        Random generator = new Random();
        for (Hero h : heroes) {
            do {
                fieldNumber = generator.nextInt(hideouts.size());
            } while (hideoutHero.containsKey(hideouts.get(fieldNumber)));

            hideoutHero.put(hideouts.get(fieldNumber), h);
        }

        //das Mapobjekt zusammensetzen
        GameData standardMap = new GameData(hideoutHero, hideouts, heroes);

        //--------------------
        //VIEW-Objekte
        MapPanel mp = new MapPanel(hideouts, hideoutHero, mainHero);

        GameSidePanel gsp = new GameSidePanel(heroes, mainHero);

        GamePanel gamePanel = new GamePanel(mp, gsp, mainFrame);

        //------------
        //SingleplayerGame als CONTROL-Objekt zusammensetzen
        SingleplayerGame resultGame = new SingleplayerGame(mainFrame, gamePanel, standardMap);
        return resultGame;

    }

    /**
     * Erzeugt ein Test - SingleplayerGame basierend auf der Standardkarte und gibt es
     * zurück. Es enthält 5 Helden (inklusive Hauptspieler).
     *
     * @param mainFrame Das JFrame, in dem dieses SingleplayerGame sich
     * darstellen soll.
     * @return
     */
    public static SingleplayerGame createTestSingleplayerGame(JFrame mainFrame) {

        //Helden initialisieren
        ArrayList<Hero> heroes = new ArrayList<>();
        heroes.add(new HeroBalthur());
        heroes.add(new HeroDahlia());
        heroes.add(new HeroTolpanLongbeard());
        heroes.add(new HeroWorok());
        heroes.add(new HeroFlint());

        heroes.get(4).setPlayerControlled(true);
        Hero mainHero = heroes.get(4);

        SingleplayerGame resultGame = createSingleplayerGame(mainFrame, heroes, mainHero);
        return resultGame;
    }

}
