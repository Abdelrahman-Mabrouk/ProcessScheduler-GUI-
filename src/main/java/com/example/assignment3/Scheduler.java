package com.example.assignment3;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

public class Scheduler {
    // Non-preemptive Priority Scheduling
    public static List<Process> priorityScheduling(List<Process> processes,int contextSwitch) {
        System.out.println("Before Running Algorithm:");
        for (Process process : processes) {
            System.out.printf("Process: %s, Priority: %d, EffBurstTime: %d, Arrival time: %d\n",
                    process.getName(), process.getPriority(), process.getEffBurstTime(), process.getArrivalTime());
        }

        List<Process> timeline = new ArrayList<>(); // لتسجيل عمليات الجدولة لاستخدامها في الجراف
        List<Process> running = new ArrayList<>();
        int n = processes.size();
        int avgWT = 0, avgTAT = 0;
        int i = 0, time = 0, finish_count = 0;
        Process inCPU = null;

        while (finish_count < n) {
            while (i < processes.size() && processes.get(i).getArrivalTime() <= time) {
                running.add(processes.get(i));
                i++;
            }
            if (running.isEmpty()) {
                time++;
                continue;
            }

            Process leastPriorityProcess = Collections.min(running, comparingInt(Process::getPriority));
            if (inCPU == null) {
                inCPU = leastPriorityProcess;
                time++;
                continue;
            }
            if (leastPriorityProcess.getPriority() < inCPU.getPriority()) {
                inCPU = leastPriorityProcess;
            }

            inCPU.setRemainingTime(inCPU.getRemainingTime() - 1);

            timeline.add(inCPU);

            Iterator<Process> iterator = running.iterator(); // Use Iterator for safe removal
            while (iterator.hasNext()) {
                Process process = iterator.next();
                if (process.getRemainingTime() == 0) {
                    // Print process required info
                    System.out.printf("Process: %s\n", process.getName());
                    System.out.printf("WT: %d\n", process.getWaitTime());
                    System.out.printf("TAT: %d\n", process.getTurnAround());
                    System.out.println("------------------------------");
                    avgWT += process.getWaitTime();
                    avgTAT += process.getTurnAround();
                    finish_count++;

                    // Remove the completed process from the running list
                    iterator.remove();
                }
                // to solve starvation problem
                if (process.getBurstTime() > process.getRemainingTime() && process != inCPU) {
                    process.decPriority(time);
                }
                if (process != inCPU) {
                    process.incrementWaitTime();
                }
                process.incrementTurnaround();

                // If no processes are currently in the CPU, just increment time
                if (inCPU != null && inCPU.getRemainingTime() == 0) {
                    inCPU = null;  // Set CPU to idle when the current process finishes
                }
            }
            time++;
        }
        System.out.printf("Average Waiting Time: %d \n", avgWT / finish_count);
        System.out.printf("Average Turnaround Time: %d", avgTAT / finish_count);
        System.out.println("Before Running Algorithm:");
        for (Process process : processes) {
            System.out.printf("Process: %s, Priority: %d, EffBurstTime: %d, Arrival time: %d\n",
                    process.getName(), process.getPriority(), process.getEffBurstTime(), process.getArrivalTime());
        }

        return timeline;
    }

