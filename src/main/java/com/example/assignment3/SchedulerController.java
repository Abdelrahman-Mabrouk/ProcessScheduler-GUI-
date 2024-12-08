package com.example.assignment3;
import javafx.scene.paint.Color;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;



import java.io.IOException;

import javafx.scene.control.ComboBox;



import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;


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
    @FXML
    private TableColumn<Process, Integer> colWaitingTime;
    @FXML
    private TableColumn<Process, Integer> colTurnaroundTime;
    @FXML
    private TableColumn<Process, Color> colColor;
    @FXML
    private ComboBox<String> algorithmComboBox;

    @FXML
    private Canvas schedulerCanvas;  // الـ Canvas الذي سنرسم عليه

    private ObservableList<Process> processList = FXCollections.observableArrayList();  // ObservableList لعرض العمليات في الـ Table

    @FXML
    public void initialize() {
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colArrivalTime.setCellValueFactory(cellData -> cellData.getValue().arrivalTimeProperty().asObject());
        colBurstTime.setCellValueFactory(cellData -> cellData.getValue().burstTimeProperty().asObject());
        colPriority.setCellValueFactory(cellData -> cellData.getValue().priorityProperty().asObject());
        colWaitingTime.setCellValueFactory(cellData -> cellData.getValue().waitingTimeProperty().asObject());
        colTurnaroundTime.setCellValueFactory(cellData -> cellData.getValue().turnaroundTimeProperty().asObject());

        // إعداد عمود اللون
        colColor.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        colColor.setCellFactory(column -> new TableCell<Process, Color>() {
            @Override
            protected void updateItem(Color color, boolean empty) {
                super.updateItem(color, empty);
                if (empty || color == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText("");
                    setStyle("-fx-background-color: " + toHex(color) + ";");
                }
            }
        });

        algorithmComboBox.getItems().addAll(
                "Priority Scheduling",
                "Shortest Job First (SJF)",
                "Shortest Remaining Time First (SRTF)",
                "FCAI Scheduling"
        );
        processTable.setItems(processList);

    }

    private String toHex(Color color) {
        return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }


    @FXML
    public void onRunScheduler() {
        String selectedAlgorithm = algorithmComboBox.getValue();

        if (selectedAlgorithm == null) {
            showError("No Algorithm Selected", "Please select a scheduling algorithm.");
            return;
        }

        List<Process> scheduledProcesses = new ArrayList<>();

        // اختيار الخوارزمية لتشغيلها
        switch (selectedAlgorithm) {
            case "Priority Scheduling":
                scheduledProcesses = Scheduler.priorityScheduling(new ArrayList<>(processList));
                break;
            case "Shortest Job First (SJF)":
                scheduledProcesses = Scheduler.sjfScheduling(new ArrayList<>(processList));
                break;
            case "Shortest Remaining Time First (SRTF)":
                scheduledProcesses = Scheduler.srtfScheduling(new ArrayList<>(processList));
                break;
            case "FCAI Scheduling":
                scheduledProcesses = Scheduler.fcaiScheduling(new ArrayList<>(processList));
                break;
            default:
                showError("Unknown Algorithm", "Selected algorithm is not recognized.");
                return;
        }

        processTable.getItems().setAll(scheduledProcesses);

        // تحويل العمليات إلى مصفوفة لاستخدامها في الرسم البياني
        Process[] processArray = new Process[scheduledProcesses.size()];
        scheduledProcesses.toArray(processArray);

        // إنشاء كائن الرسم البياني
        SchedulerGraph schedulerGraph = new SchedulerGraph(800, 400);

        // رسم العمليات على الجراف
        schedulerGraph.drawProcessGraph(processArray);

        // إضافة الرسم البياني إلى الواجهة
        schedulerCanvas.getGraphicsContext2D().drawImage(schedulerGraph.snapshot(null, null), 0, 0);
    }

    private void updateGraph() {
        // هنا نرسل البيانات للرسم البياني (Canvas)
        SchedulerGraph schedulerGraph = new SchedulerGraph(schedulerCanvas.getWidth(), schedulerCanvas.getHeight());
        Process[] processesArray = processList.toArray(new Process[0]);
        schedulerGraph.drawProcessGraph(processesArray);
    }




        @FXML
        public void onAddProcess() {
            try {
                // فتح نافذة إضافة عملية جديدة
                FXMLLoader loader = new FXMLLoader(getClass().getResource("add-process.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Add Process");
                stage.showAndWait();  // الانتظار حتى يتم إغلاق النافذة

                // الحصول على الـ Controller من النافذة التي تم فتحها
                AddProcessController controller = loader.getController();

                // إذا تم تأكيد العملية من قبل المستخدم
                if (controller.isConfirmed()) {
                    Process newProcess = controller.getNewProcess();  // استلام العملية الجديدة

                    // تحقق من وجود عملية بنفس الاسم
                    boolean exists = processList.stream()
                            .anyMatch(process -> process.getName().equalsIgnoreCase(newProcess.getName()));

                    if (exists) {
                        showError("Duplicate Process", "A process with this name already exists.");
                    } else {
                        // إضافة العملية الجديدة إلى القائمة
                        processList.add(newProcess);  // استخدام processList الذي هو ObservableList
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        @FXML
        public void onLoadFromFile() {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

            Stage stage = (Stage) processTable.getScene().getWindow();
            fileChooser.setTitle("Open Process File");
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                try {
                    List<Process> processesFromFile = TXTReader.readProcessesFromTXT(selectedFile.getAbsolutePath());

                    for (Process process : processesFromFile) {
                        // تحقق من وجود لون للبروسس
                        if (process.getColor() == null) {
                            // إذا لم يكن هناك لون مخصص، اطلب من المستخدم اختيار لون
                            Color userColor = askForColor(process.getName());
                            process.setColor(userColor);
                        }
                    }

                    processList.addAll(processesFromFile);  // أضف العمليات إلى الـ ObservableList
                    processTable.refresh();  // تحديث الجدول بعد إضافة العمليات
                    showInfo("File Loaded", "Processes loaded successfully from the file.");
                } catch (IOException e) {
                    showError("File Error", "Failed to read from the file.");
                }
            }
        }


        private Color askForColor(String processName) {
            // نافذة لاختيار اللون باستخدام ColorPicker
            ColorPicker colorPicker = new ColorPicker();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Choose Color");
            alert.setHeaderText("Choose a color for process: " + processName);
            alert.getDialogPane().setContent(colorPicker);
            alert.showAndWait();

            return colorPicker.getValue();  // إرجاع اللون الذي اختاره المستخدم
        }





        private void showInfo(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }

        @FXML
        public void onAlgorithmSelected() {
            // الحصول على الخوارزمية المختارة
            String selectedAlgorithm = algorithmComboBox.getValue();
            System.out.println("Selected algorithm: " + selectedAlgorithm);
        }



        // Helper method to calculate the average waiting time
        private double getAverageWaitingTime(List<Process> processes) {
            double totalWaitingTime = 0;
            for (Process process : processes) {
                totalWaitingTime += process.getWaitingTime();
            }
            return totalWaitingTime / processes.size();
        }

        // Helper method to calculate the average turnaround time
        private double getAverageTurnaroundTime(List<Process> processes) {
            double totalTurnaroundTime = 0;
            for (Process process : processes) {
                totalTurnaroundTime += process.getTurnaroundTime();
            }
            return totalTurnaroundTime / processes.size();
        }

        // Update Gantt chart based on process execution order

        public void calculateTimes() {
            int totalWaitingTime = 0;
            int totalTurnaroundTime = 0;

            for (Process process : processList) {
                // حساب turnaround time و waiting time
                int turnaroundTime = process.getBurstTime() + process.getArrivalTime(); // مثال حساب turnaround
                process.setTurnaroundTime(turnaroundTime);

                int waitingTime = turnaroundTime - process.getBurstTime() - process.getArrivalTime();
                process.setWaitingTime(waitingTime);

                totalWaitingTime += waitingTime;
                totalTurnaroundTime += turnaroundTime;
            }

            // عرض النتائج
            System.out.println("Average Waiting Time: " + (totalWaitingTime / processList.size()));
            System.out.println("Average Turnaround Time: " + (totalTurnaroundTime / processList.size()));
        }

        // Show error alert
        private void showError(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }


    }

