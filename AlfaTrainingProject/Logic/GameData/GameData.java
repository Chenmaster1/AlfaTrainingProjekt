package GameData;

import java.util.ArrayList;
import java.util.HashMap;

import Heroes.Hero;
import Hideouts.Hideout;

/**
 * Dies ist das Grundgeruest jedes Spielfelds
 * @author Kevin
 */
public class GameData {

	private HashMap<Hideout, Hero> hideoutHero;
	
	private ArrayList<Hideout> hideouts;
	private ArrayList<Hero> heroes;
        
        private HashMap<Integer, Integer> mapIntToVisualField;

	/**
	 * Dies ist der Konstruktor eines Spielfelds
	 * @param hideoutHero Hier findet man die Information welcher Held in welchem Hideout versteckt ist
	 * @param hideouts Alle Hideouts im aktuellen Spiel
	 * @param heroes Alle Helden im aktuellen Spiel
	 * @param eventcards Alle Eventkarten im Spiel
	 * @author Kevin
	 */
	public GameData(HashMap<Hideout, Hero> hideoutHero, ArrayList<Hideout> hideouts, ArrayList<Hero> heroes) {
           
		this.hideoutHero = hideoutHero;
		this.hideouts = hideouts;
		this.heroes = heroes;
                
                fillmapIntToVisualField();
	}
	
	//-------------------------GETTER-------------------------//
	
	public HashMap<Hideout, Hero> getHideoutHero() {
		return hideoutHero;
	}
	public ArrayList<Hideout> getHideouts() {
		return hideouts;
	}
	public ArrayList<Hero> getHeroes() {
		return heroes;
	}


    public HashMap<Integer, Integer> getMapIntToVisualField()
    {
        return mapIntToVisualField;
    }


    private void fillmapIntToVisualField()
    {
        mapIntToVisualField = new HashMap<Integer, Integer>(){{
        put(0 , 20);
        put(1 , 1);
        put(2 , 18);
        put(3 , 4);
        put(4 , 13);
        put(5 , 6);
        put(6 ,10 );
        put(7 , 15);
        put(8 , 2);
        put(9 , 17);
        put(10 , 3);
        put(11 , 19);
        put(12 , 7);
        put(13 , 16);
        put(14 , 8);
        put(15 , 11);
        put(16 , 14);
        put(17 ,9 );
        put(18 , 12);
        put(19 , 5);
        }};
       
    }
	
}
