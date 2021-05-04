package com.aryuuu.basic;

import org.javasim.RestartException;
import org.javasim.Scheduler;
import org.javasim.SimulationException;

public class Job {
    public Job() {
        boolean empty = false;

        ResponseTime = 0.0;
        ArrivalTime = Scheduler.currentTime();

        empty = MachineShop.JobQ.isEmpty();
        MachineShop.JobQ.enqueue(this);
        MachineShop.TotalJobs++;

        if (empty && !MachineShop.M.processing() && MachineShop.M.isOperational()) {
            try {
                MachineShop.M.activate();
            } catch (SimulationException e) {
            } catch (RestartException e) {
            }
        }
    }

    public void finished() {
        ResponseTime = Scheduler.currentTime() - ArrivalTime;
        MachineShop.TotalResponseTime += ResponseTime;
    }

    private double ResponseTime;

    private double ArrivalTime;
}
