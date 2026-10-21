package asuHelloWorldJavaFX;

import java.util.ArrayList;
import java.util.List;

public class Log_DataStructure {
    private static List<LogEntry> logs = new ArrayList<>();
    private static List<LogEntry> historicalLogs = new ArrayList<>();

    public static void createLog(String project, String lifeCycle, String effortCategory, String typeOfEffort) {
        int number = 0;
        // Create a unique log entry
        LogEntry logEntry = new LogEntry(project, lifeCycle, effortCategory, typeOfEffort, number);

        // Add the log entry to the list
        logs.add(logEntry);

    }

    public static void updateLog(int userStoryPoints) {
        LogEntry firstLog = logs.get(0);
        firstLog.setStoryPoints(userStoryPoints);

        // Remove the first log entry from the list
        logs.remove(0);

        historicalLogs.add(firstLog);
    }

    public static List<LogEntry> getLogs() {
        return new ArrayList<>(logs);
    }

    public static List<LogEntry> getHistoricalLogs() {
        return new ArrayList<>(historicalLogs);
    }

    // log entry
    public static class LogEntry {
        private String project;
        private String lifeCycle;
        private String effortCategory;
        private String typeOfEffort;
        private int number;

        public LogEntry(String project, String lifeCycle, String effortCategory, String typeOfEffort, int number) {
            this.project = project;
            this.lifeCycle = lifeCycle;
            this.effortCategory = effortCategory;
            this.typeOfEffort = typeOfEffort;
            this.number = number;
            this.number = number;
        }

        // getter methods
        public String getProject() {
            return project;
        }

        public String getLifeCycle() {
            return lifeCycle;
        }

        public String getEffortCategory() {
            return effortCategory;
        }

        public String getTypeOfEffort() {
            return typeOfEffort;
        }

        public void setStoryPoints(int number) {
            this.number = number;
        }

        public int getStoryValue() {
            return number;
        }
    }
}
