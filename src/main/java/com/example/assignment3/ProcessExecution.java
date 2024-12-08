package com.example.assignment3;

public class ProcessExecution {
    private final String processName;
    private final int startTime;
    private final int duration;

    public ProcessExecution(String processName, int startTime, int duration) {
        this.processName = processName;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getProcessName() {
        return processName;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }
}