    // Shortest Job First (SJF)
    public static List<Process> sjfScheduling(List<Process> processes,int contextSwitch) {
        System.out.println("Before Running Algorithm:");
        for (Process process : processes) {
            System.out.printf("Process: %s, Priority: %d, EffBurstTime: %d, Arrival time: %d\n",
                    process.getName(), process.getPriority(), process.getEffBurstTime(), process.getArrivalTime());
        }
        List<Process> running = new ArrayList<>();
        List<Process> timeline = new ArrayList<>(); // لتسجيل عمليات الجدولة لاستخدامها في الجراف
        int last_arrive = processes.getLast().getArrivalTime();
        int avgWT = 0, avgTAT = 0; // متوسط أوقات الانتظار والدوران
        int time = 0, finishCount = 0, i = 0;
        Process inCPU = null;

        // ترتيب العمليات حسب وقت الوصول
        processes.sort(comparingInt(Process::getArrivalTime));

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
            Process sjp = Collections.min(running, comparingInt(Process::getEffBurstTime));

            if (inCPU == null || sjp.getEffBurstTime() < inCPU.getEffBurstTime()) {
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
                    System.out.printf("WT: %d\n", process.getWaitTime());
                    System.out.printf("TAT: %d\n", process.getTurnAround());
                    System.out.println("------------------------------");
                    avgWT += process.getWaitTime();
                    avgTAT += process.getTurnAround();
                    finishCount++;

                    // Remove the completed process from the running list
                    iterator.remove();
                }
                // this is to solve the starvation problem
                if (process.getBurstTime() > process.getRemainingTime() && process != inCPU) {
                    process.decEffBurstTime(time);
                }
                // incrementing the waiting time for the processes not in cpu
                if (process != inCPU) {
                    process.incrementWaitTime();
                }
                // incrementing the total turn around time for all the processes
                process.incrementTurnaround();


                time++;
            }
        }

        // طباعة الإحصائيات النهائية
        System.out.printf("Average Waiting Time: %d \n", avgWT / finishCount);
        System.out.printf("Average Turnaround Time: %d\n", avgTAT / finishCount);
        System.out.println("Before Running Algorithm:");
        for (Process process : processes) {
            System.out.printf("Process: %s, Priority: %d, EffBurstTime: %d, Arrival time: %d\n",
                    process.getName(), process.getPriority(), process.getEffBurstTime(), process.getArrivalTime());
        }
        return timeline; // إرجاع الجدول الزمني لتحديث الرسم البياني

    }

    // Shortest Remaining Time First (SRTF)
    public static List<Process> srtfScheduling(List<Process> processes,int contextSwitch) {
        System.out.println("Before Running Algorithm:");
        for (Process process : processes) {
            System.out.printf("Process: %s, Priority: %d, EffBurstTime: %d, Arrival time: %d\n",
                    process.getName(), process.getPriority(), process.getEffBurstTime(), process.getArrivalTime());
        }
        List<Process> timeline = new ArrayList<>(); // لتسجيل عمليات الجدولة لاستخدامها في الجراف
        List<Process> running = new ArrayList<>();
        int i = 0, time = 0;
        int totalWT = 0, totalTAT = 0;
        int finishCount = 0;
        Process inCPU = null;

        while (finishCount < processes.size()) {
            // Add newly arrived processes to the running list
            while (i < processes.size() && processes.get(i).getArrivalTime() <= time) {
                running.add(processes.get(i));
                i++;
            }

            // If no processes are ready, increment time and continue
            if (running.isEmpty()) {
                time++;
                continue;
            }

            // Get the process with the shortest remaining time
            Process SRTP = Collections.min(running, Comparator.comparingInt(Process::getRemainingTime));
            Process maxWaitProcess = Collections.max(running, Comparator.comparingInt(Process::getWaitTime));

            // Calculate median waiting time
            int maxWaitTime = maxWaitProcess.getWaitTime();
            int minWaitTime = Collections.min(running, Comparator.comparingInt(Process::getWaitTime)).getWaitTime();
            int median = maxWaitTime - minWaitTime;

            // Starvation handling
            if (inCPU == null || SRTP.getRemainingTime() < inCPU.getRemainingTime()) {
                if (maxWaitProcess.getWaitTime() > median + 3) {
                    inCPU = maxWaitProcess;
                } else {
                    inCPU = SRTP;
                }
            }

            // Execute the selected process for one unit of time
            inCPU.setRemainingTime(inCPU.getRemainingTime() - 1);
            time++; // Increment time after executing
            timeline.add(inCPU);


            // Check if the process has finished execution
            if (inCPU.getRemainingTime() == 0) {
                inCPU.setTurnAround(time - inCPU.getArrivalTime());
                inCPU.setWaitTime(inCPU.getTurnAround() - inCPU.getBurstTime());

                // Validate metrics to prevent negative or overflowed values
                if (inCPU.getWaitTime() < 0) {
                    inCPU.setWaitTime(0);
                }

                // Print process metrics
                System.out.printf("Process: %s\n", inCPU.getName());
                System.out.printf("WT: %d\n", inCPU.getWaitTime());
                System.out.printf("TAT: %d\n", inCPU.getTurnAround());
                System.out.println("------------------------------");

                totalWT += inCPU.getWaitTime();
                totalTAT += inCPU.getTurnAround();
                finishCount++;
                running.remove(inCPU); // Safely remove from running list
                inCPU = null; // Reset CPU
            }

            // Increment waiting and turnaround times for all other processes
            for (Process process : running) {
                if (process != inCPU) {
                    process.incrementWaitTime();
                }
                process.incrementTurnaround();
            }
        }

        // Print overall statistics
        System.out.printf("Average Waiting Time: %.2f \n", (double) totalWT / processes.size());
        System.out.printf("Average Turnaround Time: %.2f \n", (double) totalTAT / processes.size());
        System.out.println("Before Running Algorithm:");
        for (Process process : processes) {
            System.out.printf("Process: %s, Priority: %d, EffBurstTime: %d, Arrival time: %d\n",
                    process.getName(), process.getPriority(), process.getEffBurstTime(), process.getArrivalTime());
        }
        return timeline;
    }

    // FCAI Scheduling (Uses Quantum)
    public static int executeProcess(Process process, int v1, int v2) {
        process.decrementRemainingTime();
        if (process.getRemainingTime() == 0) {
            return 1;
        }
        process.updateUsedQuantum();
        process.setFcai(v1, v2);
        if (process.getUsedQuant() == process.getQuantum()) {
            return 2;
        }
        return 0;
    }

    public static int compareFcais(Process a, Process b) {
        if (a.getFcai() != b.getFcai()) {
            return Double.compare(a.getFcai(), b.getFcai()); // Compare FCAIs
        }
        return Integer.compare(a.getPriority(), b.getPriority()); // Compare priorities if FCAIs are equal
    }


    public static List<Process> fcaiScheduling(List<Process> processList, int contextSwitch) {
        List<Process> timeline = new ArrayList<>(); // لتسجيل عمليات الجدولة لاستخدامها في الجراف
        int n = processList.size(), done_count = 0;
        processList.sort(comparingInt(Process::getArrivalTime));
        int v1 = processList.get(processList.size() - 1).getArrivalTime();
        int v2 = processList.stream().mapToInt(Process::getBurstTime).max().orElse(-1);
        for (Process process : processList) {
            process.setFcai(v1, v2);
        }

        // Prepare tracking variables
        List<Process> done = new ArrayList<>();
        Process inCPU = null;
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Scheduler::compareFcais);
        int time = 0;
        while (done_count < n) {
            if (inCPU == null) {
                Iterator<Process> iterator = processList.iterator();
                while (iterator.hasNext()) {
                    Process process = iterator.next();
                    if (process.getArrivalTime() <= time) {
                        readyQueue.offer(process);
                        iterator.remove(); // Safely remove the element
                    }
                }
                if (!readyQueue.isEmpty()) {
                    inCPU = readyQueue.poll();
                    System.out.println("Process " + inCPU.getName() + " activated at time " + time);
                } else {
                    System.out.println("CPU is idle at time " + time++);
                    continue;
                }
            }
            // Execute the current process
            for (Process process : readyQueue) {
                process.incrementWaitTime();
            }

            switch (executeProcess(inCPU, v1, v2)) {
                case 0 -> {
                    if (!readyQueue.isEmpty()) { // Check if you need to do a swap
                        double calc = (double) inCPU.getUsedQuant() / inCPU.getQuantum();
                        if (calc > 0.4) {
                            Process next = readyQueue.peek();
                            if (inCPU.getFcai() > next.getFcai()
                                    || (inCPU.getFcai() == next.getFcai() && inCPU.getPriority() > next.getPriority())) {
                                inCPU.updateQuantumHistory(inCPU.getQuantum() - inCPU.getUsedQuant());
                                inCPU.resetUsedQuant();
                                readyQueue.poll();
                                readyQueue.offer(inCPU);
                                inCPU = next;
                                System.out.println("Process " + inCPU.getName() + " preempted " + readyQueue.peek().getName() + " at time " + time);
                                time += contextSwitch;
                            }
                        }
                    }
                }
                case 1 -> {
                    System.out.println("Process " + inCPU.getName() + " finished at time " + time);
                    inCPU.setCompletionTime(time);
                    done_count++;
                    done.add(inCPU);
                    inCPU = null;
                }
                case 2 -> {
                    System.out.println("Process " + inCPU.getName() + " has finished a quantum at time " + time);
                    readyQueue.offer(inCPU);
                    inCPU = null;
                }
            }
            time++;
        }

        // طباعة الإحصائيات النهائية
        System.out.println("\n\nExecution finished, here are the stats: \n");
        double avgTurnaround = done.stream().mapToInt(Process::getTurnAround).average().orElse(0);
        double avgWait = done.stream().mapToInt(Process::getWaitTime).average().orElse(0);
        System.out.println(done.stream().map(Process::getName).collect(Collectors.joining(" | ")));
        for (Process process : done) {
            System.out.println("Process " + process.getName() + " arrived at time " + process.getArrivalTime());
            System.out.println(">>> " + process.getName() + " finished at time " + process.getCompletionTime());
            System.out.println(">>> " + process.getName() + " waited for time " + process.getWaitTime() + "\n");
            if (!process.getQuantumHistory().isEmpty()) {
                System.out.println(">>> The Quantuam Update History of " + process.getName() + ":");
                System.out.println(String.join(" -> ", process.getQuantumHistory().stream().map(String::valueOf).toList()));
            }
        }
        System.out.println("Average Waiting Time: " + avgWait);
        System.out.println("Average TurnAround Time: " + avgTurnaround);

        // return the final timeline that includes process updates
        return done;  // استخدام done هنا لأن هذه هي العمليات التي تم تنفيذها بالفعل
    }
}

