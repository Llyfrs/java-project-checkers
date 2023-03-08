package checkers;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
 
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
				try {
					

				FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));

				Parent root = loader.load();

				MainMenuController controller = loader.getController();
				controller.setStage(primaryStage);

					
	    		Scene scene = new Scene(root);
	    		
	    		primaryStage.setTitle("Checkers");
	    		primaryStage.setScene(scene);



	    		primaryStage.show();
	    		
				}
				catch (IOException exc) {
					exc.printStackTrace();
				}
    		

    }
}