package com.github.shimeoki.jfx.rasterization.test;

public interface Timekeeper {

    // unit

    public TimeUnit unit();

    public void setUnit(final TimeUnit unit);

    // keeping control

    public void keep();

    public void unkeep();

    public boolean keeping();

    // keeped stats

    public long count();

    public double sum();

    public double avg();

    public void clear();

    // tracking control

    public void track();

    public void untrack();

    public boolean tracking();

    // time

    public double time(final Timeable func);

    public double elapsed();

    public double lastTrack();
}
