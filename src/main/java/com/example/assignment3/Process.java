package com.example.assignment3;
import javafx.beans.property.ObjectProperty;  // Import ObjectProperty
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

public class Process {
    private final StringProperty name;
    private final IntegerProperty arrivalTime;
    private final IntegerProperty burstTime;
    private final IntegerProperty priority;
    private final IntegerProperty initialQuantum;
    private IntegerProperty waitingTime;
    private IntegerProperty turnaroundTime;
    private ObjectProperty<Color> color;
    private final StringProperty status; // إضافة خاصية الحالة

    public Process(String name, int arrivalTime, int burstTime, int priority, int initialQuantum , Color color) {
        this.name = new SimpleStringProperty(name);
        this.arrivalTime = new SimpleIntegerProperty(arrivalTime);
        this.burstTime = new SimpleIntegerProperty(burstTime);
        this.priority = new SimpleIntegerProperty(priority);
        this.initialQuantum = new SimpleIntegerProperty(initialQuantum);
        this.waitingTime = new SimpleIntegerProperty(0);
        this.turnaroundTime = new SimpleIntegerProperty(0);
        this.color = new SimpleObjectProperty<>(color);
        this.status = new SimpleStringProperty("Waiting");
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


    public Color getColor() {
        return color.get();  // Getter for color
    }

    // Setter for color
    public void setColor(Color color) {
        this.color.set(color);  // Setter for color
    }
    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    // Setter methods for Waiting Time and Turnaround Time
    public void setWaitingTime(int waitingTime) {
        this.waitingTime.set(waitingTime);
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime.set(turnaroundTime);
    }

    public String getStatus() {
        return status.get();  // جلب الحالة
    }
    // Getter and setter for status
    public void setStatus(String status) {
        this.status.set(status);  // تعيين الحالة
    }

    public StringProperty statusProperty() {
        return status;  // خاصية الحالة
    }

    public void setBurstTime(int burstTime) {
        this.burstTime.set(burstTime);
    }
}
