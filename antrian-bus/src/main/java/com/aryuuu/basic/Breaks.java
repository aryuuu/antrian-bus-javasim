package com.aryuuu.basic;

import java.io.IOException;

import org.javasim.RestartException;
import org.javasim.SimulationException;
import org.javasim.SimulationProcess;
import org.javasim.streams.UniformStream;

public class Breaks extends SimulationProcess {
    public Breaks() {
        RepairTime = new UniformStream(10, 100);
        OperativeTime = new UniformStream(200, 500);
        interrupted_service = false;
    }

    public void run() {
        while (!terminated()) {
            try {
                double failedTime = RepairTime.getNumber();

                hold(OperativeTime.getNumber());

                MachineShop.M.broken();
                MachineShop.M.cancel();

                if (!MachineShop.JobQ.isEmpty())
                    interrupted_service = true;

                hold(failedTime);

                MachineShop.MachineFailedTime += failedTime;
                MachineShop.M.fixed();

                if (interrupted_service)
                    MachineShop.M.activateAt(MachineShop.M.serviceTime() + currentTime());
                else
                    MachineShop.M.activate();

                interrupted_service = false;
            } catch (SimulationException e) {
            } catch (RestartException e) {
            } catch (IOException e) {
            }
        }
    }

    private UniformStream RepairTime;

    private UniformStream OperativeTime;

    private boolean interrupted_service;
}
