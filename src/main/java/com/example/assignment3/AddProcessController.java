package com.example.assignment3;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddProcessController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField arrivalTimeField;
    @FXML
    private TextField burstTimeField;
    @FXML
    private TextField priorityField;

    private Process newProcess;
    private boolean isConfirmed = false;

    public Process getNewProcess() {
        return newProcess;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    @FXML
    public void onAddClicked() {
        try {
            String name = nameField.getText();
            int arrivalTime = Integer.parseInt(arrivalTimeField.getText());
            int burstTime = Integer.parseInt(burstTimeField.getText());
            int priority = Integer.parseInt(priorityField.getText());

            newProcess = new Process(name, arrivalTime, burstTime, priority);
            isConfirmed = true;
            closeWindow();
        } catch (NumberFormatException e) {
            // Handle invalid input (e.g., show an error message)
        }
    }

    @FXML
    public void onCancelClicked() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}

