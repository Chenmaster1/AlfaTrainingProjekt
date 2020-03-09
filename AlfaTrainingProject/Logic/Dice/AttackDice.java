/**
 * AttackDice
 * 
 * Running a W10 Dice
 * 
 * Possibilities
 * 1-4 >> Result_Center_Hit (40%)
 * 5-6 >> Result_Left_Center_Hit (20%)
 * 7-8 >> Result_Right_Center_Hit (20%)
 * 9 >> Outer_Left_Hit (10%)
 * 10 >> Outer_Right_Hit (10%)
 * @author Holger
 */


package Dice;

import java.util.Random;

public class AttackDice extends Dice{
    
    public static final int Result_Center_Hit = 1, Result_Left_Center_Hit = 2, Result_Right_Center_Hit = 3, Outer_Left_Hit = 4, Outer_Right_Hit = 5;

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
                resultAttackDice = Result_Center_Hit;
                break;
            case 1:
                resultAttackDice = Result_Center_Hit;
                break;
            case 2:
                resultAttackDice = Result_Center_Hit;
                break;
            case 3:
                resultAttackDice = Result_Center_Hit;
                break;
            case 4:
                resultAttackDice = Result_Left_Center_Hit;
                break;
            case 5:
                resultAttackDice = Result_Left_Center_Hit;
                break;
            case 6:
                resultAttackDice = Result_Right_Center_Hit;
                break;
            case 7:
                resultAttackDice = Result_Right_Center_Hit;
                break;
            case 8:
                resultAttackDice = Outer_Left_Hit;
                break;
            case 9:
                resultAttackDice = Outer_Right_Hit;
                break;
        }

        return resultAttackDice;
    }

}
