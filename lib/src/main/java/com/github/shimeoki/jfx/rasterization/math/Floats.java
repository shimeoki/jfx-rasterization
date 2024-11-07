package com.github.shimeoki.jfx.rasterization.math;

/**
 * Class for some operations on floats using a fixed epsilon value.
 *
 * @author shimeoki
 * @since 1.0.0
 */
public final class Floats {

    /**
     * Epsilon that is used for comparison operations in this class.
     *
     * <p>
     * {@code EPSILON} is almost equal to zero, so all functions try to get zero and
     * compare it to the epsilon.
     *
     * @since 1.0.0
     */
    public static final float EPSILON = 1E-5f;

    private Floats() {
    }

    /**
     * Returns {@code true}, if the values are equal.
     *
     * <p>
     * Uses epsilon for the comparison.
     *
     * @param v1 first float
     * @param v2 second float
     *
     * @return {@code true}, if the values are equal; {@code false} otherwise
     *
     * @since 1.0.0
     */
    public static boolean equals(final float v1, final float v2) {
        return Math.abs(v1 - v2) < EPSILON;
    }

    /**
     * Returns {@code true}, is left comparand is more or equal to right comparand.
     *
     * <p>
     * Uses epsilon for the comparison. Because of that, comparison is non-strict.
     * <p>
     * Should give the same result as {@link #lessThan(float, float) lessThan} with
     * swapped comparands, if they are not equal. If they are equal, both functions
     * should return {@code true}.
     *
     * @param left  float to be compared
     * @param right float it is compared to
     *
     * @return {@code true}, if {@code left} is more than or equals {@code right};
     *         {@code false} otherwise
     *
     * @since 1.0.0
     *
     * @see #lessThan(float, float)
     */
    public static boolean moreThan(final float left, final float right) {
        return (left - right) > -EPSILON;
    }

    /**
     * Returns {@code true}, is left comparand is less or equal to right comparand.
     *
     * <p>
     * Uses epsilon for the comparison. Because of that, comparison is non-strict.
     * <p>
     * Should give the same result as {@link #moreThan(float, float) moreThan} with
     * swapped comparands, if they are not equal. If they are equal, both functions
     * should return {@code true}.
     *
     * @param left  float to be compared
     * @param right float it is compared to
     *
     * @return {@code true}, if {@code left} is less than or equals {@code right};
     *         {@code false} otherwise
     *
     * @since 1.0.0
     *
     * @see #moreThan(float, float)
     */
    public static boolean lessThan(final float left, final float right) {
        return moreThan(right, left);
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
     * It does not function as shorthand syntax for {@link #lessThan(float, float)}
     * and {@link #moreThan(float, float)}, because they are non-strict comparisons.
     *
     * @param left  float to be compared
     * @param right float it is compared to
     *
     * @return 0, 1 or -1
     *
     * @since 1.0.0
     *
     * @see #moreThan(float, float)
     * @see #lessThan(float, float)
     * @see #equals(float, float)
     */
    public static int compare(final float left, final float right) {
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
     * Returns a float that is the confined value of {@code x} for two bounds.
     *
     * <p>
     * If {@code x} is less than {@code low}, then {@code low} is returned.
     * <p>
     * If {@code x} is more than {@code high}, then {@code high} is returned.
     * <p>
     * Otherwise {@code x} is returned.
     *
     * @param low  lower bound
     * @param x    target float to confine
     * @param high upper bound
     *
     * @return confined float
     *
     * @since 1.0.0
     */
    public static float confined(
            final float low, final float x, final float high) {

        return Math.min(Math.max(low, x), high);
    }

    /**
     * Gets a minumum float out of three values.
     *
     * @param v1 first float
     * @param v2 second float
     * @param v3 third float
     *
     * @return minimum float out of three values
     *
     * @since 1.0.0
     */
    public static float min3(final float v1, final float v2, final float v3) {
        return Math.min(v1, Math.min(v2, v3));
    }

    /**
     * Gets a maximum float out of three values.
     *
     * @param v1 first float
     * @param v2 second float
     * @param v3 third float
     *
     * @return maximum float out of three values
     *
     * @since 1.0.0
     */
    public static float max3(final float v1, final float v2, final float v3) {
        return Math.max(v1, Math.max(v2, v3));
    }
}
