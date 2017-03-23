package com.siemens.spe.spelunky.system.utility.timer;

/**
 * Created by 152544be on 23.03.2017.
 */

/**
 * Designed in a way so that the User doesn't has to bother with anything the Timer does
 * Useage:
 * \tSTARTUP: add "TimerManager.update(double elapsed)" to your Main Loop and give it he timeSpan of each Iteration
 * \t1) Choose a suitable Timer. Available Timers are
 * \t\tAUTO_COUNTDOWN_TIMER, A timer that'll automatically count to zero
 * \t\tAUTO_COUNTDOWN_WAITING_TIMER,A timer that'll also count to zero but stops until request by the User (for longer time - critical Effects)
 * \t2) Initialie: You can't Initialise a Timer yourself, you can only request one by calling TimeManager.requestTimerHandle(...)
 * \t
 * \t3) You can request the State of your Timer by calling the ready() function (Imagine it like the ca-ching of a Microwave)
 * Example:
 * TimerHandle timer = TimerManager.requestTimerHandle(AUTO_COUNTDOWN_TIMER,1000.f/60.f)//fires with 60 Hz
 * if(timer.ready())
 * {
 * //Do something...
 * }
 */


final public class TimerHandle {
    static int counter;
    int HANDLE_ID;

    private TimerHandle() {
        ++counter;
        HANDLE_ID = counter;
    }

    public int hashCode() {
        return HANDLE_ID * 31;
    }

    public boolean equals(Object obj) {
        TimerHandle other = (TimerHandle) obj;
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (this.HANDLE_ID == other.HANDLE_ID) {

            return true;
        }

        return false;

    }

    public boolean ready() {
        return TimerManager.isReady(this);
    }

    public static TimerHandle requestTimerHandle(TimerManager.FRIEND friend) {
        friend.hashCode();
        return new TimerHandle();
    }
}
