package Hideouts;

public abstract class Hideout {

	private int fieldNumber;
	boolean isActive;
	HideoutType hideoutType;
	
	public Hideout(int fieldNumber, HideoutType hideoutType) {
		this.fieldNumber = fieldNumber;
		this.hideoutType = hideoutType;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public int getFieldNumber() {
		return fieldNumber;
	}
	
	public HideoutType getHideoutType() {
		return hideoutType;
	}
	
	public boolean isActive() {
		return isActive;
	}
}

