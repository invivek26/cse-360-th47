package asuHelloWorldJavaFX;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class UserWindow extends Stage {
    @FXML Button planningPokerButton;
    @FXML Button effortLogEditorButton;

    public UserWindow() {
        try { 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/effortlogger.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Scene adminScene = new Scene(root,635,500);
            setScene(adminScene); 
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        // Add event handler for button
        planningPokerButton.setOnAction(e -> openNewUserWindow());
        effortLogEditorButton.setOnAction(e -> openNewUserWindow());
    }

    private void openNewUserWindow() {
        planningPoker planningPoker = new planningPoker();
        planningPoker.show();
    }
    private void openEffortLogEditor() {
    	effortLogEditor effortLogEditor = new effortLogEditor();
    	effortLogEditor.show();
    }
}
