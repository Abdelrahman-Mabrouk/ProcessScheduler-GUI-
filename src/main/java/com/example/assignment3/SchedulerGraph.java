package com.example.assignment3;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;

public class SchedulerGraph extends Canvas {
    public SchedulerGraph(double width, double height) {
        super(width, height);
    }

    // هذا الكود يحدد كيف يتم رسم العمليات في الجراف
    public void drawProcessGraph(List<Process> processes) {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight());  // Clear the canvas before drawing

        double widthPerProcess = this.getWidth() / processes.size();  // calculate width per process

        for (int i = 0; i < processes.size(); i++) {
            Process process = processes.get(i);
            double xPosition = i * widthPerProcess;  // position for each process in the row
            double yPosition = 50;  // row height, adjust as needed

            gc.setFill(process.getColor());
            gc.fillRect(xPosition, yPosition, widthPerProcess, 50);  // drawing a rectangle for each process

            gc.setFill(Color.BLACK);
            gc.fillText(process.getName(), xPosition + widthPerProcess / 2, yPosition + 25);  // drawing the process name
        }
    }
}
