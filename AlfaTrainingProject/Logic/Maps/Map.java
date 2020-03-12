package Maps;

import java.util.ArrayList;
import java.util.HashMap;

import Heroes.Hero;
import Hideouts.Hideout;

/**
 * Dies ist das Grundgeruest jedes Spielfelds
 * @author Kevin
 */
public class Map {

	private HashMap<Hideout, Hero> hideoutHero;
	
	private ArrayList<Hideout> hideouts;
	private ArrayList<Hero> heroes;

	/**
	 * Dies ist der Konstruktor eines Spielfelds
	 * @param hideoutHero Hier findet man die Information welcher Held in welchem Hideout versteckt ist
	 * @param hideouts Alle Hideouts im aktuellen Spiel
	 * @param heroes Alle Helden im aktuellen Spiel
	 * @param eventcards Alle Eventkarten im Spiel
	 * @author Kevin
	 */
	public Map(HashMap<Hideout, Hero> hideoutHero, ArrayList<Hideout> hideouts, ArrayList<Hero> heroes) {
           
		this.hideoutHero = hideoutHero;
		this.hideouts = hideouts;
		this.heroes = heroes;
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
	
}
