package com.example.assignment3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TXTReader {
    public static List<Process> readProcessesFromTXT(String filePath) throws IOException {
        List<Process> processes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\\s+"); // فصل البيانات باستخدام المسافات أو التاب

                if (data.length == 5) {  // تعديل لتتناسب مع 5 قيم
                    String name = data[0];
                    int arrivalTime = Integer.parseInt(data[1]);
                    int burstTime = Integer.parseInt(data[2]);
                    int priority = Integer.parseInt(data[3]);
                    int initialQuantum = Integer.parseInt(data[4]); // قراءة الـ Quantum

                    processes.add(new Process(name, arrivalTime, burstTime, priority, initialQuantum));
                }
            }
        }

        return processes;
    }
}

