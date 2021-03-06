package Hideouts;

/**
 * Das Grundgeruest fuer jedes Hideout
 * @author Kevin
 *
 */
public class Hideout {

	/**
	 * liegt zwischen 0 und 19
	 */
	private final int fieldNumber;
	
	private boolean isActive;
	
	private HideoutType hideoutType;
	
	/**
	 * Dies ist der Konstruktor fuer das Hideout
	 * @param fieldNumber Die interne Nummer des Hideouts
	 * @param hideoutType Die Art des Hideouts
	 * @author Kevin
	 */
	public Hideout(int fieldNumber, HideoutType hideoutType) {
		this.fieldNumber = fieldNumber;
		this.hideoutType = hideoutType;
	}
	
	//-------------------------GETTER-------------------------//
	
	public int getFieldNumber() {
		return fieldNumber;
	}
	
	public HideoutType getHideoutType() {
		return hideoutType;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	//-------------------------SETTER-------------------------//
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	

}

