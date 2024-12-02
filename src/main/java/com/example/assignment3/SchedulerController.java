package com.example.assignment3;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        // Add logic to show a dialog for adding a new process
    }

    @FXML
    public void onRunScheduler() {
        // Run the scheduling algorithm and display results
    }
}
