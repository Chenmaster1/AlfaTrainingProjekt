package Abilities;

import Actions.Action;

/**
 * Dies ist das Grundgeruest fuer alle Faehigkeiten. Jede Faehigkeit ist
 * gleichzeitig eine <code>Action</code>
 *
 * @author Kevin
 */
public abstract class Ability extends Action {

    private String name;
    private String description;

    private AbilityType abilityTpye;

    /**
     * Dies ist der Konstruktor fuer jede Faehigkeit
     *
     * @param actionPointRequired die benoetigte Anzahl an Aktionspunkten fuer
     * die Faehigkeit
     * @author Kevin
     */
    public Ability(int actionPointRequired, AbilityType abilityType, String name, String description) {
        super(actionPointRequired, name);
        this.abilityTpye = abilityType;
        this.description = description;
    }

    //-------------------------GETTER-------------------------//
    public String getDescription() {
        return description;
    }

    public AbilityType getAbilityType() {
        return abilityTpye;
    }

}

enum AbilityType {
    TURN, REACTION
}
