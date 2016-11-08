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
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.NoSuchElementException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
	
	//private ObservableList<Critter> allCritters;	// list of all critter classes you can make
	//private ObservableList<Critter> activeCritters;	// list of all critter types currently on the board
	private ObservableList<String> allCritters;	// list of all critter classes you can make
	private ObservableList<String> activeCritters;	// list of all critter types currently on the board
	// display elements
	static Button add = new Button("Add");
	static Button step = new Button("Step");
	static Button runStats = new Button("Stats");
	static Button clear = new Button("Clear World");
	static Button quit = new Button("Quit");
	static Button animate = new Button("Animate Steps");
	
	//private ComboBox<Critter> critterAddList = new ComboBox(allCritters);
	//private ComboBox<Critter> critterStatsList = new ComboBox(activeCritters);
	private ComboBox<String> critterAddList;
	private ComboBox<String> critterStatsList;
	static Button setSeed = new Button("Seed");
	private TextField seedAmount = new TextField();
	private IntField addAmount = new IntField(1);
	private IntField stepAmount = new IntField(1);
	private Slider slider = new Slider(0, 50, 1);
	private Text statsText = new Text("");
	private Text seed = new Text("No seed set");
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
	
	public void populatePulldowns() throws IllegalAccessException, ClassNotFoundException, IOException {
		   String myPackage = Critter.class.getPackage().toString().split(" ")[1];
	        System.out.printf("%s\n", myPackage);

			//File folder = new File(myPackage);
			File folder = new File("bin"+File.separator+myPackage);

			File[] listOfFiles = folder.listFiles();
			//FXCollections.observableArrayList() myCrits;
			List<String> myCrit = new java.util.ArrayList<String>();
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  String myClass = listOfFiles[i].toString();
		    	  String[] myParts = myClass.split(File.separator);
		    	  myClass = myParts[myParts.length-1];
		    	  myParts = myClass.split(".class");
		    	  myClass = myParts[0];
		    	  String class_name = myPackage + "." + myClass;
		    	  System.out.println(class_name);
		    		try {
		    			Critter newCritter = (Critter) Class.forName(class_name).newInstance();
				      // critterStatsList.getItems().addAll(newCritter);
		    		myCrit.add(myClass);
		    			
				       //allCritters.add("Craig");
		    		} catch (ClassCastException e) { // This is fine, just ignore what won't cast.
		    		} catch (InstantiationException e) { // This also gets thrown on non-critters
		    		}	

		      }
		    }
			allCritters = 
				    FXCollections.observableArrayList(
				        myCrit
			);
		    critterAddList = new ComboBox(allCritters);
		    critterStatsList = new ComboBox(allCritters);


	}

	@Override
	public void start(Stage primaryStage) throws Exception {	//*** Need to take out the "throws Exception" before submitting I think
			makeSomeCritters();
			populatePulldowns();
			// initialize layout
	        primaryStage.setTitle("Critters");
	        
	        controls.setVgap(10);
	        controls.setHgap(10);
			grid.setGridLinesVisible(true);			
	        ui.setPadding(new Insets(10, 10, 30, 10));
	        ui.getColumnConstraints().add(new ColumnConstraints(300));
	        ui.add(controls, 0, 0);
	        ui.add(grid, 1, 0);
	        
	        
	        // Set seed controls
	        controls.add(new Label("Set Seed"), 0, 0);
	        controls.add(seedAmount, 0, 1);
	        controls.add(setSeed, 1, 1);
	        controls.add(seed, 0, 2);
	        seed.setFill(Color.GRAY);	        
	        
	        // Add Critter Controls
	        controls.add(new Label("Add Critter"), 0, 3);			// title lable
	        controls.add(critterAddList, 0, 4);						// combobox
	        critterAddList.setPrefWidth(150);						
	        critterAddList.setPromptText("Select a Critter...");	// prompt
	        addAmount.setPrefWidth(30);								
	        controls.add(addAmount, 1, 4);							// input field
	        controls.add(add, 2, 4);								// button
	        /*addAmount.setOnAction(new EventHandler<ActionEvent>(){
	        	@Override
	            public void handle(ActionEvent event) {
	        		//addAmount = new IntField(addAmount.getValue());
	        		System.out.println(addAmount.getValue());
	        	}
	        });*/
		       add.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
		            	System.out.println(addAmount.getValue());

		            	int count = addAmount.getValue();
		            	System.out.println(count);
		            	for (int c = 0;c < count; c++) {        			
		        			try {		            	
		        				String className = critterAddList.getValue();
		        				if (className != null) 
		        					Critter.makeCritter(className);
		  
							} catch (InvalidCritterException e) {}
		        		}
		            	Critter.displayWorld();
		            }            
		        });	
		       
	        // Step Controls
	        controls.add(new Label("Time Step"), 0, 6);				// title label
	        controls.add(slider, 0, 7);								// slider
	        slider.setPrefWidth(150);								
	        slider.setShowTickMarks(true);							
	        slider.setShowTickLabels(true);							
	        controls.add(stepAmount, 1, 7);							// input field
	        stepAmount.setPrefWidth(30);							
	        stepAmount.valueProperty().bindBidirectional(slider.valueProperty()); // bind amount box to slider
	        controls.add(step, 2, 7);								// button
	        controls.add(animate, 0, 8);
	        controls.add(stepNum, 2, 6);

		       step.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
		            	for (int c=0;c < stepAmount.getValue(); c++) {        			
	                		try {
	                			stepnumber++;
	                			stepNum.setText("Step: " + stepnumber);
								Critter.worldTimeStep();
				            	//Critter.displayWorld();
							} catch (InvalidCritterException e) {}
	            		}
		            }            
		        });	
		       
		       animate.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
	                	add.setDisable(true);
	                	step.setDisable(true);	
	                	
	                	Timeline animation = new Timeline();
	                	 
	                	// need to make this a keyframe action thing
	                	for (int c=0;c < stepAmount.getValue(); c++) {        			
	                		try {
	                			stepnumber++;
	                			stepNum.setText("Step: " + stepnumber);
								Critter.worldTimeStep();
				            	Critter.displayWorld();
							} catch (InvalidCritterException e) {}
	            		}
	                	
	                	animation.play();

	                	add.setDisable(false);
	                	step.setDisable(false);
		            }            
		        });	
		       
	        // Run Stats Controls
