package asuHelloWorldJavaFX;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.event.*;


public class planningPoker extends Stage {
	@FXML ListView<?> userStoriesList;
	
	public planningPoker() {
        try { 
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PokerScreen.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Scene adminScene = new Scene(root,750,500);
            setScene(adminScene); 
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
