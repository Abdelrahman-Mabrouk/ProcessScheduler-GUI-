package com.example.assignment3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scheduler {

    // Non-preemptive Priority Scheduling
    public static List<Process> priorityScheduling(List<Process> processes) {
        List<Process> sortedProcesses = new ArrayList<>(processes);
        // Sort processes based on priority (lower value = higher priority)
        sortedProcesses.sort(Comparator.comparingInt(Process::getPriority));

        int currentTime = 0;
        for (Process process : sortedProcesses) {
            process.setWaitingTime(Math.max(0, currentTime - process.getArrivalTime()));
            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
            currentTime = Math.max(currentTime, process.getArrivalTime()) + process.getBurstTime();
        }

        return sortedProcesses;
    }

    // Shortest Job First (SJF)
    public static List<Process> sjfScheduling(List<Process> processes) {
        List<Process> sortedProcesses = new ArrayList<>(processes);
        // Sort processes based on burst time (shorter burst time first)
        sortedProcesses.sort(Comparator.comparingInt(Process::getBurstTime));

        int currentTime = 0;
        for (Process process : sortedProcesses) {
            process.setWaitingTime(Math.max(0, currentTime - process.getArrivalTime()));
            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
            currentTime = Math.max(currentTime, process.getArrivalTime()) + process.getBurstTime();
        }

        return sortedProcesses;
    }

    // Shortest Remaining Time First (SRTF)
    public static List<Process> srtfScheduling(List<Process> processes) {
        List<Process> sortedProcesses = new ArrayList<>(processes);
        sortedProcesses.sort(Comparator.comparingInt(Process::getBurstTime)); // Sort by burst time

        int currentTime = 0;
        List<Process> result = new ArrayList<>();
        while (!sortedProcesses.isEmpty()) {
            Process currentProcess = sortedProcesses.get(0);
            sortedProcesses.remove(0);

            currentProcess.setWaitingTime(Math.max(0, currentTime - currentProcess.getArrivalTime()));
            currentProcess.setTurnaroundTime(currentProcess.getWaitingTime() + currentProcess.getBurstTime());
            currentTime += currentProcess.getBurstTime();

            result.add(currentProcess);
        }

        return result;
    }

    // FCAI Scheduling (Uses Quantum)
    public static List<Process> fcaiScheduling(List<Process> processes) {
        List<Process> sortedProcesses = new ArrayList<>(processes);

        // Sort processes by priority initially (this can be adjusted based on your strategy)
        sortedProcesses.sort(Comparator.comparingInt(Process::getPriority));

        int currentTime = 0;
        int contextSwitchCount = 0;
        List<Integer> quantumHistory = new ArrayList<>();
        List<String> executionOrder = new ArrayList<>();
        double totalWaitingTime = 0, totalTurnaroundTime = 0;

        for (Process process : sortedProcesses) {
            int initialQuantum = process.getInitialQuantum(); // Quantum is used only for FCAI
            int remainingQuantum = initialQuantum;

            // Set the waiting time for the process
            process.setWaitingTime(Math.max(0, currentTime - process.getArrivalTime()));
            totalWaitingTime += process.getWaitingTime();

            // Set the turnaround time
            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
            totalTurnaroundTime += process.getTurnaroundTime();

            // Update the current time after executing the process
            currentTime = Math.max(currentTime, process.getArrivalTime()) + process.getBurstTime();

            // Record the quantum history for FCAI Scheduling
            quantumHistory.add(remainingQuantum); // Store the quantum value after execution

            // Record the execution order
            executionOrder.add(process.getName());

            // Count context switches (each time we switch between processes)
            contextSwitchCount++;
        }

        // Calculate averages
        double avgWaitingTime = totalWaitingTime / sortedProcesses.size();
        double avgTurnaroundTime = totalTurnaroundTime / sortedProcesses.size();

        // Print results (in console or use elsewhere)
        System.out.println("Execution Order: " + executionOrder);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Quantum History: " + quantumHistory);
        System.out.println("Total Context Switches: " + contextSwitchCount);

        return sortedProcesses;
    }
}

