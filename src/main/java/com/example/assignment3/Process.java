package com.example.assignment3;
import javafx.beans.property.ObjectProperty;  // Import ObjectProperty
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

//public class Process {
//    private final StringProperty name;
//    private final IntegerProperty arrivalTime;
//    private final IntegerProperty burstTime;
//    private final IntegerProperty priority;
//    private final IntegerProperty initialQuantum;
//    private IntegerProperty waitingTime;
//    private IntegerProperty turnaroundTime;
//    private ObjectProperty<Color> color;
//    private final StringProperty status;
//
//    public Process(String name, int arrivalTime, int burstTime, int priority, int initialQuantum , Color color) {
//        this.name = new SimpleStringProperty(name);
//        this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
//        this.burstTime = new SimpleIntegerProperty(burstTime);
//        this.priority = new SimpleIntegerProperty(priority);
//        this.initialQuantum = new SimpleIntegerProperty(initialQuantum);
//        this.waitingTime = new SimpleIntegerProperty(0);
//        this.turnaroundTime = new SimpleIntegerProperty(0);
//        this.color = new SimpleObjectProperty<>(color);
//        this.status = new SimpleStringProperty("Waiting");
//    }
//
//    // Getter methods
//    public String getName() {
//        return name.get();
//    }
//
//    public int getArrivalTime() {
//        return arrivalTime.get();
//    }
//
//    public int getBurstTime() {
//        return burstTime.get();
//    }
//
//    public int getPriority() {
//        return priority.get();
//    }
//
//    public int getInitialQuantum() {
//        return initialQuantum.get();
//    }
//
//    public int getWaitingTime() {
//        return waitingTime.get();
//    }
//
//    public int getTurnaroundTime() {
//        return turnaroundTime.get();
//    }
//
//    // Property methods for TableView binding
//    public StringProperty nameProperty() {
//        return name;
//    }
//
//    public IntegerProperty arrivalTimeProperty() {
//        return arrivalTime;
//    }
//
//    public IntegerProperty burstTimeProperty() {
//        return burstTime;
//    }
//
//    public IntegerProperty priorityProperty() {
//        return priority;
//    }
//
//    public IntegerProperty initialQuantumProperty() {
//        return initialQuantum;
//    }
//
//    public IntegerProperty waitingTimeProperty() {
//        return waitingTime;
//    }
//
//    public IntegerProperty turnaroundTimeProperty() {
//        return turnaroundTime;
//    }
//
//
//    public Color getColor() {
//        return color.get();  // Getter for color
//    }
//
//    // Setter for color
//    public void setColor(Color color) {
//        this.color.set(color);  // Setter for color
//    }
//    public ObjectProperty<Color> colorProperty() {
//        return color;
//    }
//
//    // Setter methods for Waiting Time and Turnaround Time
//    public void setWaitingTime(int waitingTime) {
//        this.waitingTime.set(waitingTime);
//    }
//
//    public void setTurnaroundTime(int turnaroundTime) {
//        this.turnaroundTime.set(turnaroundTime);
//    }
//
//    public String getStatus() {
//        return status.get();  // جلب الحالة
//    }
//    // Getter and setter for status
//    public void setStatus(String status) {
//        this.status.set(status);  // تعيين الحالة
//    }
//
//    public StringProperty statusProperty() {
//        return status;  // خاصية الحالة
//    }
//
//    public void setBurstTime(int burstTime) {
//        this.burstTime.set(burstTime);
//    }
//}

public class Process {
    private String name;
    private final int arrivalTime;
    private final int burstTime;
    private int effBustTime;
    private int remainingTime;
    private int quantum;
    private int priority;
    private int WT;
    private int TAT;
    private Color color;


    public Process(String name, int arrivalTime, int burstTime, int priority, int quantum , Color color) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.effBustTime = burstTime;
        this.quantum = quantum;
        this.priority = priority;
        this.WT = 0;
        this.TAT = 0;
        this.color = color;
    }

    public final int getArrivalTime() {
        return arrivalTime;
    }

    public final int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    @Override
    public String toString() {
        return String.format("Process{name='%s', arrivalTime=%d, burstTime=%d, priority=%d, quantum=%d}",
                name, arrivalTime, burstTime, priority, quantum);
    }

    public int getPriority() {return this.priority; }
    public void decPriority(int time){this.priority = this.priority - time / 3; }
    public void incWT(){this.WT++; }
    public void incTAT(){this.TAT++; }

    public String getName() {return this.name; }
    public int getTAT(){return this.TAT; }
    public int getWT(){return this.WT++; }
    public void setTAT(int TAT){this.TAT = TAT; }

    public int getEffBustTime() {
        return this.effBustTime;
    }
    public void decEffBustTime(int time) {
        this.effBustTime = this.effBustTime - time / 6;
    }
    public Color getColor() {
        return color;  // Getter for color
    }

    // Setter for color
    public void setColor(Color color) {
        this.color = color;  // Setter for color
    }

}
