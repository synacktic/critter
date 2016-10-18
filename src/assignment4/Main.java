/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Katya Malyavina
 * ym5356
 * 16465
 * Brian Sutherland
 * bcs2433
 * 16445
 * Slip days used: 0
 * Fall 2016
 * GitHub Repository: https://github.com/synacktic/critter
 */

package assignment4; // cannot be in default package
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     * @throws InvalidCritterException 
     */
    public static void main(String[] args) throws InvalidCritterException { 
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));			
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        //System.out.println("GLHF");
        //System.out.printf("%s",Critter.run(2));
        //Critter.run(2);
        /* Write your code above */
	    for (int c=0;c < 25; c++) {        			
			Critter.makeCritter("Craig");
			Critter.makeCritter("Critter1");
			Critter.makeCritter("Critter3");
			Critter.makeCritter("Critter4");
			if (Critter.getRandomInt(5) == 1)
				Critter.makeCritter("Critter2");


		}

    	for (int c=0;c < 100; c++) {        			
			Critter.makeCritter("Algae");
		}
        int quit = 0;
        while (quit != 1) {
        	System.out.printf(">");
    		String myLine = kb.nextLine();
    		Scanner ls = new Scanner(myLine);
    		String myAns = ls.next();
    		//Critter.g
			if (myAns.equals("quit")) {
				quit = 1;
			} else if (myAns.equals("show")) {
			       Critter.displayWorld();
			} else if (myAns.equals("stats")) {
				try {
	        		String className = ls.next();
					Critter.runStats(Critter.getInstances(className));
				} catch (InvalidCritterException e) {
	    			System.out.printf("error processing: %s\n",myLine);
	    		}
        	} else if (myAns.equals("step")) {
        		try { 
        			int count = ls.nextInt();
            		for (int c=0;c < count; c++) {        			
                		Critter.worldTimeStep();
            		}
        		} catch (InputMismatchException e) {
        			System.out.printf("error processing: %s\n",myLine);
        		}
        		finally {}
        		
        	} else if (myAns.equals("seed")) {
        		try {
	        		int seed = ls.nextInt();
	        		Critter.setSeed(seed);
	        	} catch (InputMismatchException e) {
	    			System.out.printf("error processing: %s\n",myLine);
	    		}
        	} else if (myAns.equals("clear")) {
        		Critter.clearWorld();
		       //Display stuff
        	} else if (myAns.equals("make")) {
        		try {
	        		String className = ls.next();
	        		int count = ls.nextInt();
	        		for (int c=0;c < count; c++) {        			
	        			Critter.makeCritter(className);
	        		}
	        		} catch (InputMismatchException e) {
		    			System.out.printf("error processing: %s\n",myLine);
        			} catch (InvalidCritterException e) {
        				System.out.printf("error processing: %s\n",myLine);
	        		}
        	
		       //Display stuff
     	    }
        }
 
        System.out.flush();

    }
    
}
