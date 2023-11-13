package asuHelloWorldJavaFX;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class planningPoker extends Stage {
    @FXML
    private ListView<Log_DataStructure.LogEntry> userStoriesList;
    @FXML
    private ListView<Log_DataStructure.LogEntry> dataList;
    @FXML
    private TextField userStoryPointText;
    @FXML
    private Button updateButton;

    public planningPoker() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PokerScreen.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Scene adminScene = new Scene(root, 750, 500);
            setScene(adminScene);

            // Set items and cell factory for userStoriesList after the stage is shown
            setOnShown(event -> {
                List<Log_DataStructure.LogEntry> logs = Log_DataStructure.getLogs();
                userStoriesList.setItems(FXCollections.observableArrayList(logs));
                userStoriesList.setCellFactory(new LogEntryCellFactory());
            });

            // Add event handler for updateButton
            updateButton.setOnAction(event -> updateDataList());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDataList() {
        // Get the input from userStoryPointText
        int userStoryPoints;
        try {
            userStoryPoints = Integer.parseInt(userStoryPointText.getText());
        } catch (NumberFormatException e) {
            // Handle invalid input (non-integer)
            // You may want to show an error message or take appropriate action
            return;
        }

        // Assuming you have a method in Log_DataStructure to update story points
        // Update the story points for the first log entry
        List<Log_DataStructure.LogEntry> logs = Log_DataStructure.getLogs();
        if (!logs.isEmpty()) {
            Log_DataStructure.LogEntry firstLog = logs.get(0);
            firstLog.setStoryPoints(userStoryPoints);

            // Move the first log entry from userStoriesList to dataList
            userStoriesList.getItems().remove(firstLog);
            dataList.getItems().add(firstLog);

            // Remove the first log entry from the list
            logs.remove(0);
        }

        // Refresh userStoriesList and dataList
        userStoriesList.setItems(FXCollections.observableArrayList(logs));
        userStoriesList.setCellFactory(new LogEntryCellFactory());
        dataList.setCellFactory(new LogEntryCellFactory());
    }
}
