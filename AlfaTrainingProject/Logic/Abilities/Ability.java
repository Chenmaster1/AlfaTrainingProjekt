package Abilities;

import Actions.Action;
/**
 * Dies ist das Grundgeruest fuer alle Faehigkeiten. Jede Faehigkeit ist gleichzeitig eine <code>Action</code>
 * 
 * @author Kevin
 */
public abstract class Ability extends Action{

	private String name;
	private String description;
	
	private AbilityType abilityTpye;
	/**
	 * Dies ist der Konstruktor fuer jede Faehigkeit
	 * @param actionPointRequired die benoetigte Anzahl an Aktionspunkten fuer die Faehigkeit
	 * @author Kevin
	 */
	public Ability(int actionPointRequired, AbilityType abilityType) {
		super(actionPointRequired);
		this.abilityTpye = abilityType;
	}
	
	
	//-------------------------GETTER-------------------------//
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
}

enum AbilityType{
	TURN, REACTION
}