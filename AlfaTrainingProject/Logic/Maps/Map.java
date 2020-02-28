package Maps;

import java.util.ArrayList;
import java.util.HashMap;

import Cards.EventCard;
import Heroes.Hero;
import Hideouts.Hideout;

public abstract class Map {

	private HashMap<Hideout, Hero> hideoutHero;
	
	private ArrayList<Hideout> hideouts;
	private ArrayList<Hero> heroes;
	private ArrayList<EventCard> eventcards;

	
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
	public ArrayList<EventCard> getEventcards() {
		return eventcards;
	}
	
}
