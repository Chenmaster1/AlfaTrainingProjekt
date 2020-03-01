package Dice;

/**
 * Dies ist das Grundgeruest fuer jeden Wuerfel
 * @author Kevin
 */
public abstract class Dice {

    protected int countSides;
    
    /**
     * Dies ist der Kuntruktor fuer einen Wuerfel
     * @param countSides die Anzahl der Seiten
     */
    public Dice(int countSides){
        this.countSides = countSides;
    }
    
    /**
     * Diese Methode wird von jedem Wuerfel implementiert und aufgerufen, sobald der entsprechende Wurf getaetigt werden soll
     * @return gibt die "Augenzahl" zurueck
     */
    public abstract int rollDice();
    
}
