package com.siemens.spe.spelunky.system.utility.timer;

import java.util.HashMap;

/**
 * Created by 152544be on 23.03.2017.
 */
public class TimerManager {
    private static HashMap<TimerHandle, ITimer> timerMap = new HashMap<TimerHandle, ITimer>();

    public static final class FRIEND {
        private FRIEND() {
        }
    }

    private static final FRIEND friend = new FRIEND();

    public static TimerHandle requestTimerHandle(TimerType tType, int... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Keine Parameter");
        }

        TimerHandle tH = TimerHandle.requestTimerHandle(friend);

        switch (tType) {
            case AUTO_COUNTDOWN_TIMER:
                if (values.length != 1) {
                    throw new IllegalArgumentException("Timer erwartet EIN Zeitintervall in Millisekunden");
                }
                timerMap.put(tH, AutoCountdownTimer.requestNewAutoCountdownTimer(friend, values[0]));
                break;
            case AUTO_COUNTDOWN_WAITING_TIMER:
                if (values.length != 1) {
                    throw new IllegalArgumentException("Timer erwartet EIN Zeitintervall in Millisekunden");
                }
                timerMap.put(tH, AutoCountdownWaitingTimer.requestNewAutoCountdownWaitingTimer(friend, values[0]));
                break;
            case AUTO_TICK_TIMER:
                if (values.length != 1) {
                    throw new IllegalArgumentException("Timer erwartet EINE stepSize");
                }
                timerMap.put(tH, AutoTickTimer.requestNewAutoTickTimer(friend, values[0]));
                break;
        }

        return tH;
    }

    public static void update(double elapsedMS) {
        timerMap.forEach((k, v) -> v.update(elapsedMS));
    }

    public static boolean isReady(TimerHandle tH) {
        return timerMap.get(tH).ready();
    }
}
