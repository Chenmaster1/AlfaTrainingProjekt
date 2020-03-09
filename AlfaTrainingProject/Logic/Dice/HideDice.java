package Dice;

import java.util.Random;


/**
 * When a hero seeks a new hideout
 * Hero needs to roll a 6 sided HideDice
 * 
 * resultHideDice = 1 >> RESULT_SUCCESS (rolled HideDice = 0 or 1)
 * resultHideDice = 2 >> RESULT_FAILURE (rolled HideDice = 2)
 * resultHideDice = 3 >> RESULT_NOTHING (rolled HideDice = 3, 4 or 5)
 * @author Yovo
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
        Random randomDice = new Random();
        int rolledHideDice = randomDice.nextInt(countSides);

        int resultHideDice = 0;
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

        return resultHideDice;
    }


}

