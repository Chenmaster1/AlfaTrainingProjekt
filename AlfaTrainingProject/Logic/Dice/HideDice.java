package Dice;

import java.util.Random;


/**
 *
 * @author Bastian
 */
public class HideDice extends Dice
{
	public static final int RESULT_SUCCESS = 1, RESULT_FAILURE = 2, RESULT_NOTHING = 3;

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
        int rolledHideDice = randomDice.nextInt(countSides);

        int resultHideDice = 0;
        // resultHideDice = 0 >> no HideDice was rolled
        // resultHideDice = 1 >> HideDice successful       (rolled HideDice = 0 or 1)
        // resultHideDice = 2 >> HideDice failure           (rolled HideDice = 2)       
        // resultHideDice = 3 >> HideDice nothing happens   (rolled HideDice = 3, 4 or 5)

        switch (rolledHideDice)
        {
            case 0:
                resultHideDice = RESULT_SUCCESS;
                break;
            case 1:
                resultHideDice = RESULT_SUCCESS;
                break;
            case 2:
                resultHideDice = RESULT_FAILURE;
                break;
            case 3:
                resultHideDice = RESULT_NOTHING;
                break;
            case 4:
                resultHideDice = RESULT_NOTHING;
                break;
            case 5:
                resultHideDice = RESULT_NOTHING;
                break;

        }

        //System.out.println("resultHideDice, 1=success, 2=failure, 3=nothing  : " + resultHideDice);
        return resultHideDice;
    }


}

