// ArrayCarGame.java
// Rowan Rice
// October 9, 2019

/* 

Array Car Game is a program that allows the user to move 10 cars around a 
20 x 20 board.  The cars have four attributes: number, color, ignition state,
and position (x, y).  The color and starting position of the cars will
be set randomly at the start of the program; the ignition will always
start in the off position.  The user selects which car to operate on
and then the program gives the user the choice of turning on the ignition, 
moving the car, or quitting the game as the program moves along.

The program catches errors at each menu (except when the user is 
selecting which car to use) and when a user enters a movement that 
would push the car out of bounds.

*/

import java.util.Scanner;
import java.lang.Math;

public class ArrayCarGame
{

	/* ~~~~~~~~CONSTANTS~~~~~~~~ */
	public static final char RED = 'R';
	public static final char BLACK = 'B';
	public static final char GREEN = 'G';
	public static final char WHITE = 'W';
	public static final char SILVER = 'S';

	public static final char QUIT_SELECTION = 'Q';
	public static final char IGNITION_SELECTION = 'I';
	public static final char MOVE_SELECTION = 'M';

	public static final char MOVE_VERT = 'V';
	public static final char MOVE_HORIZ = 'H';

	public static final int COLUMNS = 20;
	public static final int ROWS = 20;
	public static final int START = 1;

	public static final int CARS = 10;


	/* ~~~~~~~~MAIN METHOD~~~~~~~~ */
	public static void main (String [] args)
	{

		System.out.println("Welcome to the Car Game!");

		// inititalize arrays
		boolean[] ignitionArray = initializeIgnitions();
		char[] colorArray = initializeColors();
		int[] xPosArray = initializeXPos();
		int[] yPosArray = initializeYPos();

		// initialize the loop variable to play the game
		boolean checkGameOn = true;

		Scanner bringInput = new Scanner(System.in);

		int carSelection; // variable for user selection

		while (checkGameOn)
		{
			System.out.print("Which car would you like to use? ");
			System.out.print("(Choose from 1-10):  ");
			carSelection = bringInput.nextInt();
			checkGameOn = playGame(carSelection, ignitionArray, colorArray,
				xPosArray, yPosArray);
		}

	}

	// this is the method that allows the user to play the game with the
	// selected car.  If the user quits the game, the method will return
	// false and the loop (in the main method), that continues asking 
	// the user what car to play with, will stop
	// arguments:   - car selected is the user-entered car number
	//				- all the arrays of the cars
	// returns:		- true if the user did not quit the game
	//				- false if the user did quit the game
	public static boolean playGame(int carSelected, boolean [] ignitions, 
		char [] colors, int [] xPositions, int [] yPositions)
	{
		char mainInput; // variable for main menu entry
		char moveInput; // variable for move menu entry
		int moveUnits; // variable for number spaces to move
		boolean continueGame = true; // variable to return

		int carIndex = carSelected - 1; // calc index in array

		Scanner input = new Scanner(System.in);

		printMainMenu(); // asks user for input
		mainInput = input.next().charAt(0);

		switch (mainInput)
		{
			case QUIT_SELECTION:
			{
				continueGame = false;
				System.out.println("\nThanks for playing!");
				break;
			}

			case IGNITION_SELECTION:
			{
				ignitions[carIndex] = ignitionSwitch(ignitions[carIndex]);
				reportState(xPositions[carIndex], yPositions[carIndex], 
					colors[carIndex], ignitions[carIndex], carSelected);
				break;
			}

			case MOVE_SELECTION:
			{
				printMvmtMenu();
				moveInput = input.next().charAt(0);

				switch (moveInput)
				{
					case MOVE_HORIZ:
					{
						System.out.println("Enter a movement distance: ");
						moveUnits = input.nextInt();
						xPositions[carIndex] = moveHorizontally(xPositions[carIndex], 
							moveUnits, ignitions[carIndex]);
						reportState(xPositions[carIndex], yPositions[carIndex], 
							colors[carIndex], ignitions[carIndex], carSelected);
						break;
					}

					case MOVE_VERT:
					{
						System.out.print("\nEnter a movement distance: ");
						moveUnits = input.nextInt();
						yPositions[carIndex] = moveVertically(yPositions[carIndex], 
							moveUnits, ignitions[carIndex]);
						reportState(xPositions[carIndex], yPositions[carIndex], 
							colors[carIndex], ignitions[carIndex], carSelected);
						break;
					}

					default:
					{
						System.out.println("Error: invalid direction");
						break;
					}
				}				
				break;
			}

			default: // default in the main game loop (main menu input)
			{
				System.out.println("Error: incorrect input. Try again.");
				break;
			}
		}

		return continueGame;
	}

	/* ~~~~~~~~METHODS~~~~~~~~ */


	// creates an array of booleans and loops through
	// the array (size of number of cars) to set them
	// all to false to start
	public static boolean [] initializeIgnitions()
	{
		boolean [] ignArray = new boolean [CARS];

		for (int i = 0; i < ignArray.length; i++)
		{
			ignArray[i] = false;
		}

		return ignArray;
	}

	// creates an array of chars and loops through 
	// the array (size of number of cars) to set 
	// them all to false to start
	public static char [] initializeColors()
	{
		char [] colArray = new char [CARS];

		for (int i = 0; i < colArray.length; i++)
		{
			colArray[i] = assignColor();
		}

		return colArray;
	}

	public static int [] initializeXPos()
	{
		int [] xArray = new int [CARS];

		for (int i = 0; i < xArray.length; i++)
		{
			xArray[i] = randomizePosition();
		}

		return xArray;
	}

