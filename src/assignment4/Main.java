/* CRITTERS Main.java
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
import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;
import java.lang.reflect.Method;


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
    public static void main(String[] args)  { 
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
  
        int quit = 0;
        while (quit != 1) {
    		String myLine = kb.nextLine();
    		Scanner ls = new Scanner(myLine);
    		String myAns = ls.next();
    		//Critter.g

			if (myAns.equals("quit")) {
				try {
					if (ls.hasNext())
						throw new IllegalArgumentException();
					quit = 1;
				} catch (IllegalArgumentException e) {
	    			procError(myLine);
	    		}
				
			} else if (myAns.equals("show")) {
				try {
					if (ls.hasNext())
						throw new IllegalArgumentException();
					  Critter.displayWorld();
				} catch (IllegalArgumentException e) {
	    			procError(myLine);
	    		}
			} else if (myAns.equals("stats")) {
				try {
	        			String className = ls.next();	
						Class critter = Class.forName(myPackage + "." + className);						
						Method stats = critter.getMethod("runStats", java.util.List.class);
						stats.invoke(null, Critter.getInstances(className));
					}
						catch (NoSuchMethodException e)		{procError(myLine);} 
						catch (IllegalAccessException e) 	{procError(myLine);} 
						catch (InvocationTargetException e) {procError(myLine);}
						catch (ClassNotFoundException e) 	{procError(myLine);}
						catch (NoSuchElementException e)    {procError(myLine);}
						catch (InvalidCritterException e)   {procError(myLine);}
			} else if (myAns.equals("step")) {
        		
        		try { 
        			int count = ls.nextInt();
        			if (ls.hasNext())
						throw new IllegalArgumentException();
            		for (int c=0;c < count; c++) {        			
                		Critter.worldTimeStep();
            		}
        		} 
        		catch (InputMismatchException e)   {procError(myLine);}
        		catch (IllegalArgumentException e) {procError(myLine);} 
        		catch (InvalidCritterException e) { procError(myLine);}
        		
        	} else if (myAns.equals("seed")) {
        		try {
	        		int seed = ls.nextInt();
	        		if (ls.hasNext())
						throw new IllegalArgumentException();
	        		Critter.setSeed(seed);
	        	} catch (InputMismatchException e) {
	    			procError(myLine);
	    		} catch (IllegalArgumentException e) {
	    			procError(myLine);
	    		}
        	} else if (myAns.equals("clear")) {
        		try {
					if (ls.hasNext())
						throw new IllegalArgumentException();
	        		Critter.clearWorld();
				} catch (IllegalArgumentException e) {
	    			procError(myLine);
	    		}
        	} else if (myAns.equals("make")) {
        		try {
	        		String className = ls.next();
	        		int count = ls.nextInt();
	        		if (ls.hasNext())
						throw new IllegalArgumentException();
	        		for (int c=0;c < count; c++) {        			
	        			Critter.makeCritter(className);
	        		}
	        		} catch (InputMismatchException e) {
		    			procError(myLine);
        			} catch (InvalidCritterException e) {
        				procError(myLine);
	        		} catch (IllegalArgumentException e) {
		    			procError(myLine);
		    		}
            } else {
     	    	System.out.printf("invalid command: %s\n", myLine);
     	    }
        }
 
        /* Write your code above */
       
        System.out.flush();

    }
    public static void procError(String line) {
		System.out.printf("error processing: %s\n",line);
    }
    
}
