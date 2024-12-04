package io.github.shimeoki.jfx.rasterization.math;

/**
 * Class for some operations on doubles using a fixed epsilon value.
 *
 * @since 1.0.0
 */
public final class Doubles {

    /**
     * Epsilon that is used for comparison operations in this class.
     *
     * <p>
     * {@code EPSILON} is almost equal to zero, so all functions try to get zero and
     * compare it to the epsilon.
     *
     * @since 1.0.0
     */
    public static final double EPSILON = 0.000000000001;

    private Doubles() {
    }

    /**
     * Returns {@code true}, if the values are equal.
     *
     * <p>
     * Uses epsilon for the comparison.
     *
     * @param v1 first double
     * @param v2 second double
     *
     * @return {@code true}, if the values are equal; {@code false} otherwise
     *
     * @since 1.0.0
     */
    public static boolean equals(final double v1, final double v2) {
        return Math.abs(v1 - v2) < EPSILON;
    }

    /**
     * Returns {@code true}, is left comparand is more or equal to right comparand.
     *
     * <p>
     * Uses epsilon for the comparison. Because of that, comparison is non-strict.
     * <p>
     * Should give the same result as {@link #lessThan(double, double) lessThan}
     * with swapped comparands, if they are not equal. If they are equal, both
     * functions should return {@code true}.
     *
     * @param left  double to be compared
     * @param right double it is compared to
     *
     * @return {@code true}, if {@code left} is more than or equals {@code right};
     *         {@code false} otherwise
     *
     * @since 1.0.0
     *
     * @see #lessThan(double, double)
     */
    public static boolean moreThan(final double left, final double right) {
        return (left - right) > -EPSILON;
    }

    /**
     * Returns {@code true}, is left comparand is less or equal to right comparand.
     *
     * <p>
     * Uses epsilon for the comparison. Because of that, comparison is non-strict.
     * <p>
     * Should give the same result as {@link #moreThan(double, double) moreThan}
     * with swapped comparands, if they are not equal. If they are equal, both
     * functions should return {@code true}.
     *
     * @param left  double to be compared
     * @param right double it is compared to
     *
     * @return {@code true}, if {@code left} is less than or equals {@code right};
     *         {@code false} otherwise
     *
     * @since 1.0.0
     *
     * @see #moreThan(double, double)
     */
    public static boolean lessThan(final double left, final double right) {
        return (left - right) < EPSILON;
    }

    /**
     * Gets an integer, that indicates the result of the comparison.
     *
     * <p>
     * If both values are equal, then 0 is returned.
     * <p>
     * If {@code left} is more than {@code right}, then 1 is returned.
     * <p>
     * Otherwise, -1 is returned.
     * <p>
     * It does not function as shorthand syntax for
     * {@link #lessThan(double, double)} and {@link #moreThan(double, double)},
     * because they are non-strict comparisons.
     *
     * @param left  double to be compared
     * @param right double it is compared to
     *
     * @return 0, 1 or -1
     *
     * @since 1.0.0
     *
     * @see #moreThan(double, double)
     * @see #lessThan(double, double)
     * @see #equals(double, double)
     */
    public static int compare(final double left, final double right) {
        if (equals(left, right)) {
            return 0;
        }

        if (left > right) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Returns a double that is the confined value of {@code x} for two bounds.
     *
     * <p>
     * If {@code x} is less than {@code low}, then {@code low} is returned.
     * <p>
     * If {@code x} is more than {@code high}, then {@code high} is returned.
     * <p>
     * Otherwise {@code x} is returned.
     *
     * @param low  lower bound
     * @param x    target double to confine
     * @param high upper bound
     *
     * @return confined double
     *
     * @since 1.0.0
     */
    public static double confined(
            final double low, final double x, final double high) {

        return Math.min(Math.max(low, x), high);
    }
}