	public static int [] initializeYPos()
	{
		int [] yArray = new int [CARS];

		for (int i = 0; i< yArray.length; i++)
		{
			yArray[i] = randomizePosition();
		}

		return yArray;
	}

	// this method assigns a random color to the car when it's called
	// returns a char (which represents a color)	
	public static char assignColor()
	{
		int numToColor = (int)(Math.random()*5);
		char color;

		switch (numToColor)
		{
			case 0:
				color = RED;
				break;

			case 1:
				color = BLACK;
				break;

			case 2:
				color = GREEN;
				break;

			case 3: 
				color = WHITE;
				break;

			default:
				color = SILVER;
				break;
		}

		return color;
	}

	// this method gives a random number between 1 and 20
	// it's called to give the car a random position at start
	// called for both the horizontal and vertical positions
	public static int randomizePosition()
	{
		return ((int) (Math.random()*20)) + 1;
	}

	// prints the main menu
	public static void printMainMenu()
	{
		System.out.println("\nWhat would you like to do?");
		System.out.println("I: turn the ignition on/off");
		System.out.println("M: move the car");
		System.out.println("Q: quit this program");
		System.out.print("Input: ");
	}

	// flips the ignition switch (off to on or on to off)
	public static boolean ignitionSwitch(boolean currIgn)
	{
		return (!currIgn);
	}

	// prints the movement menu
	public static void printMvmtMenu()
	{
		System.out.println("\nIn which direction do you want to move the car?");
		System.out.println("H: Horizontal");
		System.out.println("V: Vertical");
		System.out.print("Direction: ");
	}

	// moves the car's x-position on the grid
	// only moves if ignition is on
	// if the user entered a value that would move the car out of bounds
	// (either before space 1 or past space 20), then the position is not 
	// updated and the current value is passed back
	public static int moveHorizontally (int xPos, int mvmtInput, boolean ignition)
	{
		int desiredPos = xPos + mvmtInput;

		if (ignition) // checks to see if ignition is on
		{
			if ((desiredPos <= COLUMNS) && (desiredPos >= START))
			{
				xPos = desiredPos; // only updates position if in bounds
			}
			else
			{
				System.out.println("Error: Out of bounds");
			}
		}
		else // if ignition is off, don't update the position
		{
			System.out.println("Error: Turn the ignition on");
		}

		return xPos;
	}

	// moves the car's y-position on the grid
	// only moves if ignition is on
	// if the user entered a value that would move the car out of bounds
	// (either before space 1 or past space 20), then the position is not 
	// updated and the current value is passed back
	public static int moveVertically (int yPos, int mvmtInput, boolean ignition)
	{
		int desiredPos = yPos + mvmtInput;

		if (ignition) // check to see if ignition is on 
		{
			if ((desiredPos <= ROWS) && (desiredPos >= START))
			{
				yPos = desiredPos; // only updates position if in bounds
			}
			else
			{
				System.out.println("Error: Out of bounds");
			}
		}
		else // if ignition is off, don't update the position
		{
			System.out.println("Error: Turn the ignition on");
		}

		return yPos;
	}
	
	// prints out the grid and the car's position in the grid 
	// arguments: all of the car's characteristics (position, color, ignition)
	// returns: nothing; prints to the screen 
	// calls other methods to translate the color and ignition into words before
	// 		printing the info (translateColorChar and translateIgnition)
	public static void reportState (int xPos, int yPos, char color, boolean ignition,
		int carSelect)
	{
			// NEED A PRINT STATEMENT FOR CAR SELECTED
			System.out.println("\nCar Information");
			System.out.println("Car: " + carSelect);
			System.out.println("Color: " + translateColorChar(color));
			System.out.println("Ignition: " + translateIgnition(ignition));
			System.out.println("Location: (" + xPos + ", " + yPos + ")\n");

			// this first loop will print all the rows up to (not including)
			// the row that the car is in 
			// uses constants to set dimensions
			for (int rows = START; rows < yPos; rows++)
			{
				for (int cols = START; cols <= COLUMNS; cols++)
				{
					System.out.print("- ");
				}
				System.out.println();
			}

			// second loop prints the row that the car is on up to 
			// (not including) the car position
			for (int carColumn = START; carColumn < xPos; carColumn++)
			{
				System.out.print("- ");
			}

			// print car
			System.out.print(color + " ");

			// third loop prints the the rest of the row that the car is on
			for (int colsRem = START; colsRem <= (COLUMNS - xPos); colsRem++)
			{
				System.out.print("- ");
			}
			System.out.println();

			// fourth loop prints all the rows below the car 
			for (int rowRem = START; rowRem <= (ROWS - yPos); rowRem++)
			{
				for (int cols = START; cols <= COLUMNS; cols++)
				{
					System.out.print("- ");
				}
				System.out.println();
			}
	}

	// translates the char that represents the car's color into a 
	// word to be printed for the user 
	public static String translateColorChar (char color)
	{
		switch (color)
		{
			case RED:
			{
				return "Red";
			}

			case BLACK:
			{	
				return "Black";
			}

			case GREEN:
			{	
				return "Green";
			}

			case WHITE:
			{	
				return "White";
			}

			default:
			{	
				return "Silver";
			}
		}
	}

	// translates the boolean that represents the car's ignition
	// state into a word to be printed for the user
	public static String translateIgnition (boolean ignition)
	{
		if (ignition)
		{
			return "On";
		}

		return "Off";
	}
	
} 
