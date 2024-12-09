package com.example.assignment3;
import javafx.beans.property.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
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
    private List<Process> originalProcesses = new ArrayList<>(); // قائمة العمليات الأصلية


    @FXML
    public void initialize() {
        // إعداد العمود الخاص بالاسم
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        // إعداد العمود الخاص بـ Arrival Time
        colArrivalTime.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getArrivalTime()).asObject());

        // إعداد العمود الخاص بـ Burst Time
        colBurstTime.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getBurstTime()).asObject());

        // إعداد العمود الخاص بـ Priority
        colPriority.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPriority()).asObject());

        // إعداد العمود الخاص بـ Waiting Time
        colWaitingTime.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getWaitTime()).asObject());

        // إعداد العمود الخاص بـ Turnaround Time
        colTurnaroundTime.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTurnAround()).asObject());

        // إعداد عمود اللون
        colColor.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getColor()));
        colColor.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Color color, boolean empty) {
                super.updateItem(color, empty);
                if (empty || color == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(""); // لا نعرض نص
                    setStyle("-fx-background-color: " + toHex(color) + ";");
                }
            }
        });

        // إضافة الخيارات إلى القائمة المنسدلة
        algorithmComboBox.getItems().addAll(
                "Priority Scheduling",
                "Shortest Job First (SJF)",
                "Shortest Remaining Time First (SRTF)",
                "FCAI Scheduling"
        );

        // تعيين قائمة العناصر للجدول
        processTable.setItems(processList);
    }

    private String toHex(Color color) {
        return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
    }
    private void resetProcesses() {
        processList.clear();
        for (Process process : originalProcesses) {
            processList.add(process.copy()); // أنشئ نسخة جديدة لكل عملية
        }
        processTable.refresh(); // تأكد من تحديث الجدول
    }
    @FXML
    public void onRunScheduler() {
        // إعادة تعيين العمليات إلى حالتها الأصلية
        resetProcesses();

        String selectedAlgorithm = algorithmComboBox.getValue();
        if (selectedAlgorithm == null) {
            showError("No Algorithm Selected", "Please select a scheduling algorithm.");
            return;
        }

        List<Process> scheduledProcesses = new ArrayList<>();

        // اختيار الخوارزمية لتشغيلها
        switch (selectedAlgorithm) {
            case "Priority Scheduling":
                scheduledProcesses = Scheduler.priorityScheduling(new ArrayList<>(processList),0);
                break;
            case "Shortest Job First (SJF)":
                scheduledProcesses = Scheduler.sjfScheduling(new ArrayList<>(processList),0);
                break;
            case "Shortest Remaining Time First (SRTF)":
                scheduledProcesses = Scheduler.srtfScheduling(new ArrayList<>(processList),0);
                break;
            case "FCAI Scheduling":
                scheduledProcesses = Scheduler.fcaiScheduling(new ArrayList<>(processList),0);
                break;
            default:
                showError("Unknown Algorithm", "Selected algorithm is not recognized.");
                return;
        }

        processTable.getItems().setAll(scheduledProcesses);

        updateGraph(scheduledProcesses);
    }

    private void updateGraph(List<Process> scheduledProcesses) {
        // تنظيف الجراف قبل رسم البيانات الجديدة
        GraphicsContext gc = schedulerCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, schedulerCanvas.getWidth(), schedulerCanvas.getHeight());  // تنظيف الجراف

        // تحويل العمليات إلى مصفوفة لاستخدامها في الرسم البياني
        Process[] processArray = new Process[scheduledProcesses.size()];
        scheduledProcesses.toArray(processArray);

        // إنشاء كائن الرسم البياني
        SchedulerGraph schedulerGraph = new SchedulerGraph(800, 400);

        // رسم العمليات على الجراف
        schedulerGraph.drawProcessGraph(List.of(processArray));  // رسم العمليات في الجراف

        // إضافة الرسم البياني إلى الواجهة
        gc.drawImage(schedulerGraph.snapshot(null, null), 0, 0);  // إضافة الرسم البياني إلى الجراف
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
                processList.add(newProcess);

                // تحديث النسخة الأصلية
                this.originalProcesses.clear();
                for (Process process : processList) {
                    this.originalProcesses.add(process.copy());
                }

                processTable.refresh();
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
                    loadProcesses(processesFromFile);

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

    public void loadProcesses(List<Process> processes) {
        // مسح قائمة العمليات الحالية
        this.processList.clear();
        this.processList.addAll(processes); // إضافة العمليات إلى القائمة الحالية

        // حفظ نسخة من العمليات الأصلية
        this.originalProcesses.clear();
        for (Process process : processes) {
            this.originalProcesses.add(process.copy()); // إنشاء نسخة جديدة من كل عملية
        }

        // تحديث الجدول لعرض العمليات الجديدة
        processTable.setItems(FXCollections.observableArrayList(processList));
        processTable.refresh();
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

        // Show error alert
        private void showError(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }


    }

