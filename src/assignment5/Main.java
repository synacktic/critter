package assignment5;
import java.awt.TextField;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	
	static GridPane grid = new GridPane();
	
	GridPane ui = new GridPane(); 		// for aligning ui elements
	GridPane world = new GridPane(); 	// hold critters
	
	private ObservableList critters;
	
	// display elements for addCritteer controls
	Button addCritter = new Button("Add");
	ComboBox critterAddList = new ComboBox(critters);
	Label addLabel = new Label("Add Critter");
	TextField addAmount = new TextField("#");
	
	// display elements for timestep controls
	Label stepLabel = new Label("Time Step");
	Slider slider = new Slider(1, 100, 1);
	TextField stepAmount = new TextField();
	Button step = new Button("Step");
	
	// display elements for stats controls
	Label statsLabel = new Label("Stats");
	ComboBox critterStatsList = new ComboBox(critters);
	Button runStats = new Button("Run Stats");
	Label statsText = new Label("");
	
	// quit button
	Button quit = new Button("Quit");

	@Override
	public void start(Stage primaryStage) throws Exception {
		   for (int c=0;c < 25; c++) {        			
				Critter.makeCritter("Craig");
				Critter.makeCritter("Critter1");
				Critter.makeCritter("Critter3");
				Critter.makeCritter("Critter4");
				//if (Critter.getRandomInt(5) == 1)
					Critter.makeCritter("Critter2");


			}

	    	for (int c=0;c < 100; c++) {        			
				Critter.makeCritter("Algae");
			}
 
	          
			grid.setGridLinesVisible(true);
	        primaryStage.setTitle("Skitters");

			Scene scene = new Scene(grid, Params.world_width*51,Params.world_height*51 );
			primaryStage.setScene(scene);
			
			primaryStage.show();

			// Paints the icons.
			//Painter.paint();
	    	Critter.displayWorld();

			

	}
}
/*
public class Main {

	public static void main(String[] args) {
		// launch(args);
	}

}
*/
