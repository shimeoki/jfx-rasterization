package com.github.shimeoki.jfx.rasterization.test;

public interface Timekeeper {

    // unit

    public TimeUnit unit();

    public void setUnit(final TimeUnit unit);

    // keeping control

    public void keep();

    public void unkeep();

    public boolean keeping();

    public void setKeeping(final boolean keep);

    public void clear();

    // keeped stats

    public long count();

    public double sum();

    public double avg();

    // tracking control

    public void track();

    public void untrack();

    public boolean tracking();

    public void setTracking(final boolean tracking);

    // time

    public double time(final Timeable func);

    public double elapsed();

    public double lastTrack();
}
