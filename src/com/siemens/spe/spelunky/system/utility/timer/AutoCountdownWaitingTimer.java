package com.siemens.spe.spelunky.system.utility.timer;

/**
 * Created by 152544be on 23.03.2017.
 */
public class AutoCountdownWaitingTimer implements ITimer {
    private double timeSpanMS;
    private double currentMS;
    private boolean ready;

    public static AutoCountdownWaitingTimer requestNewAutoCountdownWaitingTimer(TimerManager.FRIEND friend, double timeSpanMS) {
        friend.hashCode();
        return new AutoCountdownWaitingTimer(timeSpanMS);
    }

    private AutoCountdownWaitingTimer(double timeSpanMS) {
        this.timeSpanMS = timeSpanMS;
        this.currentMS = timeSpanMS;
        this.ready = false;
    }

    public void update(double... values) {
        double elapsedMS = values[0];
        currentMS -= elapsedMS;
        if (currentMS <= 0) {
            currentMS = 0;
            ready = true;
        }
    }

    public boolean ready() {
        if (ready) {
            ready = false;
            return true;
        }
        return false;
    }

}
