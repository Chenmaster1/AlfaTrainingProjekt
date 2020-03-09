/**
 * AttackDice
 * 
 * Running a W10 Dice
 * 
 * Possibilities
 * 1-4 >> RESULT_CENTER_HIT (40%)
 * 5-6 >> RESULT_LEFT_CENTER_HIT (20%)
 * 7-8 >> RESULT_RIGHT_CENTER_HIT (20%)
 * 9 >> RESULT_OUTER_LEFT_HIT (10%)
 * 10 >> RESULT_OUTER_RIGHT_HIT (10%)
 * @author Holger
 */


package Dice;

import java.util.Random;

public class AttackDice extends Dice{
    
    public static final int RESULT_CENTER_HIT = 1, RESULT_LEFT_CENTER_HIT = 2, RESULT_RIGHT_CENTER_HIT = 3, RESULT_OUTER_LEFT_HIT = 4, RESULT_OUTER_RIGHT_HIT = 5;

    public AttackDice(int countSides){
        super(countSides);
    }
    
    @Override
    public int rollDice() {
    	Random randomDice = new Random();
        int rolledAttackDice = randomDice.nextInt(countSides);
        
     int resultAttackDice = 0;
        switch (rolledAttackDice)
        {
            case 0:
                resultAttackDice = RESULT_CENTER_HIT;
                break;
            case 1:
                resultAttackDice = RESULT_CENTER_HIT;
                break;
            case 2:
                resultAttackDice = RESULT_CENTER_HIT;
                break;
            case 3:
                resultAttackDice = RESULT_CENTER_HIT;
                break;
            case 4:
                resultAttackDice = RESULT_LEFT_CENTER_HIT;
                break;
            case 5:
                resultAttackDice = RESULT_LEFT_CENTER_HIT;
                break;
            case 6:
                resultAttackDice = RESULT_RIGHT_CENTER_HIT;
                break;
            case 7:
                resultAttackDice = RESULT_RIGHT_CENTER_HIT;
                break;
            case 8:
                resultAttackDice = RESULT_OUTER_LEFT_HIT;
                break;
            case 9:
                resultAttackDice = RESULT_OUTER_RIGHT_HIT;
                break;
        }

        return resultAttackDice;
    }

}
