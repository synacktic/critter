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

package assignment4;

import java.util.Iterator;
import java.util.List;


/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	 static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static Sector[][] worldMap = new Sector[Params.world_height][Params.world_width];
	

	//private static Sector[0][1] = new Critter[SIZE][SIZE]
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord = getRandomInt(Params.world_width);
	private int y_coord = getRandomInt(Params.world_height);
	
	private static void updateWorld(Critter that)  {
		worldMap[that.y_coord][that.x_coord] = new Sector(that);
		//System.out.printf("%s at (%d,%d)\n",worldMap[that.y_coord][that.x_coord].critter.toString(),that.x_coord,that.y_coord);
	}
	
	private void move(int distance, int direction){
		// update location
		if(direction == 7 || direction == 0 || direction == 1 )	// right
			this.x_coord += distance;		
		if(direction == 3 || direction == 4 || direction == 5 )	// left
			this.x_coord -= distance;		
		if(direction == 5 || direction == 6 || direction == 7 ) // up
			this.y_coord += distance;		
		if(direction == 5 || direction == 6 || direction == 7 ) // down
			this.y_coord -= distance;
		
		//wrap around world	
		if (this.y_coord < 0) 							// wrap to bottom
			this.y_coord = Params.world_height - 1;
		if (this.y_coord > Params.world_height - 1) 	// wrap to top
			this.y_coord = this.y_coord - Params.world_height;
		if (this.x_coord < 0) 							// wrap to right
			this.x_coord = Params.world_width - 1;
		if (this.x_coord > Params.world_width - 1) 		// wrap to left
			this.x_coord = this.x_coord - Params.world_width;
		
		
	}
	
	protected final void walk(int direction) {
		this.energy -= Params.walk_energy_cost;
		this.move(1, direction);			// update the position
	}
	
	protected final void run(int direction) {
		this.energy -= Params.run_energy_cost;
		this.move(2, direction);			// update the position
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		offspring.energy = (int) Math.floor(this.energy/2);		// make new critter with half health of parent
		this.energy = (int) Math.ceil(this.energy/2);			// decrease parent's energy
		
		offspring.move(1, direction); 							// location is next to parent - in direction	
		
		babies.add(offspring);									// add child to the list of babies for this timestep
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		String class_name = myPackage + "." + critter_class_name.substring(0, 1).toUpperCase() + critter_class_name.substring(1);
		try {
			Object newCritter = Class.forName(class_name).newInstance();// get an instance of the critter 
			population.add((Critter) newCritter);						// add to population list
			updateWorld((Critter) newCritter);
		} catch (InstantiationException e) { throw new InvalidCritterException(class_name);
		} catch (IllegalAccessException e) { throw new InvalidCritterException(class_name);
		} catch (ClassNotFoundException e) { throw new InvalidCritterException(class_name);
		}
		
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
	
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
	}
	
	/**
	 * Call doTimeStep() for every critter
	 */
	public static void worldTimeStep() {
		// do a time step for every Critter in the population
		for(int i = 0; i < population.size(); i+=1){
			population.get(i).doTimeStep();
		}
		
		// clear dead
		
		// check for encounters
		for(int c = 0; c < Params.world_width; c += 1){ 	// columns
			for(int r = 0; r < Params.world_height; r += 1){
				while(worldMap[c][r].hasNext()){			// while there are still overlapping critters
					boolean a = worldMap[c][r].critter.fight(worldMap[c][r].next.critter.toString()); // fight or flee
					boolean b = worldMap[c][r].next.critter.fight(worldMap[c][r].critter.toString()); // fight or flee
					
					// if still in the same position and alive
					if(worldMap[c][r].critter.x_coord == worldMap[c][r].next.critter.x_coord 
							&& worldMap[c][r].critter.y_coord == worldMap[c][r].next.critter.y_coord
							&& worldMap[c][r].critter.energy > 0 && worldMap[c][r].next.critter.energy > 0){

						int roll = 0;
						int rollA = 0; // 0 if they want to flee
						int rollB = 0;
						
						if(a) rollA = Critter.getRandomInt(worldMap[c][r].critter.energy); 	// roll the dice if they want to fight
						if(b) rollB = Critter.getRandomInt(worldMap[c][r].critter.energy);	
							
						if(rollA < rollB){ // critter B wins the fight
							worldMap[c][r].next.critter.energy += (int) Math.ceil(worldMap[c][r].critter.energy / 2); 	// add half the loser's energy to the winner
							worldMap[c][r].critter.energy = 0; 	// kill critter A
						}
						if(rollA > rollB){
							worldMap[c][r].critter.energy += (int) Math.ceil(worldMap[c][r].next.critter.energy / 2); 	// add half the loser's energy to the winner
							worldMap[c][r].next.critter.energy = 0; 	// kill critter B
						}
						else
							roll = Critter.getRandomInt(1);				// randomly decide who dies
							if(roll == 0){
								worldMap[c][r].next.critter.energy = 0; // kill critter B
							}
							else
								worldMap[c][r].critter.energy = 0; 		// kill critter A
					}
				}
			}
			
		}
	}
	
	public static void displayWorld() {
		//Params.world_width;
		//Params.world_height;
		for (int current_height = -1; current_height <= Params.world_height; current_height++ ) {
			if (current_height == -1 || current_height == Params.world_height)
				System.out.printf("+");
			else 
				System.out.printf("|");
			
			for (int current_width = 0; current_width < Params.world_width; current_width++ ) {
				if (current_height == -1 || current_height == Params.world_height)
					System.out.printf("-");
				else {
					//System.out.printf("%d %d\n",current_height,current_width);
					if(worldMap[current_height][current_width] ==  null) {
						System.out.printf(" ");
					} else {
						System.out.printf("%s",worldMap[current_height][current_width].critter.toString());
					}
				}
				
			}
			if (current_height == -1 || current_height == Params.world_height)
				System.out.printf("+");
			else 
				System.out.printf("|");
			System.out.printf("\n");
		}
	}
	
	public static void cremateDead() {
		for (Iterator<Critter> criterator = population.iterator(); criterator.hasNext(); ) {
			Critter crit = criterator.next();
			//worldMap[crit.y_coord][crit.x_coord] = null;
			//while (worldMap[crit.y_coord][crit.x_coord].next != null) {
				
			//}
			if (crit.energy <= 0) {
				criterator.remove();
			}
		}
	
		
	}
	private static class Sector {

		private Critter critter; // The critter here
		private Sector prev; // The critter already in this sector
		private Sector next; // The next critter in the sector

		protected Sector(Critter critter) {
			this.critter = critter;
			//this.prev = prev;
		}
		
		public boolean hasNext(){
			if(this.next != null)
				return true;
			else
				return false;
		}
		
	}
}
