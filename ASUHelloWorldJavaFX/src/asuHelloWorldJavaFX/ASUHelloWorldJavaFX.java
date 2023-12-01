package asuHelloWorldJavaFX;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javafx.animation.*;
import javafx.event.*;
import javafx.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class ASUHelloWorldJavaFX extends Application {
    // UserDatabase instance for user authentication
    private UserDatabase userDatabase;

    // Constants
    private static final String TITLE = "Login";
    private static final String USER_DB_FILE = "UserDatabase.txt";

    // Login attempts and countdown timer variables
    private int passwordAttempts = 0;
    private Integer timeSeconds = 30; // 30 seconds
    private Label timerLabel = new Label("Time until next login attempt: " + timeSeconds.toString());

    // Entry point of the application
    public static void main(String[] args) {
        launch(args);
    }

    // Constructor
    public ASUHelloWorldJavaFX() {
        userDatabase = new UserDatabase();
    }

    // Main method for setting up the UI
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle(TITLE);

            // UI components
            TextField usernameField = createTextField("Username");
            PasswordField passwordField = createPasswordField("Password");
            Button login = new Button("LOGIN");
            Image userImage = new Image("/asuHelloWorldJavaFX/userLogin.png");
            ImageView userImageView = createUserImageView(userImage);

            GridPane root = createGridPane();
            // Add UI components to the GridPane
            addNodesToGridPane(root, userImageView, usernameField, passwordField, login, timerLabel);

            // Set login button action
            setLoginAction(login, usernameField, passwordField);

            Scene scene = new Scene(root, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Utility method to create a TextField with a prompt
    private TextField createTextField(String prompt) {
        TextField textField = new TextField();
        textField.setPromptText(prompt);
        return textField;
    }

    // Utility method to create a PasswordField with a prompt
    private PasswordField createPasswordField(String prompt) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(prompt);
        return passwordField;
    }

    // Utility method to create an ImageView with an image
    private ImageView createUserImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(45);
        imageView.setFitHeight(45);
        return imageView;
    }

    // Utility method to create a GridPane
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);
        return gridPane;
    }

    // Method to add UI components to the GridPane
    private void addNodesToGridPane(GridPane gridPane, ImageView imageView, TextField usernameField,
            PasswordField passwordField, Button login, Label timerLabel) {
        VBox imageViewContainer = new VBox(10);
        imageViewContainer.getChildren().add(imageView);

        // Add a label for "User Login" below the image
        Label loginLabel = new Label("User Login");
        loginLabel.setStyle("-fx-font-weight: bold;");
        imageViewContainer.getChildren().add(loginLabel);
        imageViewContainer.setAlignment(Pos.CENTER);

        gridPane.add(imageViewContainer, 0, 0);
        gridPane.add(usernameField, 0, 1);
        gridPane.add(passwordField, 0, 2);

        VBox loginButtonContainer = new VBox(10);
        loginButtonContainer.getChildren().add(login);
        loginButtonContainer.setAlignment(Pos.CENTER);
        gridPane.add(loginButtonContainer, 0, 3);

        loginButtonContainer.getChildren().add(timerLabel);
        loginButtonContainer.setAlignment(Pos.CENTER);
        timerLabel.setVisible(false);
        gridPane.add(timerLabel, 0, 4);
    }

    // Method to set login button action
    private void setLoginAction(Button login, TextField usernameField, PasswordField passwordField) {
        login.setOnAction(event -> {
            // Get user name and password
            String userName = usernameField.getText();
            String password = passwordField.getText();

            try {
                // Correct user name and password entered
                File userDBFile = new File(USER_DB_FILE);
                if (userDBFile.createNewFile()
                        || userDatabase.checkUserAuthentication(userName, password, userDBFile)) {
                    openUserWindow();
                } else {
                    showAlert("The password or username you entered is incorrect");

                    // Start of Alek Schmierer's Code
                    passwordAttempts++;
                    if (passwordAttempts >= 5) {
                        // Display countdown timer and disable login button
                        timerLabel.setVisible(true);
                        login.setDisable(true);

                        // Countdown timer animation
                        Timeline timeline = new Timeline();
                        timeline.setCycleCount(Timeline.INDEFINITE);
                        timeline.getKeyFrames().add(
                                new KeyFrame(Duration.seconds(1),
                                        new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                timeSeconds--;
                                                timerLabel.setText(
                                                        "Time until next login attempt: " + timeSeconds.toString());
                                                if (timeSeconds <= 0) {
                                                    // Reset timer and enable login button
                                                    timeline.stop();
                                                    timeSeconds = 30;
                                                    login.setDisable(false);
                                                    timerLabel.setVisible(false);
                                                }
                                            }
                                        }));
                        timeline.playFromStart();
                        showAlert("You have reached the maximum number of invalid login attempts, Please try again in "
                                + timeSeconds + " seconds");
                    }
                    // End of Alek Schmierer's Code
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        });
    }

    // Method to display an alert with the specified error message
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to open a new UserWindow when the user is successfully authenticated
    private void openUserWindow() {
        UserWindow userWindow = new UserWindow();
        userWindow.show();
    }
}