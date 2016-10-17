package assignment4;

/**
 * This critter will refuse to fight its own kind. 
 * @author Katya
 *
 */
public class Critter2 extends Critter {
	
	@Override
	public String toString() { return "2"; }
	
	private static final int GENE_TOTAL = 16;
	private int[] genes = new int[8];
	private int dir;
	
	public Critter2() {
		for (int k = 0; k < 8; k += 1) {
			getGenes()[k] = GENE_TOTAL / 8;
		}
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String opponent) { 
		if(opponent == this.toString()){
			return false; // don't fight its own kind!!
		}	
		return true; 
		}

	@Override
	public void doTimeStep() {
		/* take one step forward */
		walk(dir);
		
		if (getEnergy() > 150) {
			Critter2 child = new Critter2();
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
	}

	public static void runStats(java.util.List<Critter> Critter2s) {
		int total_straight = 0;
		int total_left = 0;
		int total_right = 0;
		int total_back = 0;
		for (Object obj : Critter2s) {
			Critter2 c = (Critter2) obj;
			total_straight += c.getGenes()[0];
			total_right += c.getGenes()[1] + c.getGenes()[2] + c.getGenes()[3];
			total_back += c.getGenes()[4];
			total_left += c.getGenes()[5] + c.getGenes()[6] + c.getGenes()[7];
		}
		System.out.print("" + Critter2s.size() + " total Critter2s    ");
		System.out.print("" + total_straight / (GENE_TOTAL * 0.01 * Critter2s.size()) + "% straight   ");
		System.out.print("" + total_back / (GENE_TOTAL * 0.01 * Critter2s.size()) + "% back   ");
		System.out.print("" + total_right / (GENE_TOTAL * 0.01 * Critter2s.size()) + "% right   ");
		System.out.print("" + total_left / (GENE_TOTAL * 0.01 * Critter2s.size()) + "% left   ");
		System.out.println();
	}

	public int[] getGenes() {
		return genes;
	}

	public void setGenes(int[] genes) {
		this.genes = genes;
	}
}
