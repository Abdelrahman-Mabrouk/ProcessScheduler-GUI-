# CPU Scheduler Simulator

A JavaFX-based application that simulates CPU scheduling algorithms. This project provides an interactive user interface for inputting processes, running various scheduling algorithms, and visualizing the results in a Gantt chart and table.

## **Features**
- **Input Process Details**: Add processes with attributes such as:
  - Process Name
  - Arrival Time
  - Burst Time
  - Priority
- **Scheduling Algorithms**:
  - Priority Scheduling
  - Shortest Job First (SJF)
  - Shortest Remaining Time First (SRTF)
  - Custom FCAI Scheduling
- **Results Visualization**:
  - Process execution order.
  - Waiting time and turnaround time for each process.
  - Average waiting and turnaround times.
  - Gantt chart to visualize the scheduling execution timeline.

## **Technologies Used**
- **Java**: Core programming language.
- **JavaFX**: For creating the graphical user interface.
- **FXML**: For defining the UI layout.
- **Maven**: For dependency management.

## **Project Structure**
```plaintext
src/
├── main/
│   ├── java/
│   │   └── com.example.project/
│   │       ├── MainApplication.java         # Entry point for the application
│   │       ├── SchedulerController.java     # Handles UI interactions and logic
│   │       ├── Process.java                 # Model class for process data
│   ├── resources/
│       └── com.example.project/
│           └── main-view.fxml               # FXML file for the main UI
