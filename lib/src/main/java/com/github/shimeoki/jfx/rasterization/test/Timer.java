package com.github.shimeoki.jfx.rasterization.test;

public class Timer implements Timekeeper {

    public static final TimeUnit DEFAULT_UNIT = TimeUnit.NANOSECOND;

    private TimeUnit unit = DEFAULT_UNIT;

    private boolean keeping = true;
    private boolean tracking = false;

    private long sum = 0;
    private long count = 0;

    private long trackStart = 0;
    private long lastTrack = -1;

    @Override
    public TimeUnit unit() {
        return unit;
    }

    @Override
    public void setUnit(final TimeUnit unit) {
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
    public void setKeeping(final boolean keep) {
        this.keeping = keep;
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
        // exception?
        if (tracking) {
            return;
        }

        tracking = true;

        trackStart = System.nanoTime();
    }

    @Override
    public void untrack() {
        // exception?
        if (!tracking) {
            return;
        }

        final long trackEnd = System.nanoTime();
        lastTrack = trackEnd - trackStart;

        if (keeping) {
            count++;
            sum += lastTrack;
        }

        tracking = false;
    }

    @Override
    public boolean tracking() {
        return tracking;
    }

    @Override
    public void setTracking(final boolean tracking) {
        if (tracking) {
            track();
        } else {
            untrack();
        }
    }

    @Override
    public double time(final Timeable func) {
        // exception?
        if (tracking) {
            return -1;
        }

        final long duration = duration(func);

        if (keeping) {
            count++;
            sum += duration;
        }

        lastTrack = duration;

        return converted(duration);
    }

    @Override
    public double elapsed() {
        // exception?
        if (!tracking) {
            return -1;
        }

        final long now = System.nanoTime();

        return converted(now - trackStart);
    }

    @Override
    public double lastTrack() {
        if (lastTrack == -1) {
            return -1;
        }

        return converted(lastTrack);
    }

    private long duration(final Timeable func) {
        final long start = System.nanoTime();
        func.exec();
        final long end = System.nanoTime();

        return end - start;
    }

    private double converted(final long duration) {
        final double d = (double) duration;

        switch (unit) {
            case NANOSECOND:
                return d;
            case MICROSECOND:
                return d / 1000.0;
            case MILLISECOND:
                return d / 1000000.0;
            case CENTISECOND:
                return d / 10000000.0;
            case DECISECOND:
                return d / 100000000.0;
            case SECOND:
                return d / 1000000000.0;
            case DECASECOND:
                return d / 10000000000.0;
            case HECTOSECOND:
                return d / 100000000000.0;
            case KILOSECOND:
                return d / 1000000000000.0;
            case MINUTE:
                return d / 1000000000.0 / 60.0;
            default:
                // fallback to nanosecond
                return d;
        }
    }
}
