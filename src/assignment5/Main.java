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
	    	
	        controls.setVgap(10);
	        controls.setHgap(10);
	        ui.setPadding(new Insets(10, 10, 30, 10));
	        	        	        
	        controls.add(new Label("Add Critter"), 0, 0);
	        controls.add(critterAddList, 0, 1);
	        critterAddList.setPrefWidth(150);
	        critterAddList.setPromptText("Select a Critter...");
	        addAmount.setPrefWidth(30);
	        controls.add(addAmount, 1, 1);
	       	addAmount.setPromptText("#");
	        controls.add(add, 2, 1);
	        
	        // Step Controls
	        controls.add(new Label("Time Step"), 0, 2);
	        controls.add(slider, 0, 3);
	        slider.setPrefWidth(150);
	        slider.setShowTickMarks(true);
	        slider.setShowTickLabels(true);
	        controls.add(stepAmount, 1, 3);
	        stepAmount.setPrefWidth(30);
	        stepAmount.setPromptText("#");
	        stepAmount.valueProperty().bindBidirectional(slider.valueProperty()); 	// to do: make the textfield editable outside slider shit
	        controls.add(step, 2, 3);
	        
	        // Run Stats Controls
	        controls.add(new Label("Run Stats"), 0, 4);
	        controls.add(critterStatsList, 0, 5);
	        critterStatsList.setPrefWidth(150);
	        critterStatsList.setPromptText("Select a Critter...");
	        controls.add(runStats, 1, 5);
	        controls.add(statsText, 0, 6);
	        
	        // Quit & Clear
	        controls.add(clear, 0, 8);
	        controls.add(quit, 0, 9);
	        
	        ui.getColumnConstraints().add(new ColumnConstraints(300));
	        ui.add(controls, 0, 0);
	        ui.add(grid, 1, 0);

			grid.setGridLinesVisible(true);
	        primaryStage.setTitle("Critters");
	     
		       add.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {

		            }            
		        });	
		       
		       step.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {

		            }            
		        });	
		       
		       runStats.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {

		            }            
		        });	
		       
		       clear.setOnAction(new EventHandler<ActionEvent>() {  		    	   
		            @Override
		            public void handle(ActionEvent event) {
		            	Critter.clearWorld();
		            	Critter.displayWorld();
		            }            
		        });		
		       
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
/*
public class Main {

	public static void main(String[] args) {
		// launch(args);
	}

}
*/
