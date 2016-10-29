/* CRITTERS Critter1.java
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

package assignment4;

/**
 * Decides to fight based on random rolls - fight if roll is even, a multiple of 3, or not enough energy to walk
 * Waits to breed - every 5 timesteps as long as energy is high enough
 * Run on odd random rolls in its doTimeStep
 * Less likely to fight its own kind
 * 
 * @author Katya
 *
 */
public class Critter1 extends Critter {
	
	@Override
	public String toString() { return "1"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[24];
	private int dir;
	private int longevity;
	
	public Critter1() {
		for (int k = 0; k < 8; k += 1) {
			getGenes()[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String opponent) { 
		if(opponent == "1"){	// less likely to fight its own kind
			if(Critter.getRandomInt(33)%3 == 0)
				return false;
		}
		
		// decide if wants to flee or fight
		int roll = Critter.getRandomInt(33);
		if(roll%3 == 0) 		// lucky number 3, FIGHT!
			return true;
		else if(roll%2 == 0) 
			return true;		// even fight
		else if(this.getEnergy() > Params.run_energy_cost + 1){
			roll = Critter.getRandomInt(GENE_TOTAL);
			int turn = 0;
			while (getGenes()[turn] <= roll) {
				roll = roll - getGenes()[turn];
				turn = turn + 1;
			}
			assert(turn < 8);	
			dir = (dir + turn + 333) % 8;
			return false; 	// turn in a direction to run away!!
			}
		else 
			return true; 	// guess we fight...
		}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		if(Critter.getRandomInt(10)%2 != 0)
			run(dir);
		
		if (longevity % 5 == 0 && this.getEnergy() > 200) {	// reproduce every timesteps while have enough health
			Critter1 child = new Critter1();
			for (int k = 0; k < 8; k += 1) {
				child.getGenes()[k] = this.getGenes()[k];
			}
			int g = Critter.getRandomInt(8);
			while (child.getGenes()[g] == 0) {
				g = Critter.getRandomInt(8);
			}
			child.getGenes()[g] -= 1;
			g = Critter.getRandomInt(8);
			child.getGenes()[g] += 1;
			reproduce(child, Critter.getRandomInt(8));
		}
		
		/* pick a new direction based on our genes */
		int roll = Critter.getRandomInt(GENE_TOTAL - 1);
		int turn = 0;
		while (getGenes()[turn] <= roll) {
			roll = roll - getGenes()[turn];
			turn = turn + 1;
		}
		assert(turn < 8);
		
		dir = (dir + turn + 333) % 8;
		
		longevity += 1;	// increase longevity with each time step
	}

	public static void runStats(java.util.List<Critter> Critter1s) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : Critter1s) {
			Critter1 c = (Critter1) obj;
			total_straight += c.getGenes()[0];
			total_right += c.getGenes()[1] + c.getGenes()[2] + c.getGenes()[3];
			total_back += c.getGenes()[4];
			total_left += c.getGenes()[5] + c.getGenes()[6] + c.getGenes()[7];
		}
		System.out.print("" + Critter1s.size() + " total Critter1s    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * Critter1s.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * Critter1s.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * Critter1s.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * Critter1s.size()) + "% left   ");
		System.out.println();
	}

	public int[] getGenes() {
		return genes;
	}

	public void setGenes(int[] genes) {
		this.genes = genes;
	}
}
