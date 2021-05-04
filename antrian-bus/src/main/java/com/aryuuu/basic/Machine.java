package com.aryuuu.basic;

import java.io.IOException;

import org.javasim.RestartException;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;
import org.javasim.streams.ExponentialStream;

public class Machine extends SimulationProcess {
    public Machine(double mean) {
        STime = new ExponentialStream(mean);
        operational = true;
        working = false;
        J = null;
    }

    public void run() {
        double ActiveStart, ActiveEnd;

        while (!terminated()) {
            working = true;

            while (!MachineShop.JobQ.isEmpty()) {
                ActiveStart = currentTime();
                MachineShop.CheckFreq++;

                MachineShop.JobsInQueue += MachineShop.JobQ.queueSize();
                J = MachineShop.JobQ.dequeue();

                try {
                    hold(serviceTime());
                } catch (SimulationException e) {
                } catch (RestartException e) {
                }

                ActiveEnd = currentTime();
                MachineShop.MachineActiveTime += ActiveEnd - ActiveStart;
                MachineShop.ProcessedJobs++;

                /*
                 * Introduce this new method because we usually rely upon the destructor of the
                 * object to do the work in C++.
                 */

                J.finished();
            }

            working = false;

            try {
                cancel();
            } catch (RestartException e) {
            }
        }
    }

    public void broken() {
        operational = false;
    }

    public void fixed() {
        operational = true;
    }

    public boolean isOperational() {
        return operational;
    }

    public boolean processing() {
        return working;
    }

    public double serviceTime() {
        try {
            return STime.getNumber();
        } catch (IOException e) {
            return 0.0;
        }
    }

    private ExponentialStream STime;

    private boolean operational;

    private boolean working;

    private Job J;
}