//		       - Do you have the ability for the user to invoke their runStats method? 
//		       - Do you have a panel where the results of runStats is continuously being displayed 
//		         	(updated whenever the view is updated)? 
//		       - Can the user select which critter class(es) have their runStats methods updated? 
//		       - By default is theCritter.runStats base class method invoked each time the view is updated?
	        controls.add(new Label("Run Stats"), 0, 10);				// title lable
	        controls.add(critterStatsList, 0, 11);					// combobox
	        critterStatsList.setPrefWidth(150);				
	        critterStatsList.setPromptText("Select a Critter...");	// prompt

	        controls.add(statsText, 0, 12);							// textbox
	        // sample output in statsText
		        /*
		         * CritterName1
		         * [CritterName1 runStats output]
		         * 
		         * CritterName2
		         * [CritterName2 runStats output]
		         */
	        controls.add(runStats, 1, 11);							// button

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
	        controls.add(clear, 0, 14);								// button
	        
		       clear.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
		            	Critter.clearWorld();
		            	//Critter.displayWorld();
		            }            
		        });		
		     
		    // Quit   
	        controls.add(quit, 0, 15);								// button
	        
		       quit.setOnAction(new EventHandler<ActionEvent>() {   	   
		            @Override
		            public void handle(ActionEvent event) {
		            	System.exit(1);
		            }	            
		        });
		       

			
	        Critter.displayWorld();

			Scene scene = new Scene(ui, 750, 550);		
 
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

