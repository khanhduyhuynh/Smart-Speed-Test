/*
 * Sum Class
 *
 */

/**
 *
 * @author Ken Fakamuria
 */
public class SumsClass {
    private int opA;
    private int opB;
    private int correctSum;
    private int userAnswer;

    public SumsClass()
    {
        opA = opB = correctSum = userAnswer = 0;
    }

    public void setOpsAndSum(int a, int b)
    {
        opA = a;
        opB = b;
        correctSum = a + b;
    }

    public void setUserAnswer(int ua)
    {
        userAnswer = ua;
    }
    public int getCorrectSum()
    {
        return correctSum;
    }
    public int getUserAnswer()
    {
        return userAnswer;
    }

    public int getopA()
    {
		return opA;
	}

	public int getopB()
	{
		return opB;
	}
}
