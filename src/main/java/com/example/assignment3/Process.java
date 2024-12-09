package com.example.assignment3;
import javafx.beans.property.ObjectProperty;  // Import ObjectProperty
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.*;
import javafx.scene.paint.Color;



import java.util.*;

public class Process {
    private  int arrivalTime = 0, burstTime = 0, effBurstTime = 0, priority = 0, quantum = 0, completionTime = 0;
    private String name = null;
    Color color ;
    private int used_quant, remainingTime, waitTime, fcai_factor, turnAround;
    private final List<Integer> quantum_history;

    Process(String name, Color color, int arrivalTime, int burstTime, int priority, int quantum) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.effBurstTime = burstTime;
        this.remainingTime = burstTime;
        this.quantum = quantum;
        this.priority = priority;
        this.waitTime = 0;
        this.used_quant = 0;
        this.turnAround = 0;
        this.quantum_history = new ArrayList<>();
    }

    // Getters start here-------------------------------------------------
    public Process copy() {
        return new Process(this.name, this.color, this.arrivalTime, this.burstTime, this.priority, this.quantum);
    }
    public final String getName() {
        return name;
    }

    public final Color getColor() {
        return color;
    }
    public final void setColor(Color color) {
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

    public int getQuantum() {
        return quantum;
    }

    public int getUsedQuant() {
        return used_quant;
    }

    public int getPriority() {
        return priority;

    }

    public int getFcai() {
        return fcai_factor;

    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public void setTurnAround(int turnAround){
        this.turnAround = turnAround;
    }
    public int getTurnAround() {
        return turnAround;
    }

    public List<Integer> getQuantumHistory() {
        return quantum_history;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getEffBurstTime() {
        return this.effBurstTime;
    }

    public void setEffBurstTime(int effBurstTime){
        this.effBurstTime = effBurstTime;
    }

    // Getters end here-------------------------------------------------
    // Setters start here-------------------------------------------------
    public void setFcai(int v1, int v2) {
        fcai_factor = (int) Math.ceil((10 - priority) + (arrivalTime / v1) + (remainingTime / v2));
    }

    public void setRemainingTime(int r) {
        remainingTime = r;
    }

    public void setTurnAround() {
        turnAround = waitTime + burstTime;
    }

    public void setCompletionTime(int t) {
        completionTime = t;
    }

    // Setters end here-------------------------------------------------
    // Updating functions start here------------------------------------
    public void updateQuantumHistory(int updated_quantum) {
        quantum_history.add(updated_quantum);
    }

    public void resetUsedQuant() {
        used_quant = 0;
    }

    // In the Process class
    public void decrementRemainingTime() {
        remainingTime = Math.max(0, remainingTime - 1);
    }

    public void updateUsedQuantum() {
        used_quant++;
    }

    public void incrementWaitTime() {
        waitTime++;
    }

    public void incrementTurnaround() {
        turnAround++;
    }

    public void decEffBurstTime(int time) {
        effBurstTime -= time / 6;
    }

    public void decPriority(int time) {
        priority -= time / 3;
    }

    public void reset() {
        this.arrivalTime = arrivalTime;
        this.remainingTime = this.burstTime;
        this.effBurstTime = this.burstTime;
        this.waitTime = 0;
        this.turnAround = 0;
        this.used_quant = 0; // إذا كان لديك حقول أخرى قابلة للتغيير أضفها هنا
    }
    //    Process(String name, Color color, int arrivalTime, int burstTime, int priority, int quantum) {
    // Updating functions end here--------------------------------------
}