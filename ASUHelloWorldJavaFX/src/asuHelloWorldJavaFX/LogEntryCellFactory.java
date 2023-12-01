package asuHelloWorldJavaFX;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class LogEntryCellFactory
        implements Callback<ListView<Log_DataStructure.LogEntry>, ListCell<Log_DataStructure.LogEntry>> {

    @Override
    public ListCell<Log_DataStructure.LogEntry> call(ListView<Log_DataStructure.LogEntry> listView) {
        return new ListCell<Log_DataStructure.LogEntry>() {
            @Override
            protected void updateItem(Log_DataStructure.LogEntry log, boolean empty) {
                super.updateItem(log, empty);

                if (log == null || empty) {
                    setText(null);
                } else {
                    // Customize the display of each log entry here
                    String displayText = String.format(
                            "Project: %s, LifeCycle: %s, Effort Category: %s, Type of Effort: %s, User Story Points: %d",
                            log.getProject(), log.getLifeCycle(), log.getEffortCategory(), log.getTypeOfEffort(),
                            log.getStoryValue());
                    setText(displayText);
                }
            }
        };
    }
}
