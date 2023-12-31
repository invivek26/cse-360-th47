package asuHelloWorldJavaFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class planningPoker extends Stage {

    ObservableList<String> listview = FXCollections.observableArrayList("High->low Poker Points",
            "Low->High Poker Points", "ProjectType", "ProjectLifeCycle", "ProjectEffortCategory",
            "ProjectDeliverables");
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
    private ChoiceBox sortButton;

    @FXML
    private void initialize() {

        sortButton.setItems(listview);

    };

    @FXML
    private TextField searchPhraseText;

    public planningPoker() {
        try {
            // Load FXML file for the Planning Poker screen
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
            searchPhraseText.setPromptText("Search...");

            searchPhraseText.textProperty().addListener((observable, oldValue, newValue) -> {
                // Call the search function whenever the text changes
                searchFunction(newValue);
            });

            // Add event handler for updateButton
            updateButton.setOnAction(event -> updateDataList());
            sortButton.setOnAction(event -> sortFunction());

            // catches exception
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

    private void refreshLists(List<Log_DataStructure.LogEntry> userStories,
            List<Log_DataStructure.LogEntry> historicalData) {
        userStoriesList.setItems(FXCollections.observableArrayList(userStories));
        userStoriesList.setCellFactory(new LogEntryCellFactory());

        dataList.setItems(FXCollections.observableArrayList(historicalData));
        if (historicalData.isEmpty()) {
            showAlert("No Results found!!!");
            return;
        }
        dataList.setCellFactory(new LogEntryCellFactory());
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void searchFunction(String userSearchPhrase) {
        String userSearchPhraseCleaned = userSearchPhrase.trim().toLowerCase();

        List<Log_DataStructure.LogEntry> histData = Log_DataStructure.getHistoricalLogs();

        if (histData.isEmpty()) {
            showAlert("No Results found!!!");
            return;
        }

        List<Log_DataStructure.LogEntry> filteredHistData = histData.stream()
                .filter(entry -> entry.matchesSearch(userSearchPhraseCleaned))
                .collect(Collectors.toList());

        // Refresh userStoriesList and dataList
        refreshLists(Log_DataStructure.getLogs(), FXCollections.observableArrayList(filteredHistData));
    }

    private void sortFunction() {

        String selectedOption = (String) sortButton.getValue();

        List<Log_DataStructure.LogEntry> histData = Log_DataStructure.getHistoricalLogs();

        if ("Low->High Poker Points".equals(selectedOption)) {
            // Sort poker points low to high
            Collections.sort(histData, Comparator.comparing(Log_DataStructure.LogEntry::getStoryValue));
            dataList.getItems().setAll(histData);

        } else if ("High->low Poker Points".equals(selectedOption)) {

            Collections.sort(histData, Comparator.comparing(Log_DataStructure.LogEntry::getStoryValue).reversed());
            dataList.getItems().setAll(histData);

        } else if ("ProjectType".equals(selectedOption)) {

            Collections.sort(histData, Comparator.comparing(Log_DataStructure.LogEntry::getProject));
            dataList.getItems().setAll(histData);

        } else if ("ProjectLifeCycle".equals(selectedOption)) {

            Collections.sort(histData, Comparator.comparing(Log_DataStructure.LogEntry::getLifeCycle));
            dataList.getItems().setAll(histData);

        } else if ("ProjectEffortCategory".equals(selectedOption)) {

            Collections.sort(histData, Comparator.comparing(Log_DataStructure.LogEntry::getEffortCategory));
            dataList.getItems().setAll(histData);

        } else if ("ProjectDeliverables".equals(selectedOption)) {

            Collections.sort(histData, Comparator.comparing(Log_DataStructure.LogEntry::getTypeOfEffort));
            dataList.getItems().setAll(histData);

        }

        refreshLists(histData, FXCollections.observableArrayList(histData));

    }
}
