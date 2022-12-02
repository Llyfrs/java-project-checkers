package checkers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

	private Stage stage;
	@FXML
	private Button startButton;
	@FXML 
	private Button continueButton;
	@FXML
	private Button exitButton;

	@FXML
	private void initialize() {
		SaveManager saveManager = new SaveManager();
		if(!saveManager.saveExists()) {
			continueButton.setVisible(false);
		}

	}

	@FXML
	public void continueButtonPressed(){
			// Starts the game as usual but then in also makes the controller load game from the save file
			GameBoardController controller = this.startButtonPressed();
			controller.loadLastGame();
	}

	@FXML
	public GameBoardController startButtonPressed() {
		try {
			// Load game board and make loader
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
			Parent root = loader.load();

			// Get game board controller and set its variables it needs to work
			GameBoardController controller = loader.getController();
			controller.setStage(stage);
			controller.setPreviousScene(stage.getScene());

			// Create scene and switch to it
			Scene scene = new Scene(root);
			this.stage.setScene(scene);

			return controller;

		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}



	/**
	 * Closes the program, activated when the closeButton is pressed
	 */
	@FXML
	public void exitButtonPressed() {
		this.stage.close();
	}

	/**
	 * Sets Stage
	 * @param stage - Stage that will is to be used to display GameBoard after New Game or Continue is pressed
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}


}
