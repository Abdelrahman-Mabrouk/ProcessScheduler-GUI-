package com.example.assignment3;

import javafx.scene.paint.Color;

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

                if (data.length == 6) {  // تعديل لتتناسب مع 6 قيم (اسم، وقت الوصول، وقت الانفجار، الأولوية، Quantum، اللون)
                    String name = data[0];
                    int arrivalTime = Integer.parseInt(data[1]);
                    int burstTime = Integer.parseInt(data[2]);
                    int priority = Integer.parseInt(data[3]);
                    int initialQuantum = Integer.parseInt(data[4]);
                    Color color = getColorByName(data[5]);
                    processes.add(new Process(name, arrivalTime, burstTime, priority, initialQuantum, color));  // تمرير اللون
                }
            }
        }

        return processes;
    }

    private static Color getColorByName(String colorName) {
        switch (colorName.toLowerCase()) {
            case "red":
                return Color.RED;
            case "blue":
                return Color.BLUE;
            case "green":
                return Color.GREEN;
            case "yellow":
                return Color.YELLOW;
            case "purple":
                return Color.PURPLE;
            case "orange":
                return Color.ORANGE;
            case "brown":
                return Color.BROWN;
            case "gray":
                return Color.GRAY;
            case "pink":
                return Color.PINK;
            case "teal":
                return Color.TEAL;
            case "violet":
                return Color.VIOLET;
            case "cyan":
                return Color.CYAN;
            case "lightgreen":
                return Color.LIGHTGREEN;
            case "lightblue":
                return Color.LIGHTBLUE;
            case "lightyellow":
                return Color.LIGHTYELLOW;
            case "lightpink":
                return Color.LIGHTPINK;
            case "darkred":
                return Color.DARKRED;
            case "darkblue":
                return Color.DARKBLUE;
            case "darkgreen":
                return Color.DARKGREEN;
            case "darkorange":
                return Color.DARKORANGE;
            default:
                return Color.BLACK;  // اللون الافتراضي إذا كان الاسم غير معروف
        }
    }
}
