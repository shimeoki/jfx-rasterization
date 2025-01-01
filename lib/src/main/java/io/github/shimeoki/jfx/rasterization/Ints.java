package io.github.shimeoki.jfx.rasterization;

/**
 * Class for some operations on integers that are not in the standard
 * Math class.
 *
 * @since 2.0.0
 */
public final class Ints {

    private Ints() {
    }

    /**
     * Returns an integer that is the confined value of {@code x} for two bounds.
     *
     * <p>
     * If {@code x} is less than {@code low}, then {@code low} is returned.
     * <p>
     * If {@code x} is more than {@code high}, then {@code high} is returned.
     * <p>
     * Otherwise {@code x} is returned.
     *
     * @param low  lower bound
     * @param x    target integer to confine
     * @param high upper bound
     *
     * @return confined integer
     *
     * @since 2.0.0
     */
    public static int confined(final int low, final int x, final int high) {
        return Math.min(Math.max(low, x), high);
    }

    /**
     * Gets a minumum integer out of three values.
     *
     * @param v1 first integer
     * @param v2 second integer
     * @param v3 third integer
     *
     * @return minimum integer out of three values
     *
     * @since 2.0.0
     */
    public static int min3(final int v1, final int v2, final int v3) {
        return Math.min(v1, Math.min(v2, v3));
    }

    /**
     * Gets a maximum integer out of three values.
     *
     * @param v1 first integer
     * @param v2 second integer
     * @param v3 third integer
     *
     * @return maximum integer out of three values
     *
     * @since 2.0.0
     */
    public static int max3(final int v1, final int v2, final int v3) {
        return Math.max(v1, Math.max(v2, v3));
    }
}
