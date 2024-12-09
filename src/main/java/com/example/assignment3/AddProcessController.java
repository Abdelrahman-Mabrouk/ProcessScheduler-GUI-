package com.example.assignment3;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
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
    @FXML
    private TextField quantumField;
    @FXML
    private ColorPicker colorPicker;

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
            // الحصول على البيانات المدخلة
            String name = nameField.getText();
            int arrivalTime = Integer.parseInt(arrivalTimeField.getText());
            int burstTime = Integer.parseInt(burstTimeField.getText());
            int priority = Integer.parseInt(priorityField.getText());
            Color color = colorPicker.getValue();

            // تحقق من صحة البيانات المدخلة
            if (name.isEmpty()) {
                showError("Invalid Input", "Process name cannot be empty.");
                return;
            }

            // إنشاء عملية جديدة باستخدام البيانات المدخلة
            newProcess = new Process(name,color, arrivalTime, burstTime, priority, 0);
            isConfirmed = true;
            closeWindow();
        } catch (NumberFormatException e) {
            showError("Invalid Input", "Please enter valid numbers for Arrival Time, Burst Time, and Priority.");
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
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
