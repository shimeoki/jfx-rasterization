package com.github.shimeoki.jfx.rasterization.test;

/**
 * A enum for {@link Timekeeper} convertation to different time units, mostly
 * seconds and minutes.
 *
 * <p>
 * Base unit is {@link #SECOND}, but probably because of the
 * {@code System.nanoTime()}, everything is converted from {@link #NANOSECOND}.
 * <p>
 * For names and convertation refer to <a href=
 * "https://en.wikipedia.org/wiki/Orders_of_magnitude_(time)">Wikipedia</a>.
 *
 * @author shimeoki
 * @since 1.0.0
 *
 * @see Timekeeper
 */
public enum TimeUnit {

    /**
     * Nanoseconds time unit. Should be equal to {@code seconds * 1E+9}
     *
     * @since 1.0.0
     */
    NANOSECOND,

    /**
     * Microseconds time unit. Should be equal to {@code seconds * 1E+6}
     *
     * @since 1.0.0
     */
    MICROSECOND,

    /**
     * Milliseconds time unit. Should be equal to {@code seconds * 1E+3}
     *
     * @since 1.0.0
     */
    MILLISECOND,

    /**
     * Centiseconds time unit. Should be equal to {@code seconds * 1E+2}
     *
     * @since 1.0.0
     */
    CENTISECOND,

    /**
     * Deciseconds time unit. Should be equal to {@code seconds * 1E+1}
     *
     * @since 1.0.0
     */
    DECISECOND,

    /**
     * Seconds time unit.
     *
     * @since 1.0.0
     */
    SECOND,

    /**
     * Nanoseconds time unit. Should be equal to {@code seconds * 1E-1}
     *
     * @since 1.0.0
     */
    DECASECOND,

    /**
     * Hectoseconds time unit. Should be equal to {@code seconds * 1E-2}
     *
     * @since 1.0.0
     */
    HECTOSECOND,

    /**
     * Kiloseconds time unit. Should be equal to {@code seconds * 1E-3}
     *
     * @since 1.0.0
     */
    KILOSECOND,

    /**
     * Minutes time unit. Should be equal to {@code seconds / 60.0}
     *
     * @since 1.0.0
     */
    MINUTE
}
