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
import java.util.Map;

import javafx.scene.control.ComboBox;

public class SchedulerController {

    @FXML
    private TableView<Process> processTable;  // تأكد من أن لديك TableView

    @FXML
    private TableColumn<Process, String> colName;  // تعريف الأعمدة
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

    private ObservableList<Process> processList = FXCollections.observableArrayList();  // إنشاء ObservableList

    @FXML
    private ComboBox<String> algorithmComboBox;
    @FXML
    private TextArea resultsTextArea;
    @FXML
    private BarChart<String, Number> ganttChart;
    @FXML
    private TextField quantumField; // يجب تعريف هذا العنصر في الـ Controller



    private List<Process> processes = FXCollections.observableArrayList();

    // HashMap لتخزين الألوان المستخدمة بالفعل لتجنب التكرار
    private Map<String, Color> usedColors = new HashMap<>();

    // قائمة من الألوان المحتملة باستخدام "Color.web()" للحصول على الألوان
    private static final Color[] COLORS = {
            Color.web("lightgreen"), Color.web("lightblue"), Color.web("lightcoral"), Color.web("lightyellow"),
            Color.web("palegreen"), Color.web("palevioletred"), Color.web("plum"), Color.web("mediumpurple"),
            Color.web("mediumturquoise"), Color.web("mediumorchid"), Color.web("salmon"), Color.web("lavenderblush"),
            Color.web("gold"), Color.web("lightpink"), Color.web("mistyrose"), Color.web("seashell"),
            Color.web("mediumseagreen"), Color.web("skyblue"), Color.web("cornflowerblue"), Color.web("royalblue"),
            Color.web("dodgerblue"), Color.web("chocolate"), Color.web("orangered"), Color.web("yellowgreen"),
            Color.web("limegreen"), Color.web("turquoise"), Color.web("indianred"), Color.web("darkkhaki")
    };

    @FXML
    private void initialize() {
        // إعداد الأعمدة
        colName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colArrivalTime.setCellValueFactory(cellData -> cellData.getValue().arrivalTimeProperty().asObject());
        colBurstTime.setCellValueFactory(cellData -> cellData.getValue().burstTimeProperty().asObject());
        colPriority.setCellValueFactory(cellData -> cellData.getValue().priorityProperty().asObject());

        // إضافة الأعمدة الخاصة بـ waiting time و turnaround time
        colWaitingTime.setCellValueFactory(cellData -> cellData.getValue().waitingTimeProperty().asObject());
        colTurnaroundTime.setCellValueFactory(cellData -> cellData.getValue().turnaroundTimeProperty().asObject());
        // إضافة الخوارزميات المتاحة إلى ComboBox
        algorithmComboBox.setItems(FXCollections.observableArrayList(
                "Priority Scheduling",
                "Shortest Job First (SJF)",
                "Shortest Remaining Time First (SRTF)",
                "FCAI Scheduling"
        ));
        // تخصيص الألوان للعمليات بناءً على الاسم
        colName.setCellFactory(cell -> new TableCell<Process, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);

                    // التحقق من اللون لكل عملية بناءً على اسمها
                    Process process = getTableRow().getItem();
                    if (process != null) {
                        String processName = process.getName();

                        // إذا لم يتم استخدام اللون من قبل، اختر لون جديد
                        if (!usedColors.containsKey(processName)) {
                            // تحديد اللون الأولي
                            Color color = getRandomColor();
                            usedColors.put(processName, color); // إضافة اللون إلى الخريطة
                        }

                        // تعيين اللون للعملية
                        Color processColor = usedColors.get(processName);
                        setStyle("-fx-background-color: " + rgbToHex(processColor) + ";");
                    }
                }
            }
        });

        // إضافة العمليات إلى الجدول
        processTable.setItems(processList);
    }
    // الحصول على لون عشوائي غير مستخدم
    private Color getRandomColor() {
        for (Color color : COLORS) {
            boolean colorUsed = usedColors.containsValue(color);
            if (!colorUsed) {
                return color;
            }
        }
        // إذا كانت جميع الألوان مستخدمة، قم بإرجاع أول لون
        return COLORS[0];
    }

    // تحويل لون إلى قيمة HEX
    private String rgbToHex(Color color) {
        return String.format("#%02X%02X%02X",
                (int)(color.getRed() * 255),
                (int)(color.getGreen() * 255),
                (int)(color.getBlue() * 255));
    }

    // Define the UI elements

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
                // قراءة العمليات من الملف
                List<Process> processesFromFile = TXTReader.readProcessesFromTXT(selectedFile.getAbsolutePath());

                // إضافة العمليات من الملف إلى القائمة الحالية
                processList.addAll(processesFromFile);  // إضافة العمليات إلى القائمة بدلاً من استبدالها

                // تحديث الجدول
                processTable.refresh();
                showInfo("File Loaded", "Processes loaded successfully from the file.");
            } catch (IOException e) {
                showError("File Error", "Failed to read from the file.");
            }
        }
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

    @FXML
    public void onRunScheduler() {
        String selectedAlgorithm = algorithmComboBox.getValue();

        // تنفيذ الخوارزمية بناءً على الاختيار
        if (selectedAlgorithm == null) {
            showError("No Algorithm Selected", "Please select a scheduling algorithm.");
            return;
        }

        List<Process> scheduledProcesses = new ArrayList<>();

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

        // عرض العمليات المجدولة في الجدول
        processTable.getItems().setAll(scheduledProcesses);
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
    private void updateGanttChart(List<Process> processes) {
        ganttChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Execution Timeline");

        int currentTime = 0;
        for (Process process : processes) {
            series.getData().add(new XYChart.Data<>(process.getName(), currentTime));
            currentTime += process.getBurstTime();
        }

        ganttChart.getData().add(series);
    }

    // Show error alert
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // حساب waiting time و turnaround time في الخوارزمية بعد تنفيذ الجدولة
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


}
