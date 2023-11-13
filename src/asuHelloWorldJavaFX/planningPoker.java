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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class planningPoker extends Stage {
    @FXML
    private ListView<Log_DataStructure.LogEntry> userStoriesList;
    @FXML
    private ListView<Log_DataStructure.LogEntry> dataList;
    @FXML
    private TextField userStoryPointText;
    @FXML
    private Button updateButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchPhraseText;

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

                List<Log_DataStructure.LogEntry> historicalData = Log_DataStructure.getHistoricalLogs();
                dataList.setItems(FXCollections.observableArrayList(historicalData));
                dataList.setCellFactory(new LogEntryCellFactory());
            });

            // Add event handler for updateButton
            updateButton.setOnAction(event -> updateDataList());
            searchButton.setOnAction(event -> searchFunction());

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
            Log_DataStructure.updateLog(userStoryPoints);
        }

        // Refresh userStoriesList and dataList
        userStoriesList.setItems(FXCollections.observableArrayList(Log_DataStructure.getLogs()));
        userStoriesList.setCellFactory(new LogEntryCellFactory());

        dataList.setItems(FXCollections.observableArrayList(Log_DataStructure.getHistoricalLogs()));
        dataList.setCellFactory(new LogEntryCellFactory());
    }

    private void searchFunction() {
        // Get the input from userStoryPointText
        String userSearchPhrase = searchPhraseText.getText().trim().toLowerCase();

        List<Log_DataStructure.LogEntry> histData = Log_DataStructure.getHistoricalLogs();
        if (histData.isEmpty()) {
            return;
        }

        List<Log_DataStructure.LogEntry> filteredHistData = new ArrayList<>();
        for (Log_DataStructure.LogEntry entry : histData) {
            if (entry.getProject().toLowerCase().contains(userSearchPhrase) ||
                    entry.getLifeCycle().toLowerCase().contains(userSearchPhrase) ||
                    entry.getEffortCategory().toLowerCase().contains(userSearchPhrase) ||
                    entry.getTypeOfEffort().toLowerCase().contains(userSearchPhrase)) {
                filteredHistData.add(entry);
            }
        }

        // Print filteredHistData
        for (Log_DataStructure.LogEntry entry : filteredHistData) {
            System.out.println(entry.getProject());
        }

        // Refresh userStoriesList and dataList
        userStoriesList.setItems(FXCollections.observableArrayList(Log_DataStructure.getLogs()));
        userStoriesList.setCellFactory(new LogEntryCellFactory());

        dataList.setItems(FXCollections.observableArrayList(filteredHistData));
        dataList.setCellFactory(new LogEntryCellFactory());
    }
}
