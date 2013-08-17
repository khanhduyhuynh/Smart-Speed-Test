//  Author: Khanh Duy Huynh
//  Date: 23/07/2011
//  File Name: RandomNumber.java

import java.util.Random;
import java.awt.*;
public class RandomNumber
{
	// Declare class contanst
	private final int NUM_RANDOMS = 10;

	// Return an array including ten of first question random numbers
	public int[] getFirst()
	{
		int[] array_First = new int[NUM_RANDOMS];
		for(int i = 0; i < NUM_RANDOMS; i++)
		{
			Random generate = new Random();
			int first = generate.nextInt(100);
			array_First[i] = first;
		}
		return array_First;
	}

	// Return an array including ten of second question random numbers
	public int[] getSecond()
	{
		int[] array_Second = new int[NUM_RANDOMS];
		for(int j = 0; j < 10; j++)
		{
			Random generate = new Random();
			int second = generate.nextInt(100);
			array_Second[j] = second;
		}
		return array_Second;
	}
}