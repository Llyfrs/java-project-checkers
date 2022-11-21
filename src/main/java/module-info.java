module java-project-checkers {
	  requires transitive javafx.controls;
	  requires javafx.fxml;
		opens checkers to javafx.fxml;
		exports checkers;
}
