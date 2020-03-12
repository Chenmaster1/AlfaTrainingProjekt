package Dice;

/**
 * Dies ist das Grundgeruest fuer jeden Wuerfel
 * 
 * @author Kevin
 */
public abstract class Dice {

	// Repr�sentiert die Anzahl der m�glichen W�rfelergebnisse
	protected int countSides;

	/**
	 * Diese Methode wird von jedem Wuerfel implementiert und aufgerufen, sobald der
	 * entsprechende Wurf getaetigt werden soll
	 * 
	 * @return gibt die "Augenzahl" zurueck
	 */
	public abstract int rollDice();

}
