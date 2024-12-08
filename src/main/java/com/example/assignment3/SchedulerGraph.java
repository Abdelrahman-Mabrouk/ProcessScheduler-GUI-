package com.example.assignment3;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SchedulerGraph extends Canvas {
    public SchedulerGraph(double width, double height) {
        super(width, height);
    }

    public void drawProcessGraph(Process[] processes) {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, this.getWidth(), this.getHeight()); // مسح الـ Canvas

        double rowHeight = this.getHeight() / processes.length; // ارتفاع كل صف
        double widthPerTimeUnit = (this.getWidth() - 100) / getTotalExecutionTime(processes); // عرض الزمن مع ترك مساحة للأسماء
        double labelOffset = 80; // الإزاحة الأفقية للأسماء

        for (int i = 0; i < processes.length; i++) {
            Process process = processes[i];

            // رسم اسم العملية في أقصى اليسار
            gc.setFill(Color.BLACK);
            gc.setFont(new Font("Arial", 14));
            gc.fillText(process.getName(), 5, (i + 0.5) * rowHeight); // اسم العملية في أقصى اليسار

            // رسم المستطيل الملون بعد الاسم
            gc.setFill(process.getColor());
            double rectX = labelOffset; // بداية المستطيل بعد الإزاحة
            double rectWidth = process.getBurstTime() * widthPerTimeUnit; // عرض المستطيل بناءً على الـ Burst Time
            double rectY = i * rowHeight; // بداية الصف
            gc.fillRect(rectX, rectY, rectWidth, rowHeight - 5); // رسم المستطيل مع ترك مسافة صغيرة بين الصفوف
        }
    }

    private double getTotalExecutionTime(Process[] processes) {
        double totalTime = 0;
        for (Process process : processes) {
            totalTime += process.getBurstTime();
        }
        return totalTime;
    }
}
