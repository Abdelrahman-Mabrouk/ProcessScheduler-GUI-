package com.example.assignment3;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class Process {
    private final StringProperty name;
    private final IntegerProperty arrivalTime;
    private final IntegerProperty burstTime;
    private final IntegerProperty priority;
    private final IntegerProperty initialQuantum;
    private IntegerProperty waitingTime;
    private IntegerProperty turnaroundTime;

    public Process(String name, int arrivalTime, int burstTime, int priority, int initialQuantum) {
        this.name = new SimpleStringProperty(name);
        this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
        this.burstTime = new SimpleIntegerProperty(burstTime);
        this.priority = new SimpleIntegerProperty(priority);
        this.initialQuantum = new SimpleIntegerProperty(initialQuantum);
        this.waitingTime = new SimpleIntegerProperty(0); // Initially set to 0
        this.turnaroundTime = new SimpleIntegerProperty(0); // Initially set to 0
    }

    // Getter methods
    public String getName() {
        return name.get();
    }

    public int getArrivalTime() {
        return arrivalTime.get();
    }

    public int getBurstTime() {
        return burstTime.get();
    }

    public int getPriority() {
        return priority.get();
    }

    public int getInitialQuantum() {
        return initialQuantum.get();
    }

    public int getWaitingTime() {
        return waitingTime.get();
    }

    public int getTurnaroundTime() {
        return turnaroundTime.get();
    }

    // Property methods for TableView binding
    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty arrivalTimeProperty() {
        return arrivalTime;
    }

    public IntegerProperty burstTimeProperty() {
        return burstTime;
    }

    public IntegerProperty priorityProperty() {
        return priority;
    }

    public IntegerProperty initialQuantumProperty() {
        return initialQuantum;
    }

    public IntegerProperty waitingTimeProperty() {
        return waitingTime;
    }

    public IntegerProperty turnaroundTimeProperty() {
        return turnaroundTime;
    }

    // Setter methods for Waiting Time and Turnaround Time
    public void setWaitingTime(int waitingTime) {
        this.waitingTime.set(waitingTime);
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime.set(turnaroundTime);
    }
}
