package com.siemens.spe.spelunky.system.utility.timer;

import java.util.HashMap;

/**
 * Created by 152544be on 23.03.2017.
 */
public class TimerManager {
    private static HashMap<TimerHandle, ITimer> timerMap = new HashMap<>();
    private static double elapsedMS;

    public static final class FRIEND {
        private FRIEND() {
        }
    }

    private static final FRIEND friend = new FRIEND();

    public static TimerHandle requestTimerHandle(TimerType tType, double  values) {

        TimerHandle tH = TimerHandle.requestTimerHandle(friend);

        switch (tType) {
            case AUTO_COUNTDOWN_TIMER:
                timerMap.put(tH, AutoCountdownTimer.requestNewAutoCountdownTimer(friend, values));
                break;
            case AUTO_COUNTDOWN_WAITING_TIMER:
                timerMap.put(tH, AutoCountdownWaitingTimer.requestNewAutoCountdownWaitingTimer(friend, values));
                break;
            case AUTO_TICK_TIMER:
                timerMap.put(tH, AutoTickTimer.requestNewAutoTickTimer(friend, (int)values));
                break;
        }

        return tH;
    }

    public static void tick() {
        timerMap.forEach((k, v) -> {if(v instanceof AutoTickTimer)
        {
            AutoTickTimer u = (AutoTickTimer)(v);
            u.tick();
        }});
    }

    public static void update(double elapsed) {
        elapsedMS = elapsed;
        timerMap.forEach((k, v) -> {if(!(v instanceof AutoTickTimer))v.update(elapsedMS);});
    }

    public static boolean isReady(TimerHandle tH) {
        return timerMap.get(tH).ready();
    }
}
