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
import java.awt.TextField;

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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	
	static GridPane ui = new GridPane();
	static GridPane grid = new GridPane();
	static GridPane controls = new GridPane(); 		// for aligning ui elements
	static int WIDTH = 700;
	
	private ObservableList<Critter> critters;
	
	// display elements
	static Button addButton = new Button("Add");
	static Button step = new Button("Step");
	static Button runStats = new Button("Run Stats");
	static Button clear = new Button("Clear World");
	static Button quit = new Button("Quit");
	private ComboBox<Critter> critterAddList = new ComboBox(critters);
	private ComboBox<Critter> critterStatsList = new ComboBox(critters);
	private TextField addAmount = new TextField();
	private TextField stepAmount = new TextField();
	private Slider slider = new Slider(1, 100, 1);
	private Text statsText = new Text("");

	@Override
	public void start(Stage primaryStage) throws Exception {
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
	        controls.setVgap(10);
	        controls.setHgap(10);
	        controls.setPadding(new Insets(10, 10, 30, 10));
	        	        	        
	        controls.add(new Label("Add Critter"), 0, 0);
	        controls.add(critterAddList, 0, 1);
	       // ui.add(addAmount, 1, 1);
	        //addAmount.setPromptText("#");

	        controls.add(addButton, 2, 1);

	        controls.add(new Label("Time Step"), 0, 2);
	        controls.add(slider, 0, 3);
	       //ui.add(stepAmount, 1, 3);
	       //stepAmount.setPromptText("#");

	        controls.add(step, 2, 3);
	        
	        controls.add(new Label("Run Stats"), 0, 4);
	        controls.add(critterStatsList, 0, 5);
	        controls.add(runStats, 2, 5);
	        controls.add(statsText, 0, 6);
	        
	        controls.add(clear, 0, 7);
	        controls.add(quit, 0, 8);
	        
	        ui.getColumnConstraints().add(new ColumnConstraints(250));
	        ui.add(controls, 0, 0);
	        ui.add(grid, 1, 0);

			grid.setGridLinesVisible(true);
	        primaryStage.setTitle("Skitters");
			
		       quit.setOnAction(new EventHandler<ActionEvent>() {
		    	   
		            @Override
		            public void handle(ActionEvent event) {
		            	System.exit(1);
		            }
		            
		        });
			
	        Critter.displayWorld();

			Scene scene = new Scene(ui, WIDTH, 400);		
 
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
