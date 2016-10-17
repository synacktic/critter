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
	
	private int energy = Params.start_energy; 
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	private static void updateWorld(Critter that)  {
		if (worldMap[that.y_coord][that.x_coord] == null) {
			worldMap[that.y_coord][that.x_coord] = new Sector(that);
		} else {
			worldMap[that.y_coord][that.x_coord].neighbors.add(that);
		}

		//worldMap[that.y_coord][that.x_coord].neighbors.add(that);
		//= new java.util.ArrayList<Critter>();
		//System.out.printf("%s at (%d,%d)\n",worldMap[that.y_coord][that.x_coord].critter.toString(),that.x_coord,that.y_coord);
	}
	
	private void move(int distance, int direction){
		// update location
		int x_old = this.x_coord;
		int y_old = this.y_coord;

		if(direction == 0 || direction == 1 || direction == 7 )	// right
			this.x_coord += distance;		
		if(direction == 3 || direction == 4 || direction == 5 )	// left
			this.x_coord -= distance;		
		if(direction == 5 || direction == 6 || direction == 7 ) // up
			this.y_coord -= distance;		
		if(direction == 1 || direction == 2 || direction == 3 ) // down
			this.y_coord += distance;
		
		//wrap around world	
		if (this.y_coord < 0) 							// wrap to bottom
			this.y_coord = Params.world_height - 1;
		if (this.y_coord > Params.world_height - 1) 	// wrap to top
			this.y_coord = this.y_coord - Params.world_height;
		if (this.x_coord < 0) 							// wrap to right
			this.x_coord = Params.world_width - 1;
		if (this.x_coord > Params.world_width - 1) 		// wrap to left
			this.x_coord = this.x_coord - Params.world_width;
		
		worldMap[y_old][x_old].neighbors.remove(this);
		updateWorld(this);

		//worldMap[this.y_coord][this.x_coord].neighbors.remove(this);

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
		//System.out.println("New Baby!");
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		updateWorld((Critter) offspring);						//Add to world to avoid move errors

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
			((Critter) newCritter).x_coord = getRandomInt(Params.world_width);
			((Critter) newCritter).y_coord = getRandomInt(Params.world_height);

			population.add((Critter) newCritter);						// add to population list
			updateWorld((Critter) newCritter);
		} catch (InstantiationException e) { throw new InvalidCritterException(class_name);
		} catch (IllegalAccessException e) { throw new InvalidCritterException(class_name);
		} catch (ClassNotFoundException e) { throw new InvalidCritterException(class_name);
		}	
	}
	
	private static boolean isInstance(Object o, String className) {
	    try {
	        Class clazz = Class.forName(className);
	        return clazz.isInstance(o);
	    } catch (ClassNotFoundException ex) {
	        return false;
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

		String class_name = myPackage + "." + critter_class_name.substring(0, 1).toUpperCase() + critter_class_name.substring(1);	
		
		// if Critter is an instance of critter_class_name, add it to the list
		for (Critter crit: population) {
			if(isInstance(crit, class_name))
				result.add(crit);
			}	
		
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
		population = new java.util.ArrayList<Critter>();;
		babies = new java.util.ArrayList<Critter>();
		for(int i = 0; i < Params.world_height; i += 1){
			for(int k = 0; k < Params.world_width; k+= 1){
					worldMap[i][k] = null;
			}
		}
	}
	
	/**
	 * Call doTimeStep() for every critter
	 * @throws InvalidCritterException 
	 */
	public static void worldTimeStep() throws InvalidCritterException {
		// do a time step for every Critter in the population
		for (Critter crit: population) {
			crit.doTimeStep();
		}
		
//		for(int i = 0; i < population.size(); i+=1){
//			 population.get(i).doTimeStep();
//		 }

	
		// clear dead
		Iterator<Critter> alive = population.iterator();
	      while(alive.hasNext()) {
	          Critter next = alive.next();
	          if(next.getEnergy() <= 0){
	  			worldMap[next.y_coord][next.x_coord].neighbors.remove(next);
	        	alive.remove();
	          }
	       }
		
	  	// check for encounters
	      while(checkOverlap()){
		    encounter();
	      }
	    
	    // add babies to population and clear babies
	    for(Critter baby : babies){
	    	population.add(baby);
	    }
	    babies = new java.util.ArrayList<Critter>();
	    
	    // refresh algae 
		for (int c = 0; c<Params.refresh_algae_count; c++) {
				Critter.makeCritter("Algae");
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
					if(worldMap[current_height][current_width] ==  null || worldMap[current_height][current_width].neighbors.size() == 0 ) {
						System.out.printf(" ");
					} else {
						System.out.printf("%s",worldMap[current_height][current_width].neighbors.get(0).toString());
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
	
	private static void encounter(){
		for(int c = 0; c < Params.world_height; c += 1){ 	
			for(int r = 0; r < Params.world_width; r += 1){
					while(worldMap[c][r] != null && worldMap[c][r].neighbors.size() > 1){			// while there are still overlapping critters
						//System.out.println("encounter!!");
						Critter critA = worldMap[c][r].neighbors.get(0);
						Critter critB = worldMap[c][r].neighbors.get(1);
						
						boolean a = critA.fight(critB.toString()); // fight or flee
						boolean b = critB.fight(critA.toString()); // fight or flee
						if(critA.x_coord == critB.x_coord 
								&& critA.y_coord == critB.y_coord
								&& critA.energy > 0 && critB.energy > 0
								&& a|b){
							//System.out.printf("%s vs %s\n",critA.toString(),critB.toString());

							int roll = 0;
							int rollA = 0; // 0 if they want to flee
							int rollB = 0;
							
							if(a) rollA = Critter.getRandomInt(critA.energy); 	// roll the dice if they want to fight
							if(b) rollB = Critter.getRandomInt(critB.energy);	
							//if (rollA == rollB)
							//System.out.printf("%d %d\n",rollA,rollB);

							if(rollA < rollB){ // critter B wins the fight
								critB.energy += (int) Math.ceil(critA.energy / 2); 	// add half the loser's energy to the winner
								worldMap[c][r].neighbors.remove(critA);  			// kill critter A
								population.remove(critA);
							} else if(rollA > rollB){
								critA.energy += (int) Math.ceil(critB.energy / 2); 	// add half the loser's energy to the winner
								//System.out.println("Critter A wins!");
								worldMap[c][r].neighbors.remove(critB); 		 	// kill critter B
								population.remove(critB);
							}
							else {
								roll = Critter.getRandomInt(2);					// randomly decide who dies
								if(roll == 0){
									//System.out.println("Critter A wins!");
									worldMap[c][r].neighbors.remove(critB); 		// kill critter B
									population.remove(critB);
								}
								else
									//System.out.println("Critter B wins!");
									worldMap[c][r].neighbors.remove(critA);  		// kill critter A}									
									population.remove(critA);
							}
					} else {
						//Flee
						critA.walk(Critter.getRandomInt(8));
						critB.walk(Critter.getRandomInt(8));
						
					}
				}
			}
		}

	}
	
	private static boolean checkOverlap(){
		for(int c = 0; c < Params.world_height; c += 1){ 	
			for(int r = 0; r < Params.world_width; r += 1){
				if(worldMap[c][r].neighbors.size()>1) return true;
			}
		}
		return false;
	}
	
	private static class Sector {

		private Critter critter; // The critter here
		private List<Critter> neighbors;// = new java.util.ArrayList<Critter>();

		//worldMap[that.y_coord][that.x_coord] = 
	//	private static List<Critter> neighbors = new java.util.ArrayList<Critter>();
		//protected Sector() {
			
	//	}
	//	protected Sector(List<Critter> neighbors) {
	//		this.critter = critter;
	//	}
	private Sector(Critter critter) {
		this.critter = critter;
		    neighbors = new java.util.ArrayList<Critter>();
		    neighbors.add(critter);
		}
		
	}
}
