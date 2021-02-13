package com.kekadoc.tools.android.animation.util;

/** Коллекция констант продолжительностей анимаций */
public interface Durations {

    long DELAY_DEFAULT = 0L;

    long DURATION_VERY_VERY_SHORT = 100L;
    long DURATION_VERY_SHORT = 150L;
    long DURATION_SHORT = 200L;
    long DURATION_NORMAL = 300L;
    long DURATION_LONG = 400L;
    long DURATION_VERY_LONG = 500L;
    long DURATION_VERY_VERY_LONG = 600L;
    long DURATION_SUPER_VERY_LONG = 900L;

    /** Стандартная скорость анимации */
    long DURATION_DEFAULT = DURATION_NORMAL;

}
