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
		//TODO Show continue button if save file is found
		continueButton.setVisible(false);
	}

	public MainMenuController() {

	}

	@FXML
	public void continueButtonPressed(){
		//TODO Make continues button to load saved game
	}

	@FXML
	public void startButtonPressed() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
			Parent root = loader.load();

			GameBoardController controller = loader.getController();

			controller.setStage(stage);
			controller.setPreviousScene(stage.getScene());


			Scene scene = new Scene(root);

			this.stage.setScene(scene);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	@FXML
	public void exitButtonPressed() {
		this.stage.close();
	}



	public void setStage(Stage stage) {
		this.stage = stage;
	}


}
