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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Console;
import java.io.File;
import java.util.List;
import java.util.NoSuchElementException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	
	static GridPane ui = new GridPane();			// align controls and world
	static GridPane grid = new GridPane();			// critter world
	static GridPane controls = new GridPane(); 		// for aligning control elements
	
	private ObservableList<String> allCritters;	// list of all critter classes you can make
	
	// display elements
	private static Button setSeed = new Button("Seed");
	private static Button add = new Button("Add");
	private static Button step = new Button("Step");
	private static Button runStats = new Button("Stats");
	private static Button clear = new Button("Clear World");
	private static Button quit = new Button("Quit");
	private static Button play = new Button("Play");
	private static Button pause = new Button("Stop");
	private ComboBox<String> critterAddList;
	private ComboBox<String> critterStatsList;
	private TextField seedAmount = new TextField();
	private TextField addAmount = new TextField();
	private TextField stepAmount = new TextField();
	private TextArea statsText = new TextArea("");
	private Text seed = new Text("No seed set");
	private Slider slider = new Slider(1, 20, 1);
	private Integer stepnumber = 0;
	private Label stepNum = new Label("Step: " + stepnumber);
	
    private PrintStream ps = new PrintStream(new Console(statsText));

    
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

			//File folder = new File(myPackage);
	        File folder = new File("bin"+File.separator+myPackage);
	        
			File[] listOfFiles = folder.listFiles();
			//FXCollections.observableArrayList() myCrits;
			List<String> myCrit = new java.util.ArrayList<String>();
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		    	  String myClass = listOfFiles[i].toString();
		    	  String[] myParts = myClass.split("\\" + File.separator);
		    	  myClass = myParts[myParts.length-1];
		    	  myParts = myClass.split(".class");
		    	  myClass = myParts[0];
		    	  String class_name = myPackage + "." + myClass;
		    	  //System.out.println(class_name);
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
		    critterAddList = new ComboBox<String>(allCritters);
		    critterStatsList = new ComboBox<String>(allCritters);


	}

	@Override
	public void start(Stage primaryStage) throws Exception {	//*** Need to take out the "throws Exception" before submitting I think
			makeSomeCritters();
			populatePulldowns();
			// initialize layout
	        primaryStage.setTitle("Critters");

	        System.setOut(ps);
	        System.setErr(ps);
	        
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
	        seedAmount.setPrefWidth(130);
	        controls.add(setSeed, 1, 1);
	        controls.add(seed, 0, 2);
	        seed.setFill(Color.GRAY);
	        seedAmount.setPromptText("Seed #");
	        seed.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
	            @Override public void handle(KeyEvent keyEvent) {
		              if (!"0123456789".contains(keyEvent.getCharacter())) {
		                keyEvent.consume();
		              }
		            }
		          });
	        setSeed.setOnAction(new EventHandler<ActionEvent>() {  		    	   
	            @Override
	            public void handle(ActionEvent event) {
	            	if(!seedAmount.getText().trim().isEmpty()){
	            	seed.setText("Seed set to: " + seedAmount.getText());
	            	Critter.setSeed(Integer.parseInt(seedAmount.getText()));
	            	}
	            }            
	        });	

	        // Add Critter Controls
	        controls.add(new Label("Add Critter"), 0, 3);			// title lable
	        controls.add(critterAddList, 0, 4);						// combobox
	        critterAddList.setPrefWidth(130);						
	        critterAddList.setPromptText("Select a Critter...");	// prompt
	        addAmount.setPrefWidth(30);								
	        controls.add(addAmount, 1, 4);	
	        addAmount.setPromptText("#");
	        	addAmount.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
	            @Override public void handle(KeyEvent keyEvent) {
	              if (!"0123456789".contains(keyEvent.getCharacter())) {
	                keyEvent.consume();
	              }
	            }
	          });       
	        controls.add(add, 2, 4);								// button
		       add.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
		            	if(!addAmount.getText().trim().isEmpty()){
		            	System.out.println(Integer.parseInt(addAmount.getText()));

		            	int count = Integer.parseInt(addAmount.getText());
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
		            }     
		        });	
		       
	        // Step Controls
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(400), ae -> timestep(slider.getValue()))); // timestep(slider.getValue())
            timeline.setCycleCount(Animation.INDEFINITE); 	// loop
	           
	        controls.add(new Label("Time Step"), 0, 6);				// title label
	        controls.add(slider, 0, 8);								// slider
	        slider.setMaxWidth(130);								
	        slider.setShowTickMarks(true);							
	        controls.add(play, 1, 8);
		       play.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
		            	// disable add/step action buttons
	                	add.setDisable(true);
	                	step.setDisable(true);
	                	play.setDisable(true);
	                	setSeed.setDisable(true);
	                	clear.setDisable(true);
	                	pause.setDisable(false);
	          	                		                	
	                	timeline.play();                           	
		            }				
		        });		        
		    
		    controls.add(pause, 2, 8);	     
	        pause.setDisable(true);
	        pause.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
		            	// re-enable add/step action buttons
	                	add.setDisable(false);
	                	step.setDisable(false);
	                	play.setDisable(false);
	                	setSeed.setDisable(false);
	                	clear.setDisable(false);
	                	pause.setDisable(true);
	                		                	 
	                	timeline.stop();          	               	
		            }            
		        });	
	        
	        controls.add(stepAmount, 0, 7);							// input field
	        stepAmount.setPrefWidth(30);	
	        stepAmount.setPromptText("#");
	        	stepAmount.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
	        	  @Override public void handle(KeyEvent keyEvent) {
	        	    if (!"0123456789".contains(keyEvent.getCharacter())) {
	        	      keyEvent.consume();
	        	    }
	        	  }
	        	});

	        controls.add(stepNum, 2, 7);
	        controls.add(step, 1, 7);								// button
		       step.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
		            	if(!stepAmount.getText().trim().isEmpty()){
		            		timestep(Double.parseDouble(stepAmount.getText()));
		            	}
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
	        critterStatsList.setPrefWidth(130);				
	        critterStatsList.setPromptText("Select a Critter...");	// prompt

	        controls.add(statsText, 0, 12, 3, 3);					// textbox
	        statsText.setPrefWidth(270);
	        statsText.setPrefHeight(100);
	        controls.add(runStats, 1, 11);							// button
		       runStats.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
	        			try {		            	
	        				String className = critterStatsList.getValue();
	        				if (className != null) {
	        		        	statsText.setText("");
	        					Class critter = Class.forName(Critter.class.getPackage().toString().split(" ")[1] + "." + className);
	        					Method stats = critter.getMethod("runStats", java.util.List.class);
								stats.invoke(null, Critter.getInstances(className));	
								}
	  
						} catch (InvalidCritterException | ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {}
		            }
		        });	
		       
	        // Clear
	        controls.add(clear, 0, 17);								// button        
		       clear.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
		            	Critter.clearWorld();
		            	//Critter.displayWorld();
		            }            
		        });		
		     
		    // Quit   
	        controls.add(quit, 0, 18);								// button	        
		       quit.setOnAction(new EventHandler<ActionEvent>() {   	   
		            @Override
		            public void handle(ActionEvent event) {
		            	System.exit(1);
		            }	            
		        });
		       

			
	        Critter.displayWorld();

			Scene scene = new Scene(ui, 1100, 800);		
 
	        primaryStage.setScene(scene);
	        primaryStage.show();

	}           

private KeyValue timestep(double speed) {
	for (int c=0;c < speed; c++) {        			
		try {
			stepnumber++;
			stepNum.setText("Step: " + stepnumber);
			runStats.fire();
			Critter.worldTimeStep();
		} catch (InvalidCritterException e) {}
	}
	Critter.displayWorld();
	return null;
	}

public static class Console extends OutputStream {

        private TextArea output;

        public Console(TextArea ta) {
            this.output = ta;
        }

        @Override
        public void write(int i) throws IOException {
            output.appendText(String.valueOf((char) i));
        }
    }
   
}

//public class Main {
//
//	public static void main(String[] args) {
//		 launch(args);	       
// 		 //ps.close();
//	}
//
//}
