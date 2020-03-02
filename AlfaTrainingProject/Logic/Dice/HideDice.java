package Dice;

import java.util.Random;


/**
 *
 * @author Bastian
 */
public class HideDice extends Dice
{

    public HideDice(int countSides)
    {
        super(countSides);
    }


    @Override
    public int rollDice()
    {
        //to get here, Hero must be visible and has no Tokens (checked in action class)

        //rolling 6 sided dice (0-5)
        Random randomDice = new Random();
        int rolledHideDice = randomDice.nextInt(5);

        int resultHideDice = 0;
        // resultHideDice = 0 >> no HideDice was rolled
        // resultHideDice = 1 >> HideDice successfull       (rolled HideDice = 0 or 1)
        // resultHideDice = 2 >> HideDice failure           (rolled HideDice = 2)       
        // resultHideDice = 3 >> HideDice nothing happens   (rolled HideDice = 3, 4 or 5)

        switch (rolledHideDice)
        {
            case 0:
                resultHideDice = 1;
                break;
            case 1:
                resultHideDice = 1;
                break;
            case 2:
                resultHideDice = 2;
                break;
            case 3:
                resultHideDice = 3;
                break;
            case 4:
                resultHideDice = 3;
                break;
            case 5:
                resultHideDice = 3;
                break;

        }

        //System.out.println("resultHideDice, 1=success, 2=failure, 3=nothing  : " + resultHideDice);
        return resultHideDice;
    }


}

