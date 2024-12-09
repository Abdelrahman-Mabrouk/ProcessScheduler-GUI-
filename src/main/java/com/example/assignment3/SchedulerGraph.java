package com.example.assignment3;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;

import java.util.List;

public class SchedulerGraph {

    private ScrollPane scrollPane;  // متغير للـ ScrollPane
    private Canvas schedulerCanvas; // متغير للـ Canvas

    // منشئ لـ SchedulerGraph
    public SchedulerGraph() {
        // إنشاء Canvas
        schedulerCanvas = new Canvas(800, 200);  // تحديد عرض وارتفاع الـ Canvas
        // إنشاء ScrollPane
        scrollPane = new ScrollPane();

        // وضع Canvas داخل ScrollPane
        scrollPane.setContent(schedulerCanvas);
        scrollPane.setFitToWidth(true);  // لتوفير التمرير الأفقي بشكل أفضل
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // تمرير أفقي فقط إذا كان هناك حاجة
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // لا نحتاج تمرير عمودي
    }

    // دالة لرسم العمليات
    public void drawProcessGraph(List<Process> processes) {
        GraphicsContext gc = schedulerCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, schedulerCanvas.getWidth(), schedulerCanvas.getHeight());  // تنظيف الـ Canvas

        double rowHeight = schedulerCanvas.getHeight() / processes.size();
        double widthPerProcess = schedulerCanvas.getWidth() / processes.size();

        // رسم العمليات في كل صف
        for (int i = 0; i < processes.size(); i++) {
            Process process = processes.get(i);

            gc.setFill(process.getColor());  // تعيين اللون بناء على الـ Process

            // رسم مستطيل يمثل الـ Process في الـ Canvas
            gc.fillRect(0, i * rowHeight, process.getRemainingTime() * widthPerProcess, rowHeight - 2);

            // إضافة اسم البروسس في منتصف المستطيل
            gc.setFill(Color.BLACK);
            gc.fillText(process.getName(), 10, (i * rowHeight) + rowHeight / 2);
        }
    }

    // دالة لإرجاع الـ ScrollPane ليتم إضافته إلى الواجهة
    public ScrollPane getScrollPane() {
        return scrollPane;
    }
}
