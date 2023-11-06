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


public class EffortLogEditor extends Stage {
    private int points;

    // Simulate getting story points from a data source
    public void push() {
        points = (int) (Math.random() * 20);
    }

    public EffortLogEditor() {
    	// Create text fields
    	Label label1 = new Label("Date");
    	Label label2 = new Label("Time Start");
    	Label label3 = new Label("Time Stop");
    	Label label3a = new Label("3.a Modify the Current Effort Log Entry's attributes and press 'Update This Entry' when finished.");
    	Label labelResult = new Label("");
    	TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        TextField textField3 = new TextField();

        // Create a button
        Button button = new Button("Update This Entry");

        // Set an action for the button
        button.setOnAction(e -> {
            String date = textField1.getText();
            String timeStart = textField2.getText();
            String timeStop = textField3.getText();
            
            // If any of the methods return invlaid, than it is improper input
            if (getDate(date) == 0 || getTime(timeStart) == 0 || getTime(timeStop) == 0)
            {
            	labelResult.setText("Improper Input"); 
            }
            // Else, they are all accurate and save the attributes
            else
            {
            	labelResult.setText("These attributes have been saved.");
            }
            
            System.out.println("Date: " + date);
            System.out.println("Start Time: " + timeStart);
            System.out.println("Stop Time: " + timeStop);
        });
        
        public int getDate(String date)
    	{
    		// quit is used to show result, and the dateFormat checks if the date is in the right format
    		int quit = 0;
    		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    		dateFormat.setLenient(false);

    		try
    		{
    			// throws ParseException if it is not the right format
    			Date fDate = dateFormat.parse(date);
    		}
    			
    		// If it catches ParseException, says it is invalid format and reenters while loop
    		catch (ParseException e)
    		{
    			quit = 0;
    			return quit;
    		}
    			
    		// Splits the date String by the "-" and parses it into an integer for year, month, and day
    		String[] parts = date.split("\\-");
    		int year = Integer.parseInt(parts[0]);
    		int month = Integer.parseInt(parts[1]);
    		int day = Integer.parseInt(parts[2]);
    			
    		// Checks if it is the wrong year and if it is not a valid numbered month
    		if (year != 2023 || month < 1 || month > 12)
    		{
    			quit = 0;
    			return quit;
    		}
    			
    		// If it is a month where the days end on 31, checks if day is within 1 and 31
    		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
    		{
    			if (day < 1 || day > 31)
    			{
    				quit = 0;
    				return quit;
    			}
    		}
    			
    		// For February, checks if it is a leap year and if it is within 1 and 29
    		if (month == 2)
    		{
    			if (year % 4 == 0)
    			{
    				if (day < 1 || day > 29)
    				{
    					quit = 0;
    					return quit;
    				}
    			}
    			// Not a leap year, checks whether it is within 1 and 28
    			else
    			{
    				if (day < 1 || day > 28)
    				{
    					quit = 0;
    					return quit;
    				}
    			}
    		}
    			
    		// If it is a month with a day ending on 30, checks whether it is within 1 and 30
    		if (month == 4 || month == 6 || month == 9 || month == 11)
    		{
    			if (day < 1 || day > 30)
    			{
    				quit = 0;
    				return quit;
    			}
    		}
    			
    		// For testing purposes, prints the year, month, and day and exits the loop
    		quit = 1;
    		return quit;
    	}
    	
    	
    	// Checks the text field to see if time is of proper format
    	public int getTime(String time)
    	{
    		// Quit is used for while loop and this dateFormat checks if it is the write time format
    		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
    		dateFormat.setLenient(false);
    		int quit = 0;
    		// Splits by ":"
    		String[] parts = time.split("\\:");
    		if (parts.length == 3)
    		{
    				// Tries to parse into ints
    			try 
    			{
    				int hours = Integer.parseInt(parts[0]);
    				int minutes = Integer.parseInt(parts[1]);
    				int seconds = Integer.parseInt(parts[2]);
    					
    			}
    				// If it cannot be integers, throw exception
    			catch (NumberFormatException e)
    			{
    				quit = 0;
    				return quit;
    			}
    		}
    			
    			// Not split into three parts, wrong format
    		else
    		{
    			quit = 0;
    			return quit;
    		}
    			
    		// Parses integers for hours, minutes, and seconds
    		int hours = Integer.parseInt(parts[0]);
    		int minutes = Integer.parseInt(parts[1]);
    		int seconds = Integer.parseInt(parts[2]);
    			
    		// Checks if the hour is within 0 and 23 (military time), and if minutes and seconds are within 0 and 59
    		if (hours < 0 || hours > 23 || minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59)
    		{
    			quit = 0;
    			return quit;
    		}
    			
    		// Prints the hours, minutes, and seconds for testing purposes and exits the loop
    		quit = 1;
    		return quit;
    	}

        // Create a vertical layout for the UI components
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label3a, label1, textField1, label2, textField2, label3, textField3, labelResult, button);

        Scene scene = new Scene(layout, 900, 400);

        this.setTitle("Effort Log Editor");
        this.setScene(scene);
    }
}
