/* CRITTERS GUI <MyClass.java>
 * EE422C Project 5 submission by
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

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	
//    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
//
//    static {
//        myPackage = Critter.class.getPackage().toString().split(" ")[1];
//    }
	
	static GridPane ui = new GridPane();			// align controls and world
	static GridPane grid = new GridPane();			// critter world
	static GridPane controls = new GridPane(); 		// for aligning control elements
	
	private ObservableList<Critter> allCritters;	// list of all critter classes you can make
	private ObservableList<Critter> activeCritters;	// list of all critter types currently on the board

	// display elements
	static Button add = new Button("Add");
	static Button step = new Button("Step");
	static Button runStats = new Button("Stats");
	static Button clear = new Button("Clear World");
	static Button quit = new Button("Quit");
	private ComboBox<Critter> critterAddList = new ComboBox(allCritters);
	private ComboBox<Critter> critterStatsList = new ComboBox(activeCritters);
	private IntField addAmount = new IntField(0);
	private IntField stepAmount = new IntField(1);
	private Slider slider = new Slider(0, 50, 1);
	private Text statsText = new Text("");
	private Integer stepnumber = 0;
	private Label stepNum = new Label("Step: " + stepnumber);

	public static void makeSomeCritters() throws Exception{
		   for (int c=0;c < 25; c++) {        			
				Critter.makeCritter("Craig");
				Critter.makeCritter("Critter1");
				Critter.makeCritter("Critter3");
				Critter.makeCritter("Critter4");
				Critter.makeCritter("Critter2");

			}

	    	for (int c=0;c < 100; c++) {        			
				Critter.makeCritter("Algae");
			}
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {	//*** Need to take out the "throws Exception" before submitting I think
			makeSomeCritters();
	    	
			// initialize layout
	        primaryStage.setTitle("Critters");
	        
	        controls.setVgap(10);
	        controls.setHgap(10);
			grid.setGridLinesVisible(true);			
	        ui.setPadding(new Insets(10, 10, 30, 10));
	        ui.getColumnConstraints().add(new ColumnConstraints(300));
	        ui.add(controls, 0, 0);
	        ui.add(grid, 1, 0);
	        
	        // Add Critter Controls
	        controls.add(new Label("Add Critter"), 0, 0);			// title lable
	        controls.add(critterAddList, 0, 1);						// combobox
	        critterAddList.setPrefWidth(150);						
	        critterAddList.setPromptText("Select a Critter...");	// prompt
	        addAmount.setPrefWidth(30);								
	        controls.add(addAmount, 1, 1);							// input field
	       	addAmount.setPromptText("#");							// prompt
	        controls.add(add, 2, 1);								// button
	        
		       add.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
//		            	int count = addAmount.getValue();
//		            	
//		            	for (int c = 0;c < count; c++) {        			
//		        			try {		            	
//		        				String className = ""; //critterAddList.getSelectedItem().toString();
//								Critter.makeCritter(className);
//							} catch (InvalidCritterException e) {}
//		        		}
		            	Critter.displayWorld();
		            }            
		        });	
		       
	        // Step Controls
	        controls.add(new Label("Time Step"), 0, 3);				// title lable
	        controls.add(slider, 0, 4);								// slider
	        slider.setPrefWidth(150);								
	        slider.setShowTickMarks(true);							
	        slider.setShowTickLabels(true);							
	        controls.add(stepAmount, 1, 4);							// input field
	        stepAmount.setPrefWidth(30);							
	        stepAmount.setPromptText("#");							// prompt
	        stepAmount.valueProperty().bindBidirectional(slider.valueProperty()); // bind amount box to slider
	        controls.add(step, 2, 4);								// button
	        controls.add(stepNum, 2, 3);

		       step.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
		            	for (int c=0;c < stepAmount.getValue(); c++) {        			
	                		try {
	                			stepnumber++;
	                			stepNum.setText("Step: " + stepnumber);
								Critter.worldTimeStep();
				            	Critter.displayWorld();
							} catch (InvalidCritterException e) {}
	            		}
		            }            
		        });	
		       
	        // Run Stats Controls
//		       - Do you have the ability for the user to invoke their runStats method? 
//		       - Do you have a panel where the results of runStats is continuously being displayed 
//		         	(updated whenever the view is updated)? 
//		       - Can the user select which critter class(es) have their runStats methods updated? 
//		       - By default is theCritter.runStats base class method invoked each time the view is updated?
	        controls.add(new Label("Run Stats"), 0, 6);				// title lable
	        controls.add(critterStatsList, 0, 7);					// combobox
	        critterStatsList.setPrefWidth(150);				
	        critterStatsList.setPromptText("Select a Critter...");	// prompt
	        controls.add(statsText, 0, 8);							// textbox
	        // sample output in statsText
		        /*
		         * CritterName1
		         * [CritterName1 runStats output]
		         * 
		         * CritterName2
		         * [CritterName2 runStats output]
		         */
	        controls.add(runStats, 1, 7);							// button

		       runStats.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
//		            	try {
//		        			String className  = ""; //critterAddList.getSelectedItem().toString();
//							Class critter = Class.forName(myPackage + "." + className);						
//							Method stats = critter.getMethod("runStats", java.util.List.class);
//							stats.invoke(null, Critter.getInstances(className));
//						}
//							catch (NoSuchMethodException e)		{} 
//							catch (IllegalAccessException e) 	{} 
//							catch (InvocationTargetException e) {}
//							catch (ClassNotFoundException e) 	{}
//							catch (NoSuchElementException e)    {}
//							catch (InvalidCritterException e)   {}
		            }
		        });	
		       
	        // Clear
	        controls.add(clear, 0, 10);								// button
	        
		       clear.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
		            	Critter.clearWorld();
		            	Critter.displayWorld();
		            }            
		        });		
		     
		    // Quit   
	        controls.add(quit, 0, 11);								// button
	        
		       quit.setOnAction(new EventHandler<ActionEvent>() {   	   
		            @Override
		            public void handle(ActionEvent event) {
		            	System.exit(1);
		            }	            
		        });
		       

			
	        Critter.displayWorld();

			Scene scene = new Scene(ui, 710, 410);		
 
	        primaryStage.setScene(scene);
	        primaryStage.show();
	        
	}
}

//public class Main {
//
//	public static void main(String[] args) {
//		 launch(args);
//	}
//
//}

