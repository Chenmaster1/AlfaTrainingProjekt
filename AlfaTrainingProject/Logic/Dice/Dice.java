package Dice;

public abstract class Dice {

    protected int countSides;
    
    public Dice(int countSides){
        this.countSides = countSides;
    }
    
    public abstract int rollDice();
    
}
