package io.github.shimeoki.jfx.rasterization.demo.test;

import java.util.Objects;

public final class Timekeeper {

    public static final TimeUnit DEFAULT_UNIT = TimeUnit.MILLISECOND;
    public static final boolean DEFAULT_KEEPING = true;
    public static final boolean DEFAULT_TRACKING = false;

    private TimeUnit unit;

    private boolean keeping;
    private boolean tracking;

    // average
    private long sum = 0;
    private long count = 0;

    // exponential moving average
    private final double emaCoef = 0.03;
    private double ema = 0;
    private double emaShown = 0;

    private long trackStart = 0;
    private long lastTrack = 0;

    public Timekeeper(
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
        ema += (duration - ema) * emaCoef;
        if (count % 10 == 0) {
            emaShown = converted((long) ema);
        }
    }

    public TimeUnit unit() {
        return unit;
    }

    public void setUnit(final TimeUnit unit) {
        Objects.requireNonNull(unit);

        this.unit = unit;
    }

    public void keep() {
        keeping = true;
    }

    public void unkeep() {
        keeping = false;
    }

    public boolean keeping() {
        return keeping;
    }

    public void clear() {
        sum = 0;
        count = 0;
    }

    public long count() {
        return count;
    }

    public double sum() {
        return converted(sum);
    }

    public double avg() {
        if (count == 0) {
            return 0;
        }

        return sum() / count;
    }

    public double ema() {
        return emaShown;
    }

    public void track() {
        if (tracking) {
            untrack();
        }

        tracking = true;

        trackStart = System.nanoTime();
    }

    public void untrack() {
        if (!tracking) {
            return;
        }

        final long trackEnd = System.nanoTime();
        lastTrack = trackEnd - trackStart;

        record(lastTrack);

        tracking = false;
    }

    public boolean tracking() {
        return tracking;
    }

    public double time(final Timeable func) {
        Objects.requireNonNull(func);

        final long duration = duration(func);

        record(duration);
        lastTrack = duration;

        return converted(duration);
    }

    public double elapsed() {
        if (!tracking) {
            return 0;
        }

        final long now = System.nanoTime();

        return converted(now - trackStart);
    }

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
