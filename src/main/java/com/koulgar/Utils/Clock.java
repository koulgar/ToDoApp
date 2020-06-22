package com.koulgar.Utils;

import java.time.LocalDateTime;

public final class Clock {

    private static boolean isFrozen;

    private static LocalDateTime timeSet;

    private Clock() {
    }

    public static synchronized void freeze() {
        isFrozen = true;
    }

    public static synchronized void unfreeze() {
        isFrozen = false;
        timeSet = null;
    }

    public static LocalDateTime now() {
        LocalDateTime dateTime = LocalDateTime.now();
        if (isFrozen) {
            if (timeSet == null) {
                timeSet = dateTime;
            }
            return timeSet;
        }
        return dateTime;
    }

}
