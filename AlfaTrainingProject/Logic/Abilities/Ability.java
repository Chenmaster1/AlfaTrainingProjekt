package Abilities;

import Actions.Action;

public abstract class Ability extends Action{

	private String name;
	private String description;
	
	public Ability(int actionPointRequired) {
		super(actionPointRequired);
	}
	
	
	//-------------------------GETTER-------------------------//
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
}
