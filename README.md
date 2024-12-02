# CPU Schedulers Simulator

This project simulates various CPU scheduling algorithms as part of **CS341 – Operating Systems 1** at **Cairo University, Faculty of Computers & Artificial Intelligence**.

## **Overview**

CPU scheduling is a fundamental aspect of operating systems, determining which processes run when multiple processes are eligible. This simulator implements the following schedulers:

- **Non-preemptive Priority Scheduling** (with context switching).
- **Non-preemptive Shortest Job First (SJF)** (with starvation prevention).
- **Shortest Remaining Time First (SRTF)** (with starvation prevention).
- **FCAI Scheduling**: An adaptive algorithm combining priority, arrival time, and remaining burst time into a single scheduling factor.

---

## **FCAI Scheduling**

### **Key Components**

- **Dynamic FCAI Factor**:

  Combines **Priority (P)**, **Arrival Time (AT)**, and **Remaining Burst Time (RBT)**:
  \[
  \text{FCAI Factor} = (10 - P) + \frac{\text{AT}}{V_1} + \frac{\text{RBT}}{V_2}
  \]
  Where:
  - \( V_1 = \frac{\text{Last Arrival Time of All Processes}}{10} \)
  - \( V_2 = \frac{\text{Max Burst Time of All Processes}}{10} \)

- **Quantum Allocation Rules**:
  - Initial quantum is unique to each process.
  - Quantum updates dynamically based on execution:
    - \( Q = Q + 2 \): If a process uses its quantum completely but has remaining work.
    - \( Q = Q + \text{unused quantum} \): If preempted.

- **Execution**:
  - Non-preemptive for the first 40% of quantum.
  - Allows preemption afterward.

---

### **Example of FCAI Scheduling**

#### **Input Processes**

| Process | Burst Time | Arrival Time | Priority | Initial Quantum |
| ------- | ---------- | ------------ | -------- | --------------- |
| P1      | 17         | 0            | 4        | 4               |
| P2      | 6          | 3            | 9        | 3               |
| P3      | 10         | 4            | 3        | 5               |
| P4      | 4          | 29           | 8        | 2               |

#### **Execution Timeline**

| Time Interval | Process | Action                             |
| ------------- | ------- | ---------------------------------- |
| 0–3           | P1      | Executes for 3 units, burst=14.    |
| 3–6           | P2      | Preempts P1, executes for 3 units. |
| ...           | ...     | (Detailed execution continues).    |

---

## **Program Details**

### **Input**
1. **Number of Processes**
2. **Round Robin Time Quantum**
3. **Context Switching**
4. For each process:
   - **Name**
   - **Color** (for graphical representation)
   - **Arrival Time**
   - **Burst Time**
   - **Priority Number**

### **Output**
For each scheduler:
- **Execution order** of processes.
- **Waiting time** for each process.
- **Turnaround time** for each process.
- **Average waiting time.**
- **Average turnaround time.**
- **Quantum history updates** (for FCAI Scheduling).
- **Graphical representation** of execution order (bonus).

---

## **Features**

- **Interactive Input**:
  - Allows users to input process details dynamically through a GUI.
- **Dynamic Scheduling**:
  - Implements multiple scheduling algorithms, including FCAI Scheduling.
- **Visualization**:
  - Displays execution order in a Gantt chart and a table.
- **Detailed Metrics**:
  - Provides waiting and turnaround times, with averages.

---

## **Technologies Used**
- **Java**: Programming language for core logic.
- **JavaFX**: Framework for GUI development.
- **FXML**: Markup language for UI layouts.
- **Maven**: Dependency management tool.
- 
---

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

