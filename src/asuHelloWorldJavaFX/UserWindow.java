package asuHelloWorldJavaFX;

import javafx.application.Application;
import javafx.fxml.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserWindow extends Stage {
    @FXML Button planningPokerButton;
    @FXML Button effortLogEditorButton;
    @FXML Button startActivityButton;
    @FXML Button stopActivityButton;
    @FXML ComboBox<String> projectComboBox;
    @FXML ComboBox<String> lifeCycleCombo;
    @FXML ComboBox<String> effortCategoryCombo;
    @FXML ComboBox<String> typeOfEffortCombo;
    @FXML Label clockTextBox;
    @FXML Label typeOfEffortText;
    @FXML Rectangle rectangleItem;
    @FXML Label choiceFieldsText = new Label("Please select a choice for all fields");
    
    private Instant startTime; // Variable to store the start time
    private String selectedProject;
    private String selectedLifeCycle;
    private String selectedEffortCategory;
    private String selectedTypeOfEffort;

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

    public void initialize() {
    	//Label Field Visibility
    	choiceFieldsText.setVisible(false);
    	
        // Add event handler for button
        planningPokerButton.setOnAction(e -> openNewUserWindow());

        // Add options to projectComboBox
        projectComboBox.getItems().addAll("Business Project", "Development Project");
        projectComboBox.getSelectionModel().selectFirst(); // Set the first option as the default

        // Add a listener to projectComboBox to update lifeCycleCombo 
        projectComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateLifeCycleOptions(newValue);
        });

        // Initialize lifeCycleCombo with default 
        updateLifeCycleOptions(projectComboBox.getValue());
        lifeCycleCombo.getSelectionModel().selectFirst();

        // Add options to effortCategoryCombo
        effortCategoryCombo.getItems().addAll("","Plans", "Deliverables", "Interruptions", "Others");
        effortCategoryCombo.getSelectionModel().selectFirst(); // Set the first option as the default

        // Add a listener to effortCategoryCombo to update typeOfEffortCombo 
        effortCategoryCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateTypeOfEffortOptions(newValue);
        });

        // Initialize typeOfEffortCombo with default 
        updateTypeOfEffortOptions(effortCategoryCombo.getValue());
        typeOfEffortCombo.getSelectionModel().selectFirst();
        
        // Add event handler for startActivityButton
        startActivityButton.setOnAction(e -> {
            startActivity();
        });
        
        // Add event handler for stopActivityButton
        stopActivityButton.setOnAction(e -> {
            stopActivity();
        });
    }

    private void updateLifeCycleOptions(String project) {
    	 // Clear existing options
        lifeCycleCombo.getItems().clear();

        // Add new options based on the selected project
        if ("Business Project".equals(project)) {
            lifeCycleCombo.getItems().addAll("","Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting");
        } else if ("Development Project".equals(project)) {
            lifeCycleCombo.getItems().addAll("","Problem Understanding", "Conceptual Design Plan", "Requirements","Conceptual Design", "Conceptual Design Review", "Detailed Design Plan", "Detailed Design/Prototype", "Detailed Design Review","Implementation Plan","Test Case Generation","Solution Review","Solution Implementation","Unit/System	Test","Reflection","Repository Update");
        }
    }

    private void updateTypeOfEffortOptions(String effortCategory) {
        // Clear existing options
        typeOfEffortCombo.getItems().clear();

        // Add new options based on the selected effortCategory
        if ("Plans".equals(effortCategory)) {
            typeOfEffortCombo.getItems().addAll("","Project Plan", "Risk Management Plan", "Conceptual Design Plan", "Detailed Design Plan", "Implementation Plan");
        } else if ("Deliverables".equals(effortCategory)) {
            typeOfEffortCombo.getItems().addAll("","Conceptual Design", "Detailed Design", "Test Cases", "Solution","Refelection","Outline","Draft","Report", "User Defined","Other");
        } else if ("Interruptions".equals(effortCategory)) {
            typeOfEffortCombo.getItems().addAll("","Break", "Phone", "Teammate", "Visitor", "Other");
        } else if ("Others".equals(effortCategory)) {
            typeOfEffortCombo.getItems().addAll("");
        }

        typeOfEffortCombo.getSelectionModel().selectFirst();
        typeOfEffortText.setText(effortCategory);
    }
    
    private void startActivity() {
    	startTime = Instant.now();
    	clockTextBox.setText("Clock is running ");
        rectangleItem.setFill(Color.GREEN);
        
        // Record the selected values when starting the activity
        selectedProject = projectComboBox.getValue();
        selectedLifeCycle = lifeCycleCombo.getValue();
        selectedEffortCategory = effortCategoryCombo.getValue();
        selectedTypeOfEffort = typeOfEffortCombo.getValue();
    }
    
    private void stopActivity() {
    	if (startTime == null) {
            return;
        }
    	if(validateComboBoxes()) {
    	
    	Instant endTime = Instant.now();
        Duration elapsedTime = Duration.between(startTime, endTime);
        clockTextBox.setText("Clock is stopped " + formatDuration(elapsedTime));
        startTime = null;
        
        rectangleItem.setFill(Color.RED);
        
        createLog(selectedProject,selectedLifeCycle,selectedEffortCategory,selectedTypeOfEffort);
        choiceFieldsText.setVisible(false);
    	}else {
    		choiceFieldsText.setVisible(true);
    	}
    }

    private String formatDuration(Duration duration) {
        // Format the duration as HH:mm:ss
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
    
    private void createLog(String project, String lifeCycle, String effortCategory, String typeOfEffort) {
    	Log_DataStructure.createLog(project, lifeCycle, effortCategory, typeOfEffort);
    	
    	// To get the logs
    	List<Log_DataStructure.LogEntry> logs = Log_DataStructure.getLogs();
    }
    
    private boolean validateComboBoxes() {
        // Check if all ComboBoxes have a selected value
        return projectComboBox.getValue() != null &&
               lifeCycleCombo.getValue() != null &&
               effortCategoryCombo.getValue() != null &&
               typeOfEffortCombo.getValue() != null;
    }
    
    
    private void openNewUserWindow() {
        planningPoker planningPoker = new planningPoker();
        planningPoker.show();
    }
}
