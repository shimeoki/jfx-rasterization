package com.github.shimeoki.jfx.rasterization.test;

import java.util.Objects;

/**
 * Standard {@link Timekeeper Timekeeper} implementation.
 *
 * <p>
 * Has default values for some parameters as constants.
 *
 * @since 1.0.0
 */
public final class BasicTimekeeper implements Timekeeper {

    /**
     * Default {@link #unit()} value on return, if {@code null} was passed to the
     * constructor.
     *
     * @since 1.0.0
     */
    public static final TimeUnit DEFAULT_UNIT = TimeUnit.MILLISECOND;

    /**
     * Default {@link #keeping()} value on return, if {@code null} was passed to the
     * constructor.
     *
     * @since 1.0.0
     */
    public static final boolean DEFAULT_KEEPING = true;

    /**
     * Default {@link #tracking()} value on return, if {@code null} was passed to
     * the constructor.
     *
     * @since 1.0.0
     */
    public static final boolean DEFAULT_TRACKING = false;

    private TimeUnit unit;

    private boolean keeping;
    private boolean tracking;

    private long sum = 0;
    private long count = 0;

    private long trackStart = 0;
    private long lastTrack = 0;

    /**
     * Creates a new {@link BasicTimekeeper BasicTimekeeper} instance.
     *
     * <p>
     * All the constructor parameters can be passed as {@code null} values, and in
     * this case this parameter value is initialized to corresponding
     * {@code DEFAULT} value.
     *
     * @param unit     valid {@link TimeUnit TimeUnit} or {@code null}
     * @param keeping  wrapped keeping {@code boolean} value or null
     * @param tracking wrapped tracking {@code boolean} value or null
     *
     * @since 1.0.0
     *
     * @see #DEFAULT_UNIT
     * @see #DEFAULT_KEEPING
     * @see #DEFAULT_TRACKING
     */
    public BasicTimekeeper(
            final TimeUnit unit, final Boolean keeping, final Boolean tracking) {

        if (unit != null) {
            setUnit(unit);
        } else {
            setUnit(DEFAULT_UNIT);
        }

        if (keeping != null) {
            setKeeping(keeping);
        } else {
            setKeeping(DEFAULT_KEEPING);
        }

        if (tracking != null) {
            setTracking(tracking);
        } else {
            setTracking(DEFAULT_TRACKING);
        }
    }

    private void setKeeping(final boolean keeping) {
        if (keeping) {
            keep();
        } else {
            unkeep();
        }
    }

    private void setTracking(final boolean tracking) {
        if (tracking) {
            track();
        } else {
            untrack();
        }
    }

    private void record(final long duration) {
        if (!keeping) {
            return;
        }

        count++;
        sum += duration;
    }

    @Override
    public TimeUnit unit() {
        return unit;
    }

    @Override
    public void setUnit(final TimeUnit unit) {
        Objects.requireNonNull(unit);

        this.unit = unit;
    }

    @Override
    public void keep() {
        keeping = true;
    }

    @Override
    public void unkeep() {
        keeping = false;
    }

    @Override
    public boolean keeping() {
        return keeping;
    }

    @Override
    public void clear() {
        sum = 0;
        count = 0;
    }

    @Override
    public long count() {
        return count;
    }

    @Override
    public double sum() {
        return converted(sum);
    }

    @Override
    public double avg() {
        if (count == 0) {
            return 0;
        }

        return sum() / count;
    }

    @Override
    public void track() {
        if (tracking) {
            untrack();
        }

        tracking = true;

        trackStart = System.nanoTime();
    }

    @Override
    public void untrack() {
        if (!tracking) {
            return;
        }

        final long trackEnd = System.nanoTime();
        lastTrack = trackEnd - trackStart;

        record(lastTrack);

        tracking = false;
    }

    @Override
    public boolean tracking() {
        return tracking;
    }

    @Override
    public double time(final Timeable func) {
        final long duration = duration(func);

        record(duration);
        lastTrack = duration;

        return converted(duration);
    }

    @Override
    public double elapsed() {
        if (!tracking) {
            return 0;
        }

        final long now = System.nanoTime();

        return converted(now - trackStart);
    }

    @Override
    public double lastTrack() {
        return converted(lastTrack);
    }

    private long duration(final Timeable func) {
        final long start = System.nanoTime();
        func.exec();
        final long end = System.nanoTime();

        return end - start;
    }

    private double microseconds(final double nanoseconds) {
        return nanoseconds * 1E-3;
    }

    private double milliseconds(final double nanoseconds) {
        return nanoseconds * 1E-6;
    }

    private double centiseconds(final double nanoseconds) {
        return nanoseconds * 1E-7;
    }

    private double deciseconds(final double nanoseconds) {
        return nanoseconds * 1E-8;
    }

    private double seconds(final double nanoseconds) {
        return nanoseconds * 1E-9;
    }

    private double decaseconds(final double nanoseconds) {
        return nanoseconds * 1E-10;
    }

    private double hectoseconds(final double nanoseconds) {
        return nanoseconds * 1E-11;
    }

    private double kiloseconds(final double nanoseconds) {
        return nanoseconds * 1E-12;
    }

    private double minutes(final double nanoseconds) {
        return seconds(nanoseconds) / 60;
    }

    private double convertTo(final long duration, final TimeUnit unit) {
        final double d = (double) duration;

        switch (unit) {
            case NANOSECOND:
                return d;
            case MICROSECOND:
                return microseconds(d);
            case MILLISECOND:
                return milliseconds(d);
            case CENTISECOND:
                return centiseconds(d);
            case DECISECOND:
                return deciseconds(d);
            case SECOND:
                return seconds(d);
            case DECASECOND:
                return decaseconds(d);
            case HECTOSECOND:
                return hectoseconds(d);
            case KILOSECOND:
                return kiloseconds(d);
            case MINUTE:
                return minutes(d);
            default:
                return d;
        }
    }

    private double converted(final long duration) {
        if (duration == 0) {
            return 0;
        }

        return convertTo(duration, unit);
    }
}
