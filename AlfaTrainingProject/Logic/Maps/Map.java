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
	
}
