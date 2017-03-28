package com.siemens.spe.spelunky.system.utility.timer;

/**
 * Created by 152544be on 23.03.2017.
 */
public class AutoTickTimer implements ITimer {
    private int ticks;
    private int currentTicks;
    boolean ready;

    private AutoTickTimer(int ticks) {
        this.ticks = ticks;
        currentTicks = ticks;
        ready = false;
    }

    public static AutoTickTimer requestNewAutoTickTimer(TimerManager.FRIEND friend, int ticks) {
        friend.hashCode();
        return new AutoTickTimer(ticks);
    }

    @Override
    public void update(double values) {
    }

    public void tick()
    {
        --currentTicks;
        if (currentTicks < 0) {
            currentTicks = ticks;
            ready = true;
        }
    }

    @Override
    public boolean ready() {
        if (ready) {
            ready = false;
            return true;
        }
        return false;
    }
}

