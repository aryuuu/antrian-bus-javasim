package com.aryuuu.basic;

import org.javasim.RestartException;
import org.javasim.Simulation;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;

public class MachineShop extends SimulationProcess {
    public MachineShop(boolean isBreaks) {
        useBreaks = isBreaks;

        TotalResponseTime = 0.0;
        TotalJobs = 0;
        ProcessedJobs = 0;
        JobsInQueue = 0;
        CheckFreq = 0;
        MachineActiveTime = 0.0;
        MachineFailedTime = 0.0;
    }

    public void run() {
        try {
            Breaks B = null;
            Arrivals A = new Arrivals(8);
            MachineShop.M = new Machine(8);
            Job J = new Job();

            A.activate();

            if (useBreaks) {
                B = new Breaks();
                B.activate();
            }

            Simulation.start();

            while (MachineShop.ProcessedJobs < 1000)
                hold(1000);

            System.out.println("Current time " + currentTime());
            System.out.println("Total number of jobs present " + TotalJobs);
            System.out.println("Total number of jobs processed " + ProcessedJobs);
            System.out.println("Total response time of " + TotalResponseTime);
            System.out.println("Average response time = " + (TotalResponseTime / ProcessedJobs));
            System.out.println("Probability that machine is working = "
                    + ((MachineActiveTime - MachineFailedTime) / currentTime()));
            System.out.println("Probability that machine has failed = " + (MachineFailedTime / MachineActiveTime));
            System.out.println("Average number of jobs present = " + (JobsInQueue / CheckFreq));

            Simulation.stop();

            A.terminate();
            MachineShop.M.terminate();

            if (useBreaks)
                B.terminate();

            SimulationProcess.mainResume();
        } catch (SimulationException e) {
        } catch (RestartException e) {
        }
    }

    public void await() {
        this.resumeProcess();
        SimulationProcess.mainSuspend();
    }

    public static Machine M = null;

    public static Queue JobQ = new Queue();

    public static double TotalResponseTime = 0.0;

    public static long TotalJobs = 0;

    public static long ProcessedJobs = 0;

    public static long JobsInQueue = 0;

    public static long CheckFreq = 0;

    public static double MachineActiveTime = 0.0;

    public static double MachineFailedTime = 0.0;

    private boolean useBreaks;
}
