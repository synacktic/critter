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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
	static GridPane grid = new GridPane();
	static GridPane ui = new GridPane(); 		// for aligning ui elements
	
	private ObservableList<Critter> critters;
	
	// display elements
	static Button addButton = new Button("Add");
	static Button step = new Button("Step");
	static Button runStats = new Button("Run Stats");
	static Button quit = new Button("Quit");
	private ComboBox<Critter> critterAddList = new ComboBox(critters);
	private ComboBox<Critter> critterStatsList = new ComboBox(critters);
	private TextField addAmount = new TextField();
	private TextField stepAmount = new TextField();
	private Slider slider = new Slider(1, 100, 1);
	private Text statsText = new Text("");

	@Override
	public void start(Stage primaryStage) throws Exception {
	        primaryStage.setTitle("Critters");
	        
	        ui.setVgap(10);
	        ui.setHgap(10);
	        ui.setPadding(new Insets(10, 10, 30, 10));

	        ui.add(grid, 4, 0);
	        
	        ui.add(new Label("Add Critter"), 0, 0);
	        ui.add(critterAddList, 0, 1);
	        //ui.add(addAmount, 1, 1);
	        //addAmount.setPromptText("#");

	        ui.add(addButton, 2, 1);

	        ui.add(new Label("Time Step"), 0, 2);
	        ui.add(slider, 0, 3);
	       //ui.add(stepAmount, 1, 3);
	       //stepAmount.setPromptText("#");

	        ui.add(step, 2, 3);
	        
	        ui.add(new Label("Run Stats"), 0, 4);
	        ui.add(critterStatsList, 0, 5);
	        ui.add(runStats, 2, 5);
	        ui.add(statsText, 0, 6);
	        
	        ui.add(quit, 0, 7);
	        
			grid.setGridLinesVisible(true);
			
		       quit.setOnAction(new EventHandler<ActionEvent>() {
		    	   
		            @Override
		            public void handle(ActionEvent event) {
		            	System.exit(1);
		            }
		            
		        });
			
	        StackPane root = new StackPane();

			Scene scene = new Scene(root, 500, 500);
	        root.getChildren().add(grid);
	        root.getChildren().add(ui);
	        
			// Paints the icons.
			//Painter.paint();
			
		         
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
