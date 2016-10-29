/* CRITTERS Critter4.java
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

package assignment5;

import assignment5.Critter.CritterShape;

/**
 * Does not fight if it can bread instead
 * Always eat 
 * Does not move too often, unless it runs from a fight
 * Breeds at a lower energy level
 * Sometimes mutates its genes twice as fast
 *  don't run if it already moved this time around
 * @author Brian
 *
 */
public class Critter4 extends Critter {
	
	@Override
	public String toString() { return "4"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	
	public Critter4() {
		for (int k = 0; k < 8; k += 1) {
			genes[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String other) { 
		
		if (getEnergy() < 75) { // Lover not a fighter
			if(other == "@")
				return true;
			else if (this.hasFlees == true )
				return true;
			else
				return false;
		} else {
			return true; 
		}
		}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		if (Critter.getRandomInt(8) == 4) // Stopping to smell the roses
			walk(dir);
		
		if (getEnergy() > 75) { // Breeds twice as fast
			Critter4 child = new Critter4();
			for (int k = 0; k < 8; k += 1) {
				child.genes[k] = this.genes[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.genes[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			int mutant = 1;
			if (Critter.getRandomInt(8) == 4) { // Sometimes the little mutants change a bit more
				mutant = 2;
			}
			child.genes[g] -= mutant;
			g = Critter.getRandomInt(8);
			child.genes[g] += mutant;
			reproduce(child, Critter.getRandomInt(8));
		}
		
		/* pick a new direction based on our genes */
		int roll = Critter.getRandomInt(GENE_TOTAL);
		int turn = 0;
		while (genes[turn] <= roll) {
			roll = roll - genes[turn];
			turn = turn + 1;
		}
		assert(turn < 8);
		
		dir = (dir + turn) % 8;
	}

	public static void runStats(java.util.List<Critter> fours) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : fours) {
			Critter4 c = (Critter4) obj;
			total_straight += c.genes[0];
			total_right += c.genes[1] + c.genes[2] + c.genes[3];
			total_back += c.genes[4];
			total_left += c.genes[5] + c.genes[6] + c.genes[7];
		}
		System.out.print("" + fours.size() + " total fours    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * fours.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * fours.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * fours.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * fours.size()) + "% left   ");
		System.out.println();
	}
	@Override
	public CritterShape viewShape() { return CritterShape.SQUARE; }

	@Override
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.BLACK; }
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.PURPLE; }}
