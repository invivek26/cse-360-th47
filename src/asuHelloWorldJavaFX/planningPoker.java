package asuHelloWorldJavaFX;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.event.*;


public class planningPoker extends Stage {
    private int points;

    // Simulate getting story points from a data source
    public void push() {
        points = (int) (Math.random() * 20);
    }

    public planningPoker() {
        Button btn = new Button();
        btn.setText("Analyse Historical Data");

        Label L1 = new Label();  // Label to display story points
        TextField setScore = new TextField();  // Input field for setting story points
        Button B1 = new Button("Set-Points");  // Button to set story points

        // Event handler for the "Analyse Historical Data" button
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                push();
                L1.setText("User Story Points: " + points);
            }
        });

        // Event handler for the "Set-Points" button
        B1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    int newPoints = Integer.parseInt(setScore.getText());
                    if (newPoints >= 0) {
                        points = newPoints;
                        L1.setText("User Story Points: " + points);
                    } else {
                        L1.setText("INVALID STORY POINTS. Please enter a non-negative value."); //catch non-positive numbers
                    }
                } catch (NumberFormatException e) {
                    L1.setText("Invalid input. Please enter a valid number."); //catch the exception
                }
                setScore.clear();  // Clear the input field after processing
            }
        });

        // Create a vertical layout for the UI components
        VBox root = new VBox();
        root.getChildren().addAll(btn, L1, setScore, B1);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 300, 250);

        this.setTitle("Planning Poker");
        this.setScene(scene);
    }
}
