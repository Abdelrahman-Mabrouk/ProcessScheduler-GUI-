package com.example.assignment3;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class SchedulerController {
    @FXML
    private TableView<Process> processTable;
    @FXML
    private TableColumn<Process, String> colName;
    @FXML
    private TableColumn<Process, Integer> colArrivalTime;
    @FXML
    private TableColumn<Process, Integer> colBurstTime;
    @FXML
    private TableColumn<Process, Integer> colPriority;

    private ObservableList<Process> processes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colArrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        colBurstTime.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        processTable.setItems(processes);
    }

    @FXML
    public void onAddProcess() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-process.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Add Process");
            stage.showAndWait();

            AddProcessController controller = loader.getController();
            if (controller.isConfirmed()) {
                Process newProcess = controller.getNewProcess();

                // بنشوف لو في بروسس بنفس الاسم ولا لا
                boolean exists = processes.stream()
                        .anyMatch(process -> process.getName().equalsIgnoreCase(newProcess.getName()));

                if (exists) {
                    // عرض رسالة خطأ في حالة وجود بروسس بنفس الاسم
                    showError("Duplicate Process", "A process with this name already exists.");
                } else {
                    processes.add(newProcess);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    public void onRunScheduler() {
        // Run the scheduling algorithm and display results
    }
}
