package com.siemens.spe.spelunky.system.utility.timer;

/**
 * Created by 152544be on 23.03.2017.
 */
public class AutoCountdownTimer implements ITimer {
    private double timeSpanMS;
    private double currentMS;
    private boolean ready;

    public static AutoCountdownTimer requestNewAutoCountdownTimer(TimerManager.FRIEND friend, double timeSpanMS) {
        friend.hashCode();
        return new AutoCountdownTimer(timeSpanMS);
    }

    private AutoCountdownTimer(double timeSpanMS) {
        this.timeSpanMS = timeSpanMS;
        this.currentMS = timeSpanMS;
        this.ready = false;
    }

    public void update(double... values) {
        double elapsedMS = values[0];
        currentMS -= elapsedMS;
        if (currentMS <= 0) {
            double tempMS = Math.abs(currentMS);
            currentMS = timeSpanMS;
            currentMS -= tempMS;
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
