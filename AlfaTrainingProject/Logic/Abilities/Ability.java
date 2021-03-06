package Abilities;

import Actions.Action;
import enums.AbilityType;

/**
 * Dies ist das Grundgeruest fuer alle Faehigkeiten. Jede Faehigkeit ist
 * gleichzeitig eine <code>Action</code>
 *
 * @author Kevin
 */
public abstract class Ability extends Action {

    
    private String description;

    private AbilityType abilityType;

    /**
     * Dies ist der Konstruktor fuer jede Faehigkeit
     *
     * @param actionPointRequired die benoetigte Anzahl an Aktionspunkten fuer
     * die Faehigkeit
     * @author Kevin
     */
    public Ability(int actionPointRequired, AbilityType abilityType, String name, String description) {
        super(actionPointRequired, name);
        this.abilityType = abilityType;
        this.description = description;
    }

    //-------------------------GETTER-------------------------//
    public String getDescription() {
        return description;
    }

    public AbilityType getAbilityType() {
        return abilityType;
    }

}

