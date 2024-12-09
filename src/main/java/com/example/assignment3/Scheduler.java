package com.example.assignment3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Scheduler {

    // Non-preemptive Priority Scheduling
    public static List<Process> priorityScheduling(List<Process> processes) {
        List<Process> sortedProcesses = new ArrayList<>(processes);
        // Sort processes based on priority (lower value = higher priority)
        sortedProcesses.sort(Comparator.comparingInt(Process::getPriority));

//        int currentTime = 0;
//        for (Process process : sortedProcesses) {
//            process.setWaitingTime(Math.max(0, currentTime - process.getArrivalTime()));
//            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
//            currentTime = Math.max(currentTime, process.getArrivalTime()) + process.getBurstTime();
//        }

        return sortedProcesses;
    }

    // Shortest Job First (SJF)
    public static List<Process> sjfScheduling(List<Process> processes) {
        List<Process> running = new ArrayList<>();
        List<Process> timeline = new ArrayList<>(); // لتسجيل عمليات الجدولة لاستخدامها في الجراف
        int last_arrive = processes.getLast().getArrivalTime();
        int avgWT = 0, avgTAT = 0 ; // متوسط أوقات الانتظار والدوران
        int time = 0, finishCount = 0 , i = 0;
        Process inCPU = null;

        // ترتيب العمليات حسب وقت الوصول
        processes.sort(Comparator.comparingInt(Process::getArrivalTime));

        while (finishCount < processes.size()) {
            // continuously checking if new processes arrive until all processes have arrived
            while (i < processes.size() && processes.get(i).getArrivalTime() <= time) {
                running.add(processes.get(i));
                i++;
            }
            // إذا لم تصل أي عملية، قم بزيادة الزمن
            if (running.isEmpty()) {
                time++;
                continue;
            }

            // اختيار أقصر عملية بناءً على وقت التنفيذ المتبقي
            Process sjp = Collections.min(running, Comparator.comparingInt(Process::getEffBustTime));

            if (inCPU == null || sjp.getEffBustTime() < inCPU.getEffBustTime()) {
                inCPU = sjp;
            }

            // تنفيذ العملية وتقليل الوقت المتبقي
            inCPU.setRemainingTime(inCPU.getRemainingTime() - 1);

            // تسجيل العملية في الجدول الزمني
            timeline.add(inCPU);

            // إذا انتهت العملية الحالية
            if (inCPU.getRemainingTime() == 0) {
                inCPU = null; // تفريغ الـ CPU
            }

            Iterator<Process> iterator = running.iterator(); // Using Iterator for safe removal
            while (iterator.hasNext()) {
                // removing finished processes from the running queue
                Process process = iterator.next();
                if (process.getRemainingTime() == 0) {
                    // Printing terminated process required info
                    System.out.printf("Process: %s\n", process.getName());
                    System.out.printf("WT: %d\n", process.getWT());
                    System.out.printf("TAT: %d\n", process.getTAT());
                    System.out.println("------------------------------");
                    avgWT += process.getWT();
                    avgTAT += process.getTAT();
                    finishCount++;

                    // Remove the completed process from the running list
                    iterator.remove();
                }
                // this is to solve the starvation problem
                if (process.getBurstTime() > process.getRemainingTime() && process != inCPU) {
                    process.decEffBustTime(time);
                }
                // incrementing the waiting time for the processes not in cpu
                if (process != inCPU) {
                    process.incWT();
                }
                // incrementing the total turn around time for all the processes
                process.incTAT();


            time++;
        }
        }

        // طباعة الإحصائيات النهائية
        System.out.printf("Average Waiting Time: %d \n", avgWT / finishCount);
        System.out.printf("Average Turnaround Time: %d\n", avgTAT / finishCount);

        return timeline; // إرجاع الجدول الزمني لتحديث الرسم البياني

    }

    // Shortest Remaining Time First (SRTF)
    public static List<Process> srtfScheduling(List<Process> processes) {
        List<Process> sortedProcesses = new ArrayList<>(processes);
        sortedProcesses.sort(Comparator.comparingInt(Process::getBurstTime)); // Sort by burst time

        int currentTime = 0;
        List<Process> result = new ArrayList<>();
//        while (!sortedProcesses.isEmpty()) {
//            Process currentProcess = sortedProcesses.get(0);
//            sortedProcesses.remove(0);
//
//            currentProcess.setWaitingTime(Math.max(0, currentTime - currentProcess.getArrivalTime()));
//            currentProcess.setTurnaroundTime(currentProcess.getWaitingTime() + currentProcess.getBurstTime());
//            currentTime += currentProcess.getBurstTime();
//
//            result.add(currentProcess);
//        }

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
//
//        for (Process process : sortedProcesses) {
//            int initialQuantum = process.getInitialQuantum(); // Quantum is used only for FCAI
//            int remainingQuantum = initialQuantum;
//
//            // Set the waiting time for the process
//            process.setWaitingTime(Math.max(0, currentTime - process.getArrivalTime()));
//            totalWaitingTime += process.getWaitingTime();
//
//            // Set the turnaround time
//            process.setTurnaroundTime(process.getWaitingTime() + process.getBurstTime());
//            totalTurnaroundTime += process.getTurnaroundTime();
//
//            // Update the current time after executing the process
//            currentTime = Math.max(currentTime, process.getArrivalTime()) + process.getBurstTime();
//
//            // Record the quantum history for FCAI Scheduling
//            quantumHistory.add(remainingQuantum); // Store the quantum value after execution
//
//            // Record the execution order
//            executionOrder.add(process.getName());
//
//            // Count context switches (each time we switch between processes)
//            contextSwitchCount++;
//        }
//
//        // Calculate averages
//        double avgWaitingTime = totalWaitingTime / sortedProcesses.size();
//        double avgTurnaroundTime = totalTurnaroundTime / sortedProcesses.size();
//
//        // Print results (in console or use elsewhere)
//        System.out.println("Execution Order: " + executionOrder);
//        System.out.println("Average Waiting Time: " + avgWaitingTime);
//        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
//        System.out.println("Quantum History: " + quantumHistory);
//        System.out.println("Total Context Switches: " + contextSwitchCount);

        return sortedProcesses;
    }
}

