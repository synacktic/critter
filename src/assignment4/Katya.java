package assignment4;

/*
 * Example critter
 */
public class Katya extends Critter {
	
	@Override
	public String toString() { return "K"; }
	
	private static final int GENE_TOTAL = 24;
	private int[] genes = new int[8];
	private int dir;
	private int longevity;
	
	public Katya() {
		for (int k = 0; k < 8; k += 1) {
			getGenes()[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String not_used) { 
		// decide if wants to flee or fight
		int roll = Critter.getRandomInt(10);
		if(roll == 3) 		// lucky number 3, we stand a chance!
			return true;
		else if(roll%2 == 0) 
			return true;	// even fight
		else if(this.getEnergy() > Params.walk_energy_cost){
			roll = Critter.getRandomInt(GENE_TOTAL);
			int turn = 0;
			while (getGenes()[turn] <= roll) {
				roll = roll - getGenes()[turn];
				turn = turn + 1;
			}
			assert(turn < 8);	
			dir = (dir + turn) % 8;
			return false; 	// turn in a direction to run away!!
			}
		else 
			return true; 	// need to try to fight...
		}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		run(dir);
		
		if (longevity % 5 == 0 && this.getEnergy() > Params.run_energy_cost*Params.walk_energy_cost) {	// reproduce every timesteps while have enough health
			Katya child = new Katya();
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
		int roll = Critter.getRandomInt(GENE_TOTAL);
		int turn = 0;
		while (getGenes()[turn] <= roll) {
			roll = roll - getGenes()[turn];
			turn = turn + 1;
		}
		assert(turn < 8);
		
		dir = (dir + turn) % 8;
		
		longevity += 1;	// increase longevity with each time step
	}

	public static void runStats(java.util.List<Critter> Katyas) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : Katyas) {
			Katya c = (Katya) obj;
			total_straight += c.getGenes()[0];
			total_right += c.getGenes()[1] + c.getGenes()[2] + c.getGenes()[3];
			total_back += c.getGenes()[4];
			total_left += c.getGenes()[5] + c.getGenes()[6] + c.getGenes()[7];
		}
		System.out.print("" + Katyas.size() + " total Katyas    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * Katyas.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * Katyas.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * Katyas.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * Katyas.size()) + "% left   ");
		System.out.println();
	}

	public int[] getGenes() {
		return genes;
	}

	public void setGenes(int[] genes) {
		this.genes = genes;
	}
}