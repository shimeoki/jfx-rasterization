package io.github.shimeoki.jfx.rasterization.test;

/**
 * An interface representing a timer or a stopwatch to track the execution time
 * of the code and keep tracked stats.
 *
 * <p>
 * Uses {@link TimeUnit} to
 * set the format of the return values.
 * <p>
 * Has two states: {@code keeping} and {@code tracking}.
 * <p>
 * 1. If {@code keeping = true}, then all the recorded execution time should go
 * to the stats. Opposite behaviour otherwise ({@code keeping = false}).
 * <p>
 * 2. If {@code tracking = true}, then the {@code Timekeeper} is tracking the
 * execution time right now.
 * <p>
 * Time tracking can be done in two ways: track single function with
 * {@link #time(Timeable)} and start/stop with
 * {@link #track()}/{@link #untrack()}.
 * <p>
 * After tracking, if {@code keeping} was {@code true}, the stats can be
 * retrieved with {@link #count()}, {@link #sum()} and {@link #avg()}. Also,
 * only the last track stats can be viewed with {@link #lastTrack()}. Stats can
 * be cleared with {@link #clear()}.
 * <p>
 * Example usage:
 *
 * <pre>{@code
 * final Timekeeper keeper = new BasicTimekeeper(); // or any implementation
 * keeper.keep();
 *
 * final double time1 = keeper.time(() -> {
 *     System.out.println("some heavy function");
 * });
 * final double sum1 = keeper.sum(); // = time1
 *
 * keeper.track();
 * System.out.println("another heavy function");
 * keeper.untrack();
 * final double time2 = keeper.lastTrack;
 *
 * final double sum2 = keeper.sum(); // = time1 + time2
 *
 * }</pre>
 *
 * @since 1.0.0
 *
 * @see TimeUnit
 */
public interface Timekeeper {

    /**
     * Gets current {@link TimeUnit} for return values.
     *
     * @return current time unit
     *
     * @since 1.0.0
     */
    public TimeUnit unit();

    /**
     * Sets the {@link TimeUnit} for return values.
     *
     * @param unit time unit
     *
     * @throws NullPointerException if {@code unit} is {@code null}
     *
     * @since 1.0.0
     */
    public void setUnit(final TimeUnit unit);

    /**
     * Sets {@code keeping} to {@code true}.
     *
     * @since 1.0.0
     */
    public void keep();

    /**
     * Sets {@code keeping} to {@code false}.
     *
     * @since 1.0.0
     */
    public void unkeep();

    /**
     * Gets current {@code keeping} boolean value.
     *
     * @return current {@code keeping}
     *
     * @since 1.0.0
     */
    public boolean keeping();

    /**
     * Gets the number of the keeped tracks.
     *
     * @return count of the keeped tracks
     *
     * @since 1.0.0
     */
    public long count();

    /**
     * Gets the sum value of all the keeped tracks in current time unit format.
     *
     * @return sum of all the keeped tracks
     *
     * @since 1.0.0
     */
    public double sum();

    /**
     * Gets the arithmetic average (mean) of the current keeped tracks.
     *
     * @return arithmetic average of the keeped tracks
     *
     * @since 1.0.0
     */
    public double avg();

    public double ema();

    /**
     * Clears current keeped stats.
     *
     * @since 1.0.0
     */
    public void clear();

    /**
     * Sets {@code tracking} to {@code true}.
     *
     * @since 1.0.0
     */
    public void track();

    /**
     * Sets {@code tracking} to {@code false}.
     *
     * @since 1.0.0
     */
    public void untrack();

    /**
     * Gets current {@code tracking} boolean value.
     *
     * @return current {@code tracking}
     *
     * @since 1.0.0
     */
    public boolean tracking();

    /**
     * Returns the execution time for {@code func} and keeps the result if
     * {@code keeping} is {@code true}.
     *
     * @param func timeable function
     *
     * @return execution time of the {@code func}
     *
     * @throws NullPointerException if {@code func} is {@code null}
     *
     * @since 1.0.0
     *
     * @see Timekeeper
     */
    public double time(final Timeable func);

    /**
     * Returns the current elapsed time after the last {@link #track()} call.
     *
     * <p>
     * {@link #tracking()} should be equal to {@code true}. If not, the method
     * should return 0.
     *
     * @return elapsed time from the last track call or 0
     *
     * @since 1.0.0
     */
    public double elapsed();

    /**
     * Returns the recorded time of the last track.
     *
     * <p>
     * If no tracks were recorded yet or {@link #clear()} was previously called, the
     * method should return 0.
     *
     * @return last track time or 0
     *
     * @since 1.0.0
     */
    public double lastTrack();
}
