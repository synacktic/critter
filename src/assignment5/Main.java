package assignment5;
import java.awt.TextField;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
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

			grid.setGridLinesVisible(true);

			Scene scene = new Scene(grid, 500, 500);
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
			// Paints the icons.
			Painter.paint();
			

	}
}
/*
public class Main {

	public static void main(String[] args) {
		// launch(args);
	}

}
*/
