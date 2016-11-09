package assignment5;

import java.util.List;

import assignment5.Critter.CritterShape;
//import assignment5.Critter.CritterShape;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;

import javafx.scene.shape.Shape;

import java.util.Iterator;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR,
		SCENE
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	private static boolean terraformed = false;
	public abstract CritterShape viewShape(); 
	static Shape getIcon(CritterShape shape,javafx.scene.paint.Color line,javafx.scene.paint.Color fill) {
		Shape s = null;
	
		int size;
		if(Params.world_height > Params.world_width)
			size = 770 / Params.world_height;
		else	
		 size = 770 / Params.world_width;
		int ssize = size;
		double dsize = (double) ssize-1;

		switch(shape) {
		//	public CritterShape viewShape() { return CritterShape.SQUARE; }

		//viewShape()
	
		case CIRCLE: s = new Circle(ssize/2); 
			s.setFill(fill); break;
		case SQUARE: s = new Rectangle(ssize, ssize); 
			s.setFill(fill); break;
		case DIAMOND: s = new Polygon(); 
			((Polygon) s).getPoints().addAll(new Double[]{dsize/2, 0.0, 0.0, dsize/2, dsize/2, dsize ,dsize, dsize/2});
			s.setFill(fill); break;
		case STAR: s = new Polygon(); 
			((Polygon) s).getPoints().addAll(new Double[]{	
			0.0, dsize*.4,
			dsize*.35,dsize*.4,
			dsize*.5,0.0,
			dsize*.65,dsize*.4,
			dsize, dsize*.4, 
			dsize*.75,dsize*.6,
			dsize, dsize,
			dsize*.5,dsize*.75,
			0.0, dsize,
			dsize*.25,dsize*.6
			});

			s.setFill(fill); break;
		case TRIANGLE: s = new Polygon(); 
			((Polygon) s).getPoints().addAll(new Double[]{1.0, dsize-1, dsize/2, 1.0, dsize-1, dsize-1});
			s.setFill(fill); break;
		case SCENE: s = new Rectangle(size, size); 
			s.setFill(fill); break;
		default:
			break;
		}
		// set the outline of the shape
		s.setStroke(line); // outline
		return s;
	}
	
	private Shape sNode;
	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
    private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private static Sector[][] worldMap = new Sector[Params.world_height][Params.world_width];
	

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	private int xWrap(int x, int offset) {
		x += offset;
		if (x < 0) 							// wrap to right
			x = Params.world_width - 1;
		if (x > Params.world_width - 1) 		// wrap to left
			x = x - Params.world_width;
		return x;
	}
	private int yWrap(int y, int offset) {
		y += offset;
		if (y < 0) 							// wrap to bottom
			y = Params.world_height - 1;
		if (y > Params.world_height - 1) 	// wrap to top
			y = y - Params.world_height;
		return y;
	}
	// return what critter it sees
    protected String look(int direction, boolean steps) {
    	this.energy -= Params.look_energy_cost; // pay energy cost
    

		switch (direction){
			case 0: // 0: (1,0) 
				if(steps && worldMap[xWrap(x_coord,1)][y_coord] != null) {
					return worldMap[xWrap(x_coord,1)][y_coord].neighbors.get(0).toString();					
				}
				else if (worldMap[xWrap(x_coord,2)][y_coord] != null){
					return worldMap[xWrap(x_coord,2)][y_coord].neighbors.get(0).toString();
				}
				break;
			case 1: // 1: (1,-1)
				if(steps && worldMap[xWrap(x_coord,1)][yWrap(y_coord,-1)] != null) {
					return worldMap[xWrap(x_coord,1)][yWrap(y_coord,-1)].neighbors.get(0).toString();					
				}
				else if (!steps && worldMap[xWrap(x_coord,2)][yWrap(y_coord,-2)] != null){
					return worldMap[xWrap(x_coord,2)][yWrap(y_coord,-2)].neighbors.get(0).toString();
				}
				break;
			case 2: // 2: (0,-1)
				if(steps && worldMap[x_coord][yWrap(y_coord,-1)] != null) {
					return worldMap[x_coord][yWrap(y_coord,-1)].neighbors.get(0).toString();					
				}
				else if (!steps && worldMap[x_coord][yWrap(y_coord,-2)] != null){
					return worldMap[x_coord][yWrap(y_coord,-2)].neighbors.get(0).toString();
				}
				break;
			case 3: // 3: (-1,-1)
				if(steps && worldMap[xWrap(x_coord,-1)][yWrap(y_coord,-1)] != null) {
					return worldMap[xWrap(x_coord,-1)][yWrap(y_coord,-1)].neighbors.get(0).toString();					
				}
				else if (!steps && worldMap[xWrap(x_coord,-2)][yWrap(y_coord,-2)] != null){
					return worldMap[xWrap(x_coord,-2)][yWrap(y_coord,-2)].neighbors.get(0).toString();
				}
				break;
			case 4: // 4: (-1,0)
				if(steps && worldMap[xWrap(x_coord,-1)][y_coord] != null) {
					return worldMap[xWrap(x_coord,-1)][y_coord].neighbors.get(0).toString();					
				}
				else if (!steps && worldMap[xWrap(x_coord,-2)][y_coord] != null){
					return worldMap[xWrap(x_coord,-2)][y_coord].neighbors.get(0).toString();
				}
				break;
			case 5: // 5: (-1,1)
				if(steps && worldMap[xWrap(x_coord,-1)][yWrap(y_coord,1)] != null) {
					return worldMap[xWrap(x_coord,-1)][yWrap(y_coord,1)].neighbors.get(0).toString();					
				}
				else if (!steps && worldMap[xWrap(x_coord,-2)][yWrap(y_coord,2)] != null){
					return worldMap[xWrap(x_coord,-2)][yWrap(y_coord,2)].neighbors.get(0).toString();
				}
				break;
			case 6: // 6: (0,1)
				if(steps && worldMap[x_coord][yWrap(y_coord,1)] != null) {
					return worldMap[x_coord][yWrap(y_coord,1)].neighbors.get(0).toString();					
				}
				else if (!steps && worldMap[x_coord][yWrap(y_coord,2)] != null){
					return worldMap[x_coord][yWrap(y_coord,2)].neighbors.get(0).toString();
				}
				break;
			case 7: // 7: (1,1)
				if(steps && worldMap[xWrap(x_coord,1)][yWrap(y_coord,1)] != null) {
					return worldMap[xWrap(x_coord,1)][yWrap(y_coord,1)].neighbors.get(0).toString();					
				}
				else if (!steps && worldMap[xWrap(x_coord,2)][yWrap(y_coord,2)] != null){
					return worldMap[xWrap(x_coord,2)][yWrap(y_coord,2)].neighbors.get(0).toString();
				}
				break;
			}
    	
    	return null;
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
    protected boolean hasFlees = false;
	/**
	 * Add a critter to the world map
	 * @param that The critter to add
	 */
	private static void updateWorld(Critter that)  {
		if (worldMap[that.y_coord][that.x_coord] == null) {
			worldMap[that.y_coord][that.x_coord] = new Sector(that);
		} else {
			worldMap[that.y_coord][that.x_coord].neighbors.add(that);
		}
	}
	/**
	 * Move a critter around in the world
	 * @param distance How many steps to move
	 * @param direction The direction to move in
	 */
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
		Main.grid.getChildren().remove(this.sNode);
		worldMap[y_old][x_old].neighbors.remove(this);
		updateWorld(this);

	}
	/**
	 * Call direction to move a single step
	 * @param direction The direction to go
	 */
	protected final void walk(int direction) {
		this.energy -= Params.walk_energy_cost;
		this.move(1, direction);			// update the position
	}
	/**
	 *  Call direction to move a two steps
	 * @param direction The direction to go
	 */
	protected final void run(int direction) {
		this.energy -= Params.run_energy_cost;
		this.move(2, direction);			// update the position
	}
	/**
	 * setup offspring to reflect its parent and send it off to another place
	 * @param offspring The already initialized offspring
	 * @param direction The direction the offspring will be sent
	 * 
	 */
	protected final void reproduce(Critter offspring, int direction) {
		if (this.energy < Params.min_reproduce_energy)
			return;
		offspring.energy = (int) Math.floor(this.energy/2);		// make new critter with half health of parent
		this.energy = (int) Math.ceil(this.energy/2);			// decrease parent's energy
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		updateWorld((Critter) offspring);						//Add to world to avoid move errors

		offspring.move(1, direction); 							// location is next to parent - in direction	
		
		babies.add(offspring);									// add child to the list of babies for this timestep
	}/*import assignment4.Critter;
	import assignment4.InvalidCritterException;
	import assignment4.Params;
	import assignment4.Critter.Sector;*/

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
		} catch (InstantiationException e) { throw new InvalidCritterException("");
		} catch (IllegalAccessException e) { throw new InvalidCritterException("");
		} catch (ClassNotFoundException e) { throw new InvalidCritterException("");
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
	    try {
		Class<?> critterClass = Class.forName(class_name);
		
		for (Critter crit: population) {
			// if Critter is an instance of critter_class_name, add it to the list
			if(critterClass.isInstance(crit))
				result.add(crit);
			}

	    } catch (ClassNotFoundException ex) {
	        throw new InvalidCritterException(class_name);
	    }
		
		return result;
	}/*import assignment4.Critter;


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
	 */
	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population = new java.util.ArrayList<Critter>();;
		babies = new java.util.ArrayList<Critter>();
		for(int i = 0; i < Params.world_height; i += 1){
			for(int k = 0; k < Params.world_width; k+= 1){
				if (worldMap[i][k] != null && worldMap[i][k].neighbors.size()>0 && worldMap[i][k].neighbors.get(0).sNode != null)
					Main.grid.getChildren().remove(worldMap[i][k].neighbors.get(0).sNode);
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
			int oX = crit.x_coord;
			int oY = crit.y_coord;
			crit.doTimeStep();
			if(oX != crit.x_coord || oY != crit.y_coord)
				crit.hasFlees = true;
		}
		
	
		// clear dead
		Iterator<Critter> alive = population.iterator();
	      while(alive.hasNext()) {
	          Critter next = alive.next();
	          if(next.getEnergy() <= 0){
				Main.grid.getChildren().remove(next.sNode);
	  			worldMap[next.y_coord][next.x_coord].neighbors.remove(next);
	        	alive.remove();
	          }
	       }
		
	  	// check for encounters
	      while(checkOverlap()){
		    encounter();
	      }
	      clearFlees();
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
	private static void terraform() {
		
		for (int current_height = 0; current_height < Params.world_height; current_height++ ) {	
			for (int current_width = 0; current_width < Params.world_width; current_width++ ) {
					//System.out.println(current_width);
					Shape tNode = getIcon(
							CritterShape.SQUARE,
							javafx.scene.paint.Color.LIGHTGREY,
							javafx.scene.paint.Color.WHITESMOKE
							);
					Main.grid.add(tNode, current_width, current_height);
			}
		}

	}
	/**
	 * Print the worldMap to the console with a nice border
	 */
	public static void displayWorld() {
		if (!terraformed) {
			terraform();
			terraformed=false;
		}
		for (int current_height = -1; current_height <= Params.world_height; current_height++ ) {
		
			for (int current_width = 0; current_width < Params.world_width; current_width++ ) {
				if (current_height == -1 || current_height == Params.world_height) {
				} else {
					if(worldMap[current_height][current_width] ==  null || worldMap[current_height][current_width].neighbors.size() == 0 ) {
					} else {
						if (worldMap[current_height][current_width].neighbors.get(0).sNode == null)
							worldMap[current_height][current_width].neighbors.get(0).sNode = getIcon(
								worldMap[current_height][current_width].neighbors.get(0).viewShape(),
								worldMap[current_height][current_width].neighbors.get(0).viewOutlineColor(),
								worldMap[current_height][current_width].neighbors.get(0).viewFillColor()
								);
						else
							Main.grid.getChildren().remove(worldMap[current_height][current_width].neighbors.get(0).sNode);
						
						Main.grid.add(worldMap[current_height][current_width].neighbors.get(0).sNode, current_width, current_height);
					}
				}
				
			}
			if (current_height == -1 || current_height == Params.world_height)
				System.out.printf("");
			else 
				System.out.printf("");
			System.out.printf("");
		}
	}
	/**
	 * Search the world for any creatures that need to fight, make them fight
	 */
	private static void encounter(){
		for(int c = 0; c < Params.world_height; c += 1){ 	
			for(int r = 0; r < Params.world_width; r += 1){
					while(worldMap[c][r] != null && worldMap[c][r].neighbors.size() > 1){			// while there are still overlapping critters
						Critter critA = worldMap[c][r].neighbors.get(0);
						Critter critB = worldMap[c][r].neighbors.get(1);
						boolean a = critA.fight(critB.toString());
						boolean b = critB.fight(critA.toString());
						
						if (!a && critA.hasFlees) {
							critA.energy -= Params.walk_energy_cost;
							a = true;
						}
						
						if (!b && critB.hasFlees) {
							critB.energy -= Params.walk_energy_cost;
							b = true;
						}
						
						if (a == false)
							critA.hasFlees = true;
						if (b == false)
							critB.hasFlees = true;
						
						if(critA.x_coord == critB.x_coord 
								&& critA.y_coord == critB.y_coord
								&& critA.energy > 0 && critB.energy > 0
								&& a|b){

							int roll = 0;
							int rollA = 0; // 0 if they want to flee
							int rollB = 0;
							
							if(a && critA.toString() != "@") rollA = Critter.getRandomInt(critA.energy); 	// roll the dice if they want to fight
							if(b && critB.toString() != "@") rollB = Critter.getRandomInt(critB.energy);	

							if(rollA < rollB){ // critter B wins the fight
								critB.energy += (int) Math.ceil(critA.energy / 2); 	// add half the loser's energy to the winner
								Main.grid.getChildren().remove(critA.sNode);
								worldMap[c][r].neighbors.remove(critA);  			// kill critter A
								population.remove(critA);
							} else if(rollA > rollB){
								critA.energy += (int) Math.ceil(critB.energy / 2); 	// add half the loser's energy to the winner
								Main.grid.getChildren().remove(critB.sNode);
								worldMap[c][r].neighbors.remove(critB); 		 	// kill critter B
								population.remove(critB);
							}
							else {
								roll = Critter.getRandomInt(2);					// randomly decide who dies
								if(roll == 0){
									Main.grid.getChildren().remove(critB.sNode);
									worldMap[c][r].neighbors.remove(critB); 		// kill critter B
									population.remove(critB);
								}
								else
									Main.grid.getChildren().remove(critA.sNode);
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
	
	/**
	 * See if there are more than critter occupy any sector
	 */
	private static boolean checkOverlap(){
		for(int c = 0; c < Params.world_height; c += 1){ 	
			for(int r = 0; r < Params.world_width; r += 1){
				if (worldMap[c][r] != null) {
					if(worldMap[c][r].neighbors.size()>1) return true;
				}
			}
		}
		return false;
	}
	/**
	 * See if there are more than critter occupy any sector
	 */
	private static void clearFlees(){
		for(int c = 0; c < Params.world_height; c += 1){ 	
			for(int r = 0; r < Params.world_width; r += 1){
				if (worldMap[c][r] != null) {
					for (Critter crit: worldMap[c][r].neighbors)
						crit.hasFlees = false;
				}
			}
		}
	}
	/**
	 * Sectors for the critters to live in.
	 */
	private static class Sector {

		//private Critter critter; // The critter here
		private List<Critter> neighbors;// For critters that share the same address

		private Sector(Critter critter) {
			//this.critter = critter;
			    neighbors = new java.util.ArrayList<Critter>();
			    neighbors.add(critter);
			}
		
	}
}
